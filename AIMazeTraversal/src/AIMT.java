import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class AIMT extends JFrame implements KeyListener, ActionListener {
	
	public static int NUM_TICKS = 500;
	public static final int TICK_LENGTH = 5;
	public static int POP_SIZE = 50;
	
	DrawPanel drawPanel;
	ArrayList<Bee> pop;
	ArrayList<Genome> genomePop;
	int numTicks = 0;
	GeneticAlgorithm gen;
	Timer timer;
	
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		/*if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			drawPanel.update(Bee.LEFT);
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			drawPanel.update(Bee.RIGHT);
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			drawPanel.update(Bee.UP);
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			drawPanel.update(Bee.DOWN);
		}*/
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			pop = new ArrayList<Bee>();
			for(int i = 0; i < POP_SIZE; i++) {
				try {
					pop.add(new Bee(drawPanel.spawnX, drawPanel.spawnY, drawPanel.map, drawPanel.mapWidth, drawPanel.mapHeight));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			gen = new GeneticAlgorithm(pop.get(0).brain.getNumberOfWeights());
			genomePop = gen.population;

			for(int i = 0; i < POP_SIZE; i++) {
				pop.get(i).brain.putWeights(genomePop.get(i).weights);
			}

			timer = new Timer(TICK_LENGTH, this);
			timer.setActionCommand("timer");
			timer.start();
			
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
		} else if(e.getActionCommand().equals("timer")) {
			if(numTicks < NUM_TICKS) {
					updateBees();
					drawPanel.update(pop);
					repaint();
					numTicks++;
					if(numTicks % 100 == 0)
						System.out.println("Tick: " + numTicks + "/" + NUM_TICKS);
				} else {
					timer.stop();
					System.out.println("Generation complete (Popsize: " + genomePop.size() + "). Creating a new one.");
										
					genomePop = gen.epoch(genomePop);
					System.out.println("New pop: (Popsize: " + genomePop.size()+ ")");
					
					restart();				
				}
			}
		}
	private void updateBees() {
		for(int bi = 0; bi < pop.size(); bi++) {
			ArrayList<Boolean> inputs = new ArrayList<Boolean>();
			for(int i = pop.get(bi).getX()-1; i <= pop.get(bi).getX()+1; i++) {
				for(int j = pop.get(bi).getY()-1; j <= pop.get(bi).getY()+1; j++) {
					if(!(i==pop.get(bi).getX() && j == pop.get(bi).getY())) {
						inputs.add(drawPanel.map[i][j] == DrawPanel.ENTRANCE);
					}
				}
			}
			pop.get(bi).update(inputs, genomePop.get(bi));
		}
	}
	public void restart() {
		for(int i = 0; i < POP_SIZE; i++) {
			System.out.println("HAHAH");
			pop.get(i).brain.putWeights(genomePop.get(i).weights);
			pop.get(i).setPosition(drawPanel.spawnX, drawPanel.spawnY);
		}
		numTicks = 0;
		timer = new Timer(TICK_LENGTH, this);
		timer.setActionCommand("timer");
		timer.start();
	}
}
