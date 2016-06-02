import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
		
	private int gridSize = 32;
	private Dude dude;
	
	public DrawPanel(Point pos) throws IOException {
		dude = new Dude(pos, Dude.UP);
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		
		g.setColor(new Color(0,100,0));
		
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		
		
		for(int i = 0; i < getWidth(); i+=gridSize) {
			g.drawLine(i, 0, i, getHeight());
		}
		for(int i = 0; i < getHeight(); i+=gridSize) {
			g.drawLine(0, i, getWidth(), i);
		}
		
		dude.draw(g);
		
	}
	
	public void update(int dir) {
		dude.move(dir);
		repaint();
	}
	
}
