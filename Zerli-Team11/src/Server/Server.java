package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;
import Entities.*;
import Entities.User.ConnectionStatus;
import Login.LoginController;
import client.ChatClient;
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
				userHandeler(msg,"user",client,conn);
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
		Object toReturn;
		System.out.println("This Shit Works12");
		User toSearch=(User)(((Msg)msg).getSentObj());
		System.out.println("This Shit Works5");
		User tmpUsr= new User();
		System.out.println("This Shit Works6");
		try 
		{
			System.out.println("This Shit Works10");
			Statement stmt = con.createStatement();
			System.out.println("This Shit Works12");
			ResultSet rs = stmt.executeQuery("select * from user where UserName='edoono24' and Password='darklord'");
	 		if(rs.next())
	 		{

				tmpUsr.setUserName(rs.getString(1));//Set user name for returned object
				tmpUsr.setPassword(rs.getString(2));//Set Password for returned object
				tmpUsr.setID(Integer.parseInt(rs.getString(3)));//Set ID for returned object
				tmpUsr.setFirstName(rs.getString(4));//Set FirstName for returned object
				tmpUsr.setLastName(rs.getString(5));//Set tLastName for returned object
				tmpUsr.setConnectionStatus(rs.getString(6));//Set ConnectionStatus for returned object
				tmpUsr.setUserType(rs.getString(7));//Set UserType for returned object
				tmpUsr.setPhone(rs.getString(8));//Set Phone for returned object
				tmpUsr.setGender(rs.getString(9));//Set Gender for returned object
				tmpUsr.setEmail(rs.getString(10));//Set Email for returned object
			} 
	 		rs.close();
			System.out.println(tmpUsr);//works
			
			((Msg)msg).setReturnObj((Object)tmpUsr);
			
			System.out.println((User)((Msg)msg).getReturnObj());
			toReturn=(Object)((Msg)msg).getReturnObj();
			try {//*******לא עובד**********
				client.sendToClient(toReturn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
