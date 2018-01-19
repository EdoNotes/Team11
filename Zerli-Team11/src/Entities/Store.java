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

public class Store implements Serializable 
{
	/**
	 * 			<Instance Variables>
	 */
	private int StoreID;
	private String BranchName;
	/**
	 * Empty Constructor
	 */
	public Store(){};
	/**
	 * Store's Full Constructor
	 * @param store-StoreID
	 * @param BrName-Branch Name
	 */
	public Store(int store,String BrName)
	{
		this.StoreID=store;
		this.BranchName=BrName;
	}
	/**
	 * Store's Partial Constructor
	 * @param store-StoreID
	 */
	public Store(int store)
	{
		this.StoreID=store;
	}
	/**
	 *<Getters And Setters Area>
	 * 	
	 */
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
	/**
	 * Store ToString Method
	 */
	@Override
	public String toString() {
		return "Store [StoreID=" + StoreID + ", BranchName=" + BranchName + "]";
	}
	
	
}
