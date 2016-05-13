import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawPane extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private static final long MAGIC_NUMBER_XOR = 4819776984503323676L;
	private static final long MAGIC_NUMBER =     5033236748698419776L;
	private static final int NONE = 0;
	private static final int WALL = 1;
	private static final int ENTRANCE = 2;
	private static final int EXIT = 3;
	private static final int BORDER = 4;
	private static final Color[] COLORS = {Color.WHITE, Color.GRAY, Color.GREEN, Color.BLUE, new Color(0,0,0,0)};
	
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private int selectedSquareX;
	private int selectedSquareY;
	private int mouseX;
	private int mouseY;
	private int selectedItem;
	private JFileChooser fileChooser;
	private File file;
	boolean drawSelectedItem;
	private Stack<Cell> randomStack;
	private Cell[][] cellMap;

	
	
	public DrawPane(int[][] map, int width, int height) {
		super();
		this.map = map;
		addMouseListener(this);
		addMouseMotionListener(this);
		mapWidth = width;
		mapHeight = height;
		this.map = new int[mapWidth][mapHeight];
		map = this.map;
		initMap();
		selectedSquareX = -100;
		selectedSquareY = -100;
		selectedItem = WALL;
		fileChooser = new JFileChooser(".");
		drawSelectedItem = false;
	}
	
	@Override
	public void paintComponent(Graphics g1) {
		
		Graphics2D g;
		Color oldColor;
				
		g = (Graphics2D)g1;
		oldColor = g.getColor();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {	
				drawSquare(g, COLORS[map[i][j]], Color.BLACK, i*32, j*32);
			}
		}


		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			drawSquare(g, COLORS[BORDER], Color.RED, selectedSquareX*32, selectedSquareY*32);
		}
		
		if(drawSelectedItem) {
			drawSquare(g, COLORS[selectedItem], Color.BLACK, mouseX-16, mouseY-16);
		}
		
		g.setColor(oldColor);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd;
		cmd = e.getActionCommand();
		if(cmd.equals("CLEAR")) {
			if(JOptionPane.showConfirmDialog(this, "Are you sure you want to erase the map?", "Clear Map", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				initMap();
			}
		} else if(cmd.equals("NEW")) {
			file = null;
			initMap();
		} else if(cmd.equals("SELECTED_ITEM")) {
			selectedItem = ((JComboBox)e.getSource()).getSelectedIndex()+1;
		} else if(cmd.equals("SAVE")) {
			File potentialNewFile;
			if(file != null) { 
				saveMap(file);
			} else {
				potentialNewFile = getSaveFilename();
				if(potentialNewFile != null) {
					saveMap(potentialNewFile);
				}
			}
		} else if(cmd.equals("SAVE_AS")) {
			file = getSaveFilename();
			if(file != null) {
				saveMap(file);
			}
		} else if(cmd.equals("OPEN")) {
			File potentialNewFile;
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				potentialNewFile = fileChooser.getSelectedFile();
				if(!potentialNewFile.exists()) {
					JOptionPane.showMessageDialog(this, "The file\"" + potentialNewFile + "\" could not be found.", "Open File", JOptionPane.ERROR_MESSAGE);
				} else {
					openMap(potentialNewFile);
				}
			}
		}
	}
	
	private File getSaveFilename() {
		File file;
		int result;
		file = null;
		result = fileChooser.showSaveDialog(this);
		while(result != JFileChooser.CANCEL_OPTION && file == null) {
			file = fileChooser.getSelectedFile();
			if(file.exists()) {
				if((JOptionPane.showConfirmDialog(this, "The file \"" + file + "\" already exists. Do you want to overwrite it?", "Save File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)) {
					file = null;
				}
			}
			if(file == null) {
				result = fileChooser.showSaveDialog(this);
			}
		}
		return file;
	}
		
	private void saveMap(File f) {
		long encryptedMagicNumber;
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
			encryptedMagicNumber = MAGIC_NUMBER ^ MAGIC_NUMBER_XOR;
			dos.writeLong(encryptedMagicNumber);
			for(int i = 0; i < mapWidth; i++) {
				for(int j = 0; j < mapHeight; j++) {
					dos.writeByte(map[i][j]);
				}
			}
			file = f;
			dos.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Unexpected error while attempting to save file.", "Save File", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	private void openMap(File f) {
		long encryptedMagicNumber;
		long magicNumber;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			encryptedMagicNumber = dis.readLong();
			magicNumber = encryptedMagicNumber ^ MAGIC_NUMBER_XOR;
			if(magicNumber == MAGIC_NUMBER) {
				for(int i = 0; i < mapWidth; i++) {
					for(int j = 0; j < mapHeight; j++) {
						map[i][j] = dis.readByte();
					}
				}
				file = f;
				repaint();
				dis.close();
			
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

	@Override
	public void mousePressed(MouseEvent e) {
		selectedSquareX = e.getX()/32;
		selectedSquareY = e.getY()/32;
		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				map[selectedSquareX][selectedSquareY] = selectedItem;
			} else if(e.getButton() == MouseEvent.BUTTON3){
				map[selectedSquareX][selectedSquareY] = 0;
			}
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedSquareX = mouseX/32;
		selectedSquareY = mouseY/32;
		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				map[selectedSquareX][selectedSquareY] = selectedItem;
			} else if(SwingUtilities.isRightMouseButton(e)){
				map[selectedSquareX][selectedSquareY] = 0;
			}
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();
		selectedSquareX = mouseX/32;
		selectedSquareY = mouseY/32;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		drawSelectedItem = true;
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		drawSelectedItem = false;
		repaint();
	}
	
	private void initMap() {
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				map[i][j] = 0;
			}
		}
	}
	
	private void randomize() {
		
		Random random;
		int startX, startY, unvisitedCells;
		Cell currentCell;
		
		unvisitedCells = mapWidth*mapHeight;
		
		cellMap = new Cell[mapWidth][mapHeight];
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				cellMap[i][j] = new Cell();
			}
		}
		
		random = new Random();
		randomStack = new Stack<Cell>();
		startX = random.nextInt(mapWidth);
		startY = random.nextInt(mapHeight);
		currentCell = cellMap[startX][startY];
		currentCell.setVisited();
		unvisitedCells--;
		
		while(unvisitedCells > 0) {
			if()
		}
		
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	
}
