import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_MULT;
import static org.lwjgl.opengl.GL11.GL_REPLACE;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glStencilOp;
import static org.lwjgl.opengl.GL11.glViewport;

import com.steve.graphic.Camera;
import com.steve.manager.ObjectManager;
import com.steve.manager.ShaderManager;
import com.steve.platform.Window;
import com.steve.util.ObjUtil;
import imgui.app.Application;
import imgui.app.Configuration;

public class Main3 extends Application {

  public static Window window;
  public static Camera camera;

  public static int width = 800;
  public static int height = 600;

  public static boolean isChanged = false;

  @Override
  protected void configure(Configuration config) {
    config.setWidth(width);
    config.setHeight(height);
  }

  @Override
  protected void preRun() {
    window = new Window(handle);
    camera = new Camera();

    glfwSetFramebufferSizeCallback(
        window.get(), (w, width, height) -> framebuffer_size_callback(w, width, height));

    glEnable(GL_DEPTH_TEST);
    glfwSwapInterval(0);
    glEnable(GL_MULT);
    glEnable(GL_CULL_FACE);
    glEnable(GL_STENCIL_TEST);
    glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

    ShaderManager.init();
    ObjectManager.init();

    ObjectManager.addObject(ObjUtil.loadModel("src/Main3/resources/model/cube.obj"));

    glfwSetInputMode(window.get(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    glfwSetCursorPosCallback(window.get(), Main3::mouse_callback);

    ShaderManager.getProj()
        .getValue()
        .setPerspective((float) Math.toRadians(60), (float) width / height, 0.01f, 100f);
    ShaderManager.getProj().update();
  }

  double lastTime;
  private static float[] fov = new float[] {60.0f};

  @Override
  public void process() {

    double currentTime = glfwGetTime();

    double deltaTime = currentTime - lastTime;
    lastTime = currentTime;

    processInput(handle, (float) deltaTime);

    ShaderManager.getView().setValue(camera.getViewMatrix());
    ShaderManager.getView().update();

    if (isChanged) {
      ShaderManager.getProj()
          .getValue()
          .setPerspective((float) Math.toRadians(fov[0]), (float) width / height, 0.01f, 100f);
      ShaderManager.getProj().update();
      isChanged = false;
    }

    ObjectManager.render();
  }

  public static void main(String[] args) {
    launch(new Main3());
  }

  static boolean lastState = false;
  static boolean isVisible = false;

  public static void processInput(long window, float deltaTime) {
    float cameraSpeed = deltaTime;

    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
      if (!lastState) {
        lastState = true;
        if (isVisible) {
          glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
          glfwSetCursorPosCallback(window, Main3::mouse_callback);
        } else {
          glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
          glfwSetCursorPosCallback(window, null);
        }
        isVisible = !isVisible;
      }
    } else {
      lastState = false;
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
    camera.update();
  }

  private static float lastX = 800 / 2;
  private static float lastY = 600 / 2;
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

  public static void framebuffer_size_callback(long window, int width, int height) {
    glViewport(0, 0, width, height);
    Main3.width = width;
    Main3.height = height;
    isChanged = true;
  }
}
