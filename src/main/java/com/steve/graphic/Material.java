package com.steve.graphic;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import com.steve.utils.LogUtil;

public class Material {

    private Texture diffuse;
    private Texture specular;
    private float   shininess;
    private Texture emission;

    private Uniform<Integer> diffuseUniform;
    private Uniform<Integer> specularUniform;
    private Uniform<Float>   shininessUniform;
    private Uniform<Integer> emissionUniform;

    private String id;

    private final Logger LOGGER = LogUtil.getLogger();

    public Material(String diffuse, String specular, float shininess, String id) {
        this.diffuse = new Texture(diffuse);
        this.specular = new Texture(specular);
        this.emission = new Texture("src/main/resources/matrix.jpg");
        this.shininess = shininess;
        this.id = id;

        LOGGER.debug("Material created with id: " + id);
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

    public void use() {
        diffuse.use();
        specular.use();
        emission.use();
    }
}