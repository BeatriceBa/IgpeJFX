package graphic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InfoPopupProduct extends InfoPopup {

	JTextField textField2 = new JTextField(20);
	MenuBarCategory menuBar = new MenuBarCategory(); 	 
	
	
	public InfoPopupProduct() {
		tmpPanel.setLayout(b);

    		
        //menuBar.setMaximumSize(new Dimension(Integer.MAX_VALUE,menuBar.getMinimumSize().height));

        menuBar.setMinimumSize(new Dimension(500,50));
        menuBar.setMaximumSize(new Dimension(500,50));
        tmpPanel.add(menuBar);
		
		tmpPanel.add(new JLabel("Price :"));
		tmpPanel.add(textField1);
		
		tmpPanel.add(new JLabel("Model :"));
		tmpPanel.add(textField2);
		
		int result = JOptionPane.showConfirmDialog(null, tmpPanel, "Add", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			confirm = 1;
		} else {
			confirm = 0;
		}
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
}