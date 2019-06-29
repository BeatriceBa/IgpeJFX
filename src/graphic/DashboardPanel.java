package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class DashboardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JTabbedPane tabbedpanel = new JTabbedPane();
	
	JPanel numberOfSoldProduct = new JPanel();
	JPanel numberOfProduct = new JPanel();
	JPanel numberOfCustomer = new JPanel();
	JPanel picturePanel = new JPanel();
	
	JButton numberOfSoldProductButton = new JButton();
	JButton numberOfProductButton = new JButton();
	JButton numberOfCustomerButton = new JButton();
	JButton picturePanelButton = new JButton();
	
	JLabel stringOfSoldProduct = new JLabel();
	JLabel stringOfProduct = new JLabel();
	JLabel stringOfCustomer = new JLabel();
	JLabel picture = new JLabel();
	
	int products = 0;
	int customers = 0;
	int sales = 0;
	
	JFXPanel fxPanel = new JFXPanel ();
	WebView wv;	
	
	GridLayout grid = new GridLayout(2,2,50,50) ;

	public DashboardPanel(JTabbedPane tabbedpanel) {
		super();
		
		this.setLayout(grid);
		this.tabbedpanel = tabbedpanel;
		
		this.add(numberOfSoldProductButton);
		this.add(numberOfProductButton);
		this.add(numberOfCustomerButton);
		this.add(fxPanel);
		
		try {
	        com.sun.javafx.application.PlatformImpl.runLater ( new Runnable () {
	            @Override
	            public void run () {
	                wv = new WebView ();
	                wv.getEngine().load ("https://www.google.com/maps/dir/?api=1&destination=unical" );
	                wv.setTranslateX(-500);
	                fxPanel.setScene ( new Scene ( wv, 1000, 750 ) );
	            }
	        } );

	    } catch ( Exception ex ) {
	    	System.out.println("An error occurred while loading map");
	    }
		
	}
	
	public void init(int products, int sales, int customer) {
		this.setBackground(Color.white);
		
		this.products = products;
		this.sales = sales;
		this.customers = customer;
		
		numberOfSoldProductButton.setText("We currently sold " + sales + " products.");
		numberOfProductButton.setText("We currently have " + products + " in stock.");
		numberOfCustomerButton.setText("We currently have " + customer + " customers.");	
		
		initButtons();
		
	}
	
	public void initButtons () {
		numberOfSoldProductButton.setBackground(Color.LIGHT_GRAY);
		numberOfProductButton.setBackground(Color.cyan);
		
		numberOfSoldProductButton.setFont(new Font("Arial", Font.PLAIN, 40));
		numberOfProductButton.setFont(new Font("Arial", Font.PLAIN, 40));
		numberOfCustomerButton.setFont(new Font("Arial", Font.PLAIN, 40));
		
		numberOfSoldProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedpanel.setSelectedIndex(2);
			}
			
		});
		numberOfProductButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedpanel.setSelectedIndex(1);
			}
			
		});
		numberOfCustomerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedpanel.setSelectedIndex(4);
			}
			
		});
	}
	
	public void registerSale (boolean customer) {
		products -= 1;
		sales+=1;
		if (customer)
			this.customers+=1;
		
		numberOfSoldProductButton.setText("We currently sold " + sales + " products.");
		numberOfProductButton.setText("We currently have " + products + " in stock.");
		numberOfCustomerButton.setText("We currently have " + customers + " customers.");
	}
}
