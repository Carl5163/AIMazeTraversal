import java.util.ArrayList;

public class Genome {
	ArrayList<Double> weights;
	double fitness = 0;
	
	public Genome() {
		fitness = 0;
		weights = new ArrayList<Double>();
	}
	
	public Genome(ArrayList<Double> weights, double fitness) {
		this.weights = weights;
		this.fitness = fitness;
	}
	public boolean isLessThan(Genome other) {
		return fitness < other.getFitness();
	}
	public double getFitness() {
		return fitness;
	}

	public void addWeight(double w) {
		weights.add(w);
	}
}
