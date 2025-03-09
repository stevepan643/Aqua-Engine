package com.steve.aqrender.config;

import com.steve.aqrender.platform.Window;
import lombok.Getter;
import lombok.Setter;

/**
 * 窗口配置，仅在创建窗口时被使用。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 8, 2025
 */
@Setter
@Getter
public class WindowConfiguration {
  private int width;
  private int height;
  private String title;
  private Window.BackgroundColor bgColor;

  /**
   * @param width 窗口长度
   * @param height 窗口高度
   * @param title 窗口名
   * @param bgColor 窗口背景颜色
   * @since March 8, 2025
   */
  public WindowConfiguration(int width, int height, String title, Window.BackgroundColor bgColor) {
    this.width = width;
    this.height = height;
    this.title = title;
    this.bgColor = bgColor;
  }

  /**
   * 默认值。(800x900, Game, {63, 63, 63})
   *
   * @since March 8, 2025
   */
  public WindowConfiguration() {
    this(800, 600, "Game", new Window.BackgroundColor(63, 63, 63));
  }
}
