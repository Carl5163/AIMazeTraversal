import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dude {
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;	
	
	private Image sprite[];
	private Point pos;
	private int direction;
	
	public Dude(Point pos, int startDir) throws IOException {
		this.pos = pos;
		Image strip;
		strip = ImageIO.read(new File("res\\bee_strip4.png"));
		sprite = new Image[4];
		
		for(int i = 0; i < 4; i++) {
			sprite[i] = new BufferedImage(32,32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = sprite[i].getGraphics();
			g.drawImage(strip, -i*32, 0, null);
		}
		
	}
	
	public void draw(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		System.out.println("Drawing at: " + pos.x*32 + "," + pos.y*32);
		g.drawImage(sprite[direction], pos.x*32, pos.y*32, null);
				
	}

	public void move(int dir) {
		direction = dir;	
		switch(dir) {
		case UP:
			pos.y--;
			break;
		case RIGHT:
			pos.x++;
			break;
		case LEFT:
			pos.x--;
			break;
		case DOWN:
			pos.y++;
			break;
		}
		System.out.println("Moved to: " + pos.x + "," + pos.y);
	}
}
