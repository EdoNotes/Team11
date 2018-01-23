package Entities;

import java.io.Serializable;
import java.util.ArrayList;

import Gui.BouqeutCatalogController;

public class ProductInOrder extends Product implements Serializable{
	
	private int orderId;
	private int PIOquantity;
	private double PIOtotalProductPrice;
	
	public static ArrayList<ProductInOrder> CurCart;

	public ProductInOrder() {}

	public ProductInOrder(Product p,int PIOquantity,double PIOtotalProductPrice) {
		super(p);
		this.PIOquantity=PIOquantity;
		this.PIOtotalProductPrice=PIOtotalProductPrice;
	}

	public int getPIOquantity() {
		return PIOquantity;
	}

	public double getPIOtotalProductPrice() {
		return PIOtotalProductPrice;
	}

	public void setPIOquantity(int pIOquantity) {
		PIOquantity = pIOquantity;
	}

	public void setPIOtotalProductPrice(double pIOtotalProductPrice) {
		PIOtotalProductPrice = pIOtotalProductPrice;
	}
	

	public static ArrayList<ProductInOrder> getCurCart() {
		return CurCart;
	}

	public static void setCurCart(ArrayList<ProductInOrder> curCart) {
		CurCart = curCart;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return super.getProductName();
	}
	
	
	
	

	



}
