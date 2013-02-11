package com.ackgaming;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
  
public class Menu extends BasicGameState {
  
    int stateID = -1;
  
    Menu( int stateID ) 
    {
       this.stateID = stateID;
    }
  
    @Override
    public int getID() {
        return stateID;
    }
  
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
  
    }
  
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.setColor(Color.blue);
    	g.fillRect((gc.getWidth()/2)-38, gc.getHeight()/2, 100, 20);
    	g.setColor(Color.white);
    	g.drawString("Play Game", (gc.getWidth()/2)-30, gc.getHeight()/2);
    }
  
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	int mousex = input.getMouseX();
        int mousey = input.getMouseY();
        if(mousex > (gc.getWidth()/2)-38 && mousex < ((gc.getWidth()/2)-38) + 100 &&
        		mousey > gc.getHeight()/2 && mousey < (gc.getHeight()/2) + 20){
    		if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
    			sbg.enterState(BlockBreaker.GAMEPLAYSTATE);
    		}
        }
    }
  
}