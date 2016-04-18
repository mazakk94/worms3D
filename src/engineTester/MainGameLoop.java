/*
 LESSON 1 - DISPLAY
 LESSON 3 - RENDERING 
 *index buffer - jest po to, żeby powiedzieć openGLowi w jaki sposób ma połączyć wierzchołki
 może jakies zmianki???
 dodaliśmy model, co dalej? trzeba wormsa wczytac w jednej czesci
 */
package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Worm;
import java.util.HashMap;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

    public static HashMap<Entity, Loader> initWorm() {

        HashMap<Entity, Loader> wormEntities = new HashMap();

        Loader handsLoader = new Loader();
        RawModel ObjHands = OBJLoader.loadObjModel("Hands", handsLoader);
        TexturedModel smHands = new TexturedModel(ObjHands, new ModelTexture(handsLoader.loadTexture("Hands")));
        Entity enHands = new Entity(smHands, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        wormEntities.put(enHands, handsLoader);

        Loader bodyLoader = new Loader();
        RawModel ObjBody = OBJLoader.loadObjModel("worm", bodyLoader);
        TexturedModel smBody = new TexturedModel(ObjBody, new ModelTexture(bodyLoader.loadTexture("body")));
        Entity enBody = new Entity(smBody, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        wormEntities.put(enBody, bodyLoader);

        Loader eyesLoader = new Loader();
        RawModel ObjEyes = OBJLoader.loadObjModel("Eyes", eyesLoader);
        TexturedModel smEyes = new TexturedModel(ObjEyes, new ModelTexture(eyesLoader.loadTexture("Eyes")));
        Entity enEyes = new Entity(smEyes, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        wormEntities.put(enEyes, eyesLoader);

        Loader teethLoader = new Loader();
        RawModel ObjTeeth = OBJLoader.loadObjModel("Teeth", teethLoader);
        TexturedModel smTeeth = new TexturedModel(ObjTeeth, new ModelTexture(teethLoader.loadTexture("Teeth")));
        Entity enTeeth = new Entity(smTeeth, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        wormEntities.put(enTeeth, teethLoader);

        return wormEntities;
    }

    public static void loadWorm(Entity[] wormEntities, StaticShader shader, Renderer renderer) {
        for (Entity entity : wormEntities) {
            entity.move();
            //entity.increaseRotation(0, 1, 0);
        }

        for (Entity entity : wormEntities) {
            renderer.render(entity, shader);
        }
    }

    public static void main(String[] args) { //testujemy naszą aplikacje

        DisplayManager.createDisplay(); //tworzymy displaya        
        //loader

        Worm worm = new Worm();
        HashMap<Entity, Loader> wormEntities = initWorm();
        // keys -> Entities
        // values -> Loaders

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        // RawModel model = loader.loadToVAO(vertices, textureCoords, indices); //rawmodel do 10 lekcji
        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image2"))); do 10
        //RTE
        Camera camera = new Camera(0,0,0);
        //pętla

        float dx = 0.0f;
        float dy = 0.0f;

        float mouseSensitivity = 0.05f;
        float movementSpeed = 10.0f; //move 10 units per second

        while (!Display.isCloseRequested()) {
            camera.move();

            dx = Mouse.getDX();
            dy = Mouse.getDY();

            System.out.println("dx: "+dx + " dy:" + dy);
                //controll camera yaw from x movement fromt the mouse
            //camera.yaw(-dx * mouseSensitivity);
                //controll camera pitch from y movement fromt the mouse
            //camera.pitch(dy * mouseSensitivity);
            //camera.lookThrough();

            //entity.increaseRotation(0, 1, 0);
            //System.out.println(entity.getPosition().z);
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            //logika gry
            Entity entities[] = wormEntities.keySet().toArray(new Entity[wormEntities.size()]);
            loadWorm(entities, shader, renderer);//todo 

            shader.stop();
            DisplayManager.updateDisplay();

            //GL11.glLoadIdentity();
            //look through the camera before you draw anything
        }
        shader.cleanUp();

        for (Loader loader : wormEntities.values()) {
            loader.cleanUp();
        }
        // handsLoader.cleanUp();
        // bodyLoader.cleanUp();
        DisplayManager.closeDisplay();

    }
}

/*int[] indices = {
 0, 1, 3, 
 3, 1, 2            
 };
        
 float[] vertices = {
 -0.5f,  0.5f, 0.0f, //V0
 -0.5f, -0.5f, 0.0f, //V1
 0.5f, -0.5f, 0.0f, //V2
 0.5f,  0.5f, 0.0f  //V3
 };
        
 float[] textureCoords = {
 0,0, //V0
 0,1, //V1
 1,1, //V2
 1,0  //V3
 };*/
        //KOPIUJ
/*   float[] vertices = {			
 -0.5f,0.5f,-0.5f,	
 -0.5f,-0.5f,-0.5f,	
 0.5f,-0.5f,-0.5f,	
 0.5f,0.5f,-0.5f,		

 -0.5f,0.5f,0.5f,	
 -0.5f,-0.5f,0.5f,	
 0.5f,-0.5f,0.5f,	
 0.5f,0.5f,0.5f,

 0.5f,0.5f,-0.5f,	
 0.5f,-0.5f,-0.5f,	
 0.5f,-0.5f,0.5f,	
 0.5f,0.5f,0.5f,

 -0.5f,0.5f,-0.5f,	
 -0.5f,-0.5f,-0.5f,	
 -0.5f,-0.5f,0.5f,	
 -0.5f,0.5f,0.5f,

 -0.5f,0.5f,0.5f,
 -0.5f,0.5f,-0.5f,
 0.5f,0.5f,-0.5f,
 0.5f,0.5f,0.5f,

 -0.5f,-0.5f,0.5f,
 -0.5f,-0.5f,-0.5f,
 0.5f,-0.5f,-0.5f,
 0.5f,-0.5f,0.5f
				
 };

 float[] textureCoords = {

 0,0,
 0,1,
 1,1,
 1,0,			
 0,0,
 0,1,
 1,1,
 1,0,			
 0,0,
 0,1,
 1,1,
 1,0,
 0,0,
 0,1,
 1,1,
 1,0,
 0,0,
 0,1,
 1,1,
 1,0,
 0,0,
 0,1,
 1,1,
 1,0
 };

 int[] indices = {
 0,1,3,	
 3,1,2,	
 4,5,7,
 7,5,6,
 8,9,11,
 11,9,10,
 12,13,15,
 15,13,14,	
 16,17,19,
 19,17,18,
 20,21,23,
 23,21,22
 };

 //WKLEJ
        
        
 */
/* float[] vertices = {    // bez indexowania
 -0.5f,  0.3f, 0.0f, //pierwszy trójkąt
 -0.5f, -0.5f, 0.0f, 
 0.5f, -0.5f, 0.0f, 
             
 0.5f, -0.5f, 0.0f, //drugi trójkąt
 0.5f,  0.3f, 0.0f, 
 -0.5f,  0.3f, 0.0f
 };  */          //do 10 lekcji
