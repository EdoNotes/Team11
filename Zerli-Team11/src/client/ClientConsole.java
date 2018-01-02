package client;

import java.io.*;
import common.*;
import java.util.ArrayList;

import Login.LoginController;


public class ClientConsole implements ChatIF {
	/* Variables Zone */
	public Object msg ;

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
		  //client.handleMessageFromServer(msg);

	  }

	 
	  public void display(Object message) 
	  {   
		  Login.LoginController LIC=new LoginController();
		  LIC.confirmUser(message);
	  }

	  
	  public Object get_msg()
		{
			return this.msg;
		}

	}

