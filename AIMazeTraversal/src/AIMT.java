import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class AIMT extends JFrame implements ActionListener {
	
	
	private DrawPanel drawPanel;
	private ArrayList<Bee> pop;
	private ArrayList<Genome> genomePop;
	private int ticks = 0;
	private GeneticAlgorithm gen;
	private Timer timer;
	private int genNum;
	private SimPrefs prefs;
	JMenuItem miStart;
	JMenuItem miStop;
	JMenuItem miPause;
	
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
				drawPanel.update(pop);
				repaint();
				ticks++;
			} else {
				timer.stop();	
				genomePop = gen.epoch(genomePop);
				genNum++;
				restart();				
			}
			drawPanel.updateStats(gen, prefs.popSize, genNum, ticks, prefs.numTicks);
			
		} else if(e.getActionCommand().equals("PREFS")) {
			PrefsDialog d = new PrefsDialog(prefs, this);
			d.setLocationRelativeTo(this);
		} else if(e.getActionCommand().equals("PAUSE")) {
			if(timer != null) {
				pause();
				miPause.setText("Resume");
				miPause.setActionCommand("RESUME");
			}
		} else if(e.getActionCommand().equals("RESUME")) {
			if(timer != null) {
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
					pop.add(new Bee(prefs, drawPanel.getSpawnX(), drawPanel.getSpawnY(), drawPanel.getMap(), drawPanel.getMapWidth(), drawPanel.getMapHeight()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			gen = new GeneticAlgorithm(prefs, pop.get(0).getNumberOfWeights());
			genomePop = gen.getPopulation();
	
			for(int i = 0; i < prefs.popSize; i++) {
				pop.get(i).putWeights(genomePop.get(i).getWeights());
			}
	
			timer = new Timer(prefs.tickLength, this);
			timer.setActionCommand("timer");
			timer.start();
			genNum = 0;
		}
		
	}

	private void updateBees() {
		for(int bi = 0; bi < pop.size(); bi++) {
			ArrayList<Boolean> inputs = new ArrayList<Boolean>();
			for(int i = pop.get(bi).getX()-1; i <= pop.get(bi).getX()+1; i++) {
				for(int j = pop.get(bi).getY()-1; j <= pop.get(bi).getY()+1; j++) {
					if(!(i==pop.get(bi).getX() && j == pop.get(bi).getY())) {
						inputs.add(drawPanel.getMap()[i][j] == DrawPanel.ENTRANCE);
					}
				}
			}
			pop.get(bi).update(inputs, genomePop.get(bi));
		}
	}
	public void restart() {
		for(int i = 0; i < prefs.popSize; i++) {
			pop.get(i).putWeights(genomePop.get(i).getWeights());
			pop.get(i).setPosition(drawPanel.getSpawnX(), drawPanel.getSpawnY());
		}
		ticks = 0;
		timer = new Timer(prefs.tickLength, this);
		timer.setActionCommand("timer");
		timer.start();
	}
	
	public void stop() {
		if(timer != null) {
			timer.stop();
		}
		if(pop != null) {
			pop.clear();
		}
		drawPanel.clearStats();
	}
	public void pause() {
		timer.stop();
	}
	public void resume() {
		timer.start();
	}
	
}
