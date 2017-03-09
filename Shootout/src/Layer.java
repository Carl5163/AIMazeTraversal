import java.util.ArrayList;

public class Layer {
	
	private ArrayList<Neuron> neurons;
	private int numNeurons;
	
	public Layer(int numNeurons, int numInputsPerNeuron) {
		this.numNeurons = numNeurons;
		neurons = new ArrayList<Neuron>();
		for(int i = 0; i < numNeurons; i++) {
			neurons.add(new Neuron(numInputsPerNeuron));
		}
	}
	
	public void update() {
		
	}

	public int getNumNeurons() {
		return numNeurons;
	}

	public Neuron getNeuron(int i) {
		return neurons.get(i);
	}
	
}
