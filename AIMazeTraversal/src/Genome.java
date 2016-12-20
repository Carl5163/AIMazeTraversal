import java.util.ArrayList;

public class Genome {
	private ArrayList<Double> weights;
	private int fitness;
	
	public Genome() {
		fitness = 0;
		weights = new ArrayList<Double>();
	}
	
	public Genome(ArrayList<Double> weights, int fitness) {
		this.weights = weights;
		this.fitness = fitness;
	}
	public boolean isLessThan(Genome other) {
		return getFitness() < other.getFitness();
	}
	public int getFitness() {
		return fitness;
	}
	public void addWeight(double w) {
		getWeights().add(w);
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public ArrayList<Double> getWeights() {
		return weights;
	}

	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}
}
