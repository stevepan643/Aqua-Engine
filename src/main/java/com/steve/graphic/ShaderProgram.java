/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 package com.steve.graphic;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;

import org.slf4j.Logger;

import com.steve.util.LogUtil;

/**
 * Shader program.
 * 
 * @author Steve Pan
 * @version 1.1
 */
public class ShaderProgram {
    private final int shaderProgram;

    private HashMap<String, Shader> shaders = new HashMap<>();
    
    private final Logger LOGGER = LogUtil.getLogger();

    /**
     * Create a program.
     */
    public ShaderProgram() {
        shaderProgram = glCreateProgram();
    }

    /**
     * Attach a shader to this program.
     * 
     * @param name   Shader's name. Used this to detach.
     * @param shader Which shader will adding.
     * @since 1.0
     */
    public void addShader(String name, Shader shader) {
        shaders.put(name, shader);

        glAttachShader(shaderProgram, shaders.get(name).get());
    }

    /**
     * Link all of shaders.
     * 
     * @since 1.0
     */
    public void link() {
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == NULL) {
            LOGGER.error("Failed to Link Shader Program");
        }
    }

    /**
     * Use this shader program.
     * 
     * @since 1.0
     */
    public void use() {
        glUseProgram(shaderProgram);
    }

    public void unused() {
        glUseProgram(0);
    }

    /**
     * Retrieves the shader program identifier.
     *
     * @return the identifier of the shader program.
     * @since 1.0
     */
    public int get() {
        return shaderProgram;
    }

    public void addUniform(Uniform<?> uniform) {
        uniform.setLocation(
                glGetUniformLocation(shaderProgram, uniform.getName()));
        uniform.update();
    }

    public void addUniforms(Uniform<?>... uniforms) {
        for (Uniform<?> uniform : uniforms) {
            addUniform(uniform);
        }
    }

    /**
     * Delete program, and release memory.
     * 
     * @since 1.0
     */
    public void cleanup() {
        glDeleteProgram(shaderProgram);
    }
}
