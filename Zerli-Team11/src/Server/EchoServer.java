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
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("report")) {
				get_order_report(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Ask_order")) {
				get_consumer_order(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_report")) {
				get_order_survey_report(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Customer")) {
				customerHandeler(msgRecived, "customer", client, conn);
			}
			 else if ((msgRecived.getClassType()).equalsIgnoreCase("Complaint"))
			 {
				 ComplaintHandeler(msgRecived, "complaint", client, conn);
			 }
			 else if ((msgRecived.getClassType()).equalsIgnoreCase("Store"))
			{
				 StoreHandeler(msgRecived, "store", client, conn);
			}
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	private void StoreHandeler(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkStoreExistence") == 0)
		{
			searchStoreInDB(msg, tableName, client, con);
		}
		
	}

	private void searchStoreInDB(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		Store tmpStore = new Store();
		Msg message = (Msg) msg;
		String Query=(message.getQueryQuestion() + " FROM  zerli." + tableName + " Where " +message.getColumnToUpdate()+"="+"?;");
		try {
			PreparedStatement stmt = con
					.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());//get Specific Field's value
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next())
			{
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

	private static void ComplaintHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("insertNewComplaint") == 0)
		{
			InsertComplaintToDB(msg, tableName, client, con);
		}
	}

	private static void customerHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkCustomerExistence") == 0) {
			searchCustomerInDB(msg, tableName, client, con);
		}

	}

	public static void userHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkUserExistence") == 0) // If we want to check if user is exist
																			// e.g to logIn
			searchUserInDB(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("update user") == 0)
			updateUserDetails(msg, tableName, client, con);

	}// userHandler
	private static void InsertComplaintToDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		Complaint userToUpdate = (Complaint) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		String Query=message.getQueryQuestion()+" "+tableName+" VALUES (?,?,?,?,?,?,?);";
		
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setInt(1,userToUpdate.getComplaintId());
			stmt.setInt(2, userToUpdate.getCustomerId());
			stmt.setInt(3,userToUpdate.getStoreId());
			stmt.setString(4,userToUpdate.getComplaintDetails());
			stmt.setString(5, userToUpdate.getAssigningDate());
			stmt.setInt(6,userToUpdate.getGotTreatment());
			stmt.setInt(7, userToUpdate.getGotRefund());
			stmt.executeUpdate();
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void updateUserDetails(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {
			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " zerli." + tableName + " Set "
					+ message.getColumnToUpdate() + "= ? WHERE UserName='" + userToUpdate.getUserName() + "'");
			stmt.setString(1, message.getValueToUpdate());
			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void searchCustomerInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Customer tmpCustomer = new Customer();
		Msg message = (Msg) msg;
		String Query=(message.getQueryQuestion() + " FROM  zerli." + tableName + " Where " +message.getColumnToUpdate()+"="+"?;");
		try {
			PreparedStatement stmt = con
					.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());//get Specific Field's value
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next())
			{
				tmpCustomer.setCustomerID(rs.getInt(1));
				tmpCustomer.setUserName(rs.getString(2));
				tmpCustomer.setIsSettlement(rs.getInt(3));
				tmpCustomer.setIsMember(rs.getInt(4));
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

	public static void searchUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User toSearch = (User) (((Msg) msg).getSentObj());
		User tmpUsr = new User();
		try {
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
			System.out.println(tmpUsr);// works

			((Msg) msg).setReturnObj(tmpUsr);

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

	public void get_order_report(Msg msg, Connection con, ConnectionToClient client) {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());

			while (rs.next()) {

				System.out.println(rs.getString(1) + rs.getString(2));
				directory.put(rs.getString(1), rs.getString(2));
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

	public void get_order_survey_report(Msg msg, Connection con, ConnectionToClient client) {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());
			Integer i=1;
			rs.next();
			while (i<7) {

				System.out.println(i.toString()+ rs.getString(i));
				directory.put(i.toString(), rs.getString(i));
				i+=1;
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
		ArrayList<String> directory = new ArrayList<String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());
			while (rs.next()) {
				String order="orderID: "+rs.getString(1)+" type: "+rs.getString(2)+" date: "+rs.getString(3)+" price: "+rs.getString(4);
				System.out.println(order);
				directory.add(order);
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

}
