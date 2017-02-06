package ls.java.basket;

/**
 * Class to hold the product details read from products.csv file
 * Its immutable.
 */
final public class Product {
	/*A numerical unique product code*/
	final Integer productCode;
	
	/*Product Name*/
	final String productName;
	
	/*Product price in pence.*/
	final Integer productPrice;
	
	/*Constructor*/
	Product(Integer productCode, String productName, Integer prouctPrice)
	{
		this.productCode = productCode;
		this.productName = productName;
		this.productPrice = prouctPrice;	
	}
	

	public Integer getProductCode() {
		return productCode;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		result = prime * result
				+ ((productPrice == null) ? 0 : productPrice.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productPrice == null) {
			if (other.productPrice != null)
				return false;
		} else if (!productPrice.equals(other.productPrice))
			return false;
		return true;
	}

}
