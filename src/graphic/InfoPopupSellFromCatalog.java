package graphic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import products.Customer;

public class InfoPopupSellFromCatalog extends InfoPopup {
		
	JCheckBox mail = new JCheckBox("Email with receipt");
	JCheckBox receipt = new JCheckBox("Receipt");
	JCheckBox none = new JCheckBox("None");
	
	JMenuBar customerMenuBar = new JMenuBar();
	JMenu customerMenu = new JMenu("Select customer");
	JMenuItem newCustomer = new JMenuItem ("New customer");
	
	JTextField newCustomerEmail = new JTextField();
	JTextField newCustomerName = new JTextField();
	JTextField newCustomerSurname = new JTextField();
	
	ArrayList<Customer> customersToBeAdded = new ArrayList <Customer>();
	String selectedCustomer = "";
	String result = "none";
	
	int customerConfirm;
	
	public InfoPopupSellFromCatalog( ArrayList<Customer> customers ) {
			
		initCheckBox();
		initCustomerMenu(customers);
		
		Object complexMsg[] = {new JLabel("Customer :"), customerMenuBar, new JLabel("Choose an option:"),
				mail, receipt };

		String options[] = { "Cancel", "Confirm" };

		confirm = JOptionPane.showOptionDialog(null, complexMsg, "Sell a product",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
	            options, options[0]); 
		
	}

	private void initCheckBox() {
		
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
		
	}
	
	private void initCustomerMenu(ArrayList <Customer> customers) {
		newCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewCustomer();
			}			
		});
		customerMenu.add(newCustomer);
		
		for (int i=0; i<customers.size(); i++) {
			String currentCustomer = customers.get(i).getEmail();
			createNewJMenuItem(currentCustomer);
		}
		customerMenuBar.add(customerMenu);
	}
	
	private void addNewCustomer() {
		selectedCustomer = "New customer";
		customerMenu.setText("New customer");
		Object [] addCustomerMsg = {  new JLabel("Email:"), newCustomerEmail, 
				new JLabel("Name"), newCustomerName, new JLabel("Surname"),
				newCustomerSurname
		};
		String [] customerOptions = { "Cancel", "Add" };
		customerConfirm = JOptionPane.showOptionDialog(null, addCustomerMsg, "title",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
	            customerOptions, customerOptions[0]); 
		if (customerConfirm == 1) {
			selectedCustomer = newCustomerEmail.getText();
			customersToBeAdded.add(new Customer(newCustomerEmail.getText(), newCustomerName.getText(), newCustomerSurname.getText()));
			customerMenu.setText(newCustomerEmail.getText());
			createNewJMenuItem(newCustomerEmail.getText());
			newCustomerName.setText("");
			newCustomerSurname.setText("");
			newCustomerEmail.setText("");
		}
		else
			customerMenu.setText("Select Customer");
	}
	
	public String getCustomerEMail() {
		return selectedCustomer;
	}
	
	public String getResult() {
		return result;
	}
	
	public ArrayList<Customer> getCustomer() {
		System.out.println("Size: " + customersToBeAdded.size());
		return customersToBeAdded;
	}
	
	private void createNewJMenuItem(String currentCustomer) {
		JMenuItem currentCustomerMenuItem = new JMenuItem(currentCustomer);
		currentCustomerMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				System.out.println("nuovo customer "+ currentCustomer);
				selectedCustomer = currentCustomer;
				customerMenu.setText(currentCustomer);
			}
		});
		customerMenu.add(currentCustomerMenuItem);
	}
}
