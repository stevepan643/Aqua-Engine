
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import com.steve.graphic.Camera;
import com.steve.graphic.Cube;
import com.steve.graphic.Material;
import com.steve.graphic.Mesh;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.graphic.Sphere;
import com.steve.graphic.Texture;
import com.steve.graphic.Uniform;
import com.steve.platform.Window;
import com.steve.utils.LogUtil;

public class Main {

        public static ShaderProgram shaderProgram;
        public static ShaderProgram shaderProgram2;
        public static Mesh mesh1;
        public static Mesh mesh2;
        public static Mesh mesh3;
        public static Mesh mesh4;

        public static int width = 800;
        public static int height = 600;

        public static boolean isChanged = false;

        public static Camera camera = new Camera();
        public static Window window;

        public static Uniform<Matrix4f> projUniform;
        
        public static float fov = 100.0f;

        private final static Logger LOGGER = LogUtil.getLogger();

        public static void main(String[] args) {

                window = new Window(
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

                Shader fragment2 = new Shader(
                                "src/main/resources/shaders/f2.fs",
                                GL_FRAGMENT_SHADER);

                shaderProgram = new ShaderProgram();
                shaderProgram.addShader("vertexShader", vertex);
                shaderProgram.addShader("fragmentShader", fragment);

                shaderProgram2 = new ShaderProgram();
                shaderProgram2.addShader("vertexShader", vertex);
                shaderProgram2.addShader("fragmentShader", fragment2);

                shaderProgram.link();
                shaderProgram2.link();

                Texture texture1 = new Texture("src/main/resources/textures.png");
                
                mesh1 = Cube.createCubeAndScale(0.5f);
                mesh2 = Cube.createCubeAndScale(1.0f);
                mesh3 = Cube.createCubeAndScale(0.5f);
                mesh4 = Cube.createCubeAndScale(0.5f);

                // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glEnable(GL_DEPTH_TEST);
                glfwSwapInterval(0);

                shaderProgram.use();

                Uniform<Integer> textureUniform1 = new Uniform<Integer>(
                                "texture1", 0);

                projUniform = new Uniform<Matrix4f>(
                                "proj",
                                new Matrix4f().perspective(
                                                (float) Math.toRadians(fov),
                                                (float) width / height, 0.01f, 100f));

                Uniform<Matrix4f> viewUniform = new Uniform<Matrix4f>(
                                "view", camera.getViewMatrix());  
                                
                Uniform<Vector3f> viewPosUniform = new Uniform<Vector3f>(
                                "viewPos", camera.getPosition());

                mesh1.getTransform()
                                .translate(1.0f, 1.0f, -0.5f);
                mesh2.getTransform()
                                .translate(-1.0f, 0.0f, 0.0f);
                mesh3.getTransform()
                                .translate(-1.0f, 0.0f, -2.0f);
                mesh4.getTransform()
                                .translate(2.0f, 1.5f, 0.0f);

                glfwSetInputMode(window.get(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                glfwSetCursorPosCallback(window.get(), (w, xpos, ypos) -> mouse_callback(w, xpos, ypos));

                glfwSetScrollCallback(window.get(), (w, xoffset, yoffset) -> scroll_callback(w, xoffset, yoffset));

                double lastTime = glfwGetTime();
                double lastFrameTime = lastTime;
                int frameCount = 0;
                float fps = 0;

                Material material = new Material(
                        "src/main/resources/container2.png",
                        "src/main/resources/container2_specular.png",
                        "Test Material"
                );

                while (!window.isShouldClose()) {
                        glfwPollEvents();

                        double currentTime = glfwGetTime();
                        frameCount++;

                        double deltaTime = currentTime - lastTime;
                        lastTime = currentTime;

                        if (currentTime - lastFrameTime >= 1.0) {
                                fps = (float) (frameCount / (currentTime - lastFrameTime));
                                frameCount = 0;
                                lastFrameTime = currentTime;
                                System.gc();
                        }
                        
                        glfwSetWindowTitle(window.get(), "Test Game - FPS: " + 
                                String.format("%.2f", fps));

                        processInput(window.get(), (float) deltaTime);
                        viewUniform.update();

                        glClearColor(0f, 0f, 0f, 1.0f);
                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

                        texture1.use();

                        if (isChanged) {
                                projUniform.getValue().setPerspective(
                                        (float) Math.toRadians(60.0f),
                                        (float) width / height, 0.01f, 100f);
                                projUniform.update();
                                isChanged = false;
                        }

                        // mesh1.getTransform()
                        //         .rotateY((float) Math.toRadians(deltaTime *  50f));
                        // mesh2.getTransform()
                        //         .rotateY((float) Math.toRadians(deltaTime * -50f));

                        shaderProgram.use();
                        shaderProgram.addUniform(viewUniform); 
                        shaderProgram.addUniform(projUniform);
                        Mesh.setupUniform(shaderProgram);
                        mesh1.render();
                        // shaderProgram.unused();
                        shaderProgram2.use();
                        shaderProgram2.addUniform(textureUniform1);
                        shaderProgram2.addUniform(projUniform);
                        shaderProgram2.addUniform(viewUniform);
                        shaderProgram2.addUniform(viewPosUniform);
                        Mesh.setupUniform(shaderProgram2);
                        mesh2.render();
                        mesh3.render();
                        mesh4.render();
                        // sphere.render();

                        window.swapBuffers();
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

        private static boolean lastState = false;
        public static void processInput(long window, float deltaTime) {
                float cameraSpeed = 1.0f * deltaTime;
                
                if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                        glfwSetWindowShouldClose(window, true);
                }
                if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
                        camera.moveForward(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
                        camera.moveBackward(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
                        camera.moveLeft(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                        camera.moveRight(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
                        camera.moveUp(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
                        camera.moveDown(cameraSpeed);
                }
                if (glfwGetKey(window, GLFW_KEY_F11) == GLFW_PRESS) {
                        if (!lastState) {
                                lastState = true;
                                Main.window.switchFullScreen();
                        }
                } else {
                        lastState = false;
                        
                }
                camera.update();
        }

        

        private static float lastX = width / 2;
        private static float lastY = height / 2;
        private static float yaw = -90.0f;
        private static float pitch = 0.0f;
        private static boolean firstMouse = true;
        private static float sensitivity = 0.1f;
        public static void mouse_callback(long window, double xpos, double ypos) {
                if (firstMouse) {
                        lastX = (float) xpos;
                        lastY = (float) ypos;
                        firstMouse = false;
                }

                float xoffset = (float) (xpos - lastX);
                float yoffset = (float) (lastY - ypos); 
                lastX = (float) xpos;
                lastY = (float) ypos;

                xoffset *= sensitivity;
                yoffset *= sensitivity;

                yaw += xoffset;
                pitch += yoffset;

                if (pitch > 89.0f) {
                    pitch = 89.0f;
                }
                if (pitch < -89.0f) {
                    pitch = -89.0f;
                }

                yaw = yaw % 360.0f;
                if (yaw < 0.0f) yaw += 360.0f;


                camera.lookTo(yaw, pitch);
        }

        public static void scroll_callback(long window, double xoffset, double yoffset) {
                if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
                        sensitivity += yoffset / 100.0f;
                        if (sensitivity < 0.01f) {
                                sensitivity = 0.01f;
                        } else if (sensitivity > 1.0f) {
                                sensitivity = 1.0f;
                        }
                        LOGGER.debug("sensitivity: " + sensitivity);
                } else {
                        fov -= yoffset;
                        if (fov < 30.0f) {
                                fov = 30.0f;
                        }
                        if (fov > 100.0f) {
                                fov = 100.0f;
                        }
                        LOGGER.debug("fov: " + fov);
                        projUniform.getValue().setPerspective(
                                (float) Math.toRadians(fov),
                                (float) width / height, 0.01f, 100f);
                        projUniform.update();
                }
        }

        public static float getFPS() {
                return (float) (1 / glfwGetTime());
        }

        public static void cleanup() {
                mesh1.cleanup();
                shaderProgram.cleanup();
                shaderProgram2.cleanup();
                glfwTerminate();
        }

}