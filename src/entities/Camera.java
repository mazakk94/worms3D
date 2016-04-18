/*
 lesson 8 - kamery, macierz i te sprawy
 */
package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch; //x y z ustawienie kamery
    private float yaw; //rozstaw lewo-prawo
    private float roll;

    public Camera() {
    }

    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }

    public void yaw(float amount) {
        //increment the yaw by the amount param
        yaw += amount;
    }

//increment the camera's current yaw rotation
    public void pitch(float amount) {
        //increment the pitch by the amount param
        pitch += amount;
    }

    public void walkForward(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

//moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance) {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

//strafes the camera left relitive to its current rotation (yaw)
    public void strafeLeft(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

//strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    public void lookThrough() {
        //roatate the pitch around the X axis
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        GL11.glTranslatef(position.x, position.y, position.z);
    }

    public void move() {

        //float x = distance * sin(yaw);
        // float z = distance * cos(yaw);
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.position.z += 0.2f;
            System.out.println(this.position.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.position.z -= 0.2f;
            System.out.println(this.position.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

}
