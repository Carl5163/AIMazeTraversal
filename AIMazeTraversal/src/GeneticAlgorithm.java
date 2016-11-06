import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class GeneticAlgorithm {

	private int numBees = 50;
	private int chromeLength;
	private double totalFitness;
	private double averageFitness;	
	private double worstFitness;	
	private double bestFitness;	
	private int fittestGenome;
	int generation;
	private double crossoverRate = 0;
	private double mutationRate = 0;
	private double maxPerturbation = 0;
	private int numElite = 0;
	private int numCopiesElite = 0;
	private Properties prefs;
	private ArrayList<Genome> population;
	
	public GeneticAlgorithm() {
		prefs = new Properties();
		try {
			prefs.load(new FileInputStream("config.ini"));
			crossoverRate = Double.parseDouble(prefs.getProperty("CrossoverRate"));
			mutationRate = Double.parseDouble(prefs.getProperty("MutationRate"));
			maxPerturbation = Double.parseDouble(prefs.getProperty("MaxPerturbation"));
			numElite = Integer.parseInt(prefs.getProperty("NumElite"));
			numCopiesElite = Integer.parseInt(prefs.getProperty("NumCopiesElite"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Random random = new Random();
		
		for(int i = 0; i < numBees; i++) {
			population.add(new Genome());
			for(int j = 0; j < chromeLength; j++) {
				population.get(i).addWeight(random.nextDouble() - 2 * random.nextDouble());
			}
		}
		
	}
	
	public void crossover(ArrayList<Double> parent1, ArrayList<Double> parent2, ArrayList<Double> child1, ArrayList<Double> child2) {
		
	}
	
	public void mutate(double mutate) {
		
	}
	
	public void getBest(int numBest, int numCopies, ArrayList<Genome> population) {
		
	}
	
	public ArrayList<Genome> epoch(ArrayList<Genome> lastGen) {
		return null;
	}
	
}
