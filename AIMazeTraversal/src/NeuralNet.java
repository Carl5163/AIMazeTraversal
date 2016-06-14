import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class NeuralNet {
	
	public static final int NUM_INPUTS = 8;
	public static final double ACTIVATION_RESPONSE = 0;
		
	private ArrayList<Layer> layers;
	private Properties prefs; 
	
	private int numInputs = 8;
	private int numOutputs = 1;
	private int numHiddenLayers = 1;
	private int neuronsPerHiddenLayer = 4;
	double activationResponse = 1;
	double bias = -1;
	
	
	public NeuralNet() {
		prefs = new Properties();
		try {
			prefs.load(new FileInputStream("config.ini"));
			numInputs = Integer.parseInt(prefs.getProperty("NumInputs"));
			numHiddenLayers = Integer.parseInt(prefs.getProperty("NumHidden"));
			neuronsPerHiddenLayer = Integer.parseInt(prefs.getProperty("NeuronsPerHiddenLayer"));
			numOutputs = Integer.parseInt(prefs.getProperty("NumOutputs"));
			activationResponse = Double.parseDouble(prefs.getProperty("ActivationResponse"));
			bias = Double.parseDouble(prefs.getProperty("Bias"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		createNet();
	}
	
	public void createNet() {
		
	}
	
	public double[] getWeights() {
		return null;
	}
	
	public int getNumberOfWeights() {
		return -1;
	}
	
	public void putWeights(double[] newWeights) {
		
	}
	
	public int update(ArrayList<Double> inputs) {
		
		int ret;
		int cWeight;
		int inputCount;
		double netInput;
		ArrayList<Double> outputs;
		Layer currentLayer;
		Neuron currentNeuron;
		
		ret = -999;
		outputs = new ArrayList<Double>();
		
		if (inputs.size() == numInputs) {			

		  //For each layer....

			for(int i=0; i < numHiddenLayers + 1; ++i) {
				
				currentLayer = layers.get(i);
				
				if ( i > 0 ) {
				  inputs = outputs;
				}

				//outputs.clear();

				cWeight = 0;

			    //for each neuron sum the (inputs * corresponding weights).Throw
	
			    //the total at our sigmoid function to get the output.

				for(int j=0; j < currentLayer.getNumNeurons(); j++) {
					
					currentNeuron = currentLayer.getNeuron(j);
					netInput = 0;
					inputCount = currentLayer.getNeuron(j).getNumInputs();

					//for each weight

					for(int k = 0; k < inputCount - 1; k++) {

						//sum the weights x inputs

						netInput += currentNeuron.getWeight(k) * inputs.get(cWeight);
						cWeight++;

					}

		 

					//add in the bias

					netInput += currentNeuron.getWeight(numInputs-1) * bias;

				    //We can store the outputs from each layer as we generate them.		
				    //The combined activation is first filtered through the sigmoid		
				    //function
		
				    outputs.add(sigmoid(netInput, ACTIVATION_RESPONSE));

		      		cWeight = 0;
		      		
				}
			}		
		}
		
		return ret;
		
	}
	
	public double sigmoid(double input, double activationResponse) {
		return 4/(1+Math.pow(Math.E, input/2));
	}
	

}
