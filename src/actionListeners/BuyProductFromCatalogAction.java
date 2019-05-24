package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import manager.Menu;
import products.Product;

public class BuyProductFromCatalogAction implements ActionListener {

	Menu menu;
	Product product;
	
	public BuyProductFromCatalogAction(Menu menu, Product product) {
		this.menu = menu;
		this.product = product;
	}
	
	@Override
	public void actionPerformed(ActionEvent e ) {
		String cmd = "";
		cmd = JOptionPane.showInputDialog("Insert customer's email : ");
		
		if( cmd != null ) {
			if ( ! (cmd.equals(""))  ) {
				menu.getStorage().insertSale(product.getId(), cmd);
				JOptionPane.showMessageDialog(null, "Sale was successful", "Sold", JOptionPane.INFORMATION_MESSAGE);
				menu.buyProduct(product.getId());
			}	
			else 
				JOptionPane.showMessageDialog(null, "Sale failed. Did you insert customer's email?");
		}
		

	}
	
	
	

}
