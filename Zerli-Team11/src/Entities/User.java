package Entities;

import java.io.Serializable;

/**
 * 
 * @author Matan
 * This class represents user details including the type of the user(e.g client,store manager...)
 */
public class User implements Serializable {
	
	public enum Premission {Client, StoreManager, StoreEmpployee, Expert, CostomerServiece;}
	public enum ConnectionStatus {Online, Offline,Blocked;}

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
	
	/*Empty constructor*/
	public User() {}


	/*full constructor*/
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
	
	/*Login constructor*/
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

	public Premission getUserType() {
		return UserType;
	}
//{Client, StoreManager, StoreEmpployee, Expert, CostomerServiece;}
	public void setUserType(String userType)
	{
		if(userType.equals(Premission.Client))
		{
			this.UserType=Premission.Client;
		}
		if(userType.equals(Premission.StoreManager))
		{
			this.UserType=Premission.StoreManager;
		}
		if(userType.equals(Premission.StoreEmpployee))
		{
			this.UserType=Premission.StoreEmpployee;
		}
		if(userType.equals(Premission.Expert))
		{
			this.UserType=Premission.Expert;
		}
		else//CostomerServiece
		{
			this.UserType=Premission.CostomerServiece;
		}
	}

	public ConnectionStatus getConnectionStatus() {
		return ConnectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) 
	{
		if(connectionStatus.equals(ConnectionStatus.Offline))
		{
			this.ConnectionStatus=ConnectionStatus.Offline;
		}
		else if(connectionStatus.equals(ConnectionStatus.Online))
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

}
