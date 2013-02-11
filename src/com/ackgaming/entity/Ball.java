package com.ackgaming.entity;

import org.newdawn.slick.Image;

public class Ball extends Entity{
	
	double pausedX;
	double pausedY;

	public Ball(Image img, int fps) {
		image = img;
		initBall();
		if(fps < 380) {
			accelX *= 1.5;
			accelY *= 1.5;
		}
	}
	
	public void initBall() {
		accelY = .6;
		accelX = .6;
		x = 250;
		y = 300;
	}
	
	public void stop() {
		if(accelX != 0 && accelY != 0) {
			pausedX = accelX;
			pausedY = accelY;
		}
		accelX = 0;
		accelY = 0;
	}
	
	public void resume() {
		accelX = pausedX;
		accelY = pausedY;
		pausedX = 0;
		pausedY = 0;
	}
	
	public void update() {
		x += accelX;
		y += accelY;
		
		if(x < 0 ){
			accelX *= -1;
		} else if(x > 600 - image.getWidth()){
			accelX *= -1;
		} else if(y < 0){
			accelY *= -1;;
		}
		
	}
	
	public void draw() {
		image.draw((float)x, (float)y);
	}
}
