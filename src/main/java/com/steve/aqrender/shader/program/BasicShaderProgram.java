package com.steve.aqrender.shader.program;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;

import com.steve.aqrender.graphic.mesh.Mesh;
import com.steve.aqrender.shader.Shaders;
import com.steve.aqrender.util.Identifier;
import lombok.extern.slf4j.Slf4j;

/**
 * Basic shader program that supports uniforms.
 *
 * @since March 9, 2025
 */
@Slf4j
public class BasicShaderProgram extends ShaderProgram {

  public BasicShaderProgram(Identifier id) {
    super(Shaders.BASIC_VERTEX_SHADER, Shaders.BASIC_FRAGMENT_SHADER, id);
  }

  /**
   * 这非常特殊 为了能适应多种长度的网格。
   *
   * @param name 没用
   * @param value 请传入{@link Mesh#type}的数值
   */
  @Override
  public void setUniform(String name, Object value) {
    if (!(value instanceof Integer)) throw new IllegalArgumentException("Value must be an integer");
    int type = (Integer) value;
    if (type > Mesh.ALL) throw new IndexOutOfBoundsException("Value must less than " + Mesh.ALL);

    glUniform1i(glGetUniformLocation(handle, "hasVertex"), (type & Mesh.VERTEX));
    glUniform1i(glGetUniformLocation(handle, "hasNormal"), (type & Mesh.NORMAL) >> 1);
    glUniform1i(glGetUniformLocation(handle, "hasText"), (type & Mesh.TEXTURE) >> 2);
    glUniform1i(glGetUniformLocation(handle, "hasColor"), (type & Mesh.COLOR) >> 3);
  }
}
