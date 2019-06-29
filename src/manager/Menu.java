package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import actionListeners.AddProductAction;
import actionListeners.AddToCart;
import actionListeners.BuyCartElements;
import actionListeners.BuyProductAction;
import actionListeners.BuyProductFromCatalogAction;
import actionListeners.RemoveProductAction;
import actionListeners.SearchProductAction;
import database.Database;
import graphic.DashboardPanel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;
import products.Customer;
import products.Product;
import products.Sale;

public class Menu {
	
	//Actual database
	Database storage = new Database();
	
	//Other
	int windowWidth;
	int windowHeight;
	int iconSize = 150;
	int buttonSize = 80;
	int panelWidth = 400;
	
	ArrayList<Product> products = new ArrayList<Product>();
	ArrayList<Sale> sales = new ArrayList<Sale>();
	ArrayList<Product> cart = new ArrayList<Product>();
	ArrayList<Product> research = new ArrayList<Product>();
	ArrayList<Customer> customers = new ArrayList<Customer>();
	
	//Creating icons for existing buttons
	ImageIcon addProductIcon = new ImageIcon("pictures/icons/AddProduct.jpg");
	ImageIcon removeProductIcon = new ImageIcon("pictures/icons/RemoveProduct.jpg");
	ImageIcon searchProductIcon = new ImageIcon("pictures/icons/SearchProduct.jpg");
	ImageIcon buyProductIcon = new ImageIcon("pictures/icons/BuyProduct.jpg");
	
	//Creating buttons and giving them a reason to live :)
	JButton addProduct = new JButton(addProductIcon);
	JButton removeProduct = new JButton(removeProductIcon);
	JButton searchProduct = new JButton(searchProductIcon);
	JButton cartButton = new JButton(buyProductIcon); 
	
	JFrame f = new JFrame("Storage");
	
	//Creating the left panel who contains buttons such as add product, remove product etc..
	JPanel leftPanel = new JPanel();
	
	//The second half of the frame is divided into three panels: product, sales, statistics
	JTabbedPane tabbedPanel = new JTabbedPane();
	
	//Creating each tab of the tabbed pane with the option of scrolling
	JPanel productsPanel = new JPanel();
	JPanel salesPanel = new JPanel();
	JPanel researchPanel = new JPanel();
	JPanel cartPanel = new JPanel();
	DashboardPanel dashboardPanel = new DashboardPanel(tabbedPanel);
	JFXPanel statisticsPanel = new JFXPanel();
	
