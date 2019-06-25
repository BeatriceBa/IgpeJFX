package graphic;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class InfoPopupSellFromCatalog extends InfoPopup {
	
	JCheckBox mail = new JCheckBox("Email with receipt");
	JCheckBox receipt = new JCheckBox("Receipt");
	JCheckBox none = new JCheckBox("None");
	String result = "none";
	
	public InfoPopupSellFromCatalog() {
		tmpPanel.setLayout(b);
		
		tmpPanel.add(new JLabel("Customer :"));
		tmpPanel.add(textField1);
		
		
		tmpPanel.add(new JLabel("Choose an option:"));
		tmpPanel.add(mail);
		tmpPanel.add(receipt);
		
		mail.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if ( arg0.getStateChange() == ItemEvent.SELECTED) {
					result = "mail";
					receipt.setSelected(false);
					none.setSelected(false);
				}
				else if ( arg0.getStateChange() == ItemEvent.DESELECTED) {
					result = "none";
					mail.setSelected(false);
				}
			}
		});
		
		receipt.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if ( arg0.getStateChange() == ItemEvent.SELECTED) {
					result = "receipt";
					none.setSelected(false);
					mail.setSelected(false);
				}
				else if ( arg0.getStateChange() == ItemEvent.DESELECTED) {
					result = "none";
					receipt.setSelected(false);
				}
			}
		});
		
		int result = JOptionPane.showConfirmDialog(null, tmpPanel, "Buy", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			confirm = 1;
		} else {
			confirm = 0;
		}
	}

	
	public String getCustomer() {
		if (!(textField1.getText().equals("")))
			return textField1.getText();
		return "";
	}
	
	public String getResult() {
		return result;
	}
}
