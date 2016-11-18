import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class PrefsDialog extends JDialog implements ActionListener{
	
	private JPanel contentPanel;
	private JTextField tfHidden;
	private JTextField tfNeuronsPerHidden;
	private JTextField tfActivation;
	private JTextField tfTicks;
	private JTextField tfTickLength;
	private JTextField tfPopSize;
	private JTextField tfCrossover;
	private JTextField tfMutationRate;
	private JTextField tfPerturbation;
	private JTextField tfNumElite;
	private JTextField tfCopies;
	
	private SimPrefs prefs;
	private AIMT parent;
	
	public PrefsDialog(SimPrefs prefs, AIMT parent) {
		this.prefs = prefs;
		this.parent = parent;
		setupDialog();
		setVisible(true);
	}
	
	public void setupDialog() {

		contentPanel = new JPanel();
		setTitle("Preferences");
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		MyInputVerifier iv = new MyInputVerifier(true);		
		MyInputVerifier dv = new MyInputVerifier(false);	
		
		JLabel lblNeuralNetworkSettings = new JLabel("Neural Network Settings");		
		JLabel lblNumberOfHidden = new JLabel("Number of Hidden Layers:");	
		tfHidden = new JTextField();			
		tfHidden.setText(Integer.toString(prefs.numHiddenLayers));
		tfHidden.setInputVerifier(iv);
		JLabel lblNeuronsPerHidden = new JLabel("Neurons Per Hidden Layers:");	
		tfNeuronsPerHidden = new JTextField();	
		tfNeuronsPerHidden.setText(Integer.toString(prefs.neuronsPerHiddenLayer));	
		tfNeuronsPerHidden.setInputVerifier(iv);			
		JLabel lblActivationResponse = new JLabel("Activation Response:");
		tfActivation = new JTextField();		
		tfActivation.setText(Double.toString(prefs.activationResponse));
		tfActivation.setInputVerifier(dv);				

		JLabel lblGeneticAlgorithm = new JLabel("Genetic Algorithm Settings");		
		JLabel lblNumberOfTicks = new JLabel("Number of Ticks:");		
		tfTicks = new JTextField();	
		tfTicks.setText(Integer.toString(prefs.numTicks));		
		tfTicks.setInputVerifier(iv);			
		JLabel lblTickLength = new JLabel("Tick Length:");		
		tfTickLength = new JTextField();
		tfTickLength.setText(Integer.toString(prefs.tickLength));
		tfTickLength.setInputVerifier(iv);		
		JLabel lblPopulationSize = new JLabel("Population Size:");		
		tfPopSize = new JTextField();
		tfPopSize.setText(Integer.toString(prefs.popSize));
		tfPopSize.setInputVerifier(iv);				
		JLabel lblCrossoverRate = new JLabel("Crossover Rate:");
		tfCrossover = new JTextField();
		tfCrossover.setText(Double.toString(prefs.crossoverRate));	
		tfCrossover.setInputVerifier(dv);			
		JLabel lblMutationRate = new JLabel("Mutation Rate:");		
		tfMutationRate = new JTextField();
		tfMutationRate.setText(Double.toString(prefs.mutationRate));	
		tfMutationRate.setInputVerifier(dv);		
		JLabel lblMaxPerturbation = new JLabel("Max Perturbation:");
		tfPerturbation = new JTextField();		
		tfPerturbation.setText(Double.toString(prefs.maxPerturbation));	
		tfPerturbation.setInputVerifier(dv);		
		JLabel lblNumberOfElite = new JLabel("Number of Elite:");
		tfNumElite = new JTextField();
		tfNumElite.setText(Integer.toString(prefs.numElite));	
		tfNumElite.setInputVerifier(iv);		
		JLabel lblNumCopies = new JLabel("Copies of the Elite:");
		tfCopies = new JTextField();	
		tfCopies.setText(Integer.toString(prefs.numCopiesElite));	
		tfCopies.setInputVerifier(iv);			
		
		
		GroupLayout layout = new GroupLayout(contentPanel);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()	
				.addComponent(lblNeuralNetworkSettings)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblNumberOfHidden)
					.addComponent(tfHidden)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblNeuronsPerHidden)
					.addComponent(tfNeuronsPerHidden)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblActivationResponse)
					.addComponent(tfActivation)
				)
			)
			.addGroup(layout.createParallelGroup()		
				.addComponent(lblGeneticAlgorithm)	
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblNumberOfTicks)
					.addComponent(tfTicks)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblTickLength)
					.addComponent(tfTickLength)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblPopulationSize)
					.addComponent(tfPopSize)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblCrossoverRate)
					.addComponent(tfCrossover)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblMutationRate)
					.addComponent(tfMutationRate)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblMaxPerturbation)
					.addComponent(tfPerturbation)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblNumberOfElite)
					.addComponent(tfNumElite)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(lblNumCopies)
					.addComponent(tfCopies)
				)
			)				
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup()		
				.addComponent(lblNeuralNetworkSettings)	
				.addGap(20)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblNumberOfHidden)
					.addComponent(tfHidden)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblNeuronsPerHidden)
					.addComponent(tfNeuronsPerHidden)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblActivationResponse)
					.addComponent(tfActivation)
				)
			)
			.addGroup(layout.createSequentialGroup()		
				.addComponent(lblGeneticAlgorithm)	
				.addGap(20)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblNumberOfTicks)
					.addComponent(tfTicks)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblTickLength)
					.addComponent(tfTickLength)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblPopulationSize)
					.addComponent(tfPopSize)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblCrossoverRate)
					.addComponent(tfCrossover)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblMutationRate)
					.addComponent(tfMutationRate)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblMaxPerturbation)
					.addComponent(tfPerturbation)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblNumberOfElite)
					.addComponent(tfNumElite)
				)
				.addGroup(layout.createParallelGroup()
					.addComponent(lblNumCopies)
					.addComponent(tfCopies)
				)
			)	
		);
		
		layout.linkSize(SwingConstants.VERTICAL, lblNumCopies, tfCopies, lblNumberOfElite, tfNumElite, lblMaxPerturbation, tfPerturbation, lblMutationRate, tfMutationRate, lblCrossoverRate, tfCrossover, lblPopulationSize, tfPopSize, lblTickLength, tfTickLength, lblNumberOfTicks, tfTicks, lblNumberOfHidden, tfHidden, lblNeuronsPerHidden, tfNeuronsPerHidden, lblActivationResponse, tfActivation);
		layout.linkSize(SwingConstants.HORIZONTAL, lblNumCopies, tfCopies, lblNumberOfElite, tfNumElite, lblMaxPerturbation, tfPerturbation, lblMutationRate, tfMutationRate, lblCrossoverRate, tfCrossover, lblPopulationSize, tfPopSize, lblTickLength, tfTickLength, lblNumberOfTicks, tfTicks, lblNumberOfHidden, tfHidden, lblNeuronsPerHidden, tfNeuronsPerHidden, lblActivationResponse, tfActivation);
		
		contentPanel.setLayout(layout);
				
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
		
		pack();
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OK")) {
			parent.stop();
			prefs.numHiddenLayers = (int) Double.parseDouble(tfHidden.getText().trim());
			prefs.neuronsPerHiddenLayer = (int) Double.parseDouble(tfNeuronsPerHidden.getText().trim());
			prefs.activationResponse = Double.parseDouble(tfActivation.getText().trim());
			prefs.numTicks = (int) Double.parseDouble(tfTicks.getText().trim());
			prefs.tickLength = (int) Double.parseDouble(tfTickLength.getText().trim());
			prefs.popSize = (int) Double.parseDouble(tfPopSize.getText().trim());
			prefs.crossoverRate = Double.parseDouble(tfCrossover.getText().trim());
			prefs.mutationRate = Double.parseDouble(tfMutationRate.getText().trim());
			prefs.maxPerturbation = Double.parseDouble(tfPerturbation.getText().trim());
			prefs.numElite = (int) Double.parseDouble(tfNumElite.getText().trim());
			prefs.numCopiesElite = (int) Double.parseDouble(tfCopies.getText().trim());
			try {
				prefs.write();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dispose();
		} else {
			dispose();
		}
	}
	
}
