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
 * 
 * @author Matan
 * This class represents user details including the type of the user(e.g client,store manager...)
 */
public class User implements Serializable {
	
	
	/***************************************************************************/
	/**								<Enums>								
	 */
	public enum Premission {Client, StoreManager, StoreEmpployee, Expert, CostomerServiece;}
	public enum ConnectionStatus {Online, Offline,Blocked;}
	/***************************************************************************/
	
	/***************************************************************************/
	/**							<Instance Variables>
	 */
	private String userName;
	private String password;
	private int ID;
	private String FirstName;
	private String LastName;
	private String Phone;
	private String Gender;
	private String Email;
	private Premission UserType;
	private ConnectionStatus ConnectionStatus;
	private int tryToConnectCounter;
	/***************************************************************************/
	
	/***************************************************************************/
	/**								<Methods>
	 */
	/**
	 * Empty Constructor
	 */
	public User() {}


	/**
	 * Full Constructor
	 * @param userName
	 * @param password
	 * @param ID
	 * @param FirstName
	 * @param LastName
	 * @param ConnectionStatus-{Online,Offline,Blocked}
	 * @param UserType-Acess Permission for user
	 * @param Phone
	 * @param Gender
	 * @param Email
	 */
	public User(String userName,String password,int ID, String FirstName, String LastName, ConnectionStatus ConnectionStatus, Premission UserType,
			String Phone, String Gender, String Email) {
		this.userName=userName;
		this.password=password;
		this.ID = ID;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Phone = Phone;
		this.Gender = Gender;
		this.Email = Email;
		this.UserType = UserType;
		this.ConnectionStatus = ConnectionStatus;
	}
	
	/**
	 * User Login Constructor
	 * @param userName
	 * @param password
	 */
	public User(String userName,String password)
	{
		this.userName=userName;
		this.password=password;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
/**
 * 
 * @return Premission Of User
 */
	public Premission getUserType() {
		return UserType;
	}
	public void setUserType(String userType)
	{
		if(userType.equalsIgnoreCase("Client"))
		{
			this.UserType=Premission.Client;
		}
		else if(userType.equalsIgnoreCase("StoreManager"))
		{
			this.UserType=Premission.StoreManager;
		}
		else if(userType.equalsIgnoreCase("StoreEmpployee"))
		{
			this.UserType=Premission.StoreEmpployee;
		}
		else if(userType.equalsIgnoreCase("Expert"))
		{
			this.UserType=Premission.Expert;
		}
		else//CostomerServiece
		{
			this.UserType=Premission.CostomerServiece;
		}
	}
	/**
	 * 
	 * @return User's Current Connection Status On Server
	 */
	public ConnectionStatus getConnectionStatus() {
		return ConnectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) 
	{
		if(connectionStatus.equalsIgnoreCase("offline"))
		{
			this.ConnectionStatus=ConnectionStatus.Offline;
		}
		else if(connectionStatus.equalsIgnoreCase("online"))
		{
			this.ConnectionStatus=ConnectionStatus.Online;
		}
		else//Blocked
		{
			this.ConnectionStatus=ConnectionStatus.Blocked;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return Counter That Represents Wrong Login Tries
	 */
	public int getTryToConnectCounter() {
		return tryToConnectCounter;
	}

	public void setTryToConnectCounter(int tryToConnectCounter) {
		this.tryToConnectCounter = tryToConnectCounter;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", ID=" + ID + ", FirstName=" + FirstName
				+ ", LastName=" + LastName + ", Phone=" + Phone + ", Gender=" + Gender + ", Email=" + Email
				+ ", UserType=" + UserType + ", ConnectionStatus=" + ConnectionStatus + ", tryToConnectCounter="
				+ tryToConnectCounter + "]";
	}
	/***************************************************************************/
}
