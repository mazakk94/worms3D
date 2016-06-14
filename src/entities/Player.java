package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

    private static final float RUN_SPEED = 40;
    //private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 18;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private int ID;
    private int HP;
    private boolean attacked;

    private boolean isInAir = false;

    public Player(int id, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
                    float scale, int hp) {
            super(model, position, rotX, rotY, rotZ, scale);
            ID = id;
            HP = hp;   
            attacked = false;
    }

    public void move(Terrain terrain, int turn) {
        this.currentSpeed = 0;
        this.currentTurnSpeed = 0;
        if(turn == ID){
           setSpeed();
        }

        //super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        //super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float zDistance = currentTurnSpeed * DisplayManager.getFrameTimeSeconds();
        //float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        //float dz = (float) (zDistance * Math.sin(Math.toRadians(super.getRotY())));
        //float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        //if (dx != 0 || dz != 0)
        //System.out.println("dx " + dx + " dz " + dz);
        //System.out.println("x " + super.getPosition().x + " y " + super.getPosition().y + " z " + super.getPosition().z);
        super.increasePosition(distance, 0, zDistance);
        //float x = super.getPosition().x;
        //float y = super.getPosition().y;
        //float z = super.getPosition().z;
        System.out.println(super.getPosition());
        
        //super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (super.getPosition().y < terrainHeight) {
                upwardsSpeed = 0;
                isInAir = false;
                super.getPosition().y = terrainHeight;
        }
    }

    
    public int getHP(){
        return this.HP;
    }
    
    private void jump() {
        if (!isInAir) {
                this.upwardsSpeed = JUMP_POWER;
                isInAir = true;
        }
    }


    private void setSpeed(){
        float rotY = super.getRotY();
        while(rotY > 360){
            rotY -= 360;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {        
            currentSpeed = (float) (RUN_SPEED * Math.sin(Math.toRadians(rotY)));
            currentTurnSpeed = (float) (RUN_SPEED * Math.cos(Math.toRadians(rotY)));
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            currentSpeed = -(float) (RUN_SPEED * Math.sin(Math.toRadians(rotY)));
            currentTurnSpeed = -(float) (RUN_SPEED * Math.cos(Math.toRadians(rotY)));
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            currentTurnSpeed = (float) -(RUN_SPEED * Math.sin(Math.toRadians(rotY)));
            currentSpeed = (float) (RUN_SPEED * Math.cos(Math.toRadians(rotY)));
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            currentTurnSpeed = (float) (RUN_SPEED * Math.sin(Math.toRadians(rotY)));
            currentSpeed = (float) -(RUN_SPEED * Math.cos(Math.toRadians(rotY)));
        } else {
            this.currentTurnSpeed = 0;
        }
        
        //System.out.println("x " + currentSpeed + " z " +  currentTurnSpeed + " rotY " + rotY);

        if(Mouse.isButtonDown(0)){            
           if (!attacked){
                attacked = true;
                attack();
            }
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
            
        }
        
    }

    private void attack() {
        
        System.out.println(this.ID + " atakuje"); 
    }

}
