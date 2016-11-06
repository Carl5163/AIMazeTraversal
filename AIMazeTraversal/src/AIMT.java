import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class AIMT extends JFrame implements KeyListener, ActionListener {
	
	DrawPanel drawPanel;
	
	public static void main(String[] args) {
		new AIMT();
	}
	
	public AIMT() {
		try {
			drawPanel = new DrawPanel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(1000, 1000);
		setTitle("AI Maze Traversal");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(drawPanel, BorderLayout.CENTER);
		setJMenuBar(makeMenuBar());
		addKeyListener(this);
		setVisible(true);
	}
	
	private JMenuBar makeMenuBar() {

		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");	
		menu.setMnemonic(KeyEvent.VK_F);
			
		menuItem = new JMenuItem("Open Map");
		menuItem.setActionCommand("OPEN");
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPanel);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("EXIT");
		menuItem.setMnemonic(KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);
		
		menu = new JMenu("Edit");	
		menu.setMnemonic(KeyEvent.VK_E);
			
		menuItem = new JMenuItem("Calculate Fittness");
		menuItem.setActionCommand("CALCFIT");
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPanel);
		menu.add(menuItem);

		menuBar.add(menu);
				
		return menuBar;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			drawPanel.update(Bee.LEFT);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			drawPanel.update(Bee.RIGHT);
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			drawPanel.update(Bee.UP);
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			drawPanel.update(Bee.DOWN);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		}
	}
}
