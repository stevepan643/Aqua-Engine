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

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_MULT;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glStencilOp;
import static org.lwjgl.opengl.GL11.glViewport;

import com.steve.graphic.*;
import com.steve.manager.ObjectManager;
import com.steve.manager.ShaderManager;
import com.steve.platform.Window;
import com.steve.util.LogUtil;
import com.steve.util.ObjUtil;
import org.joml.Matrix4f;
import org.slf4j.Logger;

public class Main2 {

  public static int width = 800;
  public static int height = 600;

  public static boolean isChanged = false;

  public static Camera camera = new Camera();
  public static Window window;

  public static Uniform<Matrix4f> projUniform;

  public static float fov = 60.0f;

  private static final Logger LOGGER = LogUtil.getLogger();

  public static void main(String[] args) {

    window = new Window(width, height, "Test Game");

    // If window's size is changing, callback framebuffer_size_callback.
    glfwSetFramebufferSizeCallback(
        window.get(), (w, width, height) -> framebuffer_size_callback(w, width, height));

    // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glEnable(GL_DEPTH_TEST);
    glfwSwapInterval(0);
    glEnable(GL_MULT);
    glEnable(GL_CULL_FACE);
    glEnable(GL_STENCIL_TEST);
    glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

    ShaderManager.init();
    ObjectManager.init();

    ObjectManager.addObject(ObjUtil.loadModel("src/main/resources/model/cube.obj"));

    glfwSetInputMode(window.get(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    glfwSetCursorPosCallback(window.get(), Main2::mouse_callback);
    glfwSetScrollCallback(window.get(), Main2::scroll_callback);

    double lastTime = glfwGetTime();
    double lastFrameTime = lastTime;
    int frameCount = 0;
    float fps = 0;

    // ShaderManager.getProj()
    //     .getValue()
    //     .setPerspective((float) Math.toRadians(60.0f), (float) width / height, 0.01f, 100f);
    // ShaderManager.getProj().update();

    // System.gc();
    while (!window.isShouldClose()) {
      glfwPollEvents();

      double currentTime = glfwGetTime();
      frameCount++;

      double deltaTime = currentTime - lastTime;
      lastTime = currentTime;

      if (currentTime - lastFrameTime >= 1.0) {
        fps = (float) (frameCount / (currentTime - lastFrameTime));
        frameCount = 0;
        lastFrameTime = currentTime;
        System.gc();
      }

      window.setTile("Test Game - FPS: ", String.format("%.2f", fps));

      processInput(window.get(), (float) deltaTime);

      ShaderManager.getView().setValue(camera.getViewMatrix());
      ShaderManager.getView().update();

      glClearColor(0f, 0f, 0f, 1.0f);
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

      if (isChanged) {
        ShaderManager.getProj()
            .getValue()
            .setPerspective((float) Math.toRadians(60.0f), (float) width / height, 0.01f, 100f);
        ShaderManager.getProj().update();
        isChanged = false;
      }

      ObjectManager.render();

      window.swapBuffers();
    }

    cleanup();
  }

  public static void framebuffer_size_callback(long window, int width, int height) {
    glViewport(0, 0, width, height);
    Main2.width = width;
    Main2.height = height;
    isChanged = true;
  }

  private static boolean lastState = false;

  public static void processInput(long window, float deltaTime) {
    float cameraSpeed = deltaTime;

    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
      glfwSetWindowShouldClose(window, true);
    }
    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
      camera.moveForward(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
      camera.moveBackward(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
      camera.moveLeft(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
      camera.moveRight(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
      camera.moveUp(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
      camera.moveDown(cameraSpeed);
    }
    if (glfwGetKey(window, GLFW_KEY_F11) == GLFW_PRESS) {
      if (!lastState) {
        lastState = true;
        Main2.window.switchFullScreen();
      }
    } else {
      lastState = false;
    }
    camera.update();
  }

  private static float lastX = width / 2;
  private static float lastY = height / 2;
  private static float yaw = -90.0f;
  private static float pitch = 0.0f;
  private static boolean firstMouse = true;
  private static float sensitivity = 0.1f;

  public static void mouse_callback(long window, double xpos, double ypos) {
    if (firstMouse) {
      lastX = (float) xpos;
      lastY = (float) ypos;
      firstMouse = false;
    }

    float xoffset = (float) (xpos - lastX);
    float yoffset = (float) (lastY - ypos);
    lastX = (float) xpos;
    lastY = (float) ypos;

    xoffset *= sensitivity;
    yoffset *= sensitivity;

    yaw += xoffset;
    pitch += yoffset;

    if (pitch > 89.0f) {
      pitch = 89.0f;
    }
    if (pitch < -89.0f) {
      pitch = -89.0f;
    }

    yaw = yaw % 360.0f;
    if (yaw < 0.0f) {
      yaw += 360.0f;
    }

    camera.lookTo(yaw, pitch);
  }

  public static void scroll_callback(long window, double xoffset, double yoffset) {
    if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
      sensitivity += yoffset / 100.0f;
      if (sensitivity < 0.01f) {
        sensitivity = 0.01f;
      } else if (sensitivity > 1.0f) {
        sensitivity = 1.0f;
      }
      LOGGER.debug("sensitivity: " + sensitivity);
    } else {
      fov -= yoffset;
      if (fov < 30.0f) {
        fov = 30.0f;
      }
      if (fov > 100.0f) {
        fov = 100.0f;
      }
      LOGGER.debug("fov: " + fov);

      ShaderManager.getProj()
          .getValue()
          .setPerspective((float) Math.toRadians(fov), (float) width / height, 0.01f, 100f);
      ShaderManager.getProj().update();
    }
  }

  public static void cleanup() {
    glfwTerminate();
  }
}
