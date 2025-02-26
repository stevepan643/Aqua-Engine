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

import javax.annotation.Nonnull;
import org.joml.Matrix4f;

/**
 * This class saved mesh's vertices, indices and etc.
 *
 * @author Steve Pan
 * @version 1.1
 */
public class Mesh {
  private float vertices[];
  private int indices[];
  private int vao; // Vertex Array Object
  private int vbo; // Vertex Buffer Object
  private int ebo; // Element Buffer Object

  public static float defaultColor[] = {0.5f, 0.5f, 0.5f};

  private Uniform<Matrix4f> modelUniform;

  private final int VERTEX_SIZE = 8;

  /**
   * Create a mesh by vertices, color and indices.
   *
   * @param vertices Mesh's vertex
   * @param indices Indic of each mesh's face
   * @since 1.0
   */
  protected Mesh(@Nonnull float vertices[], @Nonnull int indices[]) {
    this.vertices = vertices;
    this.indices = indices;
    this.modelUniform =
        new Uniform<Matrix4f>(
            "model",
            new Matrix4f(
                1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, 1.0f));
    loadMesh();
  }

  /**
   * Creates a new Mesh object with the specified vertices and indices.
   *
   * @param vertices an array of floats representing the vertices and colors of the mesh
   * @param indices an array of integers representing the indices of the mesh
   * @return a new Mesh object constructed with the provided vertices, colors and indices
   * @since 1.1
   */
  public static Mesh createMeshWithColor(@Nonnull float vertices[], @Nonnull int indices[]) {
    return new Mesh(vertices, indices);
  }

  /**
   * Creates a mesh without color information by adding default color values to each vertex.
   *
   * @param vertices an array of vertex positions, where each vertex is represented by three
   *     consecutive float values (x, y, z).
   * @param indices an array of indices that define the order in which vertices are drawn.
   * @return a Mesh object with the specified vertices and indices, and default color values added
   *     to each vertex.
   * @since 1.1
   */
  public static Mesh createMeshNonColor(@Nonnull float vertices[], @Nonnull int indices[]) {
    float newVertices[] = new float[vertices.length + vertices.length / 3];
    int vertexCount = vertices.length / 3;

    for (int i = 0, j = 0; i < vertexCount; i++) {
      newVertices[j++] = vertices[i * 3];
      newVertices[j++] = vertices[i * 3 + 1];
      newVertices[j++] = defaultColor[0];
      newVertices[j++] = defaultColor[1];
    }

    return createMeshWithColor(newVertices, indices);
  }

  public static Mesh createMeshWithNormalAndText(
      @Nonnull float vertices[], @Nonnull int indices[]) {
    return new Mesh(vertices, indices);
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

    // position attribute
    glVertexAttribPointer(0, 3, GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
    glEnableVertexAttribArray(0);

    // normal attribute
    glVertexAttribPointer(1, 3, GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 3 * Float.BYTES);
    glEnableVertexAttribArray(1);

    // texture attribute
    glVertexAttribPointer(2, 2, GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 6 * Float.BYTES);
    glEnableVertexAttribArray(2);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);
  }

  /**
   * Render this mesh, before using this method need set a program.
   *
   * @since 1.0
   */
  public void render() {
    modelUniform.update();
    glBindVertexArray(vao);
    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
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

  public Matrix4f getTransform() {
    return modelUniform.getValue();
  }

  public Uniform<Matrix4f> getUniform() {
    return modelUniform;
  }

  private static final Uniform<Matrix4f> staticModelUniform =
      new Uniform<Matrix4f>("model", new Matrix4f());

  public static void setupUniform(ShaderProgram shaderProgram) {
    shaderProgram.addUniform(staticModelUniform);
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
