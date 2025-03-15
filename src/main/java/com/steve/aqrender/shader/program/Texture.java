package com.steve.aqrender.shader.program;

import com.steve.aqrender.util.Identifier;
import lombok.Getter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.ARBInternalformatQuery2.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class Texture {
    @Getter private int handle;
    public Texture(Identifier resource) {
        handle = glGenBuffers();
        glBindTexture(GL_TEXTURE_2D, handle);
        // TDDO
    }
}
