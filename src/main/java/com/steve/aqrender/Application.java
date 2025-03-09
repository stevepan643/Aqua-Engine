package com.steve.aqrender;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import com.steve.aqrender.config.WindowConfiguration;
import com.steve.aqrender.platform.Window;
import org.jetbrains.annotations.NotNull;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * 继承了{@link Window}, 程序一般形式, 提供各运行时函数。
 *
 * <table>
 *     <tr>{@link #config(WindowConfiguration)}</tr>
 *     <tr>{@link #beforeLoop()}</tr>
 *     <tr>{@link Window#beforeProcess()}</tr>
 *     <tr>{@link Window#process()}</tr>
 *     <tr>{@link Window#afterProcess()}</tr>
 *     <tr>{@link #afterLoop()}</tr>
 * </table>
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 8, 2025
 */
public abstract class Application extends Window {

  /**
   * 设置{@code configuration}。
   *
   * @param configuration 配置
   * @since March 8, 2025
   */
  protected void config(WindowConfiguration configuration) {}

  /**
   * 将在循环开始前执行。
   *
   * @since March 8, 2025
   */
  protected void beforeLoop() {}

  private void gameLoop() {
    while (!glfwWindowShouldClose(handle)) {
      runFrame();
    }
    destroyWindow();
  }

  /**
   * 循环结束后。
   *
   * @since March 8, 2025
   */
  protected void afterLoop() {}

  /**
   * 初始化{@code app}, 同时创建窗口。
   *
   * @param app 继承了{@link Application}的类
   * @since March 8, 2025
   */
  public static void launch(final Application app) {
    initialize(app);
    app.beforeLoop();
    app.gameLoop();
    app.afterLoop();
  }

  // 会调用config用以设置窗口并创建窗口。
  private static void initialize(@NotNull final Application app) {
    SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
    WindowConfiguration conf = new WindowConfiguration();
    // 调整设置
    app.config(conf);
    app.init(conf);
  }
}
