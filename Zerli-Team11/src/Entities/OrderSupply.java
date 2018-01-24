package Entities;

import java.io.Serializable;
/**
 * OrderSupply saves all the data about the supply data of order no.(orderId)
 * @author tomer
 *
 */
public class OrderSupply implements Serializable{
	
	public final static String pickUp="pickUp";
	public final static String delivery="delivery";
	public final static double instantPrice=12.5;
	public final static double deliveryPrice=20;
	
	private int supplyId;
	private int orderId;
	private int storeId;
	private String supplyMethod;
	private String dateToSupp;
	private String TimeToSupp;
	private String contactName;
	private String contactAddress;
	private String contactPhone;
	private int isInstant;
	/**
	 * this variable keep the current supply all the time that id i get him from DB once it stay with easy access
	 */
	public static OrderSupply curSupply;
/**
 * Empty constryctor
 */
	public OrderSupply() {}
	
	public OrderSupply(int orderId, int storeId, String supplyMethod, String dateToSupp, String timeToSupp,
			String contactName, String contactAddress, String contactPhone, int isInstant) {
		super();
		this.orderId = orderId;
		this.storeId = storeId;
		this.supplyMethod = supplyMethod;
		this.dateToSupp = dateToSupp;
		TimeToSupp = timeToSupp;
		this.contactName = contactName;
		this.contactAddress = contactAddress;
		this.contactPhone = contactPhone;
		this.isInstant = isInstant;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getStoreId() {
		return storeId;
	}

	public String getSupplyMethod() {
		return supplyMethod;
	}

	public String getDateToSupp() {
		return dateToSupp;
	}

	public String getTimeToSupp() {
		return TimeToSupp;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public int getIsInstant() {
		return isInstant;
	}

	public int getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void setSupplyMethod(String supplyMethod) {
		this.supplyMethod = supplyMethod;
	}

	public void setDateToSupp(String dateToSupp) {
		this.dateToSupp = dateToSupp;
	}

	public void setTimeToSupp(String timeToSupp) {
		TimeToSupp = timeToSupp;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setIsInstant(int isInstant) {
		this.isInstant = isInstant;
	}

	@Override
	public String toString() {
		return "OrderSupply [orderId=" + orderId + ", storeId=" + storeId + ", supplyMethod=" + supplyMethod
				+ ", dateToSupp=" + dateToSupp + ", TimeToSupp=" + TimeToSupp + ", contactName=" + contactName
				+ ", contactAddress=" + contactAddress + ", contactPhone=" + contactPhone + ", isInstant=" + isInstant
				+ "]";
	}

}
