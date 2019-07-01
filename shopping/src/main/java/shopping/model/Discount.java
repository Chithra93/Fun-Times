package shopping.model;

public class Discount {

	private Long discountId;
	private String productName;
	private Long productQuantity;
	private String discountType;
	private String discountOn;
	private Long discountQuantity;
	private Long discountPercentage;
	private Long validityStartDay;
	private Long validityEndDay;

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountOn() {
		return discountOn;
	}

	public void setDiscountOn(String discountOn) {
		this.discountOn = discountOn;
	}

	public Long getDiscountQuantity() {
		return discountQuantity;
	}

	public void setDiscountQuantity(Long discountQuantity) {
		this.discountQuantity = discountQuantity;
	}

	public Long getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Long discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Long getValidityStartDay() {
		return validityStartDay;
	}

	public void setValidityStartDay(Long validityStartDay) {
		this.validityStartDay = validityStartDay;
	}

	public Long getValidityEndDay() {
		return validityEndDay;
	}

	public void setValidityEndDay(Long validityEndDay) {
		this.validityEndDay = validityEndDay;
	}	

}
