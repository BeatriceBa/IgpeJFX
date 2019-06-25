package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DashboardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JPanel numberOfSoldProduct = new JPanel();
	JPanel numberOfProduct = new JPanel();
	JPanel numberOfCustomer = new JPanel();
	JPanel picturePanel = new JPanel();
	
	JLabel stringOfSoldProduct = new JLabel();
	JLabel stringOfProduct = new JLabel();
	JLabel stringOfCustomer = new JLabel();
	JLabel picture = new JLabel();
	
	//GridLayout grid = new GridLayout(2,2) ;

	public DashboardPanel() {
		super();
		
		numberOfProduct.setMinimumSize(new Dimension(800,800));
		numberOfSoldProduct.setMinimumSize(new Dimension(800,800));
		numberOfCustomer.setMinimumSize(new Dimension(800,800));
		picturePanel.setMinimumSize(new Dimension(800,800));
		
		//this.setLayout(grid);
		
		this.add(numberOfProduct);
		this.add(numberOfSoldProduct);
		this.add(numberOfCustomer);
		this.add(picturePanel);
	}
	
	public void init(int products, int sales, int customer) {
		this.setBackground(Color.white);
		
		stringOfSoldProduct.setText("We currently sold " + sales + " products.");
		stringOfProduct.setText("We currently have " + products + " in stock.");
		stringOfCustomer.setText("We currently have " + customer + " customers.");		
				
		numberOfSoldProduct.add(stringOfSoldProduct);
		numberOfProduct.add(stringOfProduct);
		numberOfCustomer.add(stringOfCustomer);
		
		numberOfSoldProduct.setBackground(Color.cyan);
		numberOfProduct.setBackground(Color.red);
		numberOfCustomer.setBackground(Color.green);
		picturePanel.setBackground(Color.magenta);
	}
}
