package com.steve.aqrender.registry;

import com.steve.aqrender.graphic.mesh.Mesh;
import com.steve.aqrender.shader.Shader;
import com.steve.aqrender.shader.program.ShaderProgram;
import com.steve.aqrender.shader.program.Texture;

/**
 * 所有注册表。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 8, 2025
 */
public class Registries {
  public static Registry<Shader> SHADER = create(new SimpleRegistry<>());
  public static Registry<ShaderProgram> PROGRAM = create(new SimpleRegistry<>());
  public static Registry<Mesh> MESH = create(new SimpleRegistry<>());
  public static Registry<Texture> TEXTURE = create(new SimpleRegistry<>());

  private static <T, R extends Registry<T>> R create(R registry) {
    return registry;
  }
}
