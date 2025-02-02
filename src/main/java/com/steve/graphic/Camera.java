package com.steve.graphic;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.Vector;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Vector3f cameraPosition;
    private Vector3f cameraTarget;
    private Vector3f cameraDirection;
    private Vector3f cameraFront;
    private Vector3f cameraPPF; // camera position plus camera front

    private Vector3f up;

    private Vector3f cameraRight;
    private Vector3f cameraUp;

    private Matrix4f viewMatrix;

    public Camera() {
        cameraPosition = new Vector3f(0, 0, 3f);
        cameraTarget = new Vector3f(0, 0, 0);
        cameraDirection = new Vector3f(cameraPosition).sub(cameraTarget).normalize();
        cameraFront = new Vector3f(0, 0, -1);
        cameraPPF = new Vector3f(cameraPosition).add(cameraFront);

        up = new Vector3f(0, 1, 0);
        
        cameraRight = new Vector3f(up).cross(cameraDirection).normalize();
        cameraUp = new Vector3f(cameraDirection).cross(cameraRight).normalize();

        viewMatrix = new Matrix4f()
            .lookAt(cameraPosition, 
                    cameraPPF, 
                    cameraUp);
    }

    public void moveForward(float speed) {
        cameraPosition.add(new Vector3f(cameraFront).mul(speed));
    }

    public void moveBackward(float speed) {
        cameraPosition.sub(new Vector3f(cameraFront).mul(speed));
    }

    public void moveRight(float speed) {
        cameraPosition.fma(speed, new Vector3f(cameraFront).cross(cameraUp).normalize());
    }

    public void moveLeft(float speed) {
        cameraPosition.fma(-speed, new Vector3f(cameraFront).cross(cameraUp).normalize());
    }

    public void update() {
        cameraDirection = new Vector3f(cameraPosition).sub(cameraTarget).normalize();
        cameraRight = new Vector3f(up).cross(cameraDirection).normalize();
        cameraUp = new Vector3f(cameraDirection).cross(cameraRight).normalize();
        cameraPPF = new Vector3f(cameraPosition).add(cameraFront);

        viewMatrix = viewMatrix
            .identity()
            .lookAt(cameraPosition, 
                    cameraPPF, 
                    cameraUp);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

}