package Entities;

import java.io.Serializable;

public class Store implements Serializable 
{
	private int StoreID;
	private String BranchName;
	
	public Store(){};
	public Store(int store,String BrName)
	{
		this.StoreID=store;
		this.BranchName=BrName;
	}
	public Store(int store)
	{
		this.StoreID=store;
	}
	public int getStoreID() {
		return StoreID;
	}
	public void setStoreID(int storeID) {
		StoreID = storeID;
	}
	public String getBranchName() {
		return BranchName;
	}
	public void setBranchName(String branchName) {
		BranchName = branchName;
	}
	@Override
	public String toString() {
		return "Store [StoreID=" + StoreID + ", BranchName=" + BranchName + "]";
	}
	
	
}
