package com.steve.aqrender.shader.program;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;


import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.joml.Matrix4f;

import com.steve.aqrender.shader.Shader;
import com.steve.aqrender.util.Identifier;
import org.lwjgl.system.MemoryStack;

@Slf4j
public class SimpleUniformShaderProgram extends ShaderProgram {
    private static final Map<String, Uniform> uniforms = new HashMap<>();
    private static final Map<String, Buffer> buffers = new HashMap<>();


    public SimpleUniformShaderProgram(Shader vertexShader, Shader fragmentShader, Identifier id) {
        super(vertexShader, fragmentShader, id);
    }

    /**
     * 添加Uniform, 可添加类型{@link Uniform#TYPE}
     * @param name Uniform的名字
     * @param value Uniform的值
     */
    protected void addUniform(String name, Uniform.TYPE type) {
        Uniform u = new Uniform(glGetUniformLocation(handle, name), type);
        if (type == Uniform.TYPE.FOUR_FLOAT_MATRIX)
            buffers.put(name, MemoryStack.stackPush().mallocFloat(16));
        uniforms.put(name, u);
    }

    @Override
    public void setUniform(String name, Object value) {
        Uniform u = uniforms.get(name);
        switch (u.type()) {
            case ONE_INTEGER ->
                    glUniform1i(u.location(), (Integer) value);
            case ONE_FLOAT ->
                    glUniform1f(u.location(), (Float) value);
            case FOUR_FLOAT_MATRIX ->
                glUniformMatrix4fv(u.location(), false, ((Matrix4f) value).get((FloatBuffer) buffers.get(name)));
            case UNDEFINED ->
                    log.error("Uniform type is undefined");
        }
    }

}
