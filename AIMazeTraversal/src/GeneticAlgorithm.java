import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class GeneticAlgorithm {

	private int popSize = 50;
	int chromeLength;
	private double totalFitness;
	double averageFitness;	
	double worstFitness;	
	double bestFitness;	
	int fittestGenome;
	int generation;
	private double crossoverRate = 0;
	private double mutationRate = 0;
	private double maxPerturbation = 0;
	private int numElite = 0;
	private int numCopiesElite = 0;
	private Properties prefs;
	ArrayList<Genome> population;
	
	public GeneticAlgorithm(int chromeLength) {
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
		
		
		this.chromeLength = chromeLength;
		
		Random random = new Random();
		population = new ArrayList<Genome>();
		
		for(int i = 0; i < popSize; i++) {
			population.add(new Genome());
			for(int j = 0; j < chromeLength; j++) {
				population.get(i).addWeight(random.nextDouble() - 2 * random.nextDouble());
			}
		}
		
	}
	
	public void crossover(ArrayList<Double> parent1, ArrayList<Double> parent2, ArrayList<Double> child1, ArrayList<Double> child2) {
		Random random = new Random();
		if(random.nextFloat() > crossoverRate || parent1 == parent2) {
			child1.addAll(parent1);
			child2.addAll(parent2);
		} else {
			int crossoverPoint = random.nextInt(chromeLength-1);
			for(int i = 0; i < crossoverPoint; i++)
			{
				child1.add(parent1.get(i));
				child2.add(parent2.get(i));
			}
			for(int i = crossoverPoint; i < parent1.size(); i++)
			{
				child1.add(parent2.get(i));
				child2.add(parent1.get(i));
			}
		}
	}
	
	public void mutate(ArrayList<Double> chromo) {
		Random random = new Random();
		for(int i = 0; i < chromo.size(); i++) {
			if(random.nextFloat() < mutationRate) {
				chromo.set(i, (random.nextDouble() - 2 * random.nextDouble()) * maxPerturbation );
			}
		}
	}
	
	public void getBest(int iBest, int numCopies, ArrayList<Genome> pop) {
		while(iBest > 0) {
			iBest--;
			for(int i = 0; i < numCopies; i++) {
				pop.add(population.get(iBest));
			}
		}
	}
	
	public ArrayList<Genome> epoch(ArrayList<Genome> lastGen) {
		
		population = lastGen;
		
		totalFitness = 0;
		bestFitness = 0;
		worstFitness = Integer.MAX_VALUE;
		averageFitness = 0;
		
		boolean swapped;
		int n = population.size();
		do { 
			swapped = false;
			for(int i = 1; i < n; i++) {
				if(population.get(i-1).isLessThan(population.get(i))) {
					Genome temp = population.get(i-1);
					population.set(i-1, population.get(i));
					population.set(i, temp);
					swapped = true;
				}
			}
			n--;
		} while(swapped);

		totalFitness = 0;		
		double highest = 0;
		double lowest  = Integer.MAX_VALUE;
		
		for (int i=0; i < popSize; i++) {
			if (population.get(i).fitness > highest) {
				highest	 = population.get(i).fitness;				
				fittestGenome = i;
				bestFitness	= highest;
			}
			if (population.get(i).fitness < lowest) {
				lowest = population.get(i).fitness;				
				worstFitness = lowest;
			}
			
			totalFitness += population.get(i).fitness;
				
		}
		
		averageFitness = totalFitness / popSize;
		
		ArrayList<Genome> newPop = new ArrayList<Genome>();
		
		if((numCopiesElite * numElite % 2) == 0) {
			getBest(numElite, numCopiesElite, newPop);
		}
		
		while (newPop.size() < popSize)
		{
			Genome mum = getRoulette();
			Genome dad = getRoulette();

			ArrayList<Double> child1 = new ArrayList<Double>();
			ArrayList<Double> child2 = new ArrayList<Double>();

			crossover(mum.weights, dad.weights, child1, child2);
			mutate(child1);
			mutate(child2);
			
			Genome newGenome1 = new Genome(child1, 0);
			Genome newGenome2 = new Genome(child2, 0);
			newPop.add(newGenome1);
			newPop.add(newGenome2);
		}

		population = newPop;
		
		
		return population;
	}
	
	public Genome getRoulette()
	{
		Random random = new Random();
		double slice = (double) (random.nextFloat()*totalFitness);
		Genome ret = new Genome();
		double fitnessCounter = 0;
		
		for(int i = 0; i < popSize; i++) {
			fitnessCounter += population.get(i).getFitness();
			
			if(fitnessCounter >= slice)	{
				return population.get(i);				
			}
		}
		return ret;
	}
	
}
