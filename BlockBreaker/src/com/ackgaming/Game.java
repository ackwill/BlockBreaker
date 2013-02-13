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
import com.ackgaming.entity.blocks.BonusLifeBlock;
import com.ackgaming.entity.blocks.SecondBlock;
import com.ackgaming.entity.blocks.ThirdBlock;
  
public class Game extends BasicGameState {
  
    int stateID = -1;
    
    boolean ticked = false;
    int upd;
    
    int number = 0;
    
    public static int score = 0;
    public static int lev = 0;
    public static int lives = 3;
    
    public boolean paused = false;
    boolean dead = false;
    boolean start = true;
    
    
    String stage;
    
    
    Image playerImg;
    
    Image basicBlockImg;
    Image secondBlockImg;
    Image thirdBlockImg;
    
    Image bonusLifeBlockImg;
    Image bonusLifeBlockDamImg;
    
    Image ballImg;
    
    Sound bounce;
    Sound brick;
    Sound lostLife;
    
    Animation pau;
    int d = 5;
    
    Player paddle;
    Ball ball;
    Levels level = new Levels();
    
    ArrayList<Entity> blocks = new ArrayList<Entity>();
    ArrayList<Entity> drops = new ArrayList<Entity>();
    
  
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
    	 
    	 basicBlockImg = new Image("res/blocks/block.png");
    	 secondBlockImg = new Image("res/blocks/secondBlock.png");
    	 thirdBlockImg = new Image("res/blocks/thirdBlock.png");
    	 
    	 
    	 bonusLifeBlockImg = new Image("res/blocks/bonus_blocks/bonus_life_block.png");
    	 bonusLifeBlockDamImg = new Image("res/blocks/bonus_blocks/bonus_life_block_damaged.png");
    	 
    	 paddle = new Player(playerImg);
    	 ball = new Ball(ballImg, gc.getFPS());
    	 
    	 bounce = new Sound("res/sounds/bounce.wav");
    	 brick = new Sound("res/sounds/brick.wav");
    	 lostLife = new Sound("res/sounds/lifelost.wav");
    	 