	JScrollPane scrollSales = new JScrollPane(salesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollProducts = new JScrollPane(productsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollStatistics = new JScrollPane(statisticsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollCart = new JScrollPane(cartPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane scrollResearch = new JScrollPane(researchPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//Once the "sub-panels" are created, we create the main panel, which is a splitPane
	JSplitPane splitPanel = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT, leftPanel, tabbedPanel);
	
    ObservableList<PieChart.Data> pieChartModelData = FXCollections.observableArrayList() ;
    Map <String,Integer> modelMap;
    PieChart chartModel;
    
    ObservableList<PieChart.Data> pieChartCategoryData = FXCollections.observableArrayList() ;
    Map <String,Integer> categoryMap;
    PieChart chartCategory;
    
    ObservableList<PieChart.Data> pieChartCustomerData = FXCollections.observableArrayList() ;
    Map <String,Integer> customerMap;
    PieChart chartCustomer;

	public static void main(String[] args) {	
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		
		catch (Exception e) {
			System.out.println("WHOPSIE. Something went wrong with Nimbus LaF.");
		}
		
		@SuppressWarnings("unused")
		Menu mainmenu = new Menu();
	}
	
	/**
	 * Default constructor. Sets:
	 * -graphic components (frame and panels) 
	 * -lists (products and sales, needed to keep trace of the elements in the database
	 * -initializes buttons (override of action listener, associating icons to the button, 
	 *  handling the hover over with mouse cursor)
	 */
	public Menu() {
				
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = dim.width;
		windowHeight = dim.height - 30;
		f.setSize(new Dimension(windowWidth, windowHeight));

		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
						
		//Handles sales, products, showSales and showProducts lists, need to be always synchronized with storage
		initLists();		
		
		//Handling pie chart
		initPiechart();
		
		//Handling panel
		initPanels();
				
		//Handling buttons
		initButtons();	

		//Adding the main panel (splitPanel) to the frame
		f.add(splitPanel);
		f.setVisible(true);
	}
	
	/**
	 * Does the basic stuff for synchronizing products and sales list with the storage:
	 * -gets all the products/sales in the database with getProducts()/getSales() function 
	 * (for further details check the documentation on database.java file)
	 * -for each product/sale creates a panel (using productPanelCreation() function) and adds it
	 *  to the productsPanel/salesPanel
	 */
	private void initLists() {
		
		//List of items already in storage needs to be updated at every start
		products = storage.getProducts();
		sales = storage.getSales();
		customers = storage.getCustomers();
				
		for(int i = 0; i < products.size(); i++) {
			//Creating a panel for each product in the list
			JSplitPane tmp = productPanelCreation(products.get(i));
			//Adding the product panel to the actual panel
			productsPanel.add(tmp);
		}
				
		for (int i = 0; i < sales.size(); i++) {
			//Creating a panel for each sale in the list
			JSplitPane tmp = salePanelCreation(sales.get(i));
			//Adding the sale panel to the actual panel
			salesPanel.add(tmp);
		}		
	}

	/**
	 * Sets various parameters for each panel.
	 */
	private void initPanels() {
		
		productsPanel.setLayout(new GridLayout (0,4));
		salesPanel.setLayout(new GridLayout (0,4));
		
		dashboardPanel.init(products.size(), sales.size(), 0);
		
		productsPanel.setBackground(Color.WHITE);
		salesPanel.setBackground(Color.WHITE);
		researchPanel.setBackground(Color.WHITE);
		
		tabbedPanel.add("Dashboard", dashboardPanel);
		tabbedPanel.add("Products", scrollProducts);
		tabbedPanel.add("Sales", scrollSales);
		tabbedPanel.add("Research", scrollResearch);
		tabbedPanel.add("Statistics", scrollStatistics);
		tabbedPanel.add("Cart", cartPanel);
		
		splitPanel.setDividerLocation(buttonSize);
		splitPanel.setEnabled(false);
		splitPanel.setDividerSize(0);

		leftPanel.setLayout(new GridLayout(5, 1));
		leftPanel.setBackground(Color.white);
	
		leftPanel.add(addProduct);
		leftPanel.add(removeProduct);
		leftPanel.add(searchProduct);
//		leftPanel.add(buyProduct);	
		leftPanel.add(cartButton);

	}
	
	/**
	 * Initializes buttons:
	 * -handles the hovering over with mouse cursor with setToolTipText
	 * -to avoid the not aestethically pleasing square when a button has the focused, setFocusPainted is false
	 * -overrides actionListeners for each of the buttons:
	 *  ° addProduct: whenever the addProduct button is clicked, an InfoPopupProduct is shown (for futher
	 *                detail check the documentation on InfoPopupProduct.java file) and the user needs 
	 *                to insert informations on the product that needs to be added to the database.
	 *                In case of a missing or uncorrect field, a popup warns the user that the 
	 *                input for that specific field is not valid.
	 *                In case of multiple missing or uncorrect fields, a popup warns the user about 
	 *                the first not valid field.
	 * ° removeProduct: whenever the removeProduct button is clicked, a popup is displayed and the user
	 * 			        has to insert the ID of the product that needs to be removed.
	 *                  If the user's input is not valid, a popup is shown
	 * ° buyProduct: ---FUNCTIONING BUT USELESS---
	 * 				 whenever the buyProduct button is clicked, a popup shows up asking for the ID
	 *               of the product that needs to be sold.
	 *               A sale can be considered not valid if the id inserted does not correspond to an existing 
	 *               product.
	 * ° searchProduct: whenever the buyProduct is clicked a popup is displayed and the user has to insert 
	 *                  both the parameter of the search and the value to be searched.
	 */
	private void initButtons() {
		
		addProduct.setBackground(Color.WHITE);
		removeProduct.setBackground(Color.WHITE);
		searchProduct.setBackground(Color.WHITE);
		cartButton.setBackground(Color.WHITE);

		addProduct.setToolTipText("Add a product");
		removeProduct.setToolTipText("Remove a product");
		searchProduct.setToolTipText("Search a product");
		
		addProduct.setFocusPainted(false);
		removeProduct.setFocusPainted(false);
		searchProduct.setFocusPainted(false);
		cartButton.setFocusPainted(false);
		
		addProduct.addActionListener(new AddProductAction(this));
		removeProduct.addActionListener(new RemoveProductAction(this));
		searchProduct.addActionListener(new SearchProductAction(this));
		cartButton.addActionListener(new BuyCartElements(this));
	}
	
	/**
	 * Initializes the PieChart with the already existing products in the sales table.
	 * As it is a JavaFX component, it is added onto a JFXPanel
	 */
	private void initPiechart() {
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	GridPane grid = new GridPane();
            	Scene scene = new Scene(grid, 800, 0);
                
                modelMap = storage.mostWantedFromSale("model");
                categoryMap = storage.mostWantedFromSale("category");
                customerMap = storage.mostWantedFromSale("customer");
                
                for(Map.Entry<String, Integer> entry: modelMap.entrySet()) {
                	pieChartModelData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                for(Map.Entry<String, Integer> entry: categoryMap.entrySet()) {
                	pieChartCategoryData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                
                for(Map.Entry<String, Integer> entry: customerMap.entrySet()) {
                	pieChartCustomerData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                chartModel = new PieChart(pieChartModelData);
                chartModel.setTitle("Models");
                
                chartCategory = new PieChart(pieChartCategoryData);
                chartCategory.setTitle("Categories");
                
                chartCustomer = new PieChart(pieChartCustomerData);
                chartCustomer.setTitle("Customers");
                
                grid.add(chartModel, 0, 0);
                grid.add(chartCategory, 800, 0);
                grid.add(chartCustomer, 400, 800);
                
                statisticsPanel.setScene(scene);
            }
        });
	}

	/**
	 * Create a panel for a given product in the products list.
	 * Needed to show the details of that product.
	 * Each product has his own "sell" button. 
	 * @param Product p
	 * @return JPanel overall 
	 */
	private JSplitPane productPanelCreation(Product p) {
				
		//Overall is the complete panel, consisting of two small panels: description (which 
		//contains info about the product) and photo (which contains the actual picture)
		JPanel description = new JPanel();
		JPanel photo = new JPanel();
		JSplitPane overall = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, photo, description);
		
		description.setBackground(Color.WHITE);
		photo.setBackground(Color.WHITE);
		
		JButton sell = new JButton("Sell");
		JButton addToCart = new JButton("Add to sale");
		sell.addActionListener(new BuyProductFromCatalogAction(this,p));
		addToCart.addActionListener(new AddToCart(this,p));
		
		//Resizing the image
		ImageIcon productIcon = new ImageIcon(p.getImagePath());
		Image image = productIcon.getImage().getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
		productIcon = new ImageIcon(image);
		JLabel label = new JLabel(productIcon);
		photo.setPreferredSize(new Dimension(iconSize, iconSize));
		photo.add(label, BorderLayout.CENTER);

		//Adding descriptions
		description.setLayout(new BoxLayout(description, BoxLayout.PAGE_AXIS));
		JLabel id = new JLabel("ID : " + p.getId());
		JLabel price = new JLabel("Price : €" + p.getPrice());
		JLabel category = new JLabel("Category : " + p.getCategory());
		JLabel model = new JLabel("Model : " + p.getModel());
		description.add(id);
		description.add(price);
		description.add(category);
		description.add(model);
		description.add(sell);
		description.add(addToCart);
			
		overall.setDividerLocation(iconSize);
		overall.setEnabled(false);
		overall.setDividerSize(0);
		
		//Setting the size of the panel
		overall.setMaximumSize(new Dimension(Integer.MAX_VALUE, iconSize));

		return overall;
	}
	
	/**
	 * Same as productPanelCreation, with a sale object
	 * @param Sale s
	 * @return JPanel overall
	 */
	private JSplitPane salePanelCreation (Sale s) {
		
		JPanel description = new JPanel();
		JPanel photo = new JPanel();
		JSplitPane overall = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, photo, description);
		
		description.setBackground(Color.WHITE);
		photo.setBackground(Color.WHITE);
		
		//Image resize
		ImageIcon icon = new ImageIcon(s.getImagePath());
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(iconSize, iconSize,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		JLabel label = new JLabel(icon);
		photo.setPreferredSize(new Dimension(iconSize, iconSize));
		photo.add(label, BorderLayout.CENTER);
		
		//Text 
		description.setLayout(new BoxLayout(description, BoxLayout.PAGE_AXIS));
		JLabel id = new JLabel("ID : " + s.getId());
		JLabel customer = new JLabel("Customer : " + s.getCustomer());
		JLabel date = new JLabel("Date : " + s.getDate());
		JLabel price = new JLabel("Price : €" + s.getPrice());
		JLabel category = new JLabel("Category : " + s.getCategory());
		JLabel model = new JLabel("Model : " + s.getModel());
		description.add(id);
		description.add(customer);
		description.add(date);
		description.add(price);
		description.add(category);
		description.add(model);
		
		overall.setDividerLocation(iconSize);
		overall.setEnabled(false);
		overall.setDividerSize(0);
		
		//Setting the size of the panel
		overall.setMaximumSize(new Dimension(Integer.MAX_VALUE, iconSize));
		
		return overall;
	}
	
	/**
	 * Create a panel for a given product in the cart list.
	 * Needed to show the details of that product. Different from productpanelcreation
	 * because it just shows elements in the cart
	 * @param Product p
	 * @return JPanel overall 
	 */
	private JSplitPane cartPanelCreation(Product p) {
				
		//Overall is the complete panel, consisting of two small panels: description (which 
		//contains info about the product) and photo (which contains the actual picture)
		JPanel description = new JPanel();
		JPanel photo = new JPanel();
		JSplitPane overall = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, photo, description);
		
		description.setBackground(Color.WHITE);
		photo.setBackground(Color.WHITE);
		
		//Resizing the image
		ImageIcon productIcon = new ImageIcon(p.getImagePath());
		Image image = productIcon.getImage().getScaledInstance(iconSize, iconSize, java.awt.Image.SCALE_SMOOTH);
		productIcon = new ImageIcon(image);
		JLabel label = new JLabel(productIcon);
		photo.setPreferredSize(new Dimension(iconSize, iconSize));
		photo.add(label, BorderLayout.CENTER);

		//Adding descriptions
		description.setLayout(new BoxLayout(description, BoxLayout.PAGE_AXIS));
		JLabel id = new JLabel("ID : " + p.getId());
		JLabel price = new JLabel("Price : €" + p.getPrice());
		JLabel category = new JLabel("Category : " + p.getCategory());
		JLabel model = new JLabel("Model : " + p.getModel());
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteFromCart(p.getId());
			}
		});		
	
