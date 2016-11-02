package com.iwenk.tankwar;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.iwenk.tankwar.BUllet;
import com.iwenk.tankwar.Direction;
import com.iwenk.tankwar.HeroTank;
import com.iwenk.tankwar.TankWar;
import com.iwenk.tankwar.Tool;

public class EnemyTank extends Tank
{

	/**
	 * @param tw
	 */
	int step = Tool.random.nextInt(20) + 3;//坦克走固定步数后转变方向，使看上去个更自然

	public EnemyTank(TankWar tw)
	{
		this.x = Tool.random.nextInt(800);
		this.y = Tool.random.nextInt(400);
		speed = 3;
		dir = Tool.direction[Tool.random.nextInt(8)];

		// Image image = Tool.getImage("herotank-u.png");
		imageId = Tool.random.nextInt(8);
		width = Tool.heroImage[0].getWidth(null);// 设置宽高防止越界
		height = Tool.heroImage[0].getHeight(null);
		this.tw = tw;
		bulletDirection = Tool.direction[Tool.random.nextInt(8)];
		live = true;
		// this.x = x;
		// this.y = y;
		// this.tw = tw;
	}

	public void draw(Graphics g)
	{
		if (!live)
			return;
		g.drawImage(Tool.EnemyImage[imageId], x, y, null);
		step--;
		move();
		if (Tool.random.nextInt(30)>28)
		{
			
			tw.bullets.add(Fire());
		}
		if (step == 0)
		{
			imageId = Tool.random.nextInt(8);
			dir = Tool.direction[imageId];
			step = Tool.random.nextInt(20) + 3;
		}
	}

	public void move()
	{
		oldX = x;
		oldY = y;
		if (dir != Direction.STOP)
		{
			bulletDirection = dir;
		}
		switch (dir)
		{
		case L:
			x -= speed;
			break;
		case LU:
			x -= speed * Math.cos(Math.PI / 4);
			y -= speed * Math.sin(Math.PI / 4);
			break;
		case U:
			y -= speed;
			break;
		case RU:
			x += speed * Math.cos(Math.PI / 4);
			y -= speed * Math.sin(Math.PI / 4);
			break;
		case R:
			x += speed;
			break;
		case RD:
			x += speed * Math.cos(Math.PI / 4);
			y += speed * Math.sin(Math.PI / 4);
			break;
		case D:
			y += speed;
			break;
		case LD:
			x -= speed * Math.cos(Math.PI / 4);
			y += speed * Math.sin(Math.PI / 4);
			break;
		}
		if (x < 0)
		{
			x = 0;
		}
		if (y < 25)
		{
			y = 25;
		}
		if (x > TankWar.WIDTH - EnemyTank.width)
		{
			x = TankWar.WIDTH - EnemyTank.width;
		}
		if (y > TankWar.HEIGHT - EnemyTank.height)
		{
			y = TankWar.HEIGHT - EnemyTank.height;
		}
	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, width, height);
	}

	public BUllet Fire()
	{
		return new BUllet(x + EnemyTank.width / 2 - BUllet.width / 2, y
				+ EnemyTank.height / 2 - BUllet.height / 2, bulletDirection, tw,
				false);// 炮弹
	}
}
