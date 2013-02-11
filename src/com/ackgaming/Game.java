package com.ackgaming;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.ackgaming.entity.Ball;
import com.ackgaming.entity.Entity;
import com.ackgaming.entity.Player;
import com.ackgaming.entity.blocks.BasicBlock;
import com.ackgaming.entity.blocks.SecondBlock;
import com.ackgaming.entity.blocks.ThirdBlock;
  
public class Game extends BasicGameState {
  
    int stateID = -1;
    
    
    
    int number = 0;
    
    public static int score = 0;
    public static int lev = 0;
    public static int lives = 3;
    
    public boolean paused = false;
    
    
    String stage;
    
    
    Image playerImg;
    
    Image basicBlockImg;
    Image secondBlockImg;
    Image thirdBlockImg;
    
    Image ballImg;
    
    Sound bounce;
    Sound brick;
    
    Player paddle;
    Ball ball;
    Levels level = new Levels();
    
    ArrayList blocks = new ArrayList<Entity>();
    
  
    Game( int stateID ) {
       this.stateID = stateID;
    }
    
    
  
    @Override
    public int getID() {
        return stateID;
    }
 
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	
    	 playerImg = new Image("res/player.png");
    	 ballImg = new Image("res/ball.png");
    	 
    	 basicBlockImg = new Image("res/block.png");
    	 secondBlockImg = new Image("res/secondBlock.png");
    	 thirdBlockImg = new Image("res/thirdBlock.png");
    	 
    	 paddle = new Player(playerImg);
    	 ball = new Ball(ballImg, gc.getFPS());
    	 
