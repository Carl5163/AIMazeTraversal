import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawPane extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private static int WALL = 0;
	private static int ENTRANCE = 1;
	private static int EXIT = 2;
	
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private int selectedSquareX;
	private int selectedSquareY;
	private int mouseX;
	private int mouseY;
	private int selectedItem;
	
	
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
				if(map[i][j] == 1) {
					drawSquare(g, Color.GRAY, Color.BLACK, i, j);
				} else {
					drawSquare(g, Color.WHITE, Color.BLACK, i, j);					
				}
			}
		}


		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			if(map[selectedSquareX][selectedSquareY] == 1) {
				drawSquare(g, new Color(0,0,0,0), Color.YELLOW, selectedSquareX, selectedSquareY);
			} else {
				drawSquare(g, new Color(0,0,0,0), Color.RED, selectedSquareX, selectedSquareY);
			}
		}
		
		if(selectedItem == WALL) {
			drawSquare(g, Color.GRAY, Color.BLACK, mouseX, mouseY);
		} else if(selectedItem == ENTRANCE) {
			drawSquare(g, Color.GREEN, Color.BLACK, mouseX, mouseY);
		} else if(selectedItem == WALL) {
			drawSquare(g, Color.BLUE, Color.BLACK, mouseX, mouseY);
		}
		
		
		g.setColor(oldColor);
		
		
	}

	private void drawSquare(Graphics2D g, Color fillColor, Color borderColor, int x, int y) {
		g.setColor(fillColor);
		g.fillRect((x*32), (y*32), 32, 32);
		g.setColor(borderColor);
		g.drawRect((x*32), (y*32), 32, 32);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectedSquareX = e.getX()/32;
		selectedSquareY = e.getY()/32;
		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				map[selectedSquareX][selectedSquareY] = 1;
			} else if(e.getButton() == MouseEvent.BUTTON2){
				map[selectedSquareX][selectedSquareY] = 0;
			}
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedSquareX = mouseX/32;
		selectedSquareY = mouseY/32;
		if(selectedSquareX >= 0 && selectedSquareX < mapWidth && selectedSquareY >= 0 && selectedSquareY < mapHeight) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				map[selectedSquareX][selectedSquareY] = 1;
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
	public void actionPerformed(ActionEvent e) {
		String cmd;
		cmd = e.getActionCommand();
		if(cmd.equals("CLEAR")) {
			if(JOptionPane.showConfirmDialog(this, "Are you sure you want to erase the map?", "Clear Map", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				initMap();
			}
		}
	}
	
	private void initMap() {
		for(int i = 0; i < mapWidth; i++) {
			for(int j = 0; j < mapHeight; j++) {
				map[i][j] = 0;
			}
		}
	}
	
	
}
