import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class AIMT extends JFrame implements ActionListener, KeyListener {
	
	
	private DrawPanel drawPanel;
	private ArrayList<Bee> pop;
	private ArrayList<Genome> genomePop;
	private int ticks = 0;
	private GeneticAlgorithm gen;
	private Timer mainTimer;
	private int genNum;
	private SimPrefs prefs;
	JMenuItem miStart;
	JMenuItem miStop;
	JMenuItem miPause;
	private JCheckBoxMenuItem miDraw;
	private Bee movableBee;
	
	public static void main(String[] args) {
		new AIMT();
	}
	
	public AIMT() {
		
		prefs = new SimPrefs();
		
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
		setVisible(true);
		addKeyListener(this);
		
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
		
		menu = new JMenu("Simulation");	
		menu.setMnemonic(KeyEvent.VK_I);
			
		menuItem = new JMenuItem("Preferences");
		menuItem.setActionCommand("PREFS");
		menuItem.setMnemonic(KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		
		miStart = new JMenuItem("Start");
		miStart.setActionCommand("START");
		miStart.setMnemonic(KeyEvent.VK_S);
		miStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		miStart.addActionListener(this);
		menu.add(miStart);
		
		miPause = new JMenuItem("Pause");
		miPause.setActionCommand("PAUSE");
		miPause.setMnemonic(KeyEvent.VK_P);
		miPause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		miPause.addActionListener(this);
		miPause.setEnabled(false);
		menu.add(miPause);
		
		miStop = new JMenuItem("Stop");
		miStop.setActionCommand("STOP");
		miStop.setMnemonic(KeyEvent.VK_T);
		miStop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		miStop.addActionListener(this);
		miStop.setEnabled(false);
		menu.add(miStop);
		
		miDraw = new JCheckBoxMenuItem("Draw Bees");
		miDraw.setSelected(true);
		menu.add(miDraw);
		

		menuBar.add(menu);
				
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("EXIT")) {
			System.exit(0);
		} else if(e.getActionCommand().equals("timer")) {
			if(ticks < prefs.numTicks) {
				updateBees();
				drawPanel.update(pop, miDraw.isSelected(), movableBee);
				repaint();
				ticks++;
			} else {
				mainTimer.stop();	
				genomePop = gen.epoch(genomePop);
				genNum++;
				restart();
			}
			drawPanel.updateStats(gen, prefs.popSize, genNum, ticks, prefs.numTicks);
			
		} else if(e.getActionCommand().equals("PREFS")) {
			PrefsDialog d = new PrefsDialog(prefs, this);
			d.setLocationRelativeTo(this);
		} else if(e.getActionCommand().equals("PAUSE")) {
			if(mainTimer != null) {
				pause();
				miPause.setText("Resume");
				miPause.setActionCommand("RESUME");
			}
		} else if(e.getActionCommand().equals("RESUME")) {
			if(mainTimer != null) {
				resume();
				miPause.setText("Pause");
				miPause.setActionCommand("PAUSE");
			}
		} else if(e.getActionCommand().equals("STOP")) {
			stop();
			miStart.setEnabled(true);
		} else if(e.getActionCommand().equals("START")) {
			start();
			miStop.setEnabled(true);
			miPause.setEnabled(true);
		}
	}
	private void start() {

		if(drawPanel.mapReady()) {
		
			pop = new ArrayList<Bee>();
			for(int i = 0; i < prefs.popSize; i++) {
				try {
					pop.add(new Bee(prefs, drawPanel.getSpawnX(), drawPanel.getSpawnY(), drawPanel.getMap(), drawPanel.getMapWidth(), drawPanel.getMapHeight(), drawPanel.getEndX(), drawPanel.getEndY()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			gen = new GeneticAlgorithm(prefs, pop.get(0).getNumberOfWeights());
			genomePop = gen.getPopulation();
	
			for(int i = 0; i < prefs.popSize; i++) {
				pop.get(i).putWeights(genomePop.get(i).getWeights());
			}
	
			mainTimer = new Timer(prefs.tickLength, this);
			mainTimer.setActionCommand("timer");
			mainTimer.start();
			genNum = 0;
		}
		
	}

	private void updateBees() {
		for(int bi = 0; bi < pop.size(); bi++) {
			pop.get(bi).update(pop.get(bi).getInputs(), genomePop.get(bi));
		}
	}
	public void restart() {
		for(int i = 0; i < prefs.popSize; i++) {
			pop.get(i).putWeights(genomePop.get(i).getWeights());
			pop.get(i).setPosition(drawPanel.getSpawnX(), drawPanel.getSpawnY());
			pop.get(i).resetPenalties();
		}
		ticks = 0;
		mainTimer = new Timer(prefs.tickLength, this);
		mainTimer.setActionCommand("timer");
		mainTimer.start();
	}
	
	public void stop() {
		if(mainTimer != null) {
			mainTimer.stop();
		}
		if(pop != null) {
			pop.clear();
		}
		drawPanel.clearStats();
	}
	public void pause() {
		mainTimer.stop();
	}
	public void resume() {
		mainTimer.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_B && e.isControlDown()) {
			try {
				if(movableBee == null) {
					if(drawPanel.mapReady()) {
						System.out.println("Made test bee");
						movableBee = new Bee(prefs, drawPanel.getSpawnX(), drawPanel.getSpawnY(), drawPanel.getMap(), drawPanel.getMapWidth(), drawPanel.getMapHeight(), drawPanel.getEndX(), drawPanel.getEndY());
						drawPanel.update(null, false, movableBee);
						repaint();
					}
				} else {
					movableBee = null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			if(movableBee != null && e.getKeyCode() <= 40 && e.getKeyCode() >= 37) {
				movableBee.move(drawPanel.getMap(), e.getKeyCode()-37);
				drawPanel.update(null, false, movableBee);
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
