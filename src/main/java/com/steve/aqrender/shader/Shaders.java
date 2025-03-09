package com.steve.aqrender.shader;

import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.registry.Registry;
import com.steve.aqrender.util.Identifier;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * 提前准备好的基础着色器, 可以用于测试等。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
public class Shaders {
  public static final Shader BASIC_VERTEX_SHADER =
      register(Identifier.of("shader", "basic_vertex_shader"), VertexShader::new);
  public static final Shader BASIC_FRAGMENT_SHADER =
      register(Identifier.of("shader", "basic_fragment_shader"), FragmentShader::new);

  public static Shader register(
      Identifier identifier, @NotNull Function<Identifier, Shader> function) {
    Shader shader_ = function.apply(identifier);
    return Registry.register(Registries.SHADER, identifier, shader_);
  }
}
