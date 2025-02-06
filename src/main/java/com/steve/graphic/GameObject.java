package com.steve.graphic;

public class GameObject {
    Material material;
    Mesh mesh;
    ShaderProgram shaderProgram;

    public GameObject(Material material, Mesh mesh) {
        this.material = material;
        this.mesh = mesh;
    }

    public void render() {
        material.use();
        mesh.render();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}
