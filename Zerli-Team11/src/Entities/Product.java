/************************************************************************** 
 * Copyright (©) Zerli System 2017-2018 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Edo Notes <Edoono24@gmail.com>
 * 			  Tomer Arzuan <Tomerarzu@gmail.com>
 * 			  Matan Sabag <19matan@gmail.com>
 * 			  Ido Kalir <idotehila@gmail.com>
 * 			  Elinor Faddoul<elinor.faddoul@gmail.com
 **************************************************************************/
package Entities;

import java.io.Serializable;

public class Product implements Serializable {
	
	/**
	 * 			<Instance Variables>
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
	/**
	 * Empty Constructor
	 */
	public Product(){};
	/**
	 * Product's Full Constructor
	 * @param productId-Product ID
	 * @param productName-Product Name
	 * @param productColor-Product Color
	 * @param productType-Product Type
	 * @param productDescription-Product's Description 
	 * @param price-Product's Price
	 * @param quantity-Product' Quantity
	 * @param storeId-Store ID
	 */
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
	/**
	 * Product Constructor For Catalog
	 * @param c
	 */
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
	/**
	 *<Getters And Setters Area>
	 * 	
	 */
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
