
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.io.IOException;

import org.joml.Matrix4f;

import com.steve.graphic.Mesh;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.graphic.Texture;
import com.steve.graphic.Uniform;
import com.steve.platform.Window;

public class Main {
        public static float vertices[] = {
                        // positions // colors // texture coords
                        // 0
                        0.5f, 0.5f, 0.5f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 1.0f, // top front right
                        // 1
                        0.5f, -0.5f, 0.5f,
                        0.0f, 1.0f, 0.0f,
                        1.0f, 0.0f, // bottom front right
                        // 2
                        -0.5f, -0.5f, 0.5f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, // bottom front left
                        // 3
                        -0.5f, 0.5f, 0.5f,
                        1.0f, 1.0f, 0.0f,
                        0.0f, 1.0f, // top front left
                        // 4
                        0.5f, 0.5f, -0.5f,
                        1.0f, 0.0f, 0.0f,
                        1.0f, 1.0f, // top back right
                        // 5
                        0.5f, -0.5f, -0.5f,
                        0.0f, 1.0f, 0.0f,
                        1.0f, 0.0f, // bottom back right
                        // 6
                        -0.5f, -0.5f, -0.5f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, // bottom back left
                        // 7
                        -0.5f, 0.5f, -0.5f,
                        1.0f, 1.0f, 0.0f,
                        0.0f, 1.0f // top back left

        };

        public static final int indices[] = {
                        // top face
                        0, 1, 3,
                        1, 2, 3,

                        // bottom face
                        4, 5, 7,
                        5, 6, 7,

                        // left face
                        3, 2, 7,
                        2, 6, 7,

                        // right face
                        0, 1, 5,
                        4, 5, 0,

                        // back face
                        0, 3, 4,
                        3, 4, 7,

                        // front face
                        1, 2, 5,
                        6, 5, 2,

        };

        public static ShaderProgram shaderProgram;
        public static Mesh mesh;

        public static int width = 800;
        public static int height = 600;

        public static boolean isChanged = false;

        public static void main(String[] args) {

                Window window = new Window(
                                width, height, "Test Game");

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

                Texture texture1 = null;
                try {
                        texture1 = new Texture("src/main/resources/textures.png");

                } catch (IOException e) {
                        e.printStackTrace();
                }

                mesh = Mesh.createMeshWithColorAndText(vertices, indices);

                // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glEnable(GL_DEPTH_TEST);

                shaderProgram.use();
                Uniform<Integer> textureUniform1 = new Uniform<Integer>(
                                "texture1", 0);
                shaderProgram.addUniform(textureUniform1);

                Uniform<Matrix4f> projUniform = new Uniform<Matrix4f>(
                                "proj",
                                new Matrix4f().perspective(
                                                (float) Math.toRadians(60.0f),
                                                (float) width / height, 0.01f, 100f));
                shaderProgram.addUniform(projUniform);

                Uniform<Matrix4f> viewUniform = new Uniform<Matrix4f>(
                                "view", new Matrix4f());
                shaderProgram.addUniform(viewUniform);

                mesh.getTransform()
                                .translate(0.0f, 0.0f, -2.0f)
                                .rotateX((float) Math.toRadians(-55.0f));
                Uniform<Matrix4f> modelUniform = new Uniform<Matrix4f>(
                                "model", mesh.getTransform());
                shaderProgram.addUniform(modelUniform);

                while (!window.isShouldClose()) {
                        processInput(window.get());

                        glClearColor(0f, 0f, 0f, 1.0f);
                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                        // double time = glfwGetTime();
                        // double greenValue = Math.sin(time) / 2.0 + 0.5;
                        // int uniform = glGetUniformLocation(shaderProgram.get(), "ourColor");
                        // glUniform4f(uniform, 0, (float) greenValue, 0, 0);

                        texture1.use();

                        if (isChanged) {
                                projUniform.getValue()
                                                .identity()
                                                .perspective(
                                                                (float) Math.toRadians(60.0f),
                                                                (float) width / height, 0.01f, 100f);
                                projUniform.update();
                                isChanged = false;
                        }

                        mesh.getTransform()
                                        .rotateY((float) Math.toRadians(0.1f));
                        modelUniform.update();

                        mesh.render();

                        window.swapBuffers();
                        glfwPollEvents();
                }

                cleanup();

        }

        public static void framebuffer_size_callback(
                        long window, int width, int height) {
                glViewport(0, 0, width, height);
                Main.width = width;
                Main.height = height;
                isChanged = true;
        }

        public static void processInput(long window) {
                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                        glfwSetWindowShouldClose(window, true);
                }
        }

        public static float getFPS() {
                return (float) (1 / glfwGetTime());
        }

        public static void cleanup() {
                mesh.cleanup();
                shaderProgram.cleanup();
                glfwTerminate();
        }

}