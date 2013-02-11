package com.ackgaming.entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Entity {
	
	public Image image;
	public double x;
	public double y;
	public double accelY;
	public double accelX;
	
	public boolean visible;
	
	public Entity() {
		visible = true;
	}
	
	public void setVisible(boolean a) {
		visible = a;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setImage(Image pic) {
		image = pic;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, 
		          image.getWidth(), image.getHeight());
	}
}
