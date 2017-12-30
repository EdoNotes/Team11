package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;
import Entities.*;
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

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		try 
		{
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/zerli","root","root");
        System.out.println("SQL connection succeed 222");
		if (msg instanceof User)
			userHandeler(msg,"zerli.User",client,conn);
		} 
		catch (SQLException ex) 
		{/* handle any errors*/
	    	System.out.println("SQLException: " + ex.getMessage());
	    	System.out.println("SQLState: " + ex.getSQLState());
	    	System.out.println("VendorError: " + ex.getErrorCode());
		}	
	}

	
	
	protected void userHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		String queryToDo=((Msg)msg).getQueryQuestion();
		if(queryToDo==Msg.qSELECT)
			searchUserInDB(msg,tableName,client,con);
	}
	


	protected void searchUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		User toSearch=(User)msg;
		User tmpUsr= new User();
		try 
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Msg.qSELECTALL + "From"+ tableName + "WHERE UserName= '"+toSearch.getUserName()+"' AND Password='"+toSearch.getPassword()+"';");
			tmpUsr.setUserName(rs.getString(0));
			tmpUsr.setPassword(rs.getString(1));
			tmpUsr.setFirstName(rs.getString(2));
			tmpUsr.setLastName(rs.getString(3));
			//---------------------I dont know how I takes the eNums----------------------//
//			tmpUsr.setConnectionStatus((OnlineStatus));gi 
//			tmpUsr.setUserType(rs.getString(5));
			//----------------------------------------------------------------------------//
			tmpUsr.setPhone(rs.getString(6));
			tmpUsr.setGender(rs.getString(7));
			tmpUsr.setEmail(rs.getString(8));
			((Msg)msg).setReturnObj((Object)tmpUsr);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
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
