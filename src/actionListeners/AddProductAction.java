package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import graphic.InfoPopupProduct;
import manager.Menu;
import products.Product;

public class AddProductAction implements ActionListener {

	Menu menu;
	
	public AddProductAction (Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		menu.changeToProductTab();
		InfoPopupProduct ip = new InfoPopupProduct();
		
		if (ip.getConfirm() == 1) {
			//To give everytime a different popup (we are petty like that) we check if every field is valid (separately)
			if (ip.getCategory().equals("None"))
				JOptionPane.showMessageDialog(null, "Product has not been inserted. (Did you select a category?) ", "Error", JOptionPane.ERROR_MESSAGE);
			
			else if (ip.getPrice() == 0.0 )
				JOptionPane.showMessageDialog(null, "Product has not been inserted. (Was the price valid?) ", "Error", JOptionPane.ERROR_MESSAGE);
			
			else if ( ip.getModel().equals("") ) 
				JOptionPane.showMessageDialog(null, "Product has not been inserted. (Did you insert the model field?) ", "Error", JOptionPane.ERROR_MESSAGE);
			
			else if (ip.getQuantity().equals("0"))
				JOptionPane.showMessageDialog(null, "Product has not been inserted. (Was the quantity greater than 0?)", "Error", JOptionPane.ERROR_MESSAGE); 
			
			else {
				for (int i = 0; i < Integer.parseInt(ip.getQuantity()); i++) 
					if ( menu.getStorage().insertProduct(ip.getCategory(), ip.getPrice(), ip.getModel()) ) {
					//I need to know the newest id of the product (since it is auto-incremental) and add it both in the DB and list of products
					int tmpId = menu.getStorage().getMaximumIndex();
					//Creating a temporary product (adding it to the product list and using it to create his panel)
					menu.addProduct(new Product(tmpId, ip.getCategory(), ip.getPrice(), ip.getModel()));
					}
					else {
						JOptionPane.showMessageDialog(null, "Product has not been inserted.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				JOptionPane.showMessageDialog(null, "All the products have been inserted.", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}
}
