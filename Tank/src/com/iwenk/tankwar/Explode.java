package com.iwenk.tankwar;

import java.awt.Graphics;

public class Explode
{
	int x, y;
	int ImageId;
	boolean live = true;//判断爆炸是否完成一个周期。
	static int width = Tool.ExplodeImage[0].getWidth(null),
			height = Tool.ExplodeImage[0].getHeight(null);
	TankWar tWar;

	public Explode(int x, int y, TankWar tWar)
	{
		super();
		this.x = x;
		this.y = y;
		this.tWar = tWar;
	}

	public void draw(Graphics g)
	{
		if (!live)
		{
			return;
		}
		g.drawImage(Tool.ExplodeImage[ImageId], x, y, null);
		ImageId++;
		if (ImageId == 6)
		{
			this.live = false;
			tWar.explodes.remove(this);

		}
	}

}
