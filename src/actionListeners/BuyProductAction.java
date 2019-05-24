package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import graphic.InfoPopupSell;
import manager.Menu;

public class BuyProductAction implements ActionListener {

	Menu menu;
	
	public BuyProductAction(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e ) {
		InfoPopupSell ip = new InfoPopupSell();
		
		if (ip.getConfirm() == 1) {
			if (menu.getStorage().insertSale(ip.getID(), ip.getCustomer())) {
				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
				menu.buyProduct(ip.getID());			
			}
			else {
				JOptionPane.showMessageDialog(null, "Sale failed. (" + ip.getCustomer() + " are you sure the product existed?) ", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
