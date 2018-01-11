package Entities;

import java.sql.Date;

public class Complaint {
	private int CustomerId;
	private int StoreId;
	private String ComplaintDetails;
	private Date assigningDate;
	private int gotTreatment;
	private int gotRefund;

	public Complaint(int cusId, int StorId, String Details, Date date) {
		this.CustomerId = cusId;
		this.StoreId = StorId;
		this.ComplaintDetails = Details;
		this.assigningDate = date;
		this.gotTreatment = 0;
		this.gotRefund = 0;
	}

	public int getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}

	public int getStoreId() {
		return StoreId;
	}

	public void setStoreId(int storeId) {
		StoreId = storeId;
	}

	public String getComplaintDetails() {
		return ComplaintDetails;
	}

	public void setComplaintDetails(String complaintDetails) {
		ComplaintDetails = complaintDetails;
	}

	public Date getAssigningDate() {
		return assigningDate;
	}

	public void setAssigningDate(Date assigningDate) {
		this.assigningDate = assigningDate;
	}

	public int getGotTreatment() {
		return gotTreatment;
	}

	public void setGotTreatment(int gotTreatment) {
		this.gotTreatment = gotTreatment;
	}

	public int getGotRefund() {
		return gotRefund;
	}

	public void setGotRefund(int gotRefund) {
		this.gotRefund = gotRefund;
	}

}
