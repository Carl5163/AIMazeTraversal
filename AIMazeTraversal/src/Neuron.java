import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	
	private int numInputs;
	private ArrayList<Neuron> neurons;
	private double weights[];
	private Random random;

	
	public Neuron(int numInputs) {
		this.numInputs = numInputs;
		random = new Random();
		weights = new double[numInputs];
		for(int i = 0; i < numInputs+1; i++) {
			weights[i] = random.nextDouble() - 2 * random.nextDouble();
		}
	}
	
	public void update() {
	}
	
	public int getNumInputs() {
		return numInputs;
	}
}
