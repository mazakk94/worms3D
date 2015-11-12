/*
    LESSON 1 - DISPLAY
*/


package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS_CAP = 120;
    
    @SuppressWarnings("CallToPrintStackTrace") // TEGO NIE TRZEBA
    public static void createDisplay(){    //tworzymy klatke 
        
        ContextAttribs attribs = new ContextAttribs(3, 2)   //3,2 - wersje opengla
        .withForwardCompatible(true)
        .withProfileCore(true);
        
        try {          
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("Hello World!");
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
        
        //mówimy mu gdzie ma renderować naszą gre
        GL11.glViewport(0, 0, WIDTH, WIDTH); // LEWY GÓRNY, PRAWY DOLNY
        
    }
    
    public static void updateDisplay(){ //update'ujemy klatke      
        //1. ustalamy FPSy
        //2. update'ujemy 
        Display.sync(FPS_CAP);
        Display.update(); 
    }
    
    public static void closeDisplay(){  //zamykamy klatke
        Display.destroy();   
    } 
}
