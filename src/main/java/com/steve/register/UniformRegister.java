package com.steve.register;

import com.steve.graphic.ShaderProgram;
import com.steve.graphic.Uniform;

public class UniformRegister {
    public static ShaderProgram shaderProgram;

    public static void init(ShaderProgram shaderProgram) {
        UniformRegister.shaderProgram = shaderProgram;
    }

    public static <T> void registerUniform(String name, T value) {
        Uniforms.addUniform(name, new Uniform<T>(name, value));
        shaderProgram.addUniform(Uniforms.get(name));
    }


}
