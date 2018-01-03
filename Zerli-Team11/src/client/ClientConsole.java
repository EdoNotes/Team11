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
	 * @throws InterruptedException 
	 * @get Msg from the client and send to to the server with the func client.handleMessageFromClientUI()
	 */
	  public void accept(Object msg) throws InterruptedException
	  {  
		  client.handleMessageFromClientUI(msg);

		  Thread.sleep(3000);
		  this.msg=client.msg;
		  System.out.println("msg from server"+    client.msg);

	  }

	 
	  public void display(Object message) 
	  {   		  this.msg = message;
		  Login.LoginController LIC=new LoginController();
		  LIC.confirmUser(message);
	  }

	  
	  public Object get_msg()
		{
			return this.msg;
		}

	}

