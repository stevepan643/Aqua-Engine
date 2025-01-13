package com.steve.graphic;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;

/**
 * Shader program.
 * 
 * @author Steve Pan
 * @version 1.1
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
     * Retrieves the shader program identifier.
     *
     * @return the identifier of the shader program.
     * @since 1.0
     */
    public int get() {
        return shaderProgram;
    }

    /**
     * Sets a boolean uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param value   the boolean value to set:
     *                </p>
     *                {@code true} will be converted to {@code 1},
     *                </p>
     *                {@code false} to {@code 0}.
     * @since 1.1
     */
    public void setBool(String uniform, boolean value) {
        glUniform1i(glGetUniformLocation(shaderProgram, uniform), value ? 1 : 0);
    }

    /**
     * Sets a integer uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param value   the int value to set.
     * @since 1.1
     */
    public void setInt(String uniform, int value) {
        glUniform1i(glGetUniformLocation(shaderProgram, uniform), value);
    }

    /**
     * Sets a float uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param value   the float value to set.
     * @since 1.1
     */
    public void setFloat(String uniform, float value) {
        glUniform1f(glGetUniformLocation(shaderProgram, uniform), value);
    }

    /**
     * Sets a float array uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param values  the float array to set.
     *                The array length must be between 1 and 4.
     * @since 1.1
     */
    public void setFloats(String uniform, float[] values) {
        int size = values.length;

        switch (size) {
            case 1:
                setFloat(uniform, values[0]);
                break;
            case 2:
                glUniform2f(glGetUniformLocation(shaderProgram, uniform),
                        values[0], values[1]);
                break;
            case 3:
                glUniform3f(glGetUniformLocation(shaderProgram, uniform),
                        values[0], values[1], values[2]);
                break;
            case 4:
                glUniform4f(glGetUniformLocation(shaderProgram, uniform),
                        values[0], values[1], values[2], values[3]);
                break;
            default:
                throw new IllegalArgumentException("Only float arrays of length 1 to 4 are supported.");
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
