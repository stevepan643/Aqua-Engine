/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import com.steve.platform.Window;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

public class ImGuiTest extends Application {
  Window window;

  @Override
  protected void configure(Configuration config) {
    config.setTitle("Dear ImGui is Awesome!");
    // config.setFullScreen(true);
  }

  private float[] value = new float[] {0.0f};
  private ImBoolean ib = new ImBoolean();

  @Override
  public void process() {
    ImGui.styleColorsLight();
    if (ImGui.begin("Hello ImGUI1", ImGuiWindowFlags.AlwaysAutoResize)) {
      ImGui.text("Hello, World!");
      ImGui.button("Test", 0, 0);
    }
    ImGui.end();
    ImGui.styleColorsDark();
    if (ImGui.begin("Hello ImGUI2", ImGuiWindowFlags.AlwaysAutoResize)) {
      ImGui.text("Hello, World!");
      ImGui.button("Test", 0, 0);
      ImGui.checkbox("null", ib);
      ImGui.sameLine();
      ImGui.text("Value " + ib);
      ImGui.sliderFloat("float", value, 0.0f, 10.0f);
      ImGui.sameLine();
      ImGui.text("Value " + value[0]);
    }
    ImGui.end();
    processInput(window.get());
  }

  @Override
  protected void preRun() {
    window = new Window(handle);
  }

  public static void main(String[] args) {
    launch(new ImGuiTest());
  }

  private boolean lastState = false;

  public void processInput(long window) {
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
      glfwSetWindowShouldClose(window, true);
    }
    if (glfwGetKey(window, GLFW_KEY_F11) == GLFW_PRESS) {
      if (!lastState) {
        lastState = true;
        this.window.switchFullScreen();
      }
    } else {
      lastState = false;
    }
  }
}