    	 gc.setTargetFrameRate(300);
    	 gc.setVSync(true);
    	 
    	 
    }
  
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	
    	
    	if(paused && !dead && !start) {
    		g.drawString("Paused", 280, 200);
    	}
    	if(paused && dead) {
    		g.drawString("YOU DIED \nPress Space to Respawn", 250, 200);
    	}
    	if(paused && start && !dead) {
    		if(lev - 1 != 0) {
    			g.drawString("You Passed level " + (lev - 1) + "\nPress Space to Start Level " + lev, 200, 200);
    		} else
    			g.drawString("Press Space Bar to Start The Game", 160, 200);
    	}
    	
    	if(blocks.size() == 0) {
    		if(!(number > level.level.length - 1)){
    			blocks = level.loadLevel(basicBlockImg, secondBlockImg, thirdBlockImg, bonusLifeBlockImg, number);
        		number++;
        		lev = number;
        		ball.initBall();
        		paddle.initPlayer();
        		g.drawString("Level: ", 60, 450);
        		paused = true;
        		start = true;
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
    		} else if(blocks.get(i) instanceof BonusLifeBlock) {
    			BonusLifeBlock b = (BonusLifeBlock) blocks.get(i);
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
    	if(input.isKeyPressed(Input.KEY_SPACE) && paused && dead) {
    		paused = false;
    		ball.initBall();
    		paddle.initPlayer();
    	}
    	if(input.isKeyPressed(Input.KEY_SPACE) && paused && start) {
    		paused = false;
    		start = false;
    	}
    	
    	//Key control
    	if(input.isKeyDown(Input.KEY_LEFT)){
    		paddle.accelX = -8;
    	} else if(input.isKeyDown(Input.KEY_RIGHT)){
    		paddle.accelX = 8;
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
    			score -= 40;
    			lives -= 1;
    			lostLife.play();
    			paused = true;
    			dead = true;
    			ball.initBall();
    			ball.stop();
    			paddle.initPlayer();
    		}
    	}

    	upd++;
    	
    }
    
    public boolean checkBoxPos(Entity b) {
    	if ((ball.getRect()).intersects(b.getRect())) {

        	int ballLeft = (int)ball.getRect().getMinX();
        	int ballHeight = (int)ball.getRect().getHeight();
        	int ballWidth = (int)ball.getRect().getWidth();
        	int ballTop = (int)ball.getRect().getMinY();
        	
        	int blockLeft = (int)b.getRect().getMinX();
        	int blockHeight = (int)b.getRect().getHeight();
        	int blockWidth = (int)b.getRect().getWidth();
        	int blockTop = (int)b.getRect().getMinY();
        	int blockBottom = blockTop + blockHeight;
        	int blockRight = blockLeft + blockWidth;

        	Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop + ballHeight/2);
        	Point pointLeft = new Point(ballLeft - 1, ballTop + ballHeight/2);
        	Point pointTop = new Point(ballLeft + ballWidth/2, ballTop - 1);
        	Point pointBottom = new Point(ballLeft + ballWidth/2, ballTop + ballHeight + 1);
        	Point topLeftCorner = new Point(ballLeft - 1, ballTop);
        	Point topRightCorner = new Point(ballLeft + ballWidth + 1, ballTop);
        	Point bottomLeftCorner = new Point(ballLeft - 1, ballTop + ballHeight);
        	Point bottomRightCorner = new Point(ballLeft + ballWidth + 1, ballTop + ballHeight);
        	
        	

        	if (b.getRect().contains(pointRight)) {
                ball.accelX *= -1;
            }

            else if (b.getRect().contains(pointLeft)) {
                ball.accelX *= -1;
            }

            if (b.getRect().contains(pointTop)) {
                ball.accelY *= -1;
            }
    
            else if (b.getRect().contains(pointBottom)) {
                ball.accelY *= -1;
            }
            
            if(b.getRect().contains(pointTop) && b.getRect().contains(pointLeft)) {
            	ball.accelX *= -1;
            	ball.accelY *= -1;
            } else if(b.getRect().contains(pointTop) && b.getRect().contains(pointRight)) {
            	ball.accelX *= -1;
            	ball.accelY *= -1;
            } else if(b.getRect().contains(pointBottom) && b.getRect().contains(pointLeft)) {
            	ball.accelX *= -1;
            	ball.accelY *= -1;
            } else if(b.getRect().contains(pointBottom) && b.getRect().contains(pointRight)) {
            	ball.accelX *= -1;
            	ball.accelY *= -1;
            }
            
            
            /*else if (b.getRect().contains(topRightCorner) && ballTop <= blockBottom && !b.getRect().contains(pointLeft)) {
                ball.accelY *= -1;
                System.out.println("RightCornDown");
            } else if (b.getRect().contains(topRightCorner) && ballLeft + ballWidth + 1 >= blockLeft && !b.getRect().contains(pointLeft)) {
                ball.accelX *= -1;
                System.out.println("RightCornLeft");
            } else if (b.getRect().contains(topLeftCorner) && ballTop <= blockBottom && !b.getRect().contains(pointRight)) {
                ball.accelY *= -1;
                System.out.println("LeftCornDown");
            } else if (b.getRect().contains(topLeftCorner) && ballLeft - 1 >= blockRight && !b.getRect().contains(pointRight)) {
                ball.accelX *= -1;
                System.out.println("LeftCornRight");
            } else if (b.getRect().contains(bottomLeftCorner) && ballTop + ballHeight + 1 >= blockTop && !b.getRect().contains(pointRight)) {
                ball.accelY *= -1;
                System.out.println("bottome");
            } else if (b.getRect().contains(bottomLeftCorner) && ballLeft + ballWidth + 1 >= blockLeft && !b.getRect().contains(pointRight)) {
                ball.accelX *= -1;
                System.out.println("bottome");
            } else if (b.getRect().contains(bottomRightCorner)) {
                ball.accelY *= -1;
                System.out.println("bottome");
            }*/
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
            bounce.play(5, 7);
            
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
    		} else if(blocks.get(i) instanceof BonusLifeBlock) {
    			BonusLifeBlock b = (BonusLifeBlock) blocks.get(i);
    			
    			if(checkBoxPos(b)) {
                	b.timesHit++;
                	b.setImage(bonusLifeBlockDamImg);
                	if(b.timesHit == 2) {
                		lives++;
                		blocks.remove(i);
                		b.setDestroyed();
                		b.setVisible(false);
                	} else score += 10;
    			}
    		}
    	}
    	
    }
  
}