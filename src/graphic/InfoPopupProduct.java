package graphic;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InfoPopupProduct extends InfoPopup {

	JTextField textField2 = new JTextField(20);
	MenuBarCategory menuBar = new MenuBarCategory(); 	 
	MenuBarQuantity menuBarQuantity = new MenuBarQuantity();
	
	public InfoPopupProduct() {
		
		Object [] complexMsg = { menuBar, new JLabel("Price: "), textField1, new JLabel("Model :"),
				textField2, "Quantity :", menuBarQuantity };
		String [] options = {  "Cancel", "Confirm" };
		
		confirm = JOptionPane.showOptionDialog(null, complexMsg, "title",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
	            options, options[0]); 
	}	
	
	public String getCategory() {
		return menuBar.getCategory();
	}
	
	public double getPrice() {
		//0.0 is considered as a not valid price
		if (!textField1.getText().equals(null)) 
			try {
				if( Double.parseDouble(textField1.getText()) > 0 )
					return Double.parseDouble(textField1.getText());
			}
			catch (NumberFormatException exc) {
				return 0.0;
			}
		return 0.0;		
	}
	
	public String getModel() {
		return textField2.getText();
	}
	
	public String getQuantity() {
		return menuBarQuantity.getQuantity();
	}
}
