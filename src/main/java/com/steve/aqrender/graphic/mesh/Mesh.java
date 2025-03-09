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

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * 用于存储网格。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 9, 2025
 */
@Slf4j
public class Mesh {
  private final float[] vertices;
  private final int[] indices;
  private int vao;
  private int vbo;
  private int ebo;
  private final int vertexSize;

  /**
   * 类型:可控制顶点数据的内容,从下面选择
   *
   * <table>
   *  <tr><td>{@link #VERTEX}</td><td>顶点</td><td>3位</td></tr>
   *  <tr><td>{@link #NORMAL}</td><td>法线</td><td>3位</td></tr>
   *  <tr><td>{@link #COLOR}</td><td>颜色</td><td>3位</td></tr>
   *  <tr><td>{@link #TEXTURE}</td><td>贴图</td><td>2位</td></tr>
   * </table>
   */
  private final int type;

  public static int VERTEX = 0b0001;
  public static int NORMAL = 0b0010;
  public static int TEXTURE = 0b0100;
  public static int COLOR = 0b1000;

  /**
   * 创建和绑定网格。
   *
   * @param vertices 顶点数据
   *     <pre>
   *          {(坐标:1, 1, 1), (法线: 1, 1, 1), (颜色: 0.5f, 0.5f, 0.5f), (贴图: 1, 1)}
   *     </pre>
   *     所有都是可选的,通过{@link #type}控制
   * @param indices 索引
   * @param type 可控制顶点数据的内容,从下面选择
   *     <table>
   *             <tr><td>{@link #VERTEX}</td><td>顶点</td><td>3位</td></tr>
   *             <tr><td>{@link #NORMAL}</td><td>法线</td><td>3位</td></tr>
   *             <tr><td>{@link #COLOR}</td><td>颜色</td><td>3位</td></tr>
   *             <tr><td>{@link #TEXTURE}</td><td>贴图</td><td>2位</td></tr>
   *     </table>
   *
   * @since March 9, 2025
   */
  public Mesh(float[] vertices, int[] indices, int type) {
    this.vertices = vertices;
    this.indices = indices;

    this.type = type;

    if ((type & VERTEX) != VERTEX) {
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

    if ((type & VERTEX) == VERTEX) {
      glVertexAttribPointer(
          i, 2, GL_FLOAT, false, vertexSize * Float.BYTES, (long) i * 3 * Float.BYTES);
      glEnableVertexAttribArray(i);
    }

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);
  }

  /**
   * 渲染网格, 在此之前先设置渲染程序
   *
   * @see com.steve.aqrender.shader.program.ShaderPrograms 渲染程序
   * @since March 9, 2025
   */
  public void render() {
    GL30.glBindVertexArray(vao);
    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
  }
}
