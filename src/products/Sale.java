package products;

public class Sale extends Product {
	String customer = "";
	String date = "";

	public Sale() {
		super();
		customer = "";
		date =  "";
	}
	
	public Sale(int _id,  String _customer, String _date, String _category, double _price, String _model) {
		super(_id, _category, _price, _model);
		customer = _customer;
		date = _date;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Sell [customer=" + customer + ", date=" + date + " " + super.toString() + "]";
	}
	
}
