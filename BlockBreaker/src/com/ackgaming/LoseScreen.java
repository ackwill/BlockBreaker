package com.ackgaming;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

  
public class LoseScreen extends BasicGameState {
  
    int stateID = -1;
  
    LoseScreen( int stateID ) 
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
    	g.drawString("YOU LOSE", gc.getWidth()/2, gc.getHeight()/2);
    	g.drawString("Your score: " + Game.score, 180, 350);
    	g.drawString("Level: " + Game.lev, 360, 350);
    }
  
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	
    }
  
}