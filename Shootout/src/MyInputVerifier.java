import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class MyInputVerifier extends InputVerifier {

	boolean isInt = false;
	
	public MyInputVerifier(boolean isInt) {
		this.isInt = isInt;
	}
	
	@Override
	public boolean verify(JComponent arg0) {
		
		JTextField tf = (JTextField)arg0;
		try {
			if(isInt) {
				int i = (int) Math.round(Double.parseDouble(tf.getText().trim()));
				tf.setText(Integer.toString(i));
				if(i < 0) {
					throw new NumberFormatException("Number must be positive.");
				}
			} else {
				double d = Double.parseDouble(tf.getText().trim());
				if(d < 0 || d > 1) {
					throw new NumberFormatException("Number must be between 0 and 1.");
				}
			}
		} catch(NumberFormatException e) {	
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}
