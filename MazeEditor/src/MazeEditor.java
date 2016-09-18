import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MazeEditor extends JFrame implements ActionListener{
	
	private DrawPane drawPane;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	
	public static void main(String[] args) {
		new MazeEditor(25, 25).show(null);
	}
	
	public MazeEditor(int width, int height) {
		
		Container cp;
		
		mapWidth = width;
		mapHeight = height;
		
		setSize(1000, 1000);
		setResizable(false);
		
		cp = getContentPane();				
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		undoItem = new JMenuItem("Undo");
		undoItem.setActionCommand("UNDO");
		undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		undoItem.setEnabled(false);
		
		redoItem = new JMenuItem("Redo");
		redoItem.setActionCommand("REDO");
		redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		redoItem.setEnabled(false);
		
		undoItem.addActionListener(drawPane);
		redoItem.addActionListener(drawPane);
		
		drawPane = new DrawPane(map, undoItem, redoItem, mapWidth, mapHeight);
		
		cp.add(drawPane, BorderLayout.CENTER);
		cp.add(makeToolPanel(), BorderLayout.EAST);
		setJMenuBar(makeMenuBar());
		
	}
	
	private JPanel makeToolPanel() {
		
		JPanel panel;
		JComboBox menu;
		
		panel = new JPanel();
		
		String[] options = {"Wall", "Entrance", "Exit"};
		menu = new JComboBox(options);
		menu.setActionCommand("SELECTED_ITEM");
		menu.addActionListener(drawPane);
		
		panel.add(new JLabel("Object"));
		panel.add(menu);		
		
		return panel;
	}

	private JMenuBar makeMenuBar() {

		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");	
		menu.setMnemonic(KeyEvent.VK_F);
			
		menuItem = new JMenuItem("Open");
		menuItem.setActionCommand("OPEN");
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPane);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save");
		menuItem.setActionCommand("SAVE");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPane);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save As");
		menuItem.setActionCommand("SAVE_AS");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPane);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Exit");
		menuItem.setActionCommand("EXIT");
		menuItem.setMnemonic(KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuBar.add(menu);
		
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		
		menu.add(undoItem);		
		menu.add(redoItem);		
		
		menuItem = new JMenuItem("Clear");
		menuItem.setActionCommand("CLEAR");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(drawPane);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Randomize");
		menuItem.setActionCommand("RANDOMIZE");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		menuItem.addActionListener(drawPane);
		menu.add(menuItem);
		
		menuBar.add(menu);
		return menuBar;
	}
	
	public void show(Component parent) {
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
	
}
