package com.steve.aqrender.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

import com.steve.aqrender.util.Identifier;

/**
 * 片元着色器。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
public class FragmentShader extends Shader {

  /**
   * 创建片元着色器并编译。
   *
   * @param resource 着色器文件
   * @since March 9, 2025
   */
  public FragmentShader(Identifier resource) {
    super(GL_FRAGMENT_SHADER, resource);
  }
}
