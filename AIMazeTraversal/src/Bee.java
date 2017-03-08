import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bee {
	
	public static final int LEFT = 0;
	public static final int UP = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;	
	
	public static final int CANT_SEE = 0;
	public static final int WALL = 1;
	public static final int EXIT = 2;
	public static final int FLOOR = 3;
	public static final Color[] LOS_COLOR = {new Color(0,0,0,150), new Color(255,0,0,45), new Color(0,255,0,45), new Color(0,0,255,45)};
	
	private Image sprite[];
	private int x, y;
	private int direction;
	private Pathfinder p;
	private int[][] map;
	private int fitness;
	private NeuralNetwork brain;
<<<<<<< HEAD
	int distanceToGoal;
	int distanceOld;
	boolean update = true;
	
=======
	private int mapW, mapH;
	private int[][] visitedMap;
	private int numSpacesVisited = 0;
	private int endX, endY;
>>>>>>> 108d6abe37e6c4caea6a0f02662df4036f9a0958
	
	public Bee(SimPrefs prefs, int x, int y, int[][] map, int w, int h, int ex, int ey) throws IOException {
		
		this.x = x;
		this.y = y;
		this.map = map;
		mapW = w;
		mapH = h;
		endX = ex;
		endY = ey;
		Image strip;
		strip = ImageIO.read(new File("res\\bee_strip4.png"));
		sprite = new Image[4];
		Image[] spriteTemp = new Image[4];
		
		for(int i = 0; i < 4; i++) {
			spriteTemp[i] = new BufferedImage(32,32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = spriteTemp[i].getGraphics();
			g.drawImage(strip, -i*32, 0, null);
		}
		
		sprite[DOWN] = spriteTemp[RIGHT];
		sprite[UP] = spriteTemp[LEFT];
		sprite[LEFT] = spriteTemp[DOWN];
		sprite[RIGHT] = spriteTemp[UP];
		

		p = new Pathfinder(map, w, h);
<<<<<<< HEAD
		distanceToGoal = findDistanceToGoal();
		brain = new NeuralNetwork(prefs);
		fitness = 100;
=======
		startDist = findDistanceToGoal();
		brain = new NeuralNetwork(prefs, 27);
		visitedMap = new int[mapW][mapH];
>>>>>>> 108d6abe37e6c4caea6a0f02662df4036f9a0958
		
	}
	
	void update(ArrayList<Double> inputs, Genome mygenome) {
		
<<<<<<< HEAD
		if(update) {
			ArrayList<Double> in = new ArrayList<Double>();
			
			for(int i = 0; i < inputs.size(); i++) {
				in.add(inputs.get(i) ? 1.0 : -1.0);
=======
		ArrayList<Double> output = brain.update(inputs);
		
		double high = output.get(0);
		
		int highest = 0;
		for(int i = 1; i < 4; i++) {
			if(output.get(i) > high) {
				high = output.get(i);
				highest = i;
>>>>>>> 108d6abe37e6c4caea6a0f02662df4036f9a0958
			}
			ArrayList<Double> output = brain.update(in);
			
			double high = output.get(0);
			
			int highest = 0;
			for(int i = 1; i < 4; i++) {
				if(output.get(i) > high) {
					high = output.get(i);
					highest = i;
				}
			}
			
			move(map, highest);
			mygenome.setFitness(findFitness());
		}
		
<<<<<<< HEAD
=======
		move(map, highest);
		mygenome.setFitness(findFitness());	
		if(visitedMap[x][y] == 0) {
			numSpacesVisited++;
		}
		visitedMap[x][y]++;
		
>>>>>>> 108d6abe37e6c4caea6a0f02662df4036f9a0958
	}

	
	public int findFitness() {
<<<<<<< HEAD
		distanceOld = distanceToGoal;		
		distanceToGoal = findDistanceToGoal();
		if(distanceToGoal > distanceOld) {
			fitness -= 10;
		} else if(distanceToGoal < distanceOld){
			fitness += 10;
		}
		if(distanceToGoal == 0) {
			fitness+=50;
			update = false;
		}
		fitness--;
=======
		fitness = Math.max(0, 100*(startDist-p.getPathLength(x, y)));
		if(map[x][y] == DrawPanel.EXIT)  {
			fitness += 1000;
		} else {
			fitness += Math.max(0, 100-visitedMap[x][y]);
		}
		fitness += numSpacesVisited;
>>>>>>> 108d6abe37e6c4caea6a0f02662df4036f9a0958
		return fitness;
	}
	
	public int findDistanceToGoal() {
		return p.getPathLength(x, y);
	}
	
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
	
	public void putWeights(ArrayList<Double> weights) {
		brain.putWeights(weights);
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setPosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

	public int getNumberOfWeights() {
		System.out.println(brain.getNumberOfWeights());
		return brain.getNumberOfWeights();
	}

	public void resetPenalties() {
		numSpacesVisited = 0;
		for(int i = 0; i < mapW; i++) {
			for(int j = 0; j < mapH; j++) {
				visitedMap[i][j] = 0;
			}
		}
	}


	public ArrayList<Double> getInputs() {
		ArrayList<Double>  ret = new ArrayList<Double>();
		for(int i = x-2; i <= x+2; i++) {
			for(int j = y-2; j <= y+2; j++) {
				if(i >= 0 && i < mapW && j >= 0 && j < mapH) {
					ret.add((double) map[i][j]);
				} else {
					ret.add((double) DrawPanel.WALL);
				}
			}
		}
		
		ret.add((double) (x-endX));
		ret.add((double) (y-endY));
		
		return ret;
	}
	
}
