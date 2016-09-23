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
	//Fitness = startDist-curDist
	
	private Image sprite[];
	private int x, y;
	private int direction;
	private Pathfinder p;
	private int endX, endY;
	private int[][] map;
	private int startDist;
	private int fitness;
	
	public Dude(int x, int y, int[][] map, int w, int h) throws IOException {
		this.x = x;
		this.y = y;
		this.map = map;
		Image strip;
		strip = ImageIO.read(new File("res\\bee_strip4.png"));
		sprite = new Image[4];
		
		for(int i = 0; i < 4; i++) {
			sprite[i] = new BufferedImage(32,32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = sprite[i].getGraphics();
			g.drawImage(strip, -i*32, 0, null);
		}
		

		p = new Pathfinder(map, w, h);
		startDist = findDistanceToGoal();
		
	}
	
	public int findFitness() {
		fitness = startDist-p.getPathLength(x, y);
		return fitness;
	}
	
	public int findDistanceToGoal() {
		return p.getPathLength(x, y);
	}
	
	//146
	public void draw(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		g.drawImage(sprite[direction], x*32, y*32, null);
				
	}

	public void move(int[][] map, int dir) {
		direction = dir;	
		switch(dir) {
		case UP:
			if(map[x][y-1] != DrawPanel.WALL)
				y--;
			break;
		case RIGHT:
			if(map[x+1][y] != DrawPanel.WALL)
				x++;
			break;
		case LEFT:
			if(map[x-1][y] != DrawPanel.WALL)
				x--;
			break;
		case DOWN:
			if(map[x][y+1] != DrawPanel.WALL)
				y++;
			break;
		}
	}

	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}