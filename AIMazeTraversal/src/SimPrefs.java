
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SimPrefs {

	private Properties prefs;
	int popSize;
	int numHiddenLayers;
	int neuronsPerHiddenLayer;
	double activationResponse;
	int numTicks = 500;
	int tickLength = 1;
	double crossoverRate = 0;
	double mutationRate = 0;
	double maxPerturbation = 0;
	int numElite = 0;
	int numCopiesElite = 0;

	public SimPrefs() {

		prefs = new Properties();
		try {
			prefs.load(new FileInputStream("config.ini"));
			numTicks = Integer.parseInt(prefs.getProperty("NumTicks"));
			popSize = Integer.parseInt(prefs.getProperty("PopSize"));
			tickLength = Integer.parseInt(prefs.getProperty("TickLength"));
			numHiddenLayers = Integer.parseInt(prefs.getProperty("NumHidden"));
			neuronsPerHiddenLayer = Integer.parseInt(prefs.getProperty("NeuronsPerHiddenLayer"));
			activationResponse = Double.parseDouble(prefs.getProperty("ActivationResponse"));
			crossoverRate = Double.parseDouble(prefs.getProperty("CrossoverRate"));
			mutationRate = Double.parseDouble(prefs.getProperty("MutationRate"));
			maxPerturbation = Double.parseDouble(prefs.getProperty("MaxPerturbation"));
			numElite = Integer.parseInt(prefs.getProperty("NumElite"));
			numCopiesElite = Integer.parseInt(prefs.getProperty("NumCopiesElite"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write() throws FileNotFoundException, IOException {
		prefs.setProperty("NumTicks", Integer.toString(numTicks));
		prefs.setProperty("PopSize", Integer.toString(popSize));
		prefs.setProperty("TickLength", Integer.toString(tickLength));
		prefs.setProperty("NumHidden", Integer.toString(numHiddenLayers));
		prefs.setProperty("NeuronsPerHiddenLayer", Integer.toString(neuronsPerHiddenLayer));
		prefs.setProperty("ActivationResponse", Double.toString(activationResponse));
		prefs.setProperty("CrossoverRate", Double.toString(crossoverRate));
		prefs.setProperty("MutationRate", Double.toString(mutationRate));
		prefs.setProperty("MaxPerturbation", Double.toString(maxPerturbation));
		prefs.setProperty("NumElite", Integer.toString(numElite));
		prefs.setProperty("NumCopiesElite", Integer.toString(numCopiesElite));
		prefs.store(new FileOutputStream("config.ini"), null);
	}
}
