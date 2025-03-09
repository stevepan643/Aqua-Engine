package com.steve.aqrender.shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import com.steve.aqrender.util.Identifier;

/**
 * 顶点着色器。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
public class VertexShader extends Shader {

  /**
   * 创建顶点着色器并编译。
   *
   * @param resource 着色器文件
   * @since March 9, 2025
   */
  public VertexShader(Identifier resource) {
    super(GL_VERTEX_SHADER, resource);
  }
}
