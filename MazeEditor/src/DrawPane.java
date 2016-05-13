import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class DrawPane extends JPanel implements MouseListener, MouseMotionListener {

	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	
	public DrawPane(int[][] map, int width, int height) {
		super();
		this.map = map;
		addMouseListener(this);
		addMouseMotionListener(this);
		mapWidth = width;
		mapHeight = height;
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
				System.out.println(i + ", " + j);
				if(map[i][j] == 1) {
					g.setColor(Color.GRAY);
					g.fillRect((i*32),(j*32),(i*32)+32,(j*32)+32);
					g.setColor(Color.BLACK);
					g.drawRect((i*32),(j*32),(i*32)+32,(j*32)+32);
				}
			}
		}
		
		
		g.setColor(oldColor);
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
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
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		repaint();
	}
	
	
}
