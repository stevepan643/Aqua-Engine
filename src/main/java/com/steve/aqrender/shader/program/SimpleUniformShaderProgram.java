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
import org.lwjgl.system.MemoryUtil;

/**
 * 简单的带Uniform的Shader Program实现。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
@Slf4j
public class SimpleUniformShaderProgram extends ShaderProgram {
    private static final Map<String, Uniform> uniforms = new HashMap<>();
    private static final Map<String, Buffer> buffers = new HashMap<>();

    /**
     * 创建Program
     * @param vertexShader 顶点着色器
     * @param fragmentShader 片元着色器
     * @param id 渲染程序ID
     * @since March 11, 2025
     */
    public SimpleUniformShaderProgram(Shader vertexShader, Shader fragmentShader, Identifier id) {
        super(vertexShader, fragmentShader, id);
    }

    /**
     * 添加Uniform, 可添加类型{@link Uniform#TYPE}
     * @param name Uniform的名字
     * @param type Uniform的值
     * @since March 11, 2025
     */
    protected void addUniform(String name, Uniform.TYPE type) {
        Uniform u = new Uniform(glGetUniformLocation(handle, name), type);
        if (type == Uniform.TYPE.FOUR_FLOAT_MATRIX)
            buffers.put(name, MemoryUtil.memCallocFloat(16));
        uniforms.put(name, u);
    }

    /**
     * 设置Uniform
     * @param name uniform变量的名称
     * @param value uniform变量的值
     * @since March 11, 2025
     */
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
