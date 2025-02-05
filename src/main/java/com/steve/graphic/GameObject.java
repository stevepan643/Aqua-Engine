package com.steve.graphic;

public class GameObject {
    Texture texture;
    Texture diffuse;
    Texture specular;

    Mesh mesh;

    public GameObject(String texture, String diffuse, String specular, Mesh mesh) {
        this.texture = new Texture(texture);
        this.diffuse = new Texture(diffuse);
        this.specular = new Texture(specular);
        this.mesh = mesh;
    }

    public void render() {
        texture.use();
        diffuse.use();
        specular.use();
        mesh.render();
    }
}
