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
package Login;

import java.io.IOException;

import com.mysql.jdbc.util.ServerController;

import Entities.User;
import Server.EchoServer;
import client.ChatClient;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController 
{
	@FXML
	TextField txtUsername;
	@FXML
	PasswordField txtPassword;
	
	public ClientConsole client;
	public ChatClient chat;
	
	@FXML
	public void ExitBtn(ActionEvent e)
	{
		Alert al=new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Login Panel ");
		al.setContentText("Closing Login Panel Now");
		al.showAndWait();
		System.exit(0);
	}
	
	@FXML
	public void LoginButton(ActionEvent e) throws InterruptedException
	{
		String uName=txtUsername.getText();
		String uPass=txtPassword.getText();
		User userToConnect=new User(uName,uPass); //create a new user to make sure
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence"); // create a new msg
		userToCheck.setSentObj(userToConnect); //put the user into msg
		userToCheck.setClassType("User");
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		User returnUsr=(User)userToCheck.getReturnObj();
//		if(returnUsr.getUserName().compareTo(userToConnect.getUserName())==0)
//		{
//			System.out.println("user name exist");
//			if(returnUsr.getPassword().compareTo(userToConnect.getPassword())==0)
//			{
//				System.out.println("User Connected succesfuly!");
//				returnUsr.setConnectionStatus("Online");
//				
//			}
//			else
//			{
//				System.out.println("Wrong password");
//			}
//		}
//		else
//		{
//			System.out.println("Worng UserName");
//		}
	}
	public void confirmUser(Object msg)
	{				System.out.println("User dddddddddddddddddddd succesfuly!");

		Msg myMsg=(Msg)msg;
		User myOldUser =(User)myMsg.getSentObj();
		User myNewUser=(User)myMsg.getReturnObj();
		if(myNewUser.getUserName().compareTo(myOldUser.getUserName())==0)
		{
			if(myNewUser.getPassword().compareTo(myOldUser.getPassword())==0)
			{
				System.out.println("User Connected succesfuly!");
				myNewUser.setConnectionStatus("Online");
				
			}
			else
			{
				System.out.println("Wrong password");
			}
		}
		else
		{
			System.out.println("Worng UserName");
		}
	}
}
