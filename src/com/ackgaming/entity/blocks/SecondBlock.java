package com.ackgaming.entity.blocks;

import org.newdawn.slick.Image;

import com.ackgaming.entity.Entity;

public class SecondBlock extends Entity {
	
	boolean destroyed;
	
	public int timesHit = 0;
	
	public SecondBlock(Image img, int bx, int by) {
		x = bx;
		y = by;
		intitBasicBlock();
		image = img;
	}
	
	public void intitBasicBlock() {
		visible = true;
		destroyed = false;
	}
	
	public void setImage(Image img){
		image = img;
	}
	
	public void update() {
		
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void setDestroyed() {
		destroyed = true;
	}
	
	public void draw() {
		image.draw((float)x, (float)y);
	}
}