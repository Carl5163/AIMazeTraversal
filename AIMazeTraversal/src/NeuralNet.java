import java.util.Vector;

public class NeuralNet {
	
	private Layer[] layers;
	private int numInputs = 8;
	private int numOutputs = 1;
	private int numHiddenLayers = 1;
	private int neuronsPerHiddenLayer = 4;
	
	
	public NeuralNet() {
		
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
	
	public int update(double[] inputs) {
		
		int ret;
		int cWeight;
		int inputCount;
		double netInput;
		double[] outputs;
		Layer currentLayer;
		Neuron currentNeuron;
		
		ret = -999;
		outputs = new double[numInputs];
		
		if (inputs.length == numInputs) {			

		  //For each layer....

			for(int i=0; i < numHiddenLayers + 1; ++i) {
				
				currentLayer = layers[i];
				
				if ( i > 0 ) {
				  inputs = outputs;
				}

				//outputs.clear();

				cWeight = 0;

		 

			    //for each neuron sum the (inputs * corresponding weights).Throw
	
			    //the total at our sigmoid function to get the output.

				for(int j=0; j < currentLayer.getNumNeurons(); j++) {
					
					currentNeuron = neurons 
					netInput = 0;
					inputCount = currentLayer.getNeuron(j).getNumInputs();

					//for each weight

					for(int k=0; k < inputCount - 1; ++k) {

						//sum the weights x inputs

						netInput += m_vecLayers[i].m_vecNeurons[j].m_vecWeight[k] * inputs[cWeight++];

					}

		 

		      //add in the bias

		      netinput += m_vecLayers[i].m_vecNeurons[j].m_vecWeight[NumInputs-1] *

		                  CParams::dBias;

		 

		      //we can store the outputs from each layer as we generate them.

		      //The combined activation is first filtered through the sigmoid

		      //function

		      outputs.push_back(Sigmoid(netinput, CParams::dActivationResponse));

		 

		      cWeight = 0;

		    }

		  }
		
		}
		
		return ret;
		
	}
	

}
