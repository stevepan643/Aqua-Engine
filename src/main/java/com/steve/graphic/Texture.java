package com.steve.graphic;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;

import com.steve.utils.LogUtil;

/**
 * The {@code Texture} class is responsible for loading and managing
 * textures in an OpenGL context.
 * It uses the LWJGL library to handle OpenGL and STB image loading
 * functions.
 * 
 * <p>
 * This class provides functionality to load an image from a file, create an
 * OpenGL texture,
 * and generate mipmaps for the texture. The texture is automatically bound
 * and unbound as needed.
 * 
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * try {
 *     Texture texture = new Texture("path/to/your/image.png");
 * } catch (IOException e) {
 *      e.printStackTrace();
 * }
 * </pre>
 * 
 * @author Steve Pan
 * 
 * @version 1.0
 */
public class Texture {
    private int width[] = new int[1];
    private int height[] = new int[1];
    private int nrChannels[] = new int[1];
    private ByteBuffer data;

    private int texture;

    private static int count = 0;
    public int id;

    private final Logger LOGGER = LogUtil.getLogger();

    static {
        stbi_set_flip_vertically_on_load(true);
    }

    /**
     * Constructs a new Texture object by loading an image from the
     * specified file path.
     * 
     * @param filepath the path to the image file to be loaded as a texture
     * @throws IOException if the image file cannot be loaded
     * @since 1.0
     */
    public Texture(String filepath) {
        id = count;
        count++;

        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        data = stbi_load(
                filepath, width, height, nrChannels, 0);
        if (data == null) {
            try {
                throw new IOException("Can't open file:" + filepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        LOGGER.debug("Load Texture from: " + filepath + "<" + width[0] + "x" + height[0] +
                ", " + nrChannels[0] + ", " + id + ">");
        glPixelStorei(GL_TEXTURE_2D, 1);
        glTexImage2D(
                GL_TEXTURE_2D, 0, GL_RGBA,
                width[0], height[0], 0,
                GL_RGBA, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(data);
    }

    public int getTextureID() {
        return id;
    }

    /**
     * Binds the texture for use in OpenGL rendering.
     * This method activates the texture unit specified by the texture ID
     * and binds the texture to the GL_TEXTURE_2D target.
     * @since 1.0
     */
    public void use() {
        glActiveTexture(GL_TEXTURE0 + id);
        glBindTexture(GL_TEXTURE_2D, texture);
    }
}
