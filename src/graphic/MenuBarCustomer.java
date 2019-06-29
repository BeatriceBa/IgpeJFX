package graphic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import products.Customer;

public class MenuBarCustomer extends JMenuBar {

	private static final long serialVersionUID = 1L;
	
	JMenu customer = new JMenu("Customer");
	
	String selectedCustomer = "";
	

	public MenuBarCustomer (ArrayList <Customer> customers) {
		super();
	
		for (int i=0; i<customers.size(); i++) {
			String currentCustomer = customers.get(i).getEmail();
			JMenuItem currentCustomerMenu = new JMenuItem(currentCustomer);
			currentCustomerMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					System.out.println("nuovo customer "+ currentCustomer);
					selectedCustomer = currentCustomer;
					customer.setText(selectedCustomer);
				}
			});
			customer.add(currentCustomerMenu);
		}
		
		this.add(customer);
	}
	
	public void addNewCustomer(JMenuItem newcustomer ) {
		selectedCustomer = "New customer";
		customer.add(newcustomer);
		this.add(customer);
	}
	
	public String getSelectedCustomer() {
		return selectedCustomer;
	}
}
