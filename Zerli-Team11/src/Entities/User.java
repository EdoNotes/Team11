package Entities;
/**
 * 
 * @author Matan
 * This class represents user details including the type of the user(e.g client,store manager...)
 */
public class User {
	
	public enum Premission {Client, StoreManager, StoreEmpployee, Expert, CostomerServiece;}
	public enum OnlineStatus {Online, Offline;}

	
	private int ID;
	private String FirstName;
	private String LastName;
	private String Phone;
	private String Gender;
	private String Email;
	private Premission UserType;
	private OnlineStatus ConnectionStatus;

	public User(int ID, String FirstName, String LastName, OnlineStatus ConnectionStatus, Premission UserType,
			String Phone, String Gender, String Email) {
		this.ID = ID;
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.Phone = Phone;
		this.Gender = Gender;
		this.Email = Email;
		this.UserType = UserType;
		this.ConnectionStatus = ConnectionStatus;
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

	public void setUserType(Premission userType) {
		UserType = userType;
	}

	public OnlineStatus getConnectionStatus() {
		return ConnectionStatus;
	}

	public void setConnectionStatus(OnlineStatus connectionStatus) {
		ConnectionStatus = connectionStatus;
	}

}
