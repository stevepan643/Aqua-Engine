package com.steve.graphic;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.slf4j.Logger;

import com.steve.utils.FileUtil;
import com.steve.utils.LogUtil;

/**
 * This class saved a shader(such as vertex shader), include source code,
 * shader type.
 * 
 * @author Steve Pan
 * @version 1.0
 */
public class Shader {
    private String source;
    private int type;
    private int shader;

    private final Logger LOGGER = LogUtil.getLogger();

    /**
     * Create shader using filepath and type.
     * 
     * @param filepath Input shader file's path
     * @param type     Type of this Shader(such as {@code GL_VERTEX_SHADER} )
     * @since version 1.0
     */
    public Shader(String filepath, int type) {
        this.source = FileUtil.read(filepath);
        this.type = type;
        this.shader = glCreateShader(this.type);

        glShaderSource(this.shader, this.source);
        glCompileShader(this.shader);

        if (glGetShaderi(this.shader, GL_COMPILE_STATUS) == NULL) {
            String info = glGetShaderInfoLog(this.shader);
            LOGGER.error("Failed to Compile Vertex or Fragment Shader");
            LOGGER.error("Shader filepath: " + filepath);
            LOGGER.error("Error information:");
            for (String line : info.split("\n")) {
                LOGGER.error(line);
            }
        }
    }

    /**
     * Get shader's point.
     * 
     * @return Shader's Point
     * @since version 1.0
     */
    protected int get() {
        return shader;
    }

    /**
     * Delete this shader.
     * 
     * @since version 1.0
     */
    public void delete() {
        glDeleteShader(shader);
    }
}
