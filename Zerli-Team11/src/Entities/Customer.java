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

/**
 * This Class Presents Customer Details On The System
 * @author edoon
 *
 */
public class Customer extends User 
{
	/**
	 * 			<Instance Variables>
	 */
	private int customerID;
	private int isSettlement;//if the currency is positive and there is not debt
	private int isMember;
	private String CreditCard;
	private double Balance;


	 /**
	  * 
	  * @param CusId-customerID
	  * @param CusUserName-Customer's User name
	  * @param CusIsSettelemet-if the currency is positive and there is not debt
	  * @param CusIsMember-If The Customer Has A Subscription(Monthly Or Yearly) Or Not
	  */
	public Customer(int CusId,String CusUserName,int CusIsSettelemet,int CusIsMember)
	{
		super();//User Constructor
		
		this.customerID=CusId;
		super.setUserName(CusUserName);
		this.isSettlement=CusIsSettelemet;
		this.isMember=CusIsMember;
		this.Balance=0;
	}
	/**
	 * 
	 * @param UserName-Customer's User name
	 */
	public Customer(String UserName)
	{
		super.setUserName(UserName);
	}
	/**
	 * Empty Constructor Of Customer
	 */
	public Customer() {}
	/**
	 * Partial Constructor For Customer 
	 * @param CustomerId- Customer Id
	 */
	public Customer(int CustomerId)
	{
		this.customerID=CustomerId;
	}
	/**
	 *<Getters And Setters Area>
	 * 	
	 */
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getIsSettlement() {
		return isSettlement;
	}
	public void setIsSettlement(int isSettlement) {
		this.isSettlement = isSettlement;
	}
	public int getIsMember() {
		return isMember;
	}
	public void setIsMember(int isMember) {
		this.isMember = isMember;
	}

	public String getCreditCard() {
		return CreditCard;
	}
	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	/**
	 * Customer ToString Method
	 */
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", isSettlement=" + isSettlement + ", isMember=" + isMember + ",  CreditCard=" + CreditCard +", Balance=" + Balance +" ]";
	}
}
