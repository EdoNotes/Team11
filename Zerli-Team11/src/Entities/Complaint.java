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

/**
 * @author edoon
 *This Class Handle Complaint Entity
 */
public class Complaint implements Serializable {
	/**
	 * 			<Instance Variables>
	 * 
	 */
	private int ComplaintId;
	private int CustomerId;
	private int StoreId;
	private String ComplaintDetails;
	private String assigningDate;
	private String assigningTime;
	private int gotTreatment;/*The Complaint Handle Status(1=<Yes>,0=<No>)*/
	private int gotRefund;/*The Complaint Refund Handle Status(1=<Yes>,0=<No>)*/
	public static int ComplaintIndex=1;
	/**
	 * 
	 * @param CompId-Complaint ID
	 * @param cusId-Customer ID
	 * @param StorId-Store ID
	 * @param Details-Complaint Details
	 * @param date-Complaint Assigning Date
	 * @param time-Complaint Assigning Time
	 */
	public Complaint(int CompId,int cusId, int StorId, String Details, String date,String time) {
		this.ComplaintId=CompId;
		this.CustomerId = cusId;
		this.StoreId = StorId;
		this.ComplaintDetails = Details;
		this.assigningDate = date;
		this.assigningTime=time;
		this.gotTreatment = 0;
		this.gotRefund = 0;
		ComplaintIndex++;
	}
	/**
	 * Empty Constructor Of Complaint
	 */
	public Complaint() {}
	/**
	 * 
	 * @param CompId-Complaint ID
	 */
	public Complaint(int CompId)
	{
		this.ComplaintId=CompId;
	}
	/**
	 *<Getters And Setters Area>
	 * 	
	 */
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
	public String getAssigningTime() {
		return assigningTime;
	}
	public void setAssigningTime(String assigningTime) {
		this.assigningTime = assigningTime;
	}

}
