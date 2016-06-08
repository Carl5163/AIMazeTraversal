
public class Layer {
	
	private Neuron[] neurons;
	private int numNeurons;
	private int numInputsPerNeuron;
	
	public Layer(int numNeurons, int numInputsPerNeuron) {
		this.numNeurons = numNeurons;
		this.numInputsPerNeuron = numInputsPerNeuron;
		neurons = new Neuron[numNeurons];
		for(int i = 0; i < numNeurons; i++) {
			neurons[i] = new Neuron(numInputsPerNeuron);
		}
	}
	
	public void update() {
		
	}

	public int getNumNeurons() {
		return numNeurons;
	}

	public Neuron getNeuron(int num) {
		return neurons[num];
	}
	
}
