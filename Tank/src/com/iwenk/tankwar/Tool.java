package com.iwenk.tankwar;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Tool 
{
	public static Random random  = new Random();
	public static Direction direction[] = Direction.values();
	public static Image heroImage[] = { getImage("herotank-l.png"),
			getImage("herotank-lu.png"), getImage("herotank-u.png"),
			getImage("herotank-ru.png"), getImage("herotank-r.png"),
			getImage("herotank-rd.png"), getImage("herotank-d.png"),
			getImage("herotank-ld.png"),
	// getImage("herotank-d.png"),

	};
	public static Image EnemyImage[] = { getImage("enemytank-l.gif"),
			getImage("enemytank-lu.gif"), getImage("enemytank-u.gif"),
			getImage("enemytank-ru.gif"), getImage("enemytank-r.gif"),
			getImage("enemytank-rd.gif"), getImage("enemytank-d.gif"),
			getImage("enemytank-ld.gif"),
	// getImage("herotank-d.png"),

	};
	public static Image ExplodeImage[] = { getImage("explode0.png"),
		getImage("explode1.png"), getImage("explode2.png"),
		getImage("explode3.png"), getImage("explode4.png"),
		getImage("explode5.png"),
		// getImage("herotank-d.png"),
		
	};
	public static Image bulletImage[] = { getImage("bullet.png"),
	// getImage("explode1.png"), getImage("explode2.png"),
	// getImage("explode3.png"), getImage("explode4.png"),
	// getImage("explode5.png"),
	// getImage("herotank-d.png"),

	};

	public static Image getImage(String url)
	{
		return getImageIcon(url).getImage();
	}

	public static ImageIcon getImageIcon(String url)
	{
		return new ImageIcon(Tool.class.getClassLoader().getResource(
				"images/" + url));
	}

}
