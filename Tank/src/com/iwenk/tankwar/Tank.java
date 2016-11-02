package com.iwenk.tankwar;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Tank
{
	int x, y;
	int oldX, oldY;
	int speed;
	int imageId;
	static int width;// 设置宽高防止越界
	static int height;
	Direction dir;
	Direction bulletDirection;
	// Image image = Tool.getImage("herotank-u.png");
	TankWar tw;
	static int BloodNumber = 1;
	boolean live = true;
	public void Comeback() {
		x = oldX;
		y = oldY;
	}
	public void draw(Graphics g)
	{

	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, width, height);
	}
}
