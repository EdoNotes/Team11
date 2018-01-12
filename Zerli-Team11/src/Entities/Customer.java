package Entities;

public class Customer extends User 
{
	private int customerID;
	private int isSettlement;//if the currency is positive and there is not debt
	private int isMember;
	
	public Customer(int CusId,String CusUserName,int CusIsSettelemet,int CusIsMember)
	{
		super();//User Constructor
		
		this.customerID=CusId;
		super.setUserName(CusUserName);
		this.isSettlement=CusIsSettelemet;
		this.isMember=CusIsMember;
	}
	public Customer(String UserName)
	{
		super.setUserName(UserName);
	}
	
	public Customer() {}

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
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", isSettlement=" + isSettlement + ", isMember=" + isMember + "]";
	}
	
	
}
