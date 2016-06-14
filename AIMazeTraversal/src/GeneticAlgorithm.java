import java.util.ArrayList;

public class GeneticAlgorithm {
	
	private ArrayList<Genome> population;
	private int populationSize;
	private int chromosomeLength;
	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;
	private int fittestGenome;
	private double mutationRate;
	private double crossoverRate;
	private int generation;
	
	public GeneticAlgorithm(int populationSize, double mutationRate, double crossRate, int numWeights) {
		
	}
	
	public ArrayList<Genome> epoch(ArrayList<Genome> oldGenome) {
		return null;
	}
	
	public ArrayList<Genome> GetChromosomes() {
		return population;
	}
	
	public double getAverageFitness() {
		return totalFitness/populationSize;
	}
	
	public double getBestFitness() {
		return bestFitness;
	}
	
	private void crossover(ArrayList<Double> mom, ArrayList<Double> dad, ArrayList<Double> baby1, ArrayList<Double> baby2) {
		
	}
	
	private void mutate(ArrayList<Double> chromosome) {
		
	}
	
	private Genome GetChromoRoulette() {
		return null;
	}
	
	private void GrabNBest(int nBest, int numCopies) {
		
	}
	
	private void calculateStats() {
		
	}
	
	private void reset() {
	
	}
}
