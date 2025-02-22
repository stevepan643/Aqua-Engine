package com.steve.manager;

import com.steve.platform.Window;
import com.steve.util.LogUtil;
import org.slf4j.Logger;

public abstract class Application {
  private Window window;

  public final Logger LOGGER = LogUtil.getLogger();

  protected void config(Configuration conf) {}

  protected void preRun() {}

  protected void process() {}

  private void run() {
    while (!window.isShouldClose()) {
      window.clearBuffer();
      process();
      window.renderBuffer();
    }
  }

  public static void launch(final Application app) {
    initialize(app);
    app.preRun();
    app.run();
  }

  private static void initialize(final Application app) {
    Configuration conf = new Configuration();
    app.config(conf);
    app.window = new Window(conf);
    app.window.setBgColor(conf.bgR, conf.bgG, conf.bgB);

    ShaderManager.init();
    ObjectManager.init();
  }
}
