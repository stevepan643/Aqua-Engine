package com.steve.aqrender.shader.program;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.shader.Shader;
import com.steve.aqrender.util.Identifier;
import lombok.extern.slf4j.Slf4j;

/**
 * 渲染程序, 由顶点, 片元着色器组成。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
@Slf4j
public abstract class ShaderProgram {
  protected final Shader VERTEX_SHADER;
  protected final Shader FRAGMENT_SHADER;

  protected Identifier id;

  protected int handle;

  /**
   * 创建渲染程序。
   *
   * @param vertexShader 顶点着色器
   * @param fragmentShader 片元着色器
   * @param id 着色器id
   * @since March 9, 2025
   */
  public ShaderProgram(Shader vertexShader, Shader fragmentShader, Identifier id) {
    VERTEX_SHADER = vertexShader;
    FRAGMENT_SHADER = fragmentShader;
    this.id = id;
    handle = glCreateProgram();

    link();
  }

  /**
   * 获取顶点着色器id。
   *
   * @return 顶点着色器id。
   * @since March 9, 2025
   */
  public Identifier getVertexShaderID() {
    return Registries.SHADER.getID(VERTEX_SHADER);
  }

  /**
   * 获取片元着色器id。
   *
   * @return 片元着色器id。
   * @since March 9, 2025
   */
  public Identifier getFragmentShaderID() {
    return Registries.SHADER.getID(FRAGMENT_SHADER);
  }

  /**
   * 设置uniform变量。 <font color="Red">(使用前确保使用的渲染程序已覆盖该方法, 同时请不要在复写时调用super(), 除非你想让程序崩溃)</font>
   *
   * @param name uniform变量的名称
   * @param value uniform变量的值
   * @throws UnsupportedOperationException 如果渲染程序未覆盖任然使用
   * @since March 9, 2025
   */
  public void setUniform(String name, Object value) {
    throw new UnsupportedOperationException(
        String.format("This shader program(%s) does not support uniforms.", id));
  }

  // 链接着色器。
  private void link() {
    glAttachShader(handle, VERTEX_SHADER.getHandle());
    glAttachShader(handle, FRAGMENT_SHADER.getHandle());

    glLinkProgram(handle);

    if (glGetProgrami(handle, GL_LINK_STATUS) == NULL) {
      log.error("Failed to Link Shader Program");
      String info = glGetProgramInfoLog(handle);
      for (String line : info.split("\n")) {
        log.error(line);
      }
    }
  }

  /**
   * 使用该渲染程序。
   *
   * @since March 9, 2025
   */
  public void use() {
    glUseProgram(handle);
  }
}
