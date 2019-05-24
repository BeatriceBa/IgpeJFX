package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import graphic.InfoPopupSearch;
import manager.Menu;
import products.Product;

public class SearchProductAction implements ActionListener {
	
	Menu menu;
	
	public SearchProductAction (Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Product> rs = new ArrayList<Product>();
		InfoPopupSearch ip = new InfoPopupSearch();
		
		if (ip.getConfirm() == 1) {
			if (ip.getSelectedResearch().equals("category")) 
				rs = menu.getStorage().searchCategoryProduct(ip.getString());
			
			else if (ip.getSelectedResearch().equals("model")) 
				rs = menu.getStorage().searchModelProduct(ip.getString());
			
			else if (ip.getSelectedResearch().equals("price")) 
				rs = menu.getStorage().searchPriceProduct(ip.getPrice());
			
			else if (ip.getSelectedResearch().equals("id")) 
				rs = menu.getStorage().searchIdProduct(ip.getId());
			
			if (rs.isEmpty()) 
				JOptionPane.showMessageDialog(null, "No products found! :( ", "Error", JOptionPane.ERROR_MESSAGE);
			
			else 
				menu.searchProduct(rs);		
		}
	}
}
