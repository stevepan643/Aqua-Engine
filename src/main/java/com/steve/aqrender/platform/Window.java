package com.steve.aqrender.platform;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.steve.aqrender.config.WindowConfiguration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 初始化{@code GLFW}, 创建窗口。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 8, 2025
 */
@Slf4j
public class Window {
  protected long handle;

  private int width;
  private int height;
  private String title;
  private BackgroundColor bgColor;

  private long lastTime;
  @Getter private float deltaTime; // ns
  @Getter private int fps;

  protected void init(@NotNull WindowConfiguration configuration) {
    initGLFW();
    readConfiguration(configuration);
    createWindow();
  }

  // 初始化GLFW。
  private void initGLFW() {
    log.info("Initializing GLFW");
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    // If run on macOS, need this.
    if (isMacOs()) {
      log.info("Running on macOS");
      glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    }
    glfwWindowHint(GLFW_SAMPLES, 4);
  }

  // 读取configuration。
  private void readConfiguration(@NotNull final WindowConfiguration configuration) {
    this.width = configuration.getWidth();
    this.height = configuration.getHeight();
    this.title = configuration.getTitle();
    this.bgColor = configuration.getBgColor();
  }

  // 创建窗口。
  private void createWindow() {
    log.info("Creating window");
    handle = glfwCreateWindow(width, height, title, NULL, NULL);

    // 窗口创建失败。
    if (handle == NULL) {
      log.error("Failed to create window");
      glfwTerminate();
      System.exit(-1);
    }

    glfwMakeContextCurrent(handle);
    createCapabilities();
    glfwSetFramebufferSizeCallback(handle, this::framebufferSizeCallback);
  }

  protected void runFrame() {
    calculate();
    clearBuffer();
    beforeProcess();
    process();
    afterProcess();
    renderBuffer();
  }

  private void calculate() {
    long now = System.nanoTime();
    deltaTime = (now - lastTime);
    lastTime = now;
    fps = (int) (1 / getDeltaTime() * 1000_000_000.0);
  }

  // 清除Buffer
  private void clearBuffer() {
    glClearColor(bgColor.rNorm(), bgColor.gNorm(), bgColor.bNorm(), 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  // 交换Buffer
  private void renderBuffer() {
    glfwSwapBuffers(handle);
    glfwPollEvents();
  }

  /**
   * process()前被调用。
   *
   * @since March 8, 2025
   */
  protected void beforeProcess() {}

  /**
   * 处理函数, 渲染前被调用
   *
   * @since March 8, 2025
   */
  protected void process() {}

  /**
   * process()后被调用。
   *
   * @since March 8, 2025
   */
  protected void afterProcess() {}

  // 帧缓冲区大小回调。
  private void framebufferSizeCallback(long handle, int width, int height) {
    glViewport(0, 0, width, height);
    this.width = width;
    this.height = height;
    onResize(width, height);
  }

  /**
   * 帧缓冲区大小改变时会被调用
   *
   * @param width 窗口宽度
   * @param height 窗口高度
   * @since March 8, 2025
   */
  protected void onResize(final int width, final int height) {}

  /**
   * 销毁窗口。
   *
   * @since March 8, 2025
   */
  public void destroyWindow() {
    log.info("Destroying window");
    glfwDestroyWindow(handle);
    glfwTerminate();
  }

  // 获取是否为MacOS, 是则返回true, 不是反之。
  private static boolean isMacOs() {
    String osName = System.getProperty("os.title");
    return osName != null && osName.startsWith("Mac");
  }

  /**
   * 显示背景色。
   *
   * @param r 红色[0, 255]
   * @param g 绿色[0, 255]
   * @param b 蓝色[0, 255]
   * @since March 8, 2025
   */
  public record BackgroundColor(int r, int g, int b) {

    /**
     * @return 红色[0, 1]
     */
    float rNorm() {
      return r / 255.0f;
    }

    /**
     * @return 绿色[0, 1]
     */
    float gNorm() {
      return g / 255.0f;
    }

    /**
     * @return 蓝色[0, 1]
     */
    float bNorm() {
      return b / 255.0f;
    }
  }
}
