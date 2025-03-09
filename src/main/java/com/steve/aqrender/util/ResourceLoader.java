package com.steve.aqrender.util;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * 资源加载器。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
@Slf4j
public class ResourceLoader {
  /** 默认根目录。 */
  public static final String DEFAULT_ROOT = "src/main/resources/";

  @Setter public static String ROOT = DEFAULT_ROOT;

  /**
   * 加载着色器。
   *
   * @param identifier 着色器文件
   * @return 着色器内容
   * @since March 9, 2025
   */
  public static String shader(@NotNull Identifier identifier, int type) {
    String ext =
        switch (type) {
          case GL_VERTEX_SHADER -> ".vert";
          case GL_FRAGMENT_SHADER -> ".frag";
          default -> ".glsl";
        };
    try {
      return Files.readString(
          Paths.get(ROOT + identifier.getNamespace() + "/" + identifier.getId() + ext));
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.error("Read Failed");
    return "";
  }
}
