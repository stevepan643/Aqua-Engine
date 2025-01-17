package com.steve.graphic;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryStack;

public class Uniform<T> {
    enum Type {
        b,
        i1,
        in,
        f1,
        fn,
        mat4f,
        none,
    };

    private T value;
    private Type type;

    private String name;

    private int location;

    public Uniform(String name, T value) {
        this.value = value;
        this.name = name;
        this.type = check();
        System.out.println(type);
    }

    private Type check() {
        if (value instanceof Matrix4fc) {
            return Type.mat4f;
        } else if (value instanceof Integer) {
            return Type.i1;
        } else if (value instanceof int[]) {
            return Type.in;
        } else if (value instanceof Float) {
            return Type.f1;
        } else if (value instanceof float[]) {
            return Type.fn;
        } else if (value instanceof Boolean) {
            return Type.b;
        } else {
            return Type.none;
        }
    }

    public void update() {
        switch (type) {
            case i1:
                setInt((int) value);
                break;
            case f1:
                setFloat((float) value);
                break;
            case fn:
                setFloats((float[]) value);
                break;
            case mat4f:
                setMat4f((Matrix4f) value);
                break;
            case b:
                setBool((boolean) value);
                break;
            default:
                break;
        }
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
    private void setBool(boolean value) {
        glUniform1i(location, value ? 1 : 0);
    }

    /**
     * Sets a integer uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param value   the int value to set.
     * @since 1.1
     */
    private void setInt(int value) {
        glUniform1i(location, value);
    }

    /**
     * Sets a float uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param value   the float value to set.
     * @since 1.1
     */
    private void setFloat(float value) {
        glUniform1f(location, value);
    }

    /**
     * Sets a float array uniform variable in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program.
     * @param values  the float array to set.
     *                The array length must be between 1 and 4.
     * @since 1.1
     */
    private void setFloats(float[] values) {
        int size = values.length;

        switch (size) {
            case 1:
                setFloat(values[0]);
                break;
            case 2:
                glUniform2f(glGetUniformLocation(location, name),
                        values[0], values[1]);
                break;
            case 3:
                glUniform3f(glGetUniformLocation(location, name),
                        values[0], values[1], values[2]);
                break;
            case 4:
                glUniform4f(glGetUniformLocation(location, name),
                        values[0], values[1], values[2], values[3]);
                break;
            default:
                throw new IllegalArgumentException("Only float arrays of length 1 to 4 are supported.");
        }
    }

    /**
     * Sets the value of a 4x4 matrix uniform in the shader program.
     *
     * @param uniform the name of the uniform variable in the shader program
     * @param value   the 4x4 matrix to set the uniform to
     * @since 1.1
     */
    private void setMat4f(Matrix4f value) {
        FloatBuffer fb = MemoryStack
                .stackPush()
                .mallocFloat(16);
        value.get(fb);
        glUniformMatrix4fv(location, false, fb);
    }

    protected void setLocation(int location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
