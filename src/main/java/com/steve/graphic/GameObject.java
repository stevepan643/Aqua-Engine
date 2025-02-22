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

import com.steve.manager.ShaderManager;
import java.util.HashMap;
import org.joml.Matrix4f;

public class GameObject {
  private HashMap<String, Model> objects;
  private String id;

  public GameObject(String id) {
    this.objects = new HashMap<>();
    this.id = id;
  }

  public void addModel(Model model) {
    objects.put(model.id, model);
  }

  public Model getModel(String id) {
    return objects.get(id);
  }

  public String getId() {
    return id;
  }

  public void render() {
    objects.forEach(
        (id, model) -> {
          ShaderManager.render(model);
          model.mesh.render();
        });
  }

  public class Model {

    private Mesh mesh;
    private Material material;
    private String id;
    private String shader;

    private Matrix4f transform;

    public Model(String id, Mesh mesh, Material material) {
      this.mesh = mesh;
      this.material = material;
      this.shader = "default";
      this.transform = new Matrix4f();

      addModel(this);
    }

    public void setShader(String shaderID) {
      this.shader = shaderID;
    }

    public String getShader() {
      return shader;
    }

    public Matrix4f getTransform() {
      return transform;
    }
  }
}
