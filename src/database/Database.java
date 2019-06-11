package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import products.Product;
import products.Sale;

public class Database {

	static String url = "jdbc:sqlite:./db/storage.db";
	static int charLimit = 50;
	static double priceLimit = 1000000000;
	
	public Database() {
		createNewDatabase();
		createTables();
	}
	
	private void createNewDatabase() {
		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created."); 
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        
    }
	
	private static void createTables() {
		//Writing queries to create our two tables
        String sale = "CREATE TABLE IF NOT EXISTS sale (\n"
                + "	id integer NOT NULL,\n"
                + "	customer text NOT NULL,\n"
                + "	date text NOT NULL,\n"
                + " category text NOT NULL,\n"
                + " price real NOT NULL, \n"
                + " model text NOT NULL, \n"
                + " FOREIGN KEY (id) REFERENCES product (id)\n"
                + ");";    
        String product = "CREATE TABLE IF NOT EXISTS product (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	category text NOT NULL,\n"
                + "	price real NOT NULL,\n"
                + " model text NOT NULL\n"
                + ");";
        
        //Trying the connection
        try (Connection conn = DriverManager.getConnection(url);
        	 Statement stmt = conn.createStatement()) {
            // Executing query
        	stmt.execute(sale);
        	stmt.execute(product);
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
	}
	
	private Connection connect() {
		// SQLite connection string
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public boolean insertSale(Integer id, String customer) {
		if (customer.length() >= charLimit)
			return false;
		//Query needed to insert a sale into the storage 
		//(has as parameters category,price and model of the sold product)
        String sale = "INSERT INTO sale(id,customer,date,category,price,model)\n"
        		+ "VALUES\n"
        		+ "(\n"
        		+ "?,\n"
        		+ "?,\n"
        		+ "datetime('now', 'localtime'),\n"
        		+ "?,\n"
        		+ "?,\n"
        		+ "?\n"
        		+ ");";
        //Category,price and model are given automatically by the product
    	Product product = getProduct(id);
        if (product.getId() != 0) {
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sale)) {
	            pstmt.setInt(1, id);
	        	pstmt.setString(2, customer);
	        	pstmt.setString(3, product.getCategory());
	        	pstmt.setDouble(4, product.getPrice());
	        	pstmt.setString(5, product.getModel());
	            pstmt.executeUpdate();
	
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        //After being inserted in the sale table, it is no longer available in the product table
	        deleteFromProduct(id);
	        return true;
        }
        else 
        	return false;
    }

	public boolean insertProduct(String category, Double price, String model) {
		if (model.length() >= charLimit)
			return false;
		if (price >= priceLimit)
			return false;
		
		String prodotto = "INSERT INTO product(category,price,model)\n"
	        		+ "VALUES\n"
	        		+ "(\n"
	        		+ "?,\n"
	        		+ "?,\n"
	        		+ "?\n"
	        		+ ");";
	 
	        try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(prodotto)) {
	            pstmt.setString(1, category);
	            pstmt.setDouble(2, price);
	            pstmt.setString(3, model);
	            pstmt.executeUpdate();
	
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return true;
	        
    }

    public boolean deleteFromProduct(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        
        //Manually searching if the product is in db
        String showcaseProduct = "SELECT id, category, price, model FROM product";
    	boolean found = false;
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(showcaseProduct)){
            
            // loop through the result set
            while (rs.next() && !found) {
            	if ( rs.getInt("id") == id ) {
            		found = true;
            		break;
            	}
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //If I know there exists that product, delete it (and return true) , otherwise return false
        if (found) {
        	try (Connection conn = this.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
            	
                // set the corresponding param
                pstmt.setInt(1, id);
                // execute the delete statement
                pstmt.executeUpdate();
     
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        	
        	return true;
        }
        return false;
    }
	
    public boolean searchProduct( int id ) {
    	//Query that selects all products inside Product table
        String showcaseProduct = "SELECT id, category, price, model FROM product";

    	try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(showcaseProduct)){
               
               // loop through the result set
               while (rs.next()) {
               	if ( rs.getInt("id") == id )
               		return true;
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           
    	return false;
    }
    
    public ArrayList<Product> searchCategoryProduct ( String category ){
    	
    	String showcaseProduct = "SELECT id, category, price, model FROM product WHERE category = ?";
    	ArrayList<Product> result = new ArrayList<Product>();

    	try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(showcaseProduct)
                ){
               	
    			pstmt.setString(1, category);
    			
    			ResultSet rs    = pstmt.executeQuery();
    			// loop through the result set
	            while (rs.next()) {
	               	Product currentProduct = new Product(rs.getInt("id"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
	               	result.add(currentProduct);
	            }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }
    
    public ArrayList<Product> searchPriceProduct ( Double price ){
    	
    	String showcaseProduct = "SELECT id, category, price, model FROM product WHERE price <= ?";
    	ArrayList<Product> result = new ArrayList<Product>();

    	try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(showcaseProduct)
                ){
               	
    			pstmt.setDouble(1, price);
    			
    			ResultSet rs    = pstmt.executeQuery();
    			// loop through the result set
	            while (rs.next()) {
	               	Product currentProduct = new Product(rs.getInt("id"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
	               	result.add(currentProduct);
	            }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }
    
    public ArrayList<Product> searchModelProduct ( String model ){
    	
    	String showcaseProduct = "SELECT id, category, price, model FROM product WHERE model = ?";
    	ArrayList<Product> result = new ArrayList<Product>();

    	try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(showcaseProduct)
                ){
               	
    			pstmt.setString(1, model);
    			
    			ResultSet rs    = pstmt.executeQuery();
    			// loop through the result set
	            while (rs.next()) {
	               	Product currentProduct = new Product(rs.getInt("id"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
	               	result.add(currentProduct);
	            }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }
    
    public HashMap<String, Integer> mostWantedFromSale ( String parameter ){
    	
        String showcaseProduct = "SELECT id, customer, date, category, price, model FROM sale";
    	HashMap<String, Integer> result = new HashMap<String, Integer>();

     	try (Connection conn = this.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(showcaseProduct)){
                
                // loop through the result set
                while (rs.next()) {
                	result.merge(rs.getString(parameter), 1, Integer::sum);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    	return result;
    }
    
    public ArrayList<Product> searchIdProduct ( int id ){
    	
    	String showcaseProduct = "SELECT id, category, price, model FROM product WHERE id = ?";
    	ArrayList<Product> result = new ArrayList<Product>();

    	try (Connection conn = this.connect();
                PreparedStatement pstmt  = conn.prepareStatement(showcaseProduct)
                ){
               	
    			pstmt.setInt(1, id);
    			
    			ResultSet rs    = pstmt.executeQuery();
    			// loop through the result set
	            while (rs.next()) {
	               	Product currentProduct = new Product(rs.getInt("id"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
	               	result.add(currentProduct);
	            }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }
    
    public Product getProduct( int id ) {
    	//Query that selects a product given his ID inside Product table
        String showcaseProduct = "SELECT id, category, price, model FROM product";
        Product result = new Product();
        
    	try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(showcaseProduct)){
               
               // loop through the result set
               while (rs.next()) {
            	   if ( rs.getInt("id") == id ) {
	               		result.setId(rs.getInt("id")); 
	               		result.setCategory(rs.getString("category"));
	               		result.setPrice(rs.getDouble("price"));
	               		result.setModel(rs.getString("model"));
	               		return result;
	               	}
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }
    
    public ArrayList<Product> getProducts(){
    	//Gets all the products in storage
    	String showcaseProduct = "SELECT id, category, price, model FROM product";
    	ArrayList<Product> result = new ArrayList<Product>();

    	try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(showcaseProduct)){
               
               // loop through the result set
               while (rs.next()) {
               		Product currentProduct = new Product(rs.getInt("id"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
               		result.add(currentProduct);
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    	
    }

    public Sale getSell( int id ) {
    	//Query that selects a product given his ID inside Product table
        String showcaseProduct = "SELECT id, customer, date, category, price, model FROM sale";
        Sale result = new Sale(); 
    	try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(showcaseProduct)){
               
               // loop through the result set
               while (rs.next()) {
            	   if ( rs.getInt("id") == id ) {
	               		result.setId(rs.getInt("id")); 
	               		result.setCustomer(rs.getString("customer"));
	               		result.setDate(rs.getString("date"));
	               		result.setCategory(rs.getString("category"));
	               		result.setPrice(rs.getDouble("price"));
	               		result.setModel(rs.getString("model"));
	               		return result;
	               	}
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    }


    public ArrayList<Sale> getSales(){
    	//Gets all the products in storage
    	String showcaseSell = "SELECT id, customer, date, category, price, model FROM sale ";
    	ArrayList<Sale> result = new ArrayList<Sale>();

    	try (Connection conn = this.connect();
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(showcaseSell)){
               
               // loop through the result set
               while (rs.next()) {
               		Sale currentSell = new Sale(rs.getInt("id"), rs.getString("customer"), rs.getString("date"), rs.getString("category"), rs.getDouble("price"), rs.getString("model"));
               		result.add(currentSell);
               }
           } catch (SQLException e) {
               System.out.println(e.getMessage());
           }
    	return result;
    	
    }
    
    public int getMaximumIndex() {
    	//Function needed to fetch the newest ID (for the product list in the Menu class)
    	String getMaxIndexProduct = "SELECT id, category, price, model FROM product WHERE id = (SELECT max(id) FROM product );";
    	String getMaxIndexSell = "SELECT id, customer, date, category, price, model FROM sale WHERE id = (SELECT max(id) FROM sale );";
    	
    	int maxP = 0;
    	int maxS = 0;
    	
    	//Queries need to be executed separately:
    	//First: gets ID of the last product inserted in Product
    	try (Connection conn = this.connect();
    		Statement stmt  = conn.createStatement();
    		ResultSet rs    = stmt.executeQuery(getMaxIndexProduct)){
         		while (rs.next()) {
         			maxP = rs.getInt("id");
         	}
        } catch (SQLException e) {
               System.out.println(e.getMessage());
          }
    	//First: gets ID of the last product inserted in Sale
    	try (Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();
        		ResultSet rs    = stmt.executeQuery(getMaxIndexSell)){
             		while (rs.next()) {
             			maxS = rs.getInt("id");
             	}
            } catch (SQLException e) {
                   System.out.println(e.getMessage());
              }
    	if(maxP > maxS)
    		return maxP;
    	else 
    		return maxS;
    }
}
