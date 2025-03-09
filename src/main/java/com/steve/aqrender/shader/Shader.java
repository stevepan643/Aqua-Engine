package com.steve.aqrender.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.steve.aqrender.util.Identifier;
import com.steve.aqrender.util.ResourceLoader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 着色器。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
@Slf4j
@Getter
public abstract class Shader {
  protected int type;
  protected Identifier resource;
  protected int handle;

  /**
   * 创建着色器并编译。
   *
   * @param type 着色器类型 {@link org.lwjgl.opengl.GL20#GL_VERTEX_SHADER}或{@link
   *     org.lwjgl.opengl.GL20#GL_FRAGMENT_SHADER}
   * @param resource 着色器文件
   * @since March 9, 2025
   */
  public Shader(int type, Identifier resource) {
    this.type = type;
    this.resource = resource;

    readAndCompile();
  }

  // 读取着色器文件并编译。
  private void readAndCompile() {
    this.handle = glCreateShader(this.type);

    glShaderSource(this.handle, ResourceLoader.shader(resource, type));
    glCompileShader(handle);

    if (glGetShaderi(this.handle, GL_COMPILE_STATUS) == NULL) {
      log.error("Failed to Compile {}", this.resource);
      String info = glGetShaderInfoLog(handle);
      log.error("Error information:");
      for (String line : info.split("\n")) {
        log.error(line);
      }
    }
  }
}
