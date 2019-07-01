package shopping.model;

public class Product {

	private String productName;

	private Double cost;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Product(String productName, Double cost) {
		super();
		this.productName = productName;
		this.cost = cost;
	}

	public Product() {
		super();
	}

}
