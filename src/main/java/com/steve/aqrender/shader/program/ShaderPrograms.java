package com.steve.aqrender.shader.program;

import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.registry.Registry;
import com.steve.aqrender.util.Identifier;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/**
 * 提前准备好的渲染程序, 可以用于测试等。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
public class ShaderPrograms {
  public static final ShaderProgram BASIC_SHADER =
      register(Identifier.of("program", "basic_shader"), BasicShaderProgram::new);

  public static ShaderProgram register(
      Identifier identifier, @NotNull Function<Identifier, ShaderProgram> function) {
    ShaderProgram shaderProgram = function.apply(identifier);
    return Registry.register(Registries.PROGRAM, identifier, shaderProgram);
  }
}
