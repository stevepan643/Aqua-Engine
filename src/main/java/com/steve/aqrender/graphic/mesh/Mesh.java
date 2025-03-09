package com.steve.aqrender.graphic.mesh;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15C.glBindBuffer;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import com.steve.aqrender.util.Identifier;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

@Slf4j
public class Mesh {
  private final float[] vertices;
  private final int[] indices;
  private int vao;
  private int vbo;
  private int ebo;
  private int vertexSize;

  private Identifier id;

  private int type;

  public static int VERTEX = 0b0001;
  public static int NORMAL = 0b0010;
  public static int TEXTURE = 0b0100;
  public static int COLOR = 0b1000;

  public Mesh(float[] vertices, int[] indices, int type) {
    this.vertices = vertices;
    this.indices = indices;

    this.type = type;

    if ((type & 0b0001) != 1) {
      log.warn("Are you sure you want to create a mesh without vertex array?");
    }

    vertexSize =
        ((type & VERTEX) == VERTEX ? 3 : 0)
            + ((type & COLOR) == COLOR ? 3 : 0)
            + ((type & NORMAL) == NORMAL ? 3 : 0)
            + ((type & TEXTURE) == TEXTURE ? 2 : 0);

    bind();
  }

  private void bind() {
    vao = glGenVertexArrays();
    vbo = glGenBuffers();
    ebo = glGenBuffers();

    glBindVertexArray(vao);

    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);

    int times =
        ((type & VERTEX) == VERTEX ? 1 : 0)
            + ((type & COLOR) == COLOR ? 1 : 0)
            + ((type & NORMAL) == NORMAL ? 1 : 0);
    int i = 0;
    for (; i < times; ++i) {
      glVertexAttribPointer(
          i, 3, GL_FLOAT, false, vertexSize * Float.BYTES, (long) i * 3 * Float.BYTES);
      glEnableVertexAttribArray(i);
    }

    log.debug("{}", i);
    if ((type & VERTEX) == VERTEX) {
      glVertexAttribPointer(
          i, 2, GL_FLOAT, false, vertexSize * Float.BYTES, (long) i * 3 * Float.BYTES);
      glEnableVertexAttribArray(i);
    }

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);
  }

  public void render() {
    GL30.glBindVertexArray(vao);
    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
  }
}
