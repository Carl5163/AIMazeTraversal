import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements ActionListener {
		
	private static final long MAGIC_NUMBER_XOR = 4819776984503323676L;
	private static final long MAGIC_NUMBER     = 5033236748698419776L;
	private static final Color[] COLORS = {new Color(0,125,0,0), Color.GRAY, Color.GREEN, Color.BLUE, new Color(0,0,0,0)};
	public static final int NONE = 0;
	public static final int WALL = 1;
	public static final int ENTRANCE = 2;
	public static final int EXIT = 3;
	
	private int gridSize = 32;
	private File file;
	private Bee dude;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private JFileChooser fileChooser;
	
	public DrawPanel() throws IOException {
		fileChooser = new JFileChooser("..\\MazeEditor\\Maps");
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
			for(int i = 0; i < mapWidth; i++) {
				for(int j = 0; j < mapHeight; j++) {	
					drawSquare(g, COLORS[map[i][j]], Color.BLACK, i*32, j*32);
				}
			}
		}
		
		if(dude!= null) {
			g.setColor(Color.BLACK);			
			g.drawString("Location: (" + dude.getX() + ", " + dude.getY() + ")", 825, 20);
			g.drawString("Distance to Goal: " + dude.findDistanceToGoal(), 825, 35);
			g.drawString("Fitness: " + dude.findFitness(), 825, 50);
		}
		
		g.setColor(oldColor);
		
		if(dude != null) {
			dude.draw(g);
		}
		
		
	}
	
	private void openMap(File f) {
		long encryptedMagicNumber;
		long magicNumber;
		int dx = 0,dy = 0;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			encryptedMagicNumber = dis.readLong();
			magicNumber = encryptedMagicNumber ^ MAGIC_NUMBER_XOR;
			if(magicNumber == MAGIC_NUMBER) {
				for(int i = 0; i < mapWidth; i++) {
					for(int j = 0; j < mapHeight; j++) {
						map[i][j] = dis.readByte();
						if(map[i][j] == ENTRANCE) {
							dx = i;
							dy = j;								
						}
					}
				}
				file = f;
				repaint();
				dis.close();
				
				dude = new Bee(dx, dy, map, mapWidth, mapHeight);
				
			
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
	
	public void update(int dir) {
		if(dude != null) {
			dude.move(map, dir);
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if(command.equals("OPEN")) {
			File potentialNewFile;
			mapWidth = 25;
			mapHeight = 25;
			map = new int[mapWidth][mapHeight];
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				potentialNewFile = fileChooser.getSelectedFile();
				if(!potentialNewFile.exists()) {
					JOptionPane.showMessageDialog(this, "The file\"" + potentialNewFile + "\" could not be found.", "Open File", JOptionPane.ERROR_MESSAGE);
				} else {
					openMap(potentialNewFile);
					repaint();
				}
			}
		} else if(command.equals("CALCFIT")) {
			System.out.println("Fitness: " + dude.findFitness());
		}
	}
	
}
