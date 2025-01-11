package com.steve.graphic;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;

/**
 * Shader program.
 * 
 * @author Steve Pan
 * @version 1.0
 */
public class ShaderProgram {
    private final int shaderProgram;

    private HashMap<String, Shader> shaders = new HashMap<>();

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
     * Detach a shader.
     * 
     * @param name Which shader need to detach.
     * @since 1.0
     */
    public void delShader(String name) {
        glDetachShader(shaderProgram, shaders.get(name).get());

        shaders.remove(name);
    }

    /**
     * Link all of shaders.
     * 
     * @since 1.0
     */
    public void link() {
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == NULL) {
            System.err.println("Felid to Link Program");
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

    /**
     * Delete program, and release memory.
     * 
     * @since 1.0
     */
    public void cleanup() {
        glDeleteProgram(shaderProgram);
    }
}
