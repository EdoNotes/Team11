package client;

import java.io.*;
import common.*;
import java.util.ArrayList;


public class ClientConsole implements ChatIF {
	/* Variables Zone */
	public ArrayList<String> msg = new ArrayList<String>();

	final public static int DEFAULT_PORT = 5555;
	ChatClient client;

	/* Methods Zone */
	/**
	 * 
	 * @param host
	 * @param port
	 */
	public ClientConsole(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}
	/**
	 * @get Msg from the client and send to to the server with the func client.handleMessageFromClientUI()
	 */
	  public void accept(Object msg)
	  {  
		  client.handleMessageFromClientUI(msg);
//			}
//		try
//		{
			
//	    catch (Exception ex) 
//	    {
//	      System.out.println
//	        ("Unexpected error while reading from console!");
//	    }
		  
	  }

	 
	  public void display(Object message) 
	  {   
		  this.msg =(ArrayList<String>) message;
		  System.out.println("> " + message); 
	  }

	  
	  public ArrayList<String> get_msg()
		{
			return this.msg;
		}

	}

