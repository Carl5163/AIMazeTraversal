import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class NeuralNetwork {
			
	private ArrayList<Layer> layers;
	
	private int numInputs = 8;
	private int numOutputs = 4;
	private double bias = -1;
	private SimPrefs prefs;
	
	
	public NeuralNetwork(SimPrefs prefs) {
		this.prefs = prefs;
		createNet();
	}
	
	public void createNet() {
		
		layers = new ArrayList<Layer>();
		layers.add(new Layer(prefs.neuronsPerHiddenLayer, getNumInputs()));
		for(int i = 0; i < prefs.numHiddenLayers-1; i++) {
			layers.add(new Layer(prefs.neuronsPerHiddenLayer, prefs.neuronsPerHiddenLayer));
		}
		layers.add(new Layer(numOutputs, prefs.neuronsPerHiddenLayer));
		
	}
	
	public ArrayList<Double> getWeights() {
		
		ArrayList<Double> ret;
		Layer curLayer;
		Neuron curNeuron;
		
		ret = new ArrayList<Double>();
				
		for(int i = 0; i < prefs.numHiddenLayers+1; i++) {
			curLayer = layers.get(i);
			for(int j = 0; j < curLayer.getNumNeurons(); j++) {
				curNeuron = curLayer.getNeuron(j);
				for(int k = 0; k < curNeuron.getNumInputs(); k++) {
					ret.add(curNeuron.getWeight(k));
				}
			}
		}
		
		
		return ret;
	}
	
	public int getNumberOfWeights() {
		
		int ret = 0;
		Layer curLayer;
		Neuron curNeuron;
		
		for(int i = 0; i < prefs.numHiddenLayers+1; i++) {
			curLayer = layers.get(i);
			for(int j = 0; j < curLayer.getNumNeurons(); j++) {
				curNeuron = curLayer.getNeuron(j);
				for(int k = 0; k < curNeuron.getNumInputs(); k++) {
					ret++;
				}
			}
		}
		
		
		return ret;
	}
	
	public void putWeights(ArrayList<Double> newWeights) {

		Layer curLayer;
		Neuron curNeuron;
		int weightIndex = 0;
				
		for(int i = 0; i < prefs.numHiddenLayers+1; i++) {
			curLayer = layers.get(i);
			for(int j = 0; j < curLayer.getNumNeurons(); j++) {
				curNeuron = curLayer.getNeuron(j);
				for(int k = 0; k < curNeuron.getNumInputs(); k++) {
					curNeuron.setWeight(k, 
							newWeights.get(weightIndex));
					weightIndex++;
				}
			}
		}
	}
	
	public ArrayList<Double> update(ArrayList<Double> inputs) {
		
		ArrayList<Double> outputs;
		int weightIndex = 0;
		Layer curLayer;
		Neuron curNeuron;
		
		outputs = new ArrayList<Double>();
		
		for(int i = 0; i < prefs.numHiddenLayers+1; i++) {
			
			curLayer = layers.get(i);
			// If we have just finished a layer, then the outputs of the previous layer become the inputs for the new layer.
			if(i > 0) {
				inputs = outputs;
			}
			
			outputs = new ArrayList<Double>();
			weightIndex = 0;
			
			for(int j = 0; j < curLayer.getNumNeurons(); j++) {
				
				curNeuron = curLayer.getNeuron(j);
				double sigInput = 0;
				int numIn = curNeuron.getNumInputs();
								
				for(int k = 0; k < numIn-1; k++) {
					sigInput += curNeuron.getWeight(k) * inputs.get(weightIndex);
					weightIndex++;
				}
				
				sigInput += curNeuron.getWeight(numIn-1) * bias;
				outputs.add(sigmoid(sigInput, prefs.activationResponse));
				weightIndex = 0;
				
			}
			
		}
		
		
		
		return outputs;		
	}
	
	public double sigmoid(double input, double ar) {
		return (1 /(1 + Math.exp(-input/ar)));
	}

	public int getNumInputs() {
		return numInputs;
	}

	

}
