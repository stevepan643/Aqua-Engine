package com.steve.graphic;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import com.steve.utils.LogUtil;

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

    private final Logger LOGGER = LogUtil.getLogger();

    public Camera() {
        cameraPosition = new Vector3f(0, 0, 3f);
        cameraTarget = new Vector3f(0, 0, 0);
        cameraDirection = new Vector3f(cameraPosition).sub(cameraTarget).normalize();
        cameraFront = new Vector3f(0, 0, -1);
        cameraPPF = new Vector3f(cameraPosition).add(cameraFront);
        cameraPositionY = cameraPosition.y;

        up = new Vector3f(0, 1, 0);
        
        cameraRight = new Vector3f(up).cross(cameraDirection).normalize();
        cameraUp = new Vector3f(cameraDirection).cross(cameraRight).normalize();

        viewMatrix = new Matrix4f()
            .lookAt(cameraPosition, 
                    cameraPPF, 
                    cameraUp);
    }

    public void moveForward(float speed) {
        cameraPosition.add(new Vector3f(cameraFront.x, 0, cameraFront.z).normalize().mul(speed));
        cameraPosition.y = cameraPositionY;

        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    public void moveBackward(float speed) {
        cameraPosition.sub(new Vector3f(cameraFront.x, 0, cameraFront.z).normalize().mul(speed));
        cameraPosition.y = cameraPositionY;
        
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    public void moveRight(float speed) {
        cameraPosition.add(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(speed));
        cameraPosition.y = cameraPositionY;
        
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    public void moveLeft(float speed) {
        cameraPosition.sub(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(speed));
        cameraPosition.y = cameraPositionY;
        
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    float cameraPositionY = 0.0f;

    public void moveUp(float speed) {
        cameraPositionY += speed;
        cameraPosition.y = cameraPositionY;
        
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    public void moveDown(float speed) {
        cameraPositionY -= speed;
        cameraPosition.y = cameraPositionY;
        
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
    }

    public void lookTo(float yaw, float pitch) {

        Vector3f direction = new Vector3f();
        direction.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.y = (float) Math.sin(Math.toRadians(pitch));
        direction.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        cameraFront = direction.normalize();
        cameraTarget = new Vector3f(cameraPosition).add(cameraFront);
        // LOGGER.debug("Camera Front: " + cameraFront);
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

    public Vector3f getPosition() {
        return cameraPosition;
    }

}