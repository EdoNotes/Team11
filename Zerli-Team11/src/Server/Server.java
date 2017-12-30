package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;
import Entities.*;
import Entities.User.ConnectionStatus;
import common.*;
import ocsf.server.*;



public class Server extends AbstractServer {
	/*
	 * Attributes Area
	 * =============================================================================
	 * ==
	 */
	/**
	 * The default port and host to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	final public static String HOST= "localhost";

	/*
	 * Methods Area
	 * =============================================================================
	 * ==
	 */

	public Server(int port) {
		super(port);
	}

	
	public void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		try 
		{
			
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/zerli","root","root");
        System.out.println("SQL connection succeed 222");
        System.out.printf("\n%s\n",((Msg)msg).getClassType());
		if ((((Msg)msg).getClassType()).equalsIgnoreCase("User"))
			{
				System.out.println("SQL connection succeed 777");
				userHandeler(msg,"zerli.User",client,conn);
			}
		}
		catch (SQLException ex) 
		{/* handle any errors*/
	    	System.out.println("SQLException: " + ex.getMessage());
	    	System.out.println("SQLState: " + ex.getSQLState());
	    	System.out.println("VendorError: " + ex.getErrorCode());
		}	
	}

	
	
	public static  void userHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		String queryToDo=((Msg)msg).getQueryQuestion();
		if(queryToDo.equalsIgnoreCase(Msg.qSELECTALL)) { 
			System.out.println("Matan sabag");
			searchUserInDB(msg,tableName,client,con);
		}
	}
	


	public static  void searchUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		System.out.println("This Shit Works12");
		User toSearch=(User)msg;
		System.out.println("This Shit Works5");
		User tmpUsr= new User();
		System.out.println("This Shit Works6");
		try 
		{
			System.out.println("This Shit Works10");
			Statement stmt = con.createStatement();
			System.out.println("This Shit Works12");
			ResultSet rs = stmt.executeQuery(Msg.qSELECTALL + "From"+ tableName + "WHERE UserName= '"+toSearch.getUserName()+"' AND Password='"+toSearch.getPassword()+"';");
			System.out.println("This Shit Works13");
			if(rs.getString(0).equals(((User)msg).getUserName()))
			{
				System.out.println("This Shit Works");
			}
			tmpUsr.setUserName(rs.getString(0));//Set user name for returned object
			tmpUsr.setPassword(rs.getString(1));//Set Password for returned object
			tmpUsr.setID(Integer.parseInt(rs.getString(2)));//Set ID for returned object
			tmpUsr.setFirstName(rs.getString(3));//Set FirstName for returned object
			tmpUsr.setLastName(rs.getString(4));//Set tLastName for returned object
			tmpUsr.setConnectionStatus(rs.getString(5));//Set ConnectionStatus for returned object
			tmpUsr.setUserType(rs.getString(6));//Set UserType for returned object
			tmpUsr.setPhone(rs.getString(6));//Set Phone for returned object
			tmpUsr.setGender(rs.getString(7));//Set Gender for returned object
			tmpUsr.setEmail(rs.getString(8));//Set Email for returned object
			
			
			((Msg)msg).setReturnObj((Object)tmpUsr);
		} 
		catch (SQLException e) 
		{
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

}
