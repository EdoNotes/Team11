package Entities;

import java.io.Serializable;

public class Complaint implements Serializable {
	private int ComplaintId;
	private int CustomerId;
	private int StoreId;
	private String ComplaintDetails;
	private String assigningDate;
	private int gotTreatment;
	private int gotRefund;
	public static int ComplaintIndex=1;

	public Complaint(int CompId,int cusId, int StorId, String Details, String date) {
		this.ComplaintId=CompId;
		this.CustomerId = cusId;
		this.StoreId = StorId;
		this.ComplaintDetails = Details;
		this.assigningDate = date;
		this.gotTreatment = 0;
		this.gotRefund = 0;
		ComplaintIndex++;
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

	public String getAssigningDate() {
		return assigningDate;
	}

	public void setAssigningDate(String assigningDate) {
		this.assigningDate = assigningDate;
	}

	public int getComplaintId() {
		return ComplaintId;
	}

	public void setComplaintId(int complaintId) {
		ComplaintId = complaintId;
	}
	

}
