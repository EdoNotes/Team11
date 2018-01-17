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
package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import Entities.*;
import common.*;
import ocsf.server.AbstractServer;
//import ocsf.server.*;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	/*
	 * Attributes Area
	 * =============================================================================
	 * ==
	 */
	/**
	 * The default port and host to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	final public static String HOST = "localhost";

	/*
	 * Methods Area
	 * =============================================================================
	 * ==
	 */

	public EchoServer(int port) {
		super(port);
	}

	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			Msg msgRecived = (Msg) msg;
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/zerli", "root", "root");
			System.out.println("SQL connection succeed 222");
			if ((msgRecived.getClassType()).equalsIgnoreCase("User")) {
				userHandeler(msgRecived, "user", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_question")) {
				SurveyHandeler(msgRecived, "survey_question", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_answer")) {
				SurveyHandeler(msgRecived, "survey_answer", client, conn);
			}
			else if(msgRecived.getClassType().equalsIgnoreCase("Product"))
			{
				productHandler(msgRecived,client,conn);
			}
			else if ((msgRecived.getClassType()).equalsIgnoreCase("report")) {
				get_order_report(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Ask_order")) {
				get_consumer_order(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("CancelO")) {
				cancel_order(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_report")) {
				get_order_survey_report(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Customer")) {
				customerHandeler(msgRecived, "customer", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Complaint")) {
				ComplaintHandeler(msgRecived, "complaint", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Store")) {
				StoreHandeler(msgRecived, "store", client, conn);
			}
			
		} 
		catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	private static void productHandler(Msg msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		String queryToDo = msg.getqueryToDo();
		if(queryToDo.compareTo("load Bouqeut to catalog")==0)
		{
			loadProductsToCatalogByType(msg,"BOUQEUT",client,conn);
		}
		
	}

	private static void loadProductsToCatalogByType(Msg msg,String prodtype ,ConnectionToClient client, Connection conn) throws SQLException, IOException {
		ArrayList<Product> productList= new ArrayList<Product>();
		int i=0, storeId=((Product)msg.getSentObj()).getStoreId();
		String que="SELECT productID,productName,productType,productDescription,price,quantity,dominantColor FROM zerli.product WHERE StoreID=3 AND `productType`='BOUQUET';";
		System.out.println(storeId);
		//Statement stmt = conn.createStatement();
		PreparedStatement stmt=conn.prepareStatement("SELECT productID,productName,productType,productDescription,price,quantity,dominantColor FROM zerli.product WHERE `productType`= 'BOUQEUT' AND StoreID= 3;");
		/*The PreparedStatment is not so good i dont know why but i need to fix it :( */
		//stmt.setString(1, prodtype);
		//stmt.setInt(2,storeId);/*I need to put here storeID that i will send from client with combobox*/
		
		ResultSet rs=stmt.executeQuery(que);
		while(rs.next())
		{
			productList.add(new Product());
			productList.get(i).setProductId(rs.getInt("productID"));
			productList.get(i).setProductName(rs.getString("productName"));
			productList.get(i).setProductType(rs.getString("productType"));
			productList.get(i).setProductDescription(rs.getString("productDescription"));
			productList.get(i).setPrice(rs.getDouble("price"));
			productList.get(i).setQuantity(rs.getInt("quantity"));
			productList.get(i).setProductColor(rs.getString("dominantColor"));
			System.out.println(productList.get(i).getProductName());
			i++;
		}
		rs.close();
		conn.close();
		msg.setReturnObj(productList);
		client.sendToClient(msg);

		
	}

	private void StoreHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkStoreExistence") == 0) {
			searchStoreInDB(msg, tableName, client, con);
		}

	}

	private void searchStoreInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Store tmpStore = new Store();
		Msg message = (Msg) msg;
		String Query = (message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "
				+ message.getColumnToUpdate() + "=" + "?;");
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());// get Specific Field's value
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				tmpStore.setStoreID(rs.getInt(1));
				tmpStore.setBranchName(rs.getString(2));
			}
			con.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(tmpStore);// works

		((Msg) msg).setReturnObj(tmpStore);

		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void ComplaintHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("insertNewComplaint") == 0) {
			InsertComplaintToDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("getAllComplaints") == 0)
		{
			GetAllComplaintsIDsFromDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("checkComplaintExistence")==0)
		{
			SelectComplaintFromDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("UpdateComplaint")==0)
		{
			UpdateComplaintFromDB(msg, tableName, client, con);
		}
	}
	private static void UpdateComplaintFromDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		
		Msg message = (Msg) msg;
		Complaint complaintToUpdate=((Complaint)message.getSentObj());
		String Query=(message.getQueryQuestion() + " zerli."+tableName+" SET "+message.getColumnToUpdate()+"=?" +" WHERE complaintID=?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			stmt.setString(2,""+complaintToUpdate.getComplaintId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void SelectComplaintFromDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		Complaint tmpComplaint=new Complaint();
		Msg message = (Msg) msg;
		String Query=(message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "+ message.getColumnToUpdate() + "=" + "?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				tmpComplaint.setComplaintId(rs.getInt(1));
				tmpComplaint.setCustomerId(rs.getInt(2));
				tmpComplaint.setStoreId(rs.getInt(3));
				tmpComplaint.setComplaintDetails(rs.getString(4));
				tmpComplaint.setAssigningDate(rs.getString(5));
				tmpComplaint.setGotTreatment(rs.getInt(6));
				tmpComplaint.setGotRefund(rs.getInt(7));
			}
			con.close();
			rs.close();
			message.setReturnObj(tmpComplaint);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void GetAllComplaintsIDsFromDB(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		Msg message = (Msg) msg;
		ArrayList<Integer> ComplaintsIDS=new ArrayList<Integer>();
		String Query=(message.getQueryQuestion()+" FROM zerli."+tableName+";");
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while(rs.next())
			{
				if(rs.getInt(6)==0)//1 Means Complaint GotTreatment
				{
				ComplaintsIDS.add(rs.getInt(1));
				}
			}
			rs.close();
			con.close();
			message.setReturnObj((Object)ComplaintsIDS);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static void customerHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkCustomerExistence") == 0) {
			searchCustomerInDB(msg, tableName, client, con);
		}
		else if (requestMsg.getqueryToDo().compareTo("Save New Customer Settlement and Member") == 0) {
			System.out.println("ppppppp");
			CustomerToDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("UpdateCustomerDetails") == 0)
		{
			UpdateCustomerInDB(msg, tableName, client, con);
		}

	}

	private static void UpdateCustomerInDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		Msg message = (Msg) msg;
		Customer customerToUpdate=((Customer)message.getSentObj());
		String Query=(message.getQueryQuestion() + " zerli."+tableName+" SET "+message.getColumnToUpdate()+"=?" +" WHERE customerID=?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			stmt.setString(2,""+customerToUpdate.getCustomerID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*---------------------------------------------------End----------------------*/
	public static void userHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkUserExistence") == 0) // If we want to check if user is exist															// e.g to logIn

			searchUserInDB(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("update user") == 0)
			updateUserDetails(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("AddNewUserToDB") == 0)
			AddNewUser(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("check User By ID Existence") == 0)
			searchUserbyID(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("UpadateUserInDB") == 0)
			UpadateUserInDB(msg, tableName, client, con);
		

	}// userHandler
	public static void SurveyHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("SendAnswerSurveyToDB") == 0) 																		// e.g to logIn
			InsertAnswerSurveyToDB(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("select survey by numer survey") == 0)
			returnSurveyQues(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("SendNewQuestionSurveyToDB") == 0)
			addNewSurveyToDB(msg, tableName, client, con);
	}// SurveyHandler


	private static void searchCustomerInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Customer tmpCustomer = new Customer();
		Msg message = (Msg) msg;
		String Query = (message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "
				+ message.getColumnToUpdate() + "=" + "?;");
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());// get Specific Field's value
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				tmpCustomer.setCustomerID(rs.getInt(1));
				tmpCustomer.setUserName(rs.getString(2));
				tmpCustomer.setIsSettlement(rs.getInt(3));
				tmpCustomer.setIsMember(rs.getInt(4));
				tmpCustomer.setCreditCardNumber(rs.getString(5));
				tmpCustomer.setBalance(rs.getDouble(6));
			}
			con.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(tmpCustomer);// works

		((Msg) msg).setReturnObj(tmpCustomer);

		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	////////////////////////////////////////////////////
	private static void UpadateUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;

		try {
			PreparedStatement stmt = con
					.prepareStatement(message.getQueryQuestion() + " zerli." + tableName + "\nSET " + "Password = '"
							+ userToUpdate.getPassword() + "' , UserID = '" + userToUpdate.getID() + "' , FirstName = '"
							+ userToUpdate.getFirstName() + "' , LastName = '" + userToUpdate.getLastName()
							+ "' , ConnectionStatus = '" + userToUpdate.getConnectionStatus() + "' , Phone = '"
							+ userToUpdate.getPhone() + "' , Gender = '" + userToUpdate.getGender() + "' , Email = '"
							+ userToUpdate.getEmail() + "'\nWHERE UserName= '" + userToUpdate.getUserName() + "';");

			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/////////////////////////////////////////////////////////////

	public static void searchUserbyID(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User toSearch = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message.getQueryQuestion() + " FROM " + " zerli." + tableName
					+ " WHERE UserID='" + toSearch.getID() + "';");
			if (rs.next()) {
				toSearch.setUserName(rs.getString(1));// Set user name for returned object
				toSearch.setPassword(rs.getString(2));// Set Password for returned object
				toSearch.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
				toSearch.setFirstName(rs.getString(4));// Set FirstName for returned object
				toSearch.setLastName(rs.getString(5));// Set tLastName for returned object
				toSearch.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
				toSearch.setUserType(rs.getString(7));// Set UserType for returned object
				toSearch.setPhone(rs.getString(8));// Set Phone for returned object
				toSearch.setGender(rs.getString(9));// Set Gender for returned object
				toSearch.setEmail(rs.getString(10));// Set Email for returned object

			}
			con.close();

			((Msg) msg).setReturnObj(toSearch);

			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	private static void updateUserDetails(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
			PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+message.getColumnToUpdate()+"= ? WHERE UserName='"+userToUpdate.getUserName()+"'");
			stmt.setString(1, message.getValueToUpdate());
			stmt.executeUpdate();
			con.close();
	}

	public static void searchUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		User toSearch = (User) (((Msg) msg).getSentObj());
		User tmpUsr = new User();
			Statement stmt = con.createStatement();
			// Case 1: Username and Password Correct

			ResultSet rs = stmt.executeQuery(((Msg) msg).getQueryQuestion() + " FROM " + tableName + " WHERE UserName='"
					+ toSearch.getUserName() + "' AND Password='" + toSearch.getPassword() + "';");
			if (rs.next()) {
				tmpUsr.setUserName(rs.getString(1));// Set user name for returned object
				tmpUsr.setPassword(rs.getString(2));// Set Password for returned object
				tmpUsr.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
				tmpUsr.setFirstName(rs.getString(4));// Set FirstName for returned object
				tmpUsr.setLastName(rs.getString(5));// Set tLastName for returned object
				tmpUsr.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
				tmpUsr.setUserType(rs.getString(7));// Set UserType for returned object
				tmpUsr.setPhone(rs.getString(8));// Set Phone for returned object
				tmpUsr.setGender(rs.getString(9));// Set Gender for returned object
				tmpUsr.setEmail(rs.getString(10));// Set Email for returned object
			}
			// Case 2 :UserName Correct But Password Wrong
			else if (rs.next() == false) {
				rs = stmt.executeQuery(((Msg) msg).getQueryQuestion() + " FROM " + tableName + " WHERE UserName='"
						+ toSearch.getUserName() + "';");
				if (rs.next()) {
					tmpUsr.setUserName(rs.getString(1));// Set user name for returned object
					tmpUsr.setPassword(rs.getString(2));// Set Password for returned object
					tmpUsr.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
					tmpUsr.setFirstName(rs.getString(4));// Set FirstName for returned object
					tmpUsr.setLastName(rs.getString(5));// Set tLastName for returned object
					tmpUsr.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
					tmpUsr.setUserType(rs.getString(7));// Set UserType for returned object
					tmpUsr.setPhone(rs.getString(8));// Set Phone for returned object
					tmpUsr.setGender(rs.getString(9));// Set Gender for returned object
					tmpUsr.setEmail(rs.getString(10));// Set Email for returned object
				}

			}
			rs.close();
			con.close();
			((Msg) msg).setReturnObj(tmpUsr);
			client.sendToClient(msg);
	}

	/*
	 * Methods that insert a new customer to DB (עדין לא עובד - לא מגיע למתודה(
	 * =============================================================================
	 * ==
	 */

	public static void CustomerToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Customer CustomerDB = (Customer) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {

			System.out.println(message.getQueryQuestion() + " " + tableName + " ("
					+ "customerID ,UserName ,isSettlement , isMember )" + "\nVALUES " + "('"
					+ CustomerDB.getCustomerID() + "','" + CustomerDB.getUserName() + "','"
					+ CustomerDB.getIsSettlement() + "','" + CustomerDB.getIsMember() + "');");

			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " " + tableName + " ("
					+ "customerID ,UserName ,isSettlement , isMember )" + "\nVALUES " + "('"
					+ CustomerDB.getCustomerID() + "','" + CustomerDB.getUserName() + "','"
					+ CustomerDB.getIsSettlement() + "','" + CustomerDB.getIsMember() + "');");

			stmt.executeUpdate();

			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/*
	 * Methods that insert a new survey to DB
	 * =============================================================================
	 * ==
	 */
	public static void InsertAnswerSurveyToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Survey surveyDB = (Survey) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {
			
			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " zerli." + tableName + " ("
					+ "numSurvey ,answer1 ,answer2 ,answer3 ,answer4 ,answer5 ,answer6 )" + "\nVALUES " + "('"
					+ surveyDB.getNumSurvey() + "','" + surveyDB.getAnswer1() + "','" + surveyDB.getAnswer2() + "','"
					+ surveyDB.getAnswer3() + "','" + surveyDB.getAnswer4() + "','" + surveyDB.getAnswer5() + "','"
					+ surveyDB.getAnswer6() + "');");

			stmt.executeUpdate();

			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/*
	 * Method that adding new user to DB
	 * =============================================================================
	 * ==
	 */

	public static void AddNewUser(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User NewUserToAdd = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {

			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " " + tableName + " ("
					+ "UserName ,Password ,UserID ,FirstName ,LastName ,ConnectionStatus ,Permission ,Phone ,Gender ,Email)"
					+ "\nVALUES " + "('" + NewUserToAdd.getUserName() + "','" + NewUserToAdd.getPassword() + "','"
					+ NewUserToAdd.getID() + "','" + NewUserToAdd.getFirstName() + "','" + NewUserToAdd.getLastName()
					+ "','" + NewUserToAdd.getConnectionStatus() + "','" + NewUserToAdd.getUserType() + "','"
					+ NewUserToAdd.getPhone() + "','" + NewUserToAdd.getGender() + "','" + NewUserToAdd.getEmail()
					+ "');");
			stmt.executeUpdate();
			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	public void get_order_report(Msg msg, Connection con, ConnectionToClient client) throws SQLException, IOException {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());

			while (rs.next()) {

				System.out.println(rs.getString(1) + rs.getString(2));
				directory.put(rs.getString(1), rs.getString(2));
			}

			rs.close();
			System.out.println(directory);
				client.sendToClient(directory);

			con.close();
	}

	public void get_order_survey_report(Msg msg, Connection con, ConnectionToClient client) {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());

			Integer i = 1;
			rs.next();
			while (i < 7) {

				System.out.println(i.toString() + rs.getString(i));
				directory.put(i.toString(), rs.getString(i));
				i += 1;

			}

			rs.close();
			System.out.println(directory);
			try {
				client.sendToClient(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void get_consumer_order(Msg msg, Connection con, ConnectionToClient client) {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());
			while (rs.next()) {
				String order=rs.getString(1)+", price: "+rs.getString(2)+", date: "+rs.getString(3);
				System.out.println(order);
				directory.put(order,rs.getString(1));
			}
			rs.close();
			System.out.println(directory);
			try {
				client.sendToClient(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

	// SELECT orders.type,count(*) as count FROM zerli.orders WHERE date BETWEEN
	// '2011-10-01' AND '2011-12-31' and orders.shop = 'Ako' group by orders.type ;

	
	
	public static void returnSurveyQues(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String NumSurvey = (String) (((Msg) msg).getSentObj());
		Survey surveyques = new Survey();
		Msg message = (Msg) msg;
		try {
			System.out.println(NumSurvey);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message.getQueryQuestion() + "  FROM  zerli." + tableName
					+ " \nWHERE numSurvey= '" + NumSurvey + "';");

			if (rs.next()) {
				surveyques.setNumSurvey(rs.getInt(1));
				surveyques.setQuestion1(rs.getString(2));
				surveyques.setQuestion2(rs.getString(3));
				surveyques.setQuestion3(rs.getString(4));
				surveyques.setQuestion4(rs.getString(5));
				surveyques.setQuestion5(rs.getString(6));
				surveyques.setQuestion6(rs.getString(7));
			}

			rs.close();
			try {
				((Msg) msg).setReturnObj(surveyques);
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addNewSurveyToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Survey NewSurveyToAdd = (Survey) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {

			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " " + tableName + " ("
					+ "question1 ,question2 ,question3 ,question4 ,question5 ,question6 )" + "\nVALUES " + "('"
					+ NewSurveyToAdd.getQuestion1() + "','" + NewSurveyToAdd.getQuestion2() + "','"
					+ NewSurveyToAdd.getQuestion3() + "','" + NewSurveyToAdd.getQuestion4() + "','"
					+ NewSurveyToAdd.getQuestion5() + "','" + NewSurveyToAdd.getQuestion6() + "');");
			stmt.executeUpdate();
			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void InsertComplaintToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Complaint userToUpdate = (Complaint) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		
		
		String Query = message.getQueryQuestion() + " " + tableName+" (customerID , storeID,complaintDetails ,assigningDate ,gotTreatment,gotRefund)" 
		+ " VALUES (?,?,?,?,?,?);";
		
		System.out.println(Query);
		
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setInt(1, userToUpdate.getCustomerId());
			stmt.setInt(2, userToUpdate.getStoreId());
			stmt.setString(3, userToUpdate.getComplaintDetails());
			stmt.setString(4, userToUpdate.getAssigningDate());
			stmt.setInt(5, userToUpdate.getGotTreatment());
			stmt.setInt(6, userToUpdate.getGotRefund());
			stmt.executeUpdate();

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cancel_order(Msg msg, Connection con, ConnectionToClient client) {
		ArrayList<String> dir_result=new ArrayList <String>();
		try {
			String cmd2="SELECT orderId, orderPrice,Date,orderTime , current_date()>Date as t1,current_date()=Date and orderTime-current_time()>30000 as t2,"
				+ "current_date()=Date and orderTime-current_time() between 10000 and 30000 as t3, current_date()=Date and orderTime-current_time() between 0 and 10000 as t4,"
				+" current_time()-ordertime from zerli.`order` where orderID = ";
			cmd2+=msg.getqueryToDo()+" ;";


			
			System.out.println(cmd2);


			Statement stmt1 = con.createStatement();
				ResultSet rs = stmt1.executeQuery(cmd2);
				while (rs.next()) {
					dir_result.add(rs.getString(1));
					dir_result.add(rs.getString(2));
					dir_result.add(rs.getString(5));
					dir_result.add(rs.getString(6));
					dir_result.add(rs.getString(7));
					dir_result.add(rs.getString(8));
					System.out.println(dir_result);
				}
				System.out.println(" debug= "+dir_result);

				
				float refund=0;
				if (dir_result.get(2).equals("1"))refund=1;
				if (dir_result.get(3).equals("1"))refund=1;
				if (dir_result.get(4).equals("1"))refund=(float) 0.5;
				if (dir_result.get(5).equals("1"))refund=0;
				System.out.println(" dir_result.get(4)= "+dir_result.get(2)+dir_result.get(3)+dir_result.get(4)+dir_result.get(5));
				System.out.println(" refund= "+refund);

				try {
					client.sendToClient(dir_result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String cmd_refund= "update zerli.customer c,zerli.order o set c.balance=c.balance- o.orderPrice*"
						+ refund+"  where c.customerID=\"2468\" and o.orderId ="+msg.getqueryToDo()+";";
				System.out.println(cmd_refund);
				Statement stmt3 = con.createStatement();
				int returnValue2 = stmt3.executeUpdate(cmd_refund);
				
				
				
				System.out.println("great" + msg.getQueryQuestion());
				Statement stmt = con.createStatement();
				int returnValue1 = stmt.executeUpdate(msg.getQueryQuestion());
				rs.close();

				con.close();

				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
