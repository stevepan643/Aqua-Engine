/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import com.steve.graphic.Camera;
import com.steve.graphic.GameObject;
import com.steve.graphic.Material;
import com.steve.graphic.Mesh;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.graphic.Uniform;
import com.steve.platform.Window;
import com.steve.util.LogUtil;
import com.steve.util.ObjUtil;

public class Main {

        public static ShaderProgram shaderProgram;
        public static ShaderProgram shaderProgram2;
        public static ShaderProgram shaderProgram3;
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
        
        public static float fov = 60.0f;

        private final static Logger LOGGER = LogUtil.getLogger();

        public static void main(String[] args) {

                window = new Window(
                                width, height, "Test Game");

                // If window's size is changing, callback framebuffer_size_callback.
                glfwSetFramebufferSizeCallback(
                                window.get(), (w, width, height) -> framebuffer_size_callback(w, width, height));

                // Initialization shader(create, compile link and etc).
                Shader vertex = new Shader(
                                "src/main/resources/shader/v.vs",
                                GL_VERTEX_SHADER);
                                
                Shader vertex2 = new Shader(
                        "src/main/resources/shader/v2.vs",
                        GL_VERTEX_SHADER);

                Shader fragment = new Shader(
                                "src/main/resources/shader/f.fs",
                                GL_FRAGMENT_SHADER);

                Shader fragment2 = new Shader(
                                "src/main/resources/shader/f2.fs",
                                GL_FRAGMENT_SHADER);

                Shader fragment3 = new Shader(
                                "src/main/resources/shader/f3.fs", 
                                GL_FRAGMENT_SHADER);

                shaderProgram = new ShaderProgram();
                shaderProgram.addShader("vertexShader", vertex);
                shaderProgram.addShader("fragmentShader", fragment);

                shaderProgram2 = new ShaderProgram();
                shaderProgram2.addShader("vertexShader", vertex);
                shaderProgram2.addShader("fragmentShader", fragment2);

                shaderProgram3 = new ShaderProgram();
                shaderProgram3.addShader("vertexShader", vertex2);
                shaderProgram3.addShader("fragmentShader", fragment3);

                shaderProgram.link();
                shaderProgram2.link();
                shaderProgram3.link();

                Material material = new Material(
                        "src/main/resources/container2.png",
                        "src/main/resources/container2_specular.png",
                        64.0f,
                        "Test Material"
                );
                
                mesh1 = ObjUtil.loadModel("src/main/resources/model/Cube.obj");

                GameObject cube = new GameObject(material, ObjUtil.loadModel("src/main/resources/model/Cube.obj"));
                GameObject david = new GameObject(material, ObjUtil.loadModel("src/main/resources/model/David head.obj"));
                GameObject lady = new GameObject(material, ObjUtil.loadModel("src/main/resources/model/CREEPY_LADY_PG_EX2.obj"));
                GameObject nile = new GameObject(material, ObjUtil.loadModel("src/main/resources/model/nile.obj"));
                david.getMesh().getTransform().translate(-0.0f, 0.5f, 0.5f);
                lady.getMesh().getTransform().translate(1.0f, 0.5f, 0.5f).rotateY(45).scale(2.0f);
                nile.getMesh().getTransform().translate(0.0f, 0.0f, 1.5f).scale(0.5f);
                
                cube.getMesh().getTransform().scale(0.5f);

                // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glEnable(GL_DEPTH_TEST);
                glfwSwapInterval(0);
                glEnable(GL_MULT);
                glEnable(GL_CULL_FACE);
                glEnable(GL_STENCIL_TEST);
                glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

                shaderProgram.use();

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
                                .translate(-1.0f, 2.0f, 1.5f)
                                .scale(0.05f);
                                
                Uniform<Vector3f> lightPosition = new Uniform<Vector3f>(
                        "light.position", new Vector3f(-1.5f, 2.0f, 2f));
                Uniform<Vector3f> lightAmbient = new Uniform<Vector3f>(
                        "light.ambient", new Vector3f(0.1f, 0.1f, 0.1f));
                Uniform<Vector3f> lightDiffuse = new Uniform<Vector3f>(
                        "light.diffuse", new Vector3f(1.0f / 1.2f, 0.85f / 1.2f, 0.72f / 1.2f));
                Uniform<Vector3f> lightSpecular = new Uniform<Vector3f>(
                        "light.specular", new Vector3f(0.2f, 0.2f, 0.2f));

                Uniform<Float> timeUniform = new Uniform<Float>(
                        "move_time", 1.0f);


                glfwSetInputMode(window.get(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                glfwSetCursorPosCallback(window.get(), (w, xpos, ypos) -> mouse_callback(w, xpos, ypos));

                glfwSetScrollCallback(window.get(), (w, xoffset, yoffset) -> scroll_callback(w, xoffset, yoffset));

                double lastTime = glfwGetTime();
                double lastFrameTime = lastTime;
                int frameCount = 0;
                float fps = 0;

                // System.gc();
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
                        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

                        if (isChanged) {
                                projUniform.getValue().setPerspective(
                                        (float) Math.toRadians(60.0f),
                                        (float) width / height, 0.01f, 100f);
                                projUniform.update();
                                isChanged = false;
                        }

                        timeUniform.setValue((float) glfwGetTime() / 10);

                        shaderProgram.use();
                        shaderProgram.addUniform(viewUniform); 
                        shaderProgram.addUniform(projUniform);
                        Mesh.setupUniform(shaderProgram);
                        mesh1.render();

                        shaderProgram2.use();
                        Mesh.setupUniform(shaderProgram2);
                        shaderProgram2.addUniforms(timeUniform, projUniform, viewUniform, viewPosUniform,
                                                lightPosition, lightAmbient, lightDiffuse, lightSpecular);
                        shaderProgram2.addUniform(cube.getMesh().getUniform());
                        shaderProgram2.addUniform(cube.getMaterial().getDiffuseUniform());
                        shaderProgram2.addUniform(cube.getMaterial().getSpecularUniform());
                        shaderProgram2.addUniform(cube.getMaterial().getShininessUniform());
                        shaderProgram2.addUniform(cube.getMaterial().getEmissionUniform());
                        cube.render();
                        shaderProgram2.addUniform(lady.getMesh().getUniform());
                        lady.render();
                        shaderProgram2.addUniform(nile.getMesh().getUniform());
                        nile.render();
                        shaderProgram2.addUniform(david.getMesh().getUniform());
                        david.render();

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

        public static void cleanup() {
                mesh1.cleanup();
                shaderProgram.cleanup();
                shaderProgram2.cleanup();
                glfwTerminate();
        }

}