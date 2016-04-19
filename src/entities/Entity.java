/*
 lesson 8 - entities, czyli instancje każdego oteksturowanego obiektu graficznego za pomocą jednego VAO
 */
package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void move() {
       
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            increaseRotation(0, 0.5f, 0);
            //System.out.println(rotY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            increaseRotation(0, -0.5f, 0);
            //System.out.println(rotY);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {

            // Obtain angle in degrees from user
            double degs = (double) rotY;
            // Convert degrees to radian
            float rads = (float) Math.toRadians(degs);
            int quarter = (int)degs/90;
            
            boolean front = false;
            boolean right = false;
            if(quarter == 3 || quarter == 0){
                front = true;
            } else if (quarter == 2 || quarter == 1){
                front = false;
            } else {
                System.out.println(quarter + ": quarter error!");
            }
            
            if(quarter == 1 || quarter == 0){
                right = true;
            } else if (quarter == 2 || quarter == 3){
                right = false;
            } else {
                System.out.println(quarter + ": quarter error2!");
            }
            
            // Calculate cotangent
            float coTanA = 1.0f / (float) Math.tan(rads);
            //System.out.println("Cotangent = " + coTanA);

            //System.out.println(rotY);
            
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
                right = !right;
                front = !front;
            }
            
            if (right == true)
                this.position.x += 0.5f;
            else {
                this.position.x-= 0.5f;
            }
            
            
            if (front == true)
                this.position.z += 0.5f;
            else {
                this.position.z-= 0.5f;
            }
            //jeszcze odleglosc
            System.out.println("worm x: " + this.position.x + "\tz" + this.position.z);
        }
       
    }

    //PRZESUWANIE OBIEKTU 
    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        while (rotX > 360.0 || rotX < 0) {
            if (rotX > 360) {
                rotX -= 360;
            }
            if (rotX < 0) {
                rotX += 360;
            }
        }
        this.rotY += dy;
        while (rotY > 360.0 || rotY < 0) {
            if (rotY > 360) {
                rotY -= 360;
            }
            if (rotY < 0) {
                rotY += 360;
            }
        }
        this.rotZ += dz;
        while (rotZ > 360.0 || rotZ < 0) {
            if (rotZ > 360) {
                rotZ -= 360;
            }
            if (rotZ < 0) {
                rotZ += 360;
            }
        }
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}
