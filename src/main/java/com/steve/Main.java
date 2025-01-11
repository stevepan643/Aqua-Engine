package com.steve;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    public static final String vertexShaderSource = 
        "#version 330 core\n" +
        "layout (location = 0) in vec3 aPos;\n" +
        "void main()\n" +
        "{\n" +
        "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
        "}\0";

    public static final String fragmentShaderSource = 
        "#version 330 core\n" + 
        "out vec4 FragColor;\n" + 
        "void main()\n" + 
        "{\n" + 
        "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" + 
        "}\0";

    public static final float vertices[] = {
        // first triangle
        0.5f,  0.5f, 0.0f,  // top right
        0.5f, -0.5f, 0.0f,  // bottom right
        -0.5f,  0.5f, 0.0f,  // top left 
        // second triangle
        0.5f, -0.5f, 0.0f,  // bottom right
        -0.5f, -0.5f, 0.0f,  // bottom left
        -0.5f,  0.5f, 0.0f   // top left
    };

    public static int shaderProgram;
    public static int vao;
    public static int vbo;

    public static void main(String[] args) {
        // Initialization glfw.
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        // If run on macOS, need this.
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        // Create window.
        Long window = glfwCreateWindow(800, 600,
                "Hello LWJGL", NULL, NULL);

        // Check.
        if (window == NULL) {
            System.err.println("Failed to Create Window");
            glfwTerminate();
            System.exit(-1);
        }
        glfwMakeContextCurrent(window);

        // If window's size is changing, callback framebuffer_size_callback.
        glfwSetFramebufferSizeCallback(window, (w, width, height) -> 
            framebuffer_size_callback(w, width, height));
        
        createCapabilities();

        // Initialization shader(create, compile link and etc).
        shaderInit();

        // Load and Configure Vertex.
        vertexInit();

        while (!glfwWindowShouldClose(window)) {
            processInput(window);

            glClearColor(0f, 0f, 0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            drawTriangle();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        
        cleanup();

    }

    public static void framebuffer_size_callback(
            long window, int width, int height) {
        glViewport(0, 0, width, height);
    }
    
    public static void processInput(long window) {
        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true);
        }
    }

    public static void shaderInit() {
        // Create shaders.
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        
        // Bind shader source.
        glShaderSource(vertexShader, vertexShaderSource);
        glShaderSource(fragmentShader, fragmentShaderSource);

        // Compile Shaders
        glCompileShader(vertexShader);
        glCompileShader(fragmentShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == NULL
         && glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == NULL) {
            System.err.println("Felid to Compile Vertex or Fragment Shader");
        }

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == NULL) {
            System.err.println("Felid to Link Program");
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public static void vertexInit() {
        // Vertex Array Object
        vao = glGenVertexArrays();
        // Vertex Buffer Object
        vbo = glGenBuffers();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT,
                false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public static void drawTriangle() {
        glUseProgram(shaderProgram);
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    public static void cleanup() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteShader(shaderProgram);

        glfwTerminate();
    }

}