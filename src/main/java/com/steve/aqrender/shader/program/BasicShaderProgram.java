package com.steve.aqrender.shader.program;

import com.steve.aqrender.shader.Shaders;
import com.steve.aqrender.util.Identifier;

/**
 * 基础渲染程序, 由基础着色器组成。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
public class BasicShaderProgram extends ShaderProgram {
  public BasicShaderProgram(Identifier id) {
    super(Shaders.BASIC_VERTEX_SHADER, Shaders.BASIC_FRAGMENT_SHADER, id);
  }
}
