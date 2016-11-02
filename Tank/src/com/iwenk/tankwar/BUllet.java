package com.iwenk.tankwar;

import java.awt.Graphics;
//import java.awt.List;
import java.awt.Rectangle;
import java.util.List;

public class BUllet
{
	int x = 100, y = 100;
	int imageID;
	public static int width = Tool.bulletImage[0].getWidth(null);// 设置宽高防止越界
	public static int height = Tool.bulletImage[0].getHeight(null);
	boolean live = true;
	boolean good;
	TankWar tWar;
	Direction dir = Direction.D;
	int speed = 10;
	public static int KillNum;

	/**
	 * @param x
	 * @param y
	 * @param dir
	 */
	public BUllet(int x, int y, Direction dir, TankWar tWar, boolean good)
	{
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tWar = tWar;
		this.good = good;
	}

	public BUllet(int x, int y, int height)
	{
		super();
		this.x = x;
		this.y = y;
		this.height = height;
	}

	public void draw(Graphics g)
	{
		if (!live)
		{
			tWar.bullets.remove(this);
			return;
		}
		g.drawImage(Tool.bulletImage[0], x, y, null);
		move();
		// HitTank(tWar.enemyTank);
		hitTanks(tWar.Tanks);
	}

	public void hitTanks(List<Tank> TankList)
	{
		for (Tank eTank : TankList)
		{
			if (HitTank(eTank))// 坦克与子弹碰撞
			{
				break;
			}
			for (Tank eTank2 : TankList)// 坦克与坦克碰撞，则退回上一步
			{
				if (eTank2 != eTank)
				{
					if (eTank.live
							&& eTank2.live
							&& eTank.getRectangle().intersects(
									eTank2.getRectangle()))
					{
						eTank.Comeback();// 退回上一步
						eTank2.Comeback();
						break;
					}
				}
			}
		}
	}

	public void move()
	{
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
		// if (x < 0)
		// {
		// x = 0;
		// }
		// if (y < 25)
		// {
		// y = 25;
		// }
		if ((x < 0) || (y < 0) || (x > TankWar.WIDTH) || (y > TankWar.HEIGHT))
		{
			live = false;
		}

	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, width, height);
	}

	public boolean HitTank(Tank tank)
	{
		if (((tank instanceof EnemyTank) && (this.good))
				&& this.live// 敌人坦克与我方子弹
				&& tank.live
				&& this.getRectangle().intersects(tank.getRectangle()))
		{
			this.live = false;

			tank.live = false;
			tWar.Tanks.remove(tank);
			tWar.explodes.add(new Explode(tank.x - (Explode.width - Tank.width)
					/ 2, tank.y - (Explode.height - Tank.height), tWar));

			KillNum++;
			if (KillNum % 2 == 0)
			{
				Tank.BloodNumber++;
				if (Tank.BloodNumber > 5)
				{
					Tank.BloodNumber = 5;
				}
				// System.out.println("--------------------------"+HeroTank.BloodNumber);
				// System.out.println("--------------------------"+Tank.BloodNumber);

			}

			// if (KillNum/2==0)
			// {
			// Tank.BloodNumber++;
			// System.out.println("--------------------------"+HeroTank.BloodNumber);
			// }

			return true;// return只执行一次
		}
		if ((tank instanceof HeroTank) && (!this.good)
				&& this.live// 我方坦克与敌人子弹
				&& tank.live
				&& this.getRectangle().intersects(tank.getRectangle()))
		{
			Tank.BloodNumber--;
			// System.out.println(HeroTank.BloodNumber+"--------------------------");
			// if (KillNum%2==1)
			// {
			// Tank.BloodNumber++;
			// System.out.println("--------------------------"+HeroTank.BloodNumber);
			// }
			// HeroTank.AddBlood();
			this.live = false;
			tWar.explodes.add(new Explode(tank.x - (Explode.width - Tank.width)
					/ 2, tank.y - (Explode.height - Tank.height), tWar));
			// System.out.println(tank.BloodNumber+"---------------");
			if (Tank.BloodNumber <= 0)
			{
				tank.live = false;
				HeroTank.lifeNum--;
				if (HeroTank.ComeBackToLife <0)
				{

					tWar.Tanks.remove(tank);
				}
			}
		}
		// if (good)
		// {
		//
		// }
		return false;
	}
}
