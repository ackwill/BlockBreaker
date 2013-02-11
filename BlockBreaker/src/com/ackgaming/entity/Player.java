package com.ackgaming.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Player extends Entity{
	public Player(Image img) {
		image = img;
		initPlayer();
	}
	
	public void initPlayer() {
		x = 400;
		y = 472;
		visible = true;
	}
	
	public void stop() {
		accelX = 0;
		accelY = 0;
	}
	
	public void update() {
		x += accelX;
		y += accelY;
		
		if(x < 0) {
			x = 0;
		} else if(x + image.getWidth() > 600) {
			x = 600 - image.getWidth();
		}
	}
	
	public void draw() {
		image.draw((float)x, (float)y);
	}
}
