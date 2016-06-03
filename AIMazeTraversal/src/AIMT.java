import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

public class AIMT extends JFrame implements KeyListener {
	
	DrawPanel drawPanel;
	
	public static void main(String[] args) {
		new AIMT();
	}
	
	public AIMT() {
		try {
			drawPanel = new DrawPanel(new Point(10,10));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(711, 702);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().add(drawPanel, BorderLayout.CENTER);
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			drawPanel.update(Dude.LEFT);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			drawPanel.update(Dude.RIGHT);
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			drawPanel.update(Dude.UP);
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			drawPanel.update(Dude.DOWN);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
