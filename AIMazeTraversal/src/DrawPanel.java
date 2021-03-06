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
	private int spawn1X = -1;
	private int spawn1Y = -1;
	private Map<String, String> statMap;
	private int spawn2X;
	private int spawn2Y;
	
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
		
		if(bees != null) {
			for(Bee b : bees) {
				b.draw(g);
			}
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
		int d1x = 0,d1y = 0,d2x = 0,d2y = 0;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			encryptedMagicNumber = dis.readLong();
			magicNumber = encryptedMagicNumber ^ MAGIC_NUMBER_XOR;
			int spawnNum = 0;
			if(magicNumber == MAGIC_NUMBER) {
				for(int i = 0; i < getMapWidth(); i++) {
					for(int j = 0; j < getMapHeight(); j++) {
						map[i][j] = dis.readByte();
						if(getMap()[i][j] == ENTRANCE) {
							if(spawnNum == 0) {
								d1x = i;
								d1y = j;
								spawnNum++;
							} else if(spawnNum==1) {
								d2x = i;
								d2y = j;
							}
						}
					}
				}
				file = f;
				repaint();
				dis.close();
				
				spawn1X = d1x;
				spawn1Y = d1y;
				spawn2X = d2x;
				spawn2Y = d2y;
				
			
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
	
	public void update(ArrayList<Bee> bees) {
		this.bees = bees;
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
			if(spawn1X != -1 && spawn1Y != -1) {
				ret = true;
			}
		}
		return ret;
	}

	public int getSpawnX() {
		return spawn1X;
	}
	public int getSpawnY() {
		return spawn1Y;
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

	public void updateStats(GeneticAlgorithm gen, int numCompleted, int popSize, int genNum, int ticks, int numTicks) {
		
		statMap.put("Population Size: ", Integer.toString(popSize));
		statMap.put("Generation Number: ", Integer.toString(genNum));
		statMap.put("Lifetime: ", ticks + "/" + numTicks);
		statMap.put("Average Fitness: ", Double.toString(gen.getAverageFitness()));
		statMap.put("Best Fitness: ", Integer.toString(gen.getBestFitness()));
		statMap.put("Worst Fitness: ", Integer.toString(gen.getWorstFitness()));
		statMap.put("Total Fitness: ", Integer.toString(gen.getTotalFitness()));
		statMap.put("Percent Completed: ", (Double.toString(numCompleted/((double)popSize))) + "%");

		
		repaint();
	}

	public void clearStats() {
		statMap.clear();
	}

	
}
