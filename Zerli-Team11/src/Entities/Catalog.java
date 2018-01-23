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



import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

/**
 * @author IdoKalir
 *This Class presents Catalog Details That Stored In Our System
 */
public class Catalog implements Serializable
{

	private static final long serialVersionUID = 1L;
	/***************************************************************************/
	/**					
	 * 							<Instance Variables>
	 * productId-Catalog Id
	 * productName-Catalog Name
	 * p_type-Catalog type
	 */
		//public enum dominantColor {RED,GREEN, YELLOW, BLUE, BLACK, WHITE, PURPLE};
		//public enum productType {CUSTOMIZED, BOUQUET};
		//private dominantColor dColor;
		//private productType pType;
	
		public final static String sweetbouquet="sweetbouquet";
		public final static String weddingBouquet="weddingBouquet";
		public final static String flowerpot="flowerpot";
		public final static String bouquet="bouquet";

		public final static String RED="RED";
		public final static String GREEN="GREEN";
		public final static String YELLOW="YELLOW";
		public final static String BLUE="BLUE";
		public final static String BLACK="BLACK";
		public final static String WHITE="WHITE";
		public final static String PURPLE="PURPLE";
		/***************************************************************************/
		/**					
		 * 							<Instance Variables>
		 * productId-Catalog Id
		 * productName-Catalog Name
		 * productColor-Catalog Color
		 * productType-Catalog type
		 * productDescription-Catalog Description
		 * price-Catalog Price
		 * quantity-Catalog Quantity
		 * storeId-Catalog StoreId
		 */
		private SimpleIntegerProperty productId;
		private SimpleStringProperty productName;
		private SimpleStringProperty productColor;
		private SimpleStringProperty productType;
		private SimpleStringProperty productDescription;
		private SimpleDoubleProperty price;
		private SimpleStringProperty storeName;
		private ImageView catlogImage;
		
		
		/**
		 * <Empty Constructor Of Catalog>
		 */
			public Catalog() {
				super();
				this.productId = new SimpleIntegerProperty();
				this.productName = new SimpleStringProperty();
				this.productColor = new SimpleStringProperty();
				this.productType = new SimpleStringProperty();
				this.productDescription = new SimpleStringProperty();
				this.price = new SimpleDoubleProperty();
				this.storeName=new SimpleStringProperty();
			}
		/**
		 * <Full Constructor Of Catalog Entity>
		 * @param productId-Catalog Id
		 * @param productName-Catalog Name
		 * @param productColor-Catalog Color
		 * @param productType-Catalog type
		 * @param productDescription-Catalog Description
		 * @param price-Catalog Price
		 * @param quantity-Catalog Quantity
		 * @param storeId-Catalog StoreId
		 */
		public Catalog(Integer productId, String productName, String productColor, String productType,
				String productDescription, Double price,String storeName) {
			super();
			this.productId = new SimpleIntegerProperty(productId);
			this.productName = new SimpleStringProperty(productName);
			this.productColor = new SimpleStringProperty(productColor);
			this.productType = new SimpleStringProperty(productType);
			this.productDescription = new SimpleStringProperty(productDescription);
			this.price = new SimpleDoubleProperty(price);
			this.storeName=new SimpleStringProperty(storeName);
		}
		/**
		 * Partial Constructor Of Catalog
		 * @param p-Product On Catalog
		 */
		public Catalog(Product p) {
			this.productId = new SimpleIntegerProperty(p.getProductId());
			this.productName = new SimpleStringProperty(p.getProductName());
			this.productColor = new SimpleStringProperty(p.getProductColor());
			this.productType = new SimpleStringProperty(p.getProductType());
			this.productDescription = new SimpleStringProperty(p.getProductDescription());
			this.price = new SimpleDoubleProperty(p.getPrice());
			this.storeName=new SimpleStringProperty(p.getStoreName());
			this.catlogImage=p.getImageOfproduct();
		}
		/**
		 * <Getters And Setters Area>
		 * 	
		 */
		public Integer getProductId() {
			return productId.get();
		}

		public String getProductName() {
			return productName.get();
		}

		public String getProductColor() {
			return productColor.get();
		}

		public String getProductType() {
			return productType.get();
		}

		public String getProductDescription() {
			return productDescription.get();
		}

		public Double getPrice() {
			return price.get();
		}


		public String getstoreName() {
			return storeName.get();
		}
		
		public void setstoreName(String storeName) {
			this.storeName.set(storeName);
		}
		
		public void setProductId(int id) {
			productId.set(id);
		}

		public void setProductName(String productName) {
			this.productName.set(productName);
		}
		
		public void setProductColor(String color) {
			productColor.set(color.toUpperCase());
		}
		
		public void setProductType(String type) {
			productType.set(type.toUpperCase());
		}
		
		public void setProductDescription(String desc) {
			productDescription.set(desc);
		}
		
		public void setPrice(double price) {
			this.price.set(price);
		}
		

		public ImageView getCatlogImage() {
			return catlogImage;
		}


		public void setCatlogImage(ImageView catlogImage) {
			this.catlogImage = catlogImage;
		}


		@Override
		public String toString() {
			return "Catalog [productId=" + productId + ", productName=" + productName + ", productColor=" + productColor
					+ ", productType=" + productType + ", productDescription=" + productDescription + ", price=" + price
					+ ", storeName=" + storeName + "]";
		}

}


