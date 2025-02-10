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

import com.steve.util.LogUtil;
import org.slf4j.Logger;

public class Material {
  private Texture diffuse;
  private Texture specular;
  private float shininess;
  private Texture emission;

  private Uniform<Integer> diffuseUniform;
  private Uniform<Integer> specularUniform;
  private Uniform<Float> shininessUniform;
  private Uniform<Integer> emissionUniform;

  private String id;

  private final Logger LOGGER = LogUtil.getLogger();

  public Material(String diffuse, String specular, float shininess, String id) {
    this.diffuse = new Texture(diffuse);
    this.specular = new Texture(specular);
    this.emission = new Texture("src/main/resources/matrix.jpg");
    this.shininess = shininess;
    this.id = id;

    LOGGER.debug("Material created with id: " + this.id);
    this.diffuseUniform = new Uniform<Integer>("material.diffuse", this.diffuse.getTextureID());
    LOGGER.debug("Diffuse texture: " + diffuseUniform.getName());
    this.specularUniform = new Uniform<Integer>("material.specular", this.specular.getTextureID());
    LOGGER.debug("Specular texture: " + specularUniform.getName());
    this.emissionUniform = new Uniform<Integer>("material.emission", this.emission.getTextureID());
    LOGGER.debug("Emission texture: " + emissionUniform.getName());
    this.shininessUniform = new Uniform<Float>("material.shininess", this.shininess);
  }

  public Uniform<Integer> getDiffuseUniform() {
    return diffuseUniform;
  }

  public Uniform<Integer> getSpecularUniform() {
    return specularUniform;
  }

  public Uniform<Float> getShininessUniform() {
    return shininessUniform;
  }

  public Uniform<Integer> getEmissionUniform() {
    return emissionUniform;
  }

  public String getID() {
    return id;
  }

  public void use() {
    diffuse.use();
    specular.use();
    emission.use();
  }
}
