package products;

public class Product {

	int id;
	String category;
	double price;
	String model;
	String imagePath;
	
	public Product() {
		id = 0;
		category = "";
		price = 0;
		model = "";
	}
	
	public Product(int _id, String _category, double _price, String _model) {
		id = _id;
		category = _category;
		imagePath = "./pictures/" + category + ".jpg";
		price = _price;
		model = _model;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath() {
		imagePath = "./pictures/" + category + ".jpg";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String _category) {
		category = _category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", price=" + price + ", model=" + model + ", imagePath="
				+ imagePath + "]";
	}
}
