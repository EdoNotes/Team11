package Entities;


public class Customer extends User 
{
	private int customerID;
	private int isSettlement;//if the currency is positive and there is not debt
	private int isMember;

	private String CreditCard;
	private double Balance;

	public Customer(int CusId,String CusUserName,int CusIsSettelemet,int CusIsMember)
	{
		super();//User Constructor
		
		this.customerID=CusId;
		super.setUserName(CusUserName);
		this.isSettlement=CusIsSettelemet;
		this.isMember=CusIsMember;
		this.Balance=0;
	}
	public Customer(String UserName)
	{
		super.setUserName(UserName);
	}
	
	public Customer() {}
	public Customer(int CustomerId)
	{
		this.customerID=CustomerId;
	}

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
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", isSettlement=" + isSettlement + ", isMember=" + isMember + ",  CreditCard=" + CreditCard +", Balance=" + Balance +" ]";
	}
	
	
}