		description.add(id);
		description.add(price);
		description.add(category);
		description.add(model);
		description.add(deleteButton);
			
		overall.setDividerLocation(iconSize);
		overall.setEnabled(false);
		overall.setDividerSize(0);
		
		//Setting the size of the panel
		overall.setMaximumSize(new Dimension(Integer.MAX_VALUE, iconSize));

		return overall;
	}
		
	/**
	 * Creates and adds a panel for the given product p
	 * @param product
	 */
	public void addProduct (Product product) {
		products.add(product);
		JSplitPane panel = productPanelCreation(product);		
		productsPanel.add(panel);
		
		productsPanel.revalidate();
		productsPanel.repaint();
	}
	
	/**
	 * Deletes the product with the given id from the product panel.
	 * NOTE: It is not deleted from the database, this is just graphic
	 * @param id
	 */
	public void deleteProduct(int id) {
		//I need to find the product with given id (both productList and panelList have the same
		//elements in the same order)
		int j = 0;
		while(id != products.get(j).getId()) {	j++;	}
		
		//Removing: the actual product
		products.remove(j);
		productsPanel.remove(j);
		
		productsPanel.revalidate();
		productsPanel.repaint();
		deleteFromCart(id);
		deleteFromResearch(id);
		
	}
	
	/**
	 * Sells the product with the given id (just graphically, as it is already handled logically by 
	 * the Database class):
	 * adds the product to the salesPanel
	 * removes the product from the productsPanel
	 * updates the PieChart
	 * removes the product from the cart (if it was there)
	 * @param id
	 */
	public void buyProduct(Integer id) {
		deleteProduct(id);
		//Creating a temporary sale
		Sale sale = storage.getSell(id);
		sale.setImagePath();
		sales.add(sale);
		
		JSplitPane tmp = salePanelCreation(sale);
		//Refreshing the sale panel					
		salesPanel.add(tmp);
		
		deleteFromCart(id);
		deleteFromResearch(id);
		
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
		
		salesPanel.revalidate();
		salesPanel.repaint();			
	}
	
	/**
	 * Paints on the researchPanel the result of the search made on the database
	 * @param rs
	 */
	public void searchProduct(ArrayList<Product> rs) {
		researchPanel.removeAll();
		research.clear();
		for( int i = 0 ; i < rs.size(); i++ ) {
			JSplitPane tmpPanel = productPanelCreation(rs.get(i));
			researchPanel.add(tmpPanel);
			research.add(rs.get(i));
		}
		researchPanel.revalidate();
		researchPanel.repaint();
	}
	
	/**
	 * Adds the given product to the cart list and the cart panel
	 * @param p
	 */
	public void addingProductToCart (Product p) {
		for (int i=0; i<cart.size(); i++)
			if (cart.get(i).getId() == p.getId())
				return;
		
		cart.add(p);
		
		JSplitPane tmp = cartPanelCreation(p);
		cartPanel.add(tmp);
		cartPanel.revalidate();
		cartPanel.repaint();
	}
	
	public Database getStorage() {
		return storage;
	}
	
	public ArrayList<Product> getCart() {
		return cart;
	}
	
	public void emptyCart() {
		if (!cart.isEmpty()) {
			cart.clear(); 
			cartPanel.removeAll();
			cartPanel.revalidate();
			cartPanel.repaint();
		}
	}
	
	public void deleteFromCart(int id) {
		int j=0;
		boolean found=false;
		for (int i=0; i<cart.size(); i++) 
			if (cart.get(i).getId() == id ) {
				j = i;
				found = true;
			}		
		
		if (found) {
			cart.remove(j);
			cartPanel.remove(j);
			cartPanel.revalidate();
			cartPanel.repaint();
		}
	}
	
	public void deleteFromResearch(int id) {
		int j=0;
		boolean found=false;
		for (int i=0; i<research.size(); i++) 
			if (research.get(i).getId() == id ) {
				j = i;
				found = true;
			}		
		
		if (found) {
			research.remove(j);
			researchPanel.remove(j);
			researchPanel.revalidate();
			researchPanel.repaint();
		}
	}
	
	public void changeToCartTab () {
		tabbedPanel.setSelectedIndex(5);
	}
	
	public ArrayList<Customer> getCustomers() {
		return storage.getCustomers();
	}
	
}

