package Entities;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
/**
 * Order class saves data on the customer's order
 * @author Tomer Arzuan
 *
 */
public class Order implements Serializable{
	
	public final static String pickUp="PICKUP";
	public final static String delivery="DELIVERY";
	public static final DateTimeFormatter formtDateLocal =DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static DateFormat formtTime=new SimpleDateFormat("HH:mm");
	public static DateFormat formtDate=new SimpleDateFormat("dd/MM/yyyy");
	//orderId, customerID, supplyMethod, orderPrice, greeting, Date, orderTime, isPaid, storeID
	private int orderId;
	private int customerId;
	private String supplyMethod;
	private double orderPrice;
	private String greeting;
	private String orderDate;
	private String orderTime;
	private int isPaid;
	private int storeId;
	private ArrayList<ProductInOrder> PIO;
	/**
	 * this variable keep the current order all the time that id i get him from DB once it stay with easy access
	 */
	public static Order curOrder;
	private Date d;
	private Date t; /* I need this two Date vars to init the date of the order with the formater d-date t-time*/
	
	/**
	 * partial empty constructor that create order cart and set the order time and date
	 */
	public Order(){
		d=new Date();
		t=new Date();
		this.orderDate=formtDate.format(d);
		this.orderTime=formtTime.format(t);
		this.PIO= new ArrayList<ProductInOrder>();
	}
	/**
	 * full constructor that set the time and the date of the order
	 * @param customerId
	 * @param supplyMethod
	 * @param orderPrice
	 * @param greeting
	 * @param isPaid
	 * @param storeId
	 */
	public Order(int customerId, String supplyMethod, double orderPrice, String greeting, int isPaid, int storeId) {
		d=new Date();
		t=new Date();
		this.orderDate=formtDate.format(d);
		this.orderTime=formtTime.format(t);
		this.customerId = customerId;
		this.supplyMethod = supplyMethod;
		this.orderPrice = orderPrice;
		this.greeting = greeting;
		this.isPaid = isPaid;
		this.storeId = storeId;
		this.PIO= new ArrayList<ProductInOrder>();

	}
/**
 * getters and setters
 * 
 */
	
	public int getCustomerId() {
		return customerId;
	}

	public String getSupplyMethod() {
		return supplyMethod;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public String getGreeting() {
		return greeting;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getOrferTime() {
		return orderTime;
	}

	public int getStoreId() {
		return storeId;
	}

	public ArrayList<ProductInOrder> getPIO() {
		return PIO;
	}

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

	public void setPIO(ArrayList<ProductInOrder> pIO) {
		PIO = pIO;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public void setSupplyMethod(String supplyMethod) {
		this.supplyMethod = supplyMethod;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "Order [customerId=" + customerId + ", supplyMethod=" + supplyMethod + ", orderPrice=" + orderPrice
				+ ", greeting=" + greeting + ", orderDate=" + orderDate + ", orderTime=" + orderTime + ", isPaid="
				+ isPaid + ", storeId=" + storeId + ", PIO=" + PIO + "]";
	}
	
	

}
