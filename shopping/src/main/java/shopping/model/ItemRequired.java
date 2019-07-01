package shopping.model;

public class ItemRequired {

	private String productName;

	private int qty;
	
	private Double cost;

	public ItemRequired(String productName, int qty) {
		super();
		this.productName = productName;
		this.qty = qty;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	

}
