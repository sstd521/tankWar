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
	private Image iBuffer = null;// ����һ���λ������
	private Graphics gBuffer; // ����һ���λ�����ƶ���
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
	Font font = new Font("����",Font.BOLD,30);
	// public Explode explode ;
	public TankWar()
	{
		this.setTitle("̹�˴�ս");
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
		// iBuffer= createImage(getWidth(),getHeight());//��ʼ���λ������
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

			iBuffer = createImage(getWidth(), getHeight());// ����ͼ�λ���
//			iBuffer = createImage(getWidth(), getHeight());// ����ͼ�λ���
		}
		gBuffer = iBuffer.getGraphics();// ��ȡͼ�λ�������ͼ��
//		gBuffer.setColor(getBackground());
//		gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
		paint(gBuffer);// ��paint �����б�д�Ļ�ͼ���̶�ͼ�λ�������ͼ

//		gBuffer.dispose();// �ͷ�ͼ����������Դ
		g.drawImage(iBuffer, 0, 0, this);// ��ͼ�λ��������Ƶ���Ļ��
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
		DrawBackGround(g);// ������
		hero.draw(g);// ̹��
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

			b.draw(g);// ���ӵ�
		}
		// g.drawString("Bullet Count :" + bullets.size(), 50, 50);
		// if(bullets[i]=null)
		// b.draw(g);//���ӵ�
		// g.drawString("Bullet Count :" + bullets.size(), 50, 50);
		//g.drawString("HeroTankHP  :" + HeroTank.BloodNumber, 50, 50);//���Ͻ���ʾѪ����
		if (BUllet.KillNum>HeighKillNum)
		{
			HeighKillNum = BUllet.KillNum;
		}
		if (HeighKillNum>=50)
		{
			g.drawString("��ϲ����", 220, 300);
		}
		g.setFont(font);
		g.drawString("��ǰɱ��:" + HeighKillNum+"ͷ", 570, 80);
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
			g.drawImage(bg, x, y, this);// ����ͼ
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
		tk.addWindowListener(new WindowAdapter()// ��Ӵ��ڹرմ�����
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}

}
