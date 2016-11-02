package com.iwenk.tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TankWar extends Frame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image iBuffer = null;// 声明一个次画面对象
	private Graphics gBuffer; // 声明一个次画面绘制对象
	private Thread newThead;
	public static int HeighKillNum = 0;
	public static final int X = 300;
	public static final int Y = 100;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	// public Icon bg = Tool.getImageIcon("bg.jpg");
	public Image bg = Tool.getImage("bg.jpg");
	// public Icon icon = Tool.getImageIcon("herotank-l.png");
	public Image icon = Tool.getImage("herotank-l.png");
	public HeroTank hero = new HeroTank(this);
	// public EnemyTank enemyTank=new EnemyTank(this);
	// public BUllet bullet;
	public CopyOnWriteArrayList<Tank> Tanks = new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<BUllet> bullets = new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<Explode> explodes = new CopyOnWriteArrayList<>();
	Font font = new Font("黑体",Font.BOLD,30);
	// public Explode explode ;
	public TankWar()
	{
		this.setTitle("坦克大战");
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(X, Y);
		this.setVisible(true);
		this.setResizable(false);
		this.setIconImage(icon);
		this.addKeyListener(new KeyMonitor());
		Tanks.add(hero);
		new Thread(new PaintTread()).start();
		for (int i = 0; i < 5; i++)
		{
			Tanks.add(new EnemyTank(this));
		}
		// iBuffer= createImage(getWidth(),getHeight());//初始化次画面对象
		// gBuffer = iBuffer.getGraphics();
	}

	public void start()
	{
		newThead = new Thread();
		newThead.start();
	}

	private class KeyMonitor extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			hero.KeyPressed(e);
			// System.out.println("hero.KeyPressed(e)");
		}

		// @Override
		public void keyReleased(KeyEvent e)
		{
			// TODO Auto-generated method stub
			hero.KeyReleased(e);
		}
		

	}

	public void update(Graphics g)
	{
		if (iBuffer == null)
		{

			iBuffer = createImage(getWidth(), getHeight());// 创建图形缓冲
//			iBuffer = createImage(getWidth(), getHeight());// 创建图形缓冲
		}
		gBuffer = iBuffer.getGraphics();// 获取图形缓冲区的图形
//		gBuffer.setColor(getBackground());
//		gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(gBuffer);// 用paint 方法中编写的绘图过程对图形缓冲区绘图

//		gBuffer.dispose();// 释放图形上下文资源
		g.drawImage(iBuffer, 0, 0, this);// 将图形缓冲区绘制到屏幕上
	}

	private class PaintTread  implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{

				try
				{

					Thread.sleep(50);

				} catch (InterruptedException e)
				{
					// TODO: handle exception
					e.printStackTrace();
				}
				repaint();
				// actionPerformed();
			}

		}
		// public void actionPerformed()
		// {
		//
		// repaint();
		// }

	}

	@Override
	public void paint(Graphics g)
	{
		// g.setColor(null);
		// g.fillOval(0,0,0,0);
		// g.fillRect(0, 0, getWidth(), getHeight());
		DrawBackGround(g);// 画背景
		hero.draw(g);// 坦克
		// enemyTank.draw(g);
		for (Tank e : Tanks)
		{
			if (e != null)
			{
				e.draw(g);
			}
		}
		for (Explode explode : explodes)
		{

			if (explode != null)
			{
				explode.draw(g);
			}
		}
		for (BUllet b : bullets)
		{

			b.draw(g);// 画子弹
		}
		// g.drawString("Bullet Count :" + bullets.size(), 50, 50);
		// if(bullets[i]=null)
		// b.draw(g);//画子弹
		// g.drawString("Bullet Count :" + bullets.size(), 50, 50);
		//g.drawString("HeroTankHP  :" + HeroTank.BloodNumber, 50, 50);//左上角显示血量；
		if (BUllet.KillNum>HeighKillNum)
		{
			HeighKillNum = BUllet.KillNum;
		}
		if (HeighKillNum>=50)
		{
			g.drawString("恭喜过关", 220, 300);
		}
		g.setFont(font);
		g.drawString("当前杀敌:" + HeighKillNum+"头", 570, 80);
		if(Tanks.size() <= 2) {
			for(int i=0; i<5; i++) {
				Tanks.add(new EnemyTank(this));
			}
		}
	}

	public void DrawBackGround(Graphics g)
	{
		int x = 0, y = 0;
		while (true)
		{
			g.drawImage(bg, x, y, this);// 画地图
			x += bg.getWidth(this);
			if (x > WIDTH)
			{
				x = 0;
				y += bg.getHeight(this);
			}
			if (y > HEIGHT)
			{
				break;
			}
		}


	}

	public static void main(String[] args)
	{
		TankWar tk = new TankWar();
		tk.addWindowListener(new WindowAdapter()// 添加窗口关闭处理函数
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}

}
