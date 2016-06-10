/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolbox;

import org.lwjgl.input.Keyboard;


public class KeyboardHandler {
    
    int turn;
    
    public KeyboardHandler(){
        turn = 0;
    }
    
    public KeyboardHandler(int turn){
        this.turn = turn;
    }
    
    
    public int getTurn(){
        return checkInputs();
    }
    
    private int checkInputs() {        
        //if (Keyboard.isKeyRelease(Keyboard.KEY_RETURN)) {            
        //    turn = (turn+1)%2;		
       //     System.out.println("turn: " + turn);
      //  } 
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                    turn = (turn+1)%2;		
                    System.out.println("turn: " + turn);
                }
            }
            /*else {    
                if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                    System.out.println("A Key Released");
                }
            }*/
            
        }
        return turn;
    }
}
