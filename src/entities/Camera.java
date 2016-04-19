/*
 lesson 8 - kamery, macierz i te sprawy
 */
package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glRotatef;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20; //x y z ustawienie kamery
    private float yaw = 0; //rozstaw lewo-prawo
    private float roll;

    private Worm worm;

    public Camera(Worm worm) {
        this.worm = worm;
        position = new Vector3f(0, 0, 0);

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
    /*
     public void lookThrough() {
     //roatate the pitch around the X axis
     glRotatef(pitch, 1.0f, 0.0f, 0.0f);
     //roatate the yaw around the Y axis
     GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
     //translate to the position vector's location
     GL11.glTranslatef(position.x, position.y, position.z);
     }
     */

    public void move() {
/*
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (worm.getRotY() + angleAroundPlayer);
*/
        //float x = distance * sin(yaw);
        // float z = distance * cos(yaw);
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.position.z += 0.2f;
            System.out.println("camera x: " + this.position.x + "\tz: " + this.position.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.position.z -= 0.2f;
            System.out.println("camera x: " + this.position.x + "\tz: " + this.position.z);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
            System.out.println("camera x: " + this.position.x + "\tz: " + this.position.z);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
            System.out.println("camera x: " + this.position.x + "\tz: " + this.position.z);
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

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = worm.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = worm.getPosition().x - offsetX;
        position.z = worm.getPosition().z - offsetZ;
        position.y = worm.getPosition().y + verticalDistance;
        System.out.println("calculate x: " + position.x + " y: " + position.y + " z: " + position.y);

    }

}
