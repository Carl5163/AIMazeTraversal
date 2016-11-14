import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PrefsDialog extends JDialog{
	
	private JPanel contentPanel;
	private JTextField tfInputs;
	private JTextField tfHidden;
	private JTextField tfNeuronsPerHidden;
	private JTextField tfOutputs;
	private JTextField tfActivation;
	private JTextField tfTicks;
	private JTextField tfTickLength;
	private JTextField tfPopSize;
	private JTextField tfCrossover;
	private JTextField tfMutationRate;
	private JTextField tfPerturbation;
	private JTextField tfNumElite;
	private JTextField tfCopies;
	
	public PrefsDialog() {
		setupDialog();
		setVisible(true);
	}
	
	public void setupDialog() {

		contentPanel = new JPanel();
		setTitle("Preferences");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 686, 340);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		
		JLabel lblNeuralNetworkSettings = new JLabel("Neural Network Settings");
		
		JLabel lblNewLabel = new JLabel("Number of Inputs:");
		
		tfInputs = new JTextField();
		tfInputs.setColumns(10);
		
		tfHidden = new JTextField();
		tfHidden.setColumns(10);
		
		JLabel lblNumberOfHidden = new JLabel("Number of Hidden Layers:");
		
		tfNeuronsPerHidden = new JTextField();
		tfNeuronsPerHidden.setColumns(10);
		
		JLabel lblNeuronsPerHidden = new JLabel("Neurons Per Hidden Layers:");
		
		tfOutputs = new JTextField();
		tfOutputs.setColumns(10);
		
		JLabel lblNumberOfOutputs = new JLabel("Number of Outputs:");
		
		tfActivation = new JTextField();
		tfActivation.setColumns(10);
		
		JLabel lblActivationResponse = new JLabel("Activation Response:");
		
		JLabel lblNumberOfTicks = new JLabel("Number of Ticks:");
		
		tfTicks = new JTextField();
		tfTicks.setColumns(10);
		
		JLabel lblTicks = new JLabel("Tick Length:");
		
		tfTickLength = new JTextField();
		tfTickLength.setColumns(10);
		
		JLabel lblPopulationSize = new JLabel("Population Size:");
		
		tfPopSize = new JTextField();
		tfPopSize.setColumns(10);
		
		JLabel lblCrossoverRate = new JLabel("Crossover Rate:");
		
		tfCrossover = new JTextField();
		tfCrossover.setColumns(10);
		
		JLabel lblGeneticAlgorithm = new JLabel("Genetic Algorithm Settings");
		
		JLabel lblMutationRate = new JLabel("Mutation Rate:");
		
		tfMutationRate = new JTextField();
		tfMutationRate.setColumns(10);
		
		tfPerturbation = new JTextField();
		tfPerturbation.setColumns(10);
		
		JLabel lblMaxPerturbation = new JLabel("Max Perturbation:");
		
		JLabel lblNumberOfElite = new JLabel("Number of Elite:");
		
		tfNumElite = new JTextField();
		tfNumElite.setColumns(10);
		
		tfCopies = new JTextField();
		tfCopies.setColumns(10);
		
		JLabel lblCopiesOfElite = new JLabel("Copies of Elite:");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel, Alignment.LEADING)
										.addComponent(lblNumberOfHidden, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
										.addComponent(lblNeuronsPerHidden, Alignment.LEADING))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(tfNeuronsPerHidden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfHidden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfInputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPanel.createSequentialGroup()
											.addComponent(lblNumberOfOutputs, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
											.addGap(4))
										.addGroup(gl_contentPanel.createSequentialGroup()
											.addComponent(lblActivationResponse)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(tfActivation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfOutputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGap(18))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNeuralNetworkSettings)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblNumberOfTicks, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfTicks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblCrossoverRate)
								.addGap(4)
								.addComponent(tfCrossover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblGeneticAlgorithm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lblPopulationSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblTicks, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
									.addComponent(tfPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfTickLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblMutationRate)
							.addGap(4)
							.addComponent(tfMutationRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblMaxPerturbation)
							.addGap(4)
							.addComponent(tfPerturbation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblNumberOfElite, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNumElite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblCopiesOfElite, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCopies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGeneticAlgorithm)
						.addComponent(lblNeuralNetworkSettings))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel)
								.addComponent(tfInputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(tfHidden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNumberOfHidden)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNeuronsPerHidden))
								.addComponent(tfNeuronsPerHidden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNumberOfOutputs)
								.addComponent(tfOutputs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(tfTicks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNumberOfTicks)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(tfTickLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblTicks)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblPopulationSize))
								.addComponent(tfPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblCrossoverRate))
								.addComponent(tfCrossover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblMutationRate))
								.addComponent(tfMutationRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblMaxPerturbation))
								.addComponent(tfPerturbation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(tfNumElite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblNumberOfElite)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(6)
									.addComponent(lblCopiesOfElite))
								.addComponent(tfCopies, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfActivation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblActivationResponse)))
					.addContainerGap())
		);
		gl_contentPanel.linkSize(SwingConstants.HORIZONTAL, lblNewLabel, lblNumberOfHidden, lblNeuronsPerHidden, lblNumberOfOutputs, lblActivationResponse
		, lblNumberOfTicks, lblTicks, lblPopulationSize, lblCrossoverRate, lblMutationRate, lblMaxPerturbation, lblNumberOfElite, lblCopiesOfElite);
		contentPanel.setLayout(gl_contentPanel);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}
	
}
