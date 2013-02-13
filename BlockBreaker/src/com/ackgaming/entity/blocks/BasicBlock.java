package com.ackgaming.entity.blocks;

import org.newdawn.slick.Image;

import com.ackgaming.entity.Entity;

public class BasicBlock extends Entity {
	
	boolean destroyed;
	
	public BasicBlock(Image img, int bx, int by) {
		x = bx;
		y = by;
		image = img;
		intitBasicBlock();
	}
	
	public void intitBasicBlock() {
		visible = true;
		destroyed = false;
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
