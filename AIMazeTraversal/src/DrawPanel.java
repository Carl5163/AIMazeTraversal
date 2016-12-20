import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements ActionListener {
		
	private static final long MAGIC_NUMBER_XOR = 4819776984503323676L;
	private static final long MAGIC_NUMBER     = 5033236748698419776L;
	private static final Color[] COLORS = {new Color(0,125,0,0), Color.GRAY, Color.GREEN, Color.BLUE, new Color(0,0,0,0)};
	public static final int NONE = 0;
	public static final int WALL = 1;
	public static final int ENTRANCE = 2;
	public static final int EXIT = 3;
	
	private File file;
	private ArrayList<Bee> bees;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private JFileChooser fileChooser;
	private int spawnX = -1;
	private int spawnY = -1;
	private Map<String, String> statMap;
	private boolean drawBees;
	private Bee movableBee;
	private int endX;
	private int endY;
	
	public DrawPanel() throws IOException {
		fileChooser = new JFileChooser("..\\MazeEditor\\Maps");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Map files (.map)", "map"));
		fileChooser.setSelectedFile(new File("map3.map"));
		statMap = new HashMap<String, String>();
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		
		Graphics2D g;
		Color oldColor;
				
		g = (Graphics2D)g1;
		oldColor = g.getColor();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		if(file != null) {
			for(int i = 0; i < getMapWidth(); i++) {
				for(int j = 0; j < getMapHeight(); j++) {	
					drawSquare(g, COLORS[getMap()[i][j]], Color.BLACK, i*32, j*32);
				}
			}
		}
		
		g.setColor(oldColor);
		
		if(bees != null && drawBees) {
			for(Bee b : bees) {
				b.draw(g);
			}
		}
		
		if(movableBee != null) {
			movableBee.draw(g);
		}
		
		int i = 0; 
		for(String s : statMap.keySet()) {
			g.drawString(s + statMap.get(s), 810, 20 + 20*i);
			i++;
		}
		
		
	}
	
	private void openMap(File f) {
		long encryptedMagicNumber;
		long magicNumber;
		int dx = 0,dy = 0;
		int ex = 0,ey = 0;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			encryptedMagicNumber = dis.readLong();
			magicNumber = encryptedMagicNumber ^ MAGIC_NUMBER_XOR;
			if(magicNumber == MAGIC_NUMBER) {
				for(int i = 0; i < getMapWidth(); i++) {
					for(int j = 0; j < getMapHeight(); j++) {
						getMap()[i][j] = dis.readByte();
						if(getMap()[i][j] == ENTRANCE) {
							dx = i;
							dy = j;								
						}
						if(getMap()[i][j] == EXIT) {
							ex = i;
							ey = j;								
						}
					}
				}
				file = f;
				repaint();
				dis.close();
				
				spawnX = dx;
				spawnY = dy;
				endX = ex;
				endY = ey;
				
			
			} else {
				JOptionPane.showMessageDialog(this, "The file you chose is not a valid map file.", "Open File", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while attempting to save file.", "Save File", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void drawSquare(Graphics2D g, Color fillColor, Color borderColor, int x, int y) {
		g.setColor(fillColor);
		g.fillRect(x, y, 32, 32);
		g.setColor(borderColor);
		g.drawRect(x, y, 32, 32);
	}
	
	public void update(ArrayList<Bee> bees, boolean drawBees, Bee movableBee) {
		this.bees = bees;
		this.drawBees = drawBees;
		this.movableBee = movableBee;
		repaint();
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if(command.equals("OPEN")) {
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File potentialNewFile;
				mapWidth = 25;
				mapHeight = 25;
				map = new int[getMapWidth()][getMapHeight()];
				potentialNewFile = fileChooser.getSelectedFile();
				if(!potentialNewFile.exists()) {
					JOptionPane.showMessageDialog(this, "The file\"" + potentialNewFile + "\" could not be found.", "Open File", JOptionPane.ERROR_MESSAGE);
				} else {
					openMap(potentialNewFile);
					repaint();
				}
			}
		}
	}

	public boolean mapReady() {
		boolean ret = false;
		if(map != null) {
			if(spawnX != -1 && spawnY != -1) {
				ret = true;
			}
		}
		return ret;
	}

	public int getSpawnX() {
		return spawnX;
	}
	public int getSpawnY() {
		return spawnY;
	}
	public int getEndX() {
		return endX;
	}
	public int getEndY() {
		return endY;
	}
	public int[][] getMap() {
		return map;
	}

	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}

	public void updateStats(GeneticAlgorithm gen, int popSize, int genNum, int ticks, int numTicks) {
		
		statMap.put("Population Size: ", Integer.toString(popSize));
		statMap.put("Generation Number: ", Integer.toString(genNum));
		statMap.put("Lifetime: ", ticks + "/" + numTicks);
		statMap.put("Average Fitness: ", Double.toString(gen.getAverageFitness()));
		statMap.put("Best Fitness: ", Integer.toString(gen.getBestFitness()));
		statMap.put("Worst Fitness: ", Integer.toString(gen.getWorstFitness()));
		statMap.put("Total Fitness: ", Integer.toString(gen.getTotalFitness()));

		
		repaint();
	}

	public void clearStats() {
		statMap.clear();
	}

	
}