    	 bounce = new Sound("res/sounds/bounce.wav");
    	 brick = new Sound("res/sounds/brick.wav");
    	 
    	 
    }
  
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	//backgroundImg.draw();
    	if(blocks.size() == 0) {
    		if(!(number > level.level.length - 1)){
    			blocks = level.loadLevel(basicBlockImg, secondBlockImg, thirdBlockImg, number);
        		number++;
        		lev = number;
        		ball.initBall();
        		paddle.initPlayer();
        		g.drawString("Level: ", 60, 450);
    		} else {
			sbg.enterState(BlockBreaker.WINSCREENSTATE);
    		}
    	}
    	ball.draw();
    	paddle.draw();
    	
    	//Draw stats
    	g.drawString("Score: " + score, 60, 430);
    	g.drawString("Level: " + lev, 240, 430);
    	g.drawString("Lives: " + lives, 420, 430);
    	
    	
    	for(int i=0; i<blocks.size(); i++) {
    		if(blocks.get(i) instanceof BasicBlock) {
    			BasicBlock b = (BasicBlock) blocks.get(i);
        		b.draw();
    		} else if(blocks.get(i) instanceof SecondBlock) {
    			SecondBlock b = (SecondBlock) blocks.get(i);
    			b.draw();
    		} else if(blocks.get(i) instanceof ThirdBlock) {
    			ThirdBlock b = (ThirdBlock) blocks.get(i);
				b.draw();
    		}
    	}
    	
    	
    }
  
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	
    	if(input.isKeyPressed(Input.KEY_SPACE) && paused) {
    		paused = false;
    		ball.resume();
    	}
    	
    	//Key control
    	if(input.isKeyDown(Input.KEY_LEFT)){
    		paddle.accelX = -1;
    	} else if(input.isKeyDown(Input.KEY_RIGHT)){
    		paddle.accelX = 1;
    	} else paddle.accelX = 0;
    	
    	//Add mouse control
    	
    	if(input.isKeyPressed(Input.KEY_ESCAPE)) {
    		paused = true;
    	}
    	
    	if(paused) {
    		ball.stop();
    		paddle.stop();
    	}
    	
    	
    	paddle.update();
    	ball.update();
    	
    	
    	checkColision();
    	
    	if(ball.y > paddle.y) {
    		if(lives == 0) {
    			sbg.enterState(BlockBreaker.LOSESCREENSTATE);
    		} else {
    			lives -= 1;
    			ball.initBall();
    			paddle.initPlayer();
    		}
    	}
    	
    	/*if(gc.getFPS() < 180) {
       	 	gc.setShowFPS(true);
    	} else gc.setShowFPS(false);*/
    }
    
    public boolean checkBoxPos(Entity b) {
    	if ((ball.getRect()).intersects(b.getRect())) {

        	int ballLeft = (int)ball.getRect().getMinX();
        	int ballHeight = (int)ball.getRect().getHeight();
        	int ballWidth = (int)ball.getRect().getWidth();
        	int ballTop = (int)ball.getRect().getMinY();

        	Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
        	Point pointRightBottom = new Point(ballLeft + ballWidth + 1, ballTop + ballHeight);
        	
        	Point pointLeft = new Point(ballLeft - 1, ballTop);
        	Point pointLeftBottom = new Point(ballLeft - 1, ballTop + ballHeight);
        	
        	Point pointTopLeft = new Point(ballLeft, ballTop - 1);
        	Point pointTopRight = new Point(ballLeft + ballWidth, ballTop -1);
        	
        	Point pointBottomLeft = new Point(ballLeft, ballTop + ballHeight + 1);
        	Point pointBottomRight = new Point(ballLeft + ballWidth, ballTop + ballHeight + 1);
        	

        	if (b.getRect().contains(pointRight)) {
        		ball.accelX *= -1;
        	} else if(b.getRect().contains(pointRightBottom)) {
        		ball.accelX *= -1;
        	} else if (b.getRect().contains(pointLeft)) {
        		ball.accelX *= -1;
        	} else if(b.getRect().contains(pointLeftBottom)) {
        		ball.accelX *= -1;
        	} else if (b.getRect().contains(pointTopLeft)) {
        		ball.accelY *= -1;
        	} else if(b.getRect().contains(pointTopRight)) {
        		ball.accelY *= -1;
        	} else if (b.getRect().contains(pointBottomLeft)) {
        		ball.accelY *= -1;
        	} else if(b.getRect().contains(pointBottomRight)) {
        		ball.accelY *= -1;
        	}
        	brick.play();
        	return true;
		} else 
    	return false;
    }
    
    public void checkColision() {
    	//Check intersection with ball and paddle
    	if (ball.getRect().intersects(paddle.getRect())) {

            int paddleLPos = (int)paddle.getRect().getMinX();
            int ballMPos = (int)ball.getRect().getMinX() + 10;

            int first = paddleLPos + 30;
            int second = paddleLPos + 90;
            int third = paddleLPos + 120;
            
            if(ballMPos <= first && ballMPos >= paddleLPos) {
            	ball.accelX += .1;
            	ball.accelY += .1;
            	ball.accelX *= -1;
            	ball.accelY *= -1;
            } else
            
            if(ballMPos <= second && ballMPos > first) {
            	ball.accelY *= -1;
            } else

            if(ballMPos <= third && ballMPos > second) {
            	ball.accelX += .1;
            	ball.accelY += .1;
            		ball.accelY *= -1;
                	ball.accelX *= -1;
            }
            bounce.play();
            
        }
    	
    	//Check intersection with ball and blocks
    	for(int i=0; i<blocks.size(); i++) {
    		
    		if(blocks.get(i) instanceof BasicBlock) {
        		BasicBlock b = (BasicBlock) blocks.get(i);
        		
        		if(checkBoxPos(b)) {
        			score += 10;
                	blocks.remove(i);
                	b.setDestroyed();
                	b.setVisible(false);
    			}
    		} else if(blocks.get(i) instanceof SecondBlock){
    			SecondBlock b = (SecondBlock) blocks.get(i);
    			
    			if(checkBoxPos(b)) {
                	b.timesHit++;
                	b.setImage(basicBlockImg);
                	if(b.timesHit == 2) {
                		score += 15;
                		blocks.remove(i);
                		b.setDestroyed();
                		b.setVisible(false);
                	} else score += 5;
    			}
    		} else if(blocks.get(i) instanceof ThirdBlock) {
    			ThirdBlock b = (ThirdBlock) blocks.get(i);
    			
    			if(checkBoxPos(b)) {
                	b.timesHit++;
                	if(b.timesHit == 1){
                		b.setImage(secondBlockImg);
                		score += 5;
                	} else if(b.timesHit == 2) {
                		b.setImage(basicBlockImg);
                		score += 5;
                	} else if(b.timesHit == 3) {
                		score += 20;
                		b.setDestroyed();
                		b.setVisible(false);
                		blocks.remove(i);
                	}
    			}
    		}
    	}
    	
    //Class end
    }
    
    
  
}