package graphic;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class DashboardPanel extends JPanel {

	JPanel numberOfSoldProduct = new JPanel();
	JPanel numberOfProduct = new JPanel();
	JPanel numberOfCustomer = new JPanel();
	JPanel picturePanel = new JPanel();
	
	GridLayout grid = new GridLayout(2,2,10,10) ;

	public DashboardPanel(int products, int sales, int customer) {
		super();
		
		initPanels(products,sales,customer);
		this.setLayout(grid);
		
		this.add(numberOfProduct);
		this.add(numberOfSoldProduct);
		this.add(numberOfCustomer);
		this.add(picturePanel);
	}
	
	public void initPanels(int products, int sales, int customer) {
		numberOfSoldProduct.setBackground(Color.cyan);
		numberOfProduct.setBackground(Color.red);
		numberOfCustomer.setBackground(Color.green);
		picturePanel.setBackground(Color.magenta);
	}
}
