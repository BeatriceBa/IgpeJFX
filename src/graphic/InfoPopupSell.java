package graphic;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InfoPopupSell extends InfoPopup{

	JTextField textField2 = new JTextField(20);
	
	public InfoPopupSell() {
		tmpPanel.setLayout(b);
		
		tmpPanel.add(new JLabel("ID :"));
		tmpPanel.add(textField1);
		
		tmpPanel.add(new JLabel("Customer :"));
		tmpPanel.add(textField2);
		
		int result = JOptionPane.showConfirmDialog(null, tmpPanel, "Buy", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			confirm = 1;
		} else {
			confirm = 0;
		}
	}
	
	public int getID() {
		//0 is considered as a not valid ID
		if (!textField1.getText().equals(null)) 
			try {
				return Integer.parseInt(textField1.getText());
			}
			catch (NumberFormatException exc) {
				return 0;
			}
		return 0;
	}
	
	public String getCustomer() {
		if (!(textField1.getText().equals("")))
			return textField2.getText();
		return "InfoNotAvailable";
	}
}
