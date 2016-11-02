package com.iwenk.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.rmi.CORBA.Util;
import javax.swing.JOptionPane;
import javax.tools.JavaFileManager.Location;
import javax.swing.JOptionPane;  
import org.omg.CORBA.PUBLIC_MEMBER;

public class HeroTank extends Tank
{

	/**
	 * @param tw
	 */
	public static int lifeNum = 4;
	public static int ComeBackToLife = 4;
	Font font = new Font("黑体",Font.BOLD,30);
	boolean bL = false, bU = false, bR = false, bD = false;// 设置按键方向
	private BloodBar bloodBar = new BloodBar();
	public HeroTank(TankWar tw)
	{
		x = 400;
		y = 500;
		speed = 5;
		dir = Direction.STOP;
		bulletDirection = Direction.U;

		// Image image = Tool.getImage("herotank-u.png");
		imageId = 2;
		width = Tool.heroImage[0].getWidth(null);// 设置宽高防止越界
		height = Tool.heroImage[0].getHeight(null);
		this.tw = tw;
		BloodNumber = 5;// 玩家坦克血量为3

	}

	  
	public void draw(Graphics g)
	{
		if (HeroTank.lifeNum<0)
		{
			g.setFont(font);
			g.drawString("失败是成功他妈", 220, 300);
			//JOptionPane.showMessageDialog (tw, this, "游戏结束", 0);
			//JOptionPane.showMessageDialog(null, "提示消息.", "标题",JOptionPane.PLAIN_MESSAGE);  
		}

		g.setFont(font);
		if (!live){
			g.drawString("本局杀敌:" + BUllet.KillNum+"头", 30, 80);
			if (HeroTank.ComeBackToLife>0)
			{
				
				g.drawString("F10,即可复活", 220, 300);
			}
			return;
		}
		g.drawString("剩余复活:" + ComeBackToLife+"次", 30, 80);
		// TODO Auto-generated method stub
		g.drawImage(Tool.heroImage[imageId], x, y, null);
		move();
		bloodBar.draw(g);
		
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
		if (y < 25)// 标题占位置25
		{
			y = 25;
		}
		if (x > TankWar.WIDTH - HeroTank.width)
		{
			x = TankWar.WIDTH - HeroTank.width;
		}
		if (y > TankWar.HEIGHT - HeroTank.height)
		{
			y = TankWar.HEIGHT - HeroTank.height;
		}
		//System.out.println("*********移动成功**********");
	}

	public void KeyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch (key)
		{

		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		}
		// System.out.println("-----------"+key);
		location();
		// System.out.println("location();");
	}

	public void KeyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		// System.out.println("keyReleased "+key);
		switch (key)
		{
		
		case KeyEvent.VK_F10://摁F10复活
			if (!live)
			{
				if (HeroTank.ComeBackToLife>0)
				{
					
					live = true;
					BloodNumber = 5;
					ComeBackToLife--;
				}
			}
			break;
		case KeyEvent.VK_SPACE:
			// tw.bullet = Fire();
			tw.bullets.add(Fire());
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		}

		location();

	}

	private void location()
	{
		if (bL && !bU && !bR && !bD)
		{
			dir = Direction.L;
			imageId = 0;
		} else if (bL && bU && !bR && !bD)
		{
			dir = Direction.LU;
			imageId = 1;
		} else if (!bL && bU && !bR && !bD)
		{
			dir = Direction.U;
			imageId = 2;
		} else if (!bL && bU && bR && !bD)
		{
			dir = Direction.RU;
			imageId = 3;
		} else if (!bL && !bU && bR && !bD)
		{
			dir = Direction.R;
			imageId = 4;
		} else if (!bL && !bU && bR && bD)
		{
			dir = Direction.RD;
			imageId = 5;
		} else if (!bL && !bU && !bR && bD)
		{
			dir = Direction.D;
			imageId = 6;
		} else if (bL && !bU && !bR && bD)
		{
			dir = Direction.LD;
			imageId = 7;
		} else
		{
			dir = Direction.STOP;
			// imageId = 8;
		}

	}

	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, width, height);
	}

	public BUllet Fire()
	{
		// System.out.println("-------------");
		return new BUllet(x + HeroTank.width / 2 - BUllet.width / 2, y
				+ HeroTank.height / 2 - BUllet.height / 2, bulletDirection, tw,
				true);// 炮弹
	}
	static int i = 1;
	public static int AddBlood()
	{
		i++;
		if (BUllet.KillNum == 2*i)
		{
			BloodNumber++;
			
			if (BloodNumber>5)
			{
				BloodNumber = 5;
		    }
			//System.out.println("--------------------------"+HeroTank.BloodNumber);
	    }
		return BloodNumber;
	}

	private class BloodBar {
		
		public void draw(Graphics g) {
				
			g.setColor(Color.RED);
			Color c = g.getColor();
			g.drawRect(x, y-10, width, 10);
			int w = width *BloodNumber/5 ;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
}
