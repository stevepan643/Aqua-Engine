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

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;
import javax.annotation.Nonnull;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

public class Uniform<T> {
  enum Type {
    b,
    i1,
    in,
    f1,
    fn,
    mat4f,
    vec3f,
    none,
  };

  private T value;
  private Type type;

  private String name;

  private int location;

  private FloatBuffer buffer;

  private final MemoryStack stack;

  // private final Logger LOGGER = LogUtil.getLogger();

  public Uniform(@Nonnull String name, @Nonnull T value) {
    this.value = value;
    this.name = name;
    this.stack = MemoryStack.stackPush();
    this.type = check();
    // LOGGER.debug("name is:" + name);
  }

  private Type check() {
    if (value instanceof Matrix4fc) {
      buffer = stack.mallocFloat(16);
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
    } else if (value instanceof Vector3f) {
      return Type.vec3f;

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
      case vec3f:
        setVector3f((Vector3f) value);
        break;
      default:
        break;
    }
  }

  public void setValue(@Nonnull T v) {
    this.value = v;
  }

  /**
   * Sets a boolean uniform variable in the shader program.
   *
   * @param uniform the name of the uniform variable in the shader program.
   * @param value the boolean value to set: {@code true} will be converted to {@code 1}, {@code
   *     false} to {@code 0}.
   * @since 1.1
   */
  private void setBool(boolean value) {
    glUniform1i(location, value ? 1 : 0);
  }

  /**
   * Sets a integer uniform variable in the shader program.
   *
   * @param uniform the name of the uniform variable in the shader program.
   * @param value the int value to set.
   * @since 1.1
   */
  private void setInt(int value) {
    glUniform1i(location, value);
  }

  /**
   * Sets a float uniform variable in the shader program.
   *
   * @param uniform the name of the uniform variable in the shader program.
   * @param value the float value to set.
   * @since 1.1
   */
  private void setFloat(float value) {
    glUniform1f(location, value);
  }

  /**
   * Sets a float array uniform variable in the shader program.
   *
   * @param uniform the name of the uniform variable in the shader program.
   * @param values the float array to set. The array length must be between 1 and 4.
   * @since 1.1
   */
  private void setFloats(float[] values) {
    int size = values.length;

    switch (size) {
      case 1:
        setFloat(values[0]);
        break;
      case 2:
        glUniform2f(location, values[0], values[1]);
        break;
      case 3:
        glUniform3f(location, values[0], values[1], values[2]);
        break;
      case 4:
        glUniform4f(location, values[0], values[1], values[2], values[3]);
        break;
      default:
        throw new IllegalArgumentException("Only float arrays of length 1 to 4 are supported.");
    }
  }

  /**
   * Sets the value of a 4x4 matrix uniform in the shader program.
   *
   * @param uniform the name of the uniform variable in the shader program
   * @param value the 4x4 matrix to set the uniform to
   * @since 1.1
   */
  private void setMat4f(Matrix4f value) {
    value.get(buffer);
    glUniformMatrix4fv(location, false, buffer);
  }

  private void setVector3f(Vector3f value) {
    glUniform3f(location, value.x, value.y, value.z);
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
