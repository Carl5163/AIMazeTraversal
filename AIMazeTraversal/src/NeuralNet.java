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
	private int numOutputs = 4;
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
		return -1;		
	}
	
	public double sigmoid(double input, double activationResponse) {
		return 4/(1+Math.pow(Math.E, input/activationResponse));
	}
	

}
