import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import com.steve.manager.GameObjectManager;
import com.steve.manager.ShaderManager;
import com.steve.platform.Window;
import com.steve.util.ObjUtil;
import imgui.app.Application;
import imgui.app.Configuration;

public class Main extends Application {

  Window window;

  @Override
  protected void configure(Configuration config) {}

  @Override
  protected void preRun() {
    window = new Window(handle);
    ShaderManager.init();
    GameObjectManager.init();

    GameObjectManager.addObject(ObjUtil.loadModel("src/main/resources/model/objTest.obj"));
  }

  @Override
  public void process() {
    processInput(handle);

    GameObjectManager.render();
  }

  public static void main(String[] args) {
    launch(new Main());
  }

  public static void processInput(long window) {

    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) glfwSetWindowShouldClose(window, true);
  }
}
