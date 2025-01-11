
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUseProgram;

import com.steve.graphic.Mesh;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.platform.Window;

public class Main {
    public static final float vertices[] = {
            0.5f, 0.5f, 0.0f, // top right
            0.5f, -0.5f, 0.0f, // bottom right
            -0.5f, -0.5f, 0.0f, // bottom left
            -0.5f, 0.5f, 0.0f // top left
    };

    public static final int indices[] = {
            0, 1, 3, // first triangle
            1, 2, 3 // second triangle
    };

    public static ShaderProgram shaderProgram;
    public static Mesh mesh;

    public static void main(String[] args) {

        Window window = new Window(
                800, 600, "Test Game");

        // If window's size is changing, callback framebuffer_size_callback.
        glfwSetFramebufferSizeCallback(
                window.get(), (w, width, height) -> framebuffer_size_callback(w, width, height));

        // Initialization shader(create, compile link and etc).
        Shader vertex = new Shader(
                "src/main/resources/shaders/v.vs",
                GL_VERTEX_SHADER);

        Shader fragment = new Shader(
                "src/main/resources/shaders/f.fs",
                GL_FRAGMENT_SHADER);

        shaderProgram = new ShaderProgram();
        shaderProgram.addShader("vertexShader", vertex);
        shaderProgram.addShader("fragmentShader", fragment);

        shaderProgram.link();

        mesh = new Mesh(vertices, indices);

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        while (!window.isShouldClose()) {
            processInput(window.get());

            glClearColor(0f, 0f, 0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            shaderProgram.use();

            double time = glfwGetTime();
            double greenValue = Math.sin(time) / 2.0 + 0.5;
            int uniform = glGetUniformLocation(shaderProgram.get(), "ourColor");
            glUniform4f(uniform, 0, (float) greenValue, 0, 0);

            mesh.render();

            window.swapBuffers();
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

    public static void cleanup() {
        mesh.cleanup();
        shaderProgram.cleanup();
        glfwTerminate();
    }

}