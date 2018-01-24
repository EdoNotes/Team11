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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javafx.scene.image.ImageView;
/**
 * Product class save me all product data
 * @author tomer
 *
 */
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

	private String storeName;
	private byte[] productImage;
	private ImageView imageOfproduct;
	/**
	 * the parameters: startPrice , endPrice is for search the price range of the product
	 */
	private int startPrice;
	private int endPrice;
	
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
			String productDescription, double price, String storeName) {
		this.productId = productId;
		this.productName = productName;
		ProductColor = productColor;
		ProductType = productType;
		this.productDescription = productDescription;
		this.price = price;
		this.storeName = storeName;
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
		this.storeName=c.getstoreName();
	}
	
	public Product(Product p)
	{
		this.productId = p.productId;
		this.productName = p.productName;
		ProductColor = p.ProductColor;
		ProductType = p.ProductType;
		this.productDescription = p.productDescription;
		this.price = p.price;
		this.storeName =p.storeName;
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


	public String getStoreName() {
		return storeName;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public int getEndPrice() {
		return endPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public void setEndPrice(int endPrice) {
		this.endPrice = endPrice;
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

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	/**
	 * this function expect to path of image that we want to upload and create file then convert him to byte array so he can be serializable (for protocol TCP/IP)
	 * @param imagePath
	 * 
	 */
	public void setProductImage(String imagePath) {
		File f=new File(imagePath);
		try {
			this.productImage=ImageConverter.convertFileToByteArray(f);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * this function set byte array as byte array when i get him from DB
	 * @param b
	 */
	public void setProductImage(byte[] b) {
		this.productImage=b;
	}
/**
 * the function get the Image as byte array
 * @return byte[] productImage
 */
	public byte[] getProductImage() {
		return productImage;
	}
	
	public ImageView getImageOfproduct() {
		return imageOfproduct;
	}

	public void setImageOfproduct(ImageView imageOfproduct) {
		this.imageOfproduct = imageOfproduct;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", ProductColor=" + ProductColor
				+ ", ProductType=" + ProductType + ", productDescription=" + productDescription + ", price=" + price
				+ ", storeName=" + storeName + ", startPrice=" + startPrice + ", endPrice=" + endPrice + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ProductColor == null) ? 0 : ProductColor.hashCode());
		result = prime * result + ((ProductType == null) ? 0 : ProductType.hashCode());
		result = prime * result + endPrice;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((productDescription == null) ? 0 : productDescription.hashCode());
		result = prime * result + productId;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + startPrice;
		result = prime * result + ((storeName == null) ? 0 : storeName.hashCode());
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
		if (ProductColor == null) 
		{
			if (other.ProductColor != null)
				return false;
		} 
		else if (!ProductColor.equals(other.ProductColor))
			return false;
		if (ProductType == null) 
		{
			if (other.ProductType != null)
				return false;
		} 
		else if (!ProductType.equals(other.ProductType))
			return false;
		if (endPrice != other.endPrice)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (productDescription == null)
		{
			if (other.productDescription != null)
				return false;
		} 
		else if (!productDescription.equals(other.productDescription))
			return false;
		if (productId != other.productId)
			return false;
		if (productName == null)
		{
			if (other.productName != null)
				return false;
		} 
		else if (!productName.equals(other.productName))
			return false;
		if (startPrice != other.startPrice)
			return false;
		if (storeName == null) 
		{
			if (other.storeName != null)
				return false;
		} 
		else if (!storeName.equals(other.storeName))
			return false;
		return true;
	}
	
	
	

}
