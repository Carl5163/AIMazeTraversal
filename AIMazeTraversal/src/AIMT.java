import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class AIMT extends JFrame implements KeyListener, ActionListener {
	
	public int numTicks = 500;
	public int tickLength = 1;
	
	DrawPanel drawPanel;
	ArrayList<Bee> pop;
	ArrayList<Genome> genomePop;
	int ticks = 0;
	GeneticAlgorithm gen;
	Timer timer;
	private int genNum;
	private Properties prefs;
	private int popSize;
	
	public static void main(String[] args) {
		new AIMT();
	}
	
	public AIMT() {
		prefs = new Properties();
		try {
			prefs.load(new FileInputStream("config.ini"));
			numTicks = Integer.parseInt(prefs.getProperty("NumTicks"));
			popSize = Integer.parseInt(prefs.getProperty("PopSize"));
			tickLength = Integer.parseInt(prefs.getProperty("TickLength"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
			for(int i = 0; i < popSize; i++) {
				try {
					pop.add(new Bee(drawPanel.spawnX, drawPanel.spawnY, drawPanel.map, drawPanel.mapWidth, drawPanel.mapHeight));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			gen = new GeneticAlgorithm(pop.get(0).brain.getNumberOfWeights(), popSize);
			genomePop = gen.population;

			for(int i = 0; i < gen.popSize; i++) {
				pop.get(i).brain.putWeights(genomePop.get(i).weights);
			}

			timer = new Timer(tickLength, this);
			timer.setActionCommand("timer");
			timer.start();
			genNum=0;
			
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
			if(ticks < numTicks) {
					updateBees();
					drawPanel.update(pop);
					repaint();
					ticks++;
					if(ticks % 100 == 0)
						System.out.println("Tick: " + ticks + "/" + numTicks);
				} else {
					timer.stop();	
					genomePop = gen.epoch(genomePop);
					System.out.println("Generation: " + genNum);
					genNum++;
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
		for(int i = 0; i < popSize; i++) {
			pop.get(i).brain.putWeights(genomePop.get(i).weights);
			pop.get(i).setPosition(drawPanel.spawnX, drawPanel.spawnY);
		}
		ticks = 0;
		timer = new Timer(tickLength, this);
		timer.setActionCommand("timer");
		timer.start();
	}
}
