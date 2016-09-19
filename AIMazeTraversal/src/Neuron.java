import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	
	private int numInputs;
	private ArrayList<Neuron> neurons;
	private ArrayList<Double> weights;
	private Random random;

	
	public Neuron(int numInputs) {
		this.numInputs = numInputs+1;
		random = new Random();
		weights = new ArrayList<Double>();
		for(int i = 0; i < numInputs+1; i++) {
			weights.add(random.nextDouble() - 2 * random.nextDouble());
		}
	}
	
	public int getNumInputs() {
		return numInputs;
	}

	public double getWeight(int i) {
		return weights.get(i);
	}
}
