import java.util.ArrayList;

public class Genome{
	ArrayList<Double> weights;
	double fitness;
	public Genome() {
		fitness = 0;
	}
	public Genome(ArrayList<Double> weights, double fitness) {
		this.weights = weights;
		this.fitness = fitness;
	}
	
	public boolean isLessThan(Genome g) {
		boolean ret;
		ret = false;
		if(fitness < g.fitness) {
			ret = true;
		}
		return ret;
	}
	
	
}
