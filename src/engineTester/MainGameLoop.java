package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.KeyboardHandler;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {    
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        TextMaster.init(loader);

        FontType font = new FontType(loader.loadTexture("harrington"), new File("res/harrington.fnt"));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
                        gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));


        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain);

        TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),
                        new ModelTexture(loader.loadTexture("lamp")));
        lamp.getTexture().setUseFakeLighting(true);

        List<Entity> entities = new ArrayList<>();
        List<Entity> normalMapEntities = new ArrayList<>();


        List<Light> lights = new ArrayList<>();
        Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
        lights.add(sun);

        MasterRenderer renderer = new MasterRenderer(loader);
        RawModel bunnyModel = OBJLoader.loadObjModel("worm3", loader);
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(
                        loader.loadTexture("Wormpng")));
        
        
        Player[] players = new Player[2];            //(dlugosc, wysokosc, szerokosc)
        players[0] = new Player(0, stanfordBunny, new Vector3f(10, 5, -75), 0, 90, 0, 0.6f, 100);        
        players[1] = new Player(1, stanfordBunny, new Vector3f(140, 5, -75), 0, 270, 0, 0.6f, 100);
        entities.add(players[0]);
        entities.add(players[1]);
        
        
        Camera[] cameras = new Camera[2];//(player1);
        cameras[0] = new Camera(players[0]);
        cameras[1] = new Camera(players[1]);
        
        List<GuiTexture> guiTextures = new ArrayList<>();
        
        
        //ModelTexture crosshairTexture = new ModelTexture(loader.loadTexture("crosshair"));        
        GuiTexture crosshair = new GuiTexture(loader.loadTexture("crosshair"),  new Vector2f(0.02f,/* -0.025f*/0.2f),  new Vector2f(0.05f, 0.1f));
        guiTextures.add(crosshair);
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        
        MousePicker[] pickers = new MousePicker[2];
        pickers[0] = new MousePicker(cameras[0], renderer.getProjectionMatrix(), terrain);
        pickers[1] = new MousePicker(cameras[1], renderer.getProjectionMatrix(), terrain);

        WaterFrameBuffers buffers = new WaterFrameBuffers();
        int turn = 0;
        Player player = players[turn];
        Camera camera = cameras[turn];
        MousePicker picker = pickers[turn];
        
        KeyboardHandler keyboard = new KeyboardHandler();
        
        
        GUIText worm1Text = new GUIText(players[0].getHP() + " HP", 3f, font, new Vector2f(0f, 0.9f), 1f, false);
        worm1Text.setColour(1, 1, 0);
        GUIText worm2Text = new GUIText(players[1].getHP() + " HP", 3f, font, new Vector2f(0.85f, 0.9f), 1f, false);
        worm2Text.setColour(1, 1, 0);
        
        
        try {
            Texture worm = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/crosshair.png")));
        } catch (IOException ex) {
            Logger.getLogger(MainGameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        while (!Display.isCloseRequested()) {
                turn = keyboard.getTurn();
                player = players[turn];
                camera = cameras[turn];
                picker = pickers[turn];
                
                players[0].move(terrain, turn);  
                players[1].move(terrain, turn);
                
                
                camera.move();
                picker.update();
                GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

                //render reflection texture
                buffers.bindReflectionFrameBuffer();
                float distance = 2 * (camera.getPosition().y);
                camera.getPosition().y -= distance;
                camera.invertPitch();
                renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, 0));//-water.getHeight()+1));
                camera.getPosition().y += distance;
                camera.invertPitch();

                //render refraction texture
                buffers.bindRefractionFrameBuffer();
                renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 0));//water.getHeight()));

                //render to screen
                GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
                buffers.unbindCurrentFrameBuffer();	
                renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
                guiRenderer.render(guiTextures);
                TextMaster.render();

                DisplayManager.updateDisplay();
        }

        //*********Clean Up Below**************

        TextMaster.cleanUp();
        buffers.cleanUp();
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
