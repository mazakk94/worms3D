/*
 LESSON 1 - DISPLAY
 LESSON 3 - RENDERING 
 *index buffer - jest po to, żeby powiedzieć openGLowi w jaki sposób ma połączyć wierzchołki
 może jakies zmianki???
 dodaliśmy model, co dalej? trzeba wormsa wczytac w jednej czesci
 */
package engineTester;

import entities.Camera;
import entities.Worm;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import shaders.StaticShader;

public class MainGameLoop {

    public static void main(String[] args) { //testujemy naszą aplikacje

        DisplayManager.createDisplay(); //tworzymy displaya        
        //loader
            
        Worm worm = new Worm();

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        // RawModel model = loader.loadToVAO(vertices, textureCoords, indices); //rawmodel do 10 lekcji
        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image2"))); do 10
        //RTE
       
        
        
        //Camera camera = new Camera(0,0,0);
        Camera camera = new Camera(worm);
        
        
        //pętla

        float dx = 0.0f;
        float dy = 0.0f;

        float mouseSensitivity = 0.05f;
        float movementSpeed = 10.0f; //move 10 units per second

        while (!Display.isCloseRequested()) {
            camera.move();

            //dx = Mouse.getDX();
            //dy = Mouse.getDY();

            //System.out.println("dx: "+ dx + " dy:" + dy);
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
            
            worm.loadWorm(shader, renderer);

            shader.stop();
            DisplayManager.updateDisplay();

            //GL11.glLoadIdentity();
            //look through the camera before you draw anything
        }
        shader.cleanUp();

        for (Loader loader : worm.getHashEntities().values()) {
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
