package com.steve.graphic;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * This class saved mesh's vertices, indices and etc.
 * 
 * @author Steve Pan
 * @version 1.0
 * @since 1.0
 */
public class Mesh {
    private float vertices[];
    private int indices[];

    private int vao; // Vertex Array Object
    private int vbo; // Vertex Buffer Object
    private int ebo; // Element Buffer Object

    /**
     * Create a mesh by vertices and indices.
     * 
     * @param vertices Mesh's vertex
     * @param indices  Indic of each mesh's face
     * @since 1.0
     */
    public Mesh(float vertices[], int indices[]) {
        this.vertices = vertices;
        this.indices = indices;

        loadMesh();
    }

    /**
     * Load into render buffer.
     * 
     * @since 1.0
     */
    private void loadMesh() {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT,
                false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    /**
     * Render this mesh, before using this method need set a program.
     * 
     * @since 1.0
     */
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indices.length,
                GL_UNSIGNED_INT, 0);
        // glBindVertexArray(0);
    }

    /**
     * Get Vertex Array Object(VAO).
     * 
     * @return VAO
     * @since 1.0
     */
    public int getVao() {
        return vao;
    }

    /**
     * Cleanup all of buffers and objects.
     * 
     * @since 1.0
     */
    public void cleanup() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
    }
}