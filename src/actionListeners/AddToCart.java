package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import manager.Menu;
import products.Product;

public class AddToCart implements ActionListener {

	Menu menu;
	Product product;
	
	public AddToCart(Menu menu, Product product) {
		this.menu = menu;
		this.product = product;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		menu.addingProductToCart(product);
	}

}
