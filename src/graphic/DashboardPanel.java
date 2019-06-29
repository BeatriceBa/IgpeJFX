package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import database.Database;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import products.Sale;

public class DashboardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JTabbedPane tabbedpanel;
	
	JPanel numberOfSoldProduct;
	JPanel numberOfProduct;
	JPanel numberOfCustomer;
	JPanel picturePanel;
	
	JButton numberOfSoldProductButton;
	JButton numberOfProductButton;
	JButton numberOfCustomerButton;
	JButton picturePanelButton;
	
	int products = 0;
	int customers = 0;
	int sales = 0;
	
	JFXPanel fxPanel;
	JFXPanel customerStatistics;
	JFXPanel modelStatistics;
	JFXPanel categoryStatistics;
	WebView wv;	
	
    ObservableList<PieChart.Data> pieChartModelData = FXCollections.observableArrayList() ;
    Map <String,Integer> modelMap;
    PieChart chartModel;
    
    ObservableList<PieChart.Data> pieChartCategoryData = FXCollections.observableArrayList() ;
    Map <String,Integer> categoryMap;
    PieChart chartCategory;
    
    ObservableList<PieChart.Data> pieChartCustomerData = FXCollections.observableArrayList() ;
    Map <String,Integer> customerMap;
    PieChart chartCustomer;
	
	GridLayout grid;
	final Database storage;

	public DashboardPanel(JTabbedPane tabbedpanel, Database storage) {
		super();
		
		this.storage=storage;
		
		grid = new GridLayout(3,2,50,50) ;
		this.setLayout(grid);
		
		initComponents();
		
		this.tabbedpanel = tabbedpanel;
		
		this.add(numberOfSoldProductButton);
		this.add(numberOfProductButton);
		this.add(numberOfCustomerButton);
		this.add(fxPanel);
		this.add(customerStatistics);
		this.add(categoryStatistics);
		this.add(modelStatistics);
			
	}
	
	private void initComponents() {
	
		numberOfSoldProduct = new JPanel();
		numberOfProduct = new JPanel();
		numberOfCustomer = new JPanel();
		picturePanel = new JPanel();
		
		numberOfSoldProductButton = new JButton();
		numberOfProductButton = new JButton();
		numberOfCustomerButton = new JButton();
		picturePanelButton = new JButton();

		fxPanel = new JFXPanel ();
		customerStatistics = new JFXPanel();
		modelStatistics = new JFXPanel();
		categoryStatistics = new JFXPanel();
		
		try {
			Platform.runLater(new Runnable() {
	            @Override
	            public void run () {
	                wv = new WebView ();
	                wv.getEngine().load ("https://www.google.com/maps/dir/?api=1&destination=unical");
	                wv.setTranslateX(-500);
	                fxPanel.setScene ( new Scene ( wv, 1000, 750 ) );
	            }
	        } );

	    } catch ( Exception ex ) {
	    	System.out.println("An error occurred while loading map");
	    }

	}
	
    private void initButtons () {
    	numberOfSoldProductButton.setBackground(Color.PINK);
    	numberOfProductButton.setBackground(Color.PINK);
    	numberOfCustomerButton.setBackground(Color.PINK);
    	
    	numberOfSoldProductButton.setBorderPainted(false);
    	numberOfSoldProductButton.setBorder(new LineBorder(Color.WHITE));
    	
    	
    	numberOfSoldProductButton.setFont(new Font("Arial", Font.PLAIN, 20));
    	numberOfProductButton.setFont(new Font("Arial", Font.PLAIN, 20));
    	numberOfCustomerButton.setFont(new Font("Arial", Font.PLAIN, 20));
    	
    	numberOfSoldProductButton.setFocusPainted(false);
    	numberOfProductButton.setFocusPainted(false);
    	numberOfCustomerButton.setFocusPainted(false);
    	
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

	
	public void init(int products, int sales, int customer) {
		this.setBackground(Color.white);
		
		this.products = products;
		this.sales = sales;
		this.customers = customer;
		
		numberOfSoldProductButton.setText("We currently sold " + sales + " products.");
		numberOfProductButton.setText("We currently have " + products + " in stock.");
		numberOfCustomerButton.setText("We currently have " + customer + " customers.");	
		
		initButtons();
		initCategoryStatistics();
		initModelStatistics();
		initCustomerStatistics();	
	}
	
	/**
	 * Initializes the PieChart with the already existing products/sales/customers in storage.
	 * As it is a JavaFX component, it is added onto a JFXPanel
	 */
	
	private void initCategoryStatistics() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	GridPane grid = new GridPane();

            	Scene scene = new Scene(grid, 800, 0);
	       
	            categoryMap = storage.mostWantedFromSale("category");

	            for(Map.Entry<String, Integer> entry: categoryMap.entrySet()) {
	            	pieChartCategoryData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
	            }
	                
	                
	            chartCategory = new PieChart(pieChartCategoryData);
	            chartCategory.setTitle("Categories");
	  
	            grid.add(chartCategory, 0, 0);
	                
	            categoryStatistics.setScene(scene);
            }
		});	
	}
	
	private void initCustomerStatistics() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	GridPane grid = new GridPane();
            	Scene scene = new Scene(grid, 800, 0);
               	
                customerMap = storage.mostWantedFromSale("customer");
                
                for(Map.Entry<String, Integer> entry: customerMap.entrySet()) {
                	pieChartCustomerData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                chartCustomer = new PieChart(pieChartCustomerData);
                chartCustomer.setTitle("Customers");

                grid.add(chartCustomer, 0, 0);                
                
                customerStatistics.setScene(scene);
            }
        });
	}
	
	private void initModelStatistics() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	GridPane grid = new GridPane();
            	Scene scene = new Scene(grid, 800, 0);
            	
                modelMap = storage.mostWantedFromSale("model");
                
                for(Map.Entry<String, Integer> entry: modelMap.entrySet()) {
                	pieChartModelData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                chartModel = new PieChart(pieChartModelData);
                chartModel.setTitle("Models");
                
                grid.add(chartModel, 0, 0);               
                
                modelStatistics.setScene(scene);
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
	
	public void refresh(Sale sale) {
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	modelMap.merge(sale.getModel(), 1, Integer::sum);
	        	customerMap.merge(sale.getCustomer(), 1, Integer::sum);
	        	categoryMap.merge(sale.getCategory(), 1, Integer::sum);
	        	
	        	pieChartModelData.clear();
	        	pieChartCustomerData.clear();
	        	pieChartCategoryData.clear();
	        
	        	for(Map.Entry<String, Integer> entry: modelMap.entrySet()) {
                	pieChartModelData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                for(Map.Entry<String, Integer> entry: categoryMap.entrySet()) {
                	pieChartCategoryData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                for(Map.Entry<String, Integer> entry: customerMap.entrySet()) {
                	pieChartCustomerData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
	        }
	   });
	}
}
