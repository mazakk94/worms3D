/*
    LESSON 1 - DISPLAY
    LESSON 3 - RENDERING 
        *index buffer - jest po to, żeby powiedzieć openGLowi w jaki sposób ma połączyć wierzchołki
        może jakies zmianki???
*/

package engineTester;

import entities.Camera;
import entities.Entity;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Renderer;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args){ //testujemy naszą aplikacje
        
        DisplayManager.createDisplay(); //tworzymy displaya        
        Loader loader = new Loader();        
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        
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
        
       // RawModel model = loader.loadToVAO(vertices, textureCoords, indices); //rawmodel do 10 lekcji
        RawModel model = OBJLoader.loadObjModel("stall", loader);
        //TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image2"))); do 10
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
        
        Entity entity = new Entity(staticModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        
        Camera camera = new Camera();
        //pętla
        while(!Display.isCloseRequested()){
            //if(entity.getPosition().x > 1.5f)
            entity.increaseRotation(0,1,0);
            //entity.increasePosition(0, 0, -0.01f);
            entity.move();
            camera.move();
            //entity.increaseRotation(0, 1, 0);
            //System.out.println(entity.getPosition().z);
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            //logika gry
            renderer.render(entity, shader);    
            shader.stop();
            DisplayManager.updateDisplay();          
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
        
    }
}
