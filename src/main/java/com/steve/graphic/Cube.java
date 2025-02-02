package com.steve.graphic;

public class Cube {

    static float[] vertices = new float[] {
        // positions          // colors         // texture coords
        // front face
         0.5f,  0.5f,  0.5f,   1.0f, 0.0f, 0.0f,  1.0f, 1.0f, // top right
         0.5f, -0.5f,  0.5f,   0.0f, 1.0f, 0.0f,  1.0f, 0.0f, // bottom right
        -0.5f, -0.5f,  0.5f,   0.0f, 0.0f, 1.0f,  0.0f, 0.0f, // bottom left
        -0.5f,  0.5f,  0.5f,   1.0f, 1.0f, 0.0f,  0.0f, 1.0f, // top left

        // back face
         0.5f,  0.5f, -0.5f,   1.0f, 0.0f, 0.0f,  1.0f, 1.0f, // top right
         0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,  1.0f, 0.0f, // bottom right
        -0.5f, -0.5f, -0.5f,   0.0f, 0.0f, 1.0f,  0.0f, 0.0f, // bottom left
        -0.5f,  0.5f, -0.5f,   1.0f, 1.0f, 0.0f,  0.0f, 1.0f, // top left

        // left face
        -0.5f,  0.5f,  0.5f,   1.0f, 1.0f, 0.0f,  1.0f, 1.0f, // top front
        -0.5f, -0.5f,  0.5f,   0.0f, 0.0f, 1.0f,  1.0f, 0.0f, // bottom front
        -0.5f, -0.5f, -0.5f,   0.0f, 0.0f, 1.0f,  0.0f, 0.0f, // bottom back
        -0.5f,  0.5f, -0.5f,   1.0f, 1.0f, 0.0f,  0.0f, 1.0f, // top back

        // right face
         0.5f,  0.5f,  0.5f,   1.0f, 0.0f, 0.0f,  1.0f, 1.0f, // top front
         0.5f, -0.5f,  0.5f,   0.0f, 1.0f, 0.0f,  1.0f, 0.0f, // bottom front
         0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,  0.0f, 0.0f, // bottom back
         0.5f,  0.5f, -0.5f,   1.0f, 0.0f, 0.0f,  0.0f, 1.0f, // top back

        // top face
         0.5f,  0.5f,  0.5f,   1.0f, 0.0f, 0.0f,  1.0f, 1.0f, // front right
        -0.5f,  0.5f,  0.5f,   1.0f, 1.0f, 0.0f,  0.0f, 1.0f, // front left
        -0.5f,  0.5f, -0.5f,   1.0f, 1.0f, 0.0f,  0.0f, 0.0f, // back left
         0.5f,  0.5f, -0.5f,   1.0f, 0.0f, 0.0f,  1.0f, 0.0f, // back right

        // bottom face
         0.5f, -0.5f,  0.5f,   0.0f, 1.0f, 0.0f,  1.0f, 1.0f, // front right
        -0.5f, -0.5f,  0.5f,   0.0f, 0.0f, 1.0f,  0.0f, 1.0f, // front left
        -0.5f, -0.5f, -0.5f,   0.0f, 0.0f, 1.0f,  0.0f, 0.0f, // back left
         0.5f, -0.5f, -0.5f,   0.0f, 1.0f, 0.0f,  1.0f, 0.0f  // back right
    };

    static int[] indices = new int[] {
        // front face
        0, 1, 3,
        1, 2, 3,

        // back face
        4, 5, 7,
        5, 6, 7,

        // left face
        8, 9, 11,
        9, 10, 11,

        // right face
        12, 13, 15,
        13, 14, 15,

        // top face
        16, 17, 19,
        17, 18, 19,

        // bottom face
        20, 21, 23,
        21, 22, 23
    };

    public static Mesh createCubeAndScale(float scale) {
        float[] scaledVertices = vertices.clone();

        for (int i = 0; i < scaledVertices.length; i+=8) {
            scaledVertices[i] *= scale;
            scaledVertices[i + 1] *= scale;
            scaledVertices[i + 2] *= scale;
        }
        
        return Mesh.createMeshWithColor(scaledVertices, indices);
    }
    
}
