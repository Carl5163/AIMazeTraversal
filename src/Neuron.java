import java.util.Vector;

public class Neuron {
	
	private int numInputs;
	private Vector<Integer> weights;
	
	public Neuron(int numInputs) {
		this.numInputs = numInputs;
		weights = new Vector<Integer>();
	}
	
}
