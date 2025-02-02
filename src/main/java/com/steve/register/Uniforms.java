package com.steve.register;

import java.util.HashMap;

import com.steve.graphic.Uniform;

public class Uniforms {

    @SuppressWarnings("rawtypes")
    private static HashMap<String, Uniform> uniforms = new HashMap<>();

    protected static void addUniform(String name, Uniform<?> uniform) {
        uniforms.put(name, uniform);
    }

    @SuppressWarnings("unchecked")
    public static <T> Uniform<T> get(String name) {
        return uniforms.get(name);
    }
}
