package Entities;

import java.io.Serializable;

public class Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223997249565430501L;
	private int productId;
	private String productName;
	private String ProductColor;
	private String ProductType;
	private String productDescription;
	private double price;
	private int quantity;
	private int storeId;
	
	public Product(){};
	
	public Product(int productId, String productName, String productColor, String productType,
			String productDescription, double price, int quantity, int storeId) {
		this.productId = productId;
		this.productName = productName;
		ProductColor = productColor;
		ProductType = productType;
		this.productDescription = productDescription;
		this.price = price;
		this.quantity = quantity;
		this.storeId = storeId;
	}
	
	public Product(Catalog c) {
		this.productId=c.getProductId();
		this.productName=c.getProductName();
		this.ProductColor=c.getProductColor();
		this.ProductType=c.getProductType();
		this.productDescription=c.getProductDescription();
		this.price=c.getPrice();
		this.quantity=c.getQuantity();
		this.storeId=c.getQuantity();
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductColor() {
		return ProductColor;
	}

	public String getProductType() {
		return ProductType;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductColor(String productColor) {
		ProductColor = productColor;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	
	

}
