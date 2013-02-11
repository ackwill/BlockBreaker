package com.ackgaming;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
  

public class BlockBreaker extends StateBasedGame {
  
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
    public static final int WINSCREENSTATE = 2;
    public static final int LOSESCREENSTATE = 3;
  
    public BlockBreaker()
    {
        super("BlockBreaker");
    }
  
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new BlockBreaker());
  
         //app.setDisplayMode(1280,  800, false);
         app.setDisplayMode(600, 500, false);
         app.start();
    }
  
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new Menu(MAINMENUSTATE));
        this.addState(new Game(GAMEPLAYSTATE));
        this.addState(new WinScreen(WINSCREENSTATE));
        this.addState(new LoseScreen(LOSESCREENSTATE));
    }
}