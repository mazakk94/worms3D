package entities;

import java.util.HashMap;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class Worm {

    private HashMap<Entity, Loader> wormEntities;
    // keys -> Entities
    // values -> Loaders
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;

    public Worm() {
        this.wormEntities = initWorm();
    }

    

    private HashMap<Entity, Loader> initWorm() {

        wormEntities = new HashMap();

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

    public void loadWorm(StaticShader shader, Renderer renderer) {
        Entity[] entities = getWormEntities();
        for (Entity entity : entities) {
            entity.move();
            //entity.increaseRotation(0, 1, 0);
        }

        for (Entity entity : entities) {
            renderer.render(entity, shader);
        }
    }

    public float getRotY() {
        Entity[] entities = getWormEntities();
        return entities[0].getRotY();
    }

    public Vector3f getPosition() {
        Entity[] entities = getWormEntities();
        return entities[0].getPosition();
    }
    
    public HashMap<Entity, Loader> getHashEntities() {
        return wormEntities;
    }

    public Entity[] getWormEntities() {
        Entity entities[];
        entities = this.wormEntities.keySet().toArray(new Entity[this.wormEntities.size()]);
        
        return entities;
    }

}
