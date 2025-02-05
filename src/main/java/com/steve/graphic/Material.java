package com.steve.graphic;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import com.steve.utils.LogUtil;

public class Material {

    private Texture diffuse;
    private Texture specular;

    private Uniform<Integer> diffuseUniform;
    private Uniform<Integer> specularUniform;

    private String id;

    private final Logger LOGGER = LogUtil.getLogger();

    public Material(String diffuse, String specular, String id) {
        this.diffuse = new Texture(diffuse);
        this.specular = new Texture(specular);
        this.id = id;

        this.diffuseUniform = new Uniform<Integer>("material.diffuse", this.diffuse.getTextureID());
        this.specularUniform = new Uniform<Integer>("material.specular", this.specular.getTextureID());

        LOGGER.info("Material created with id: " + id);
        LOGGER.info("Diffuse texture: " + diffuseUniform.getName());
        LOGGER.info("Specular texture: " + specularUniform.getName());
    }

    public List<Uniform<Integer>> getUniforms() {
        return Arrays.asList(diffuseUniform, specularUniform);
    }

    public void use() {
        diffuse.use();
        specular.use();
    }
}