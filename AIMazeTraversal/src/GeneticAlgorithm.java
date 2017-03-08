import java.io.*;
import java.util.*;

public class GeneticAlgorithm {

	private int totalFitness;
	private double averageFitness;	
	private int worstFitness;	
	private int bestFitness;	
	private ArrayList<Genome> population;
	private SimPrefs prefs;
	
	public GeneticAlgorithm(SimPrefs prefs, int chromeLength) {
		
		this.prefs = prefs;
		
		Random random = new Random();
		population = new ArrayList<Genome>();
		
		for(int i = 0; i < prefs.popSize; i++) {
			population.add(new Genome());
			for(int j = 0; j < chromeLength; j++) {
				population.get(i).addWeight(random.nextDouble() - 2 * random.nextDouble());
			}
		}		
	}
	
	public void crossover(ArrayList<Double> parent1, ArrayList<Double> parent2, ArrayList<Double> child1, ArrayList<Double> child2) {
		Random random = new Random();
		if(random.nextFloat() > prefs.crossoverRate || parent1 == parent2) {
			child1.addAll(parent1);
			child2.addAll(parent2);
		} else {
			int crossoverPoint = random.nextInt(parent1.size()-1);
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
			if(random.nextFloat() < prefs.mutationRate) {
				chromo.set(i, (random.nextDouble() - 2 * random.nextDouble()) * prefs.maxPerturbation );
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
		int highest = 0;
		int lowest  = Integer.MAX_VALUE;
		
		for (int i=0; i < prefs.popSize; i++) {
			if (population.get(i).getFitness() > highest) {
				highest	 = population.get(i).getFitness();				
				bestFitness = highest;
			}
			if (population.get(i).getFitness() < lowest) {
				lowest = population.get(i).getFitness();				
				worstFitness = lowest;
			}
			
			totalFitness += population.get(i).getFitness();
				
		}
		
		averageFitness = totalFitness / prefs.popSize;
		
		ArrayList<Genome> newPop = new ArrayList<Genome>();
		
		if((prefs.numCopiesElite * prefs.numElite % 2) == 0) {
			getBest(prefs.numElite, prefs.numCopiesElite, newPop);
		}
		
		while (newPop.size() < prefs.popSize)
		{
			Genome mum = getRoulette();
			Genome dad = getRoulette();

			ArrayList<Double> child1 = new ArrayList<Double>();
			ArrayList<Double> child2 = new ArrayList<Double>();

			crossover(mum.getWeights(), dad.getWeights(), child1, child2);
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
		
		for(int i = 0; i < prefs.popSize; i++) {
			fitnessCounter += population.get(i).getFitness();
			
			if(fitnessCounter >= slice)	{
				return population.get(i);				
			}
		}		
		return ret;
	}

	public ArrayList<Genome> getPopulation() {
		return population;
	}

	public double getAverageFitness() {
		return averageFitness;
	}
	public int getWorstFitness() {
		return worstFitness;
	}

	public int getBestFitness() {
		return bestFitness;
	}
	public int getTotalFitness() {
		return totalFitness;
	}

	
}
