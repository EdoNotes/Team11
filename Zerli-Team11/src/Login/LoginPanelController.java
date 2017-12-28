package Login;

import java.awt.TextField;

import Entities.User;
import Server.Server;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class LoginPanelController 
{
	/*----------------------WHERE TO PUT IT!?----------------------*/
	//final public static int DEFAULT_PORT = 5555;
	//final public static String HOST= "localhost";
	/*-------------------------------------------------------------*/
	@FXML
	private TextField txtUserNAME;
	@FXML
	private PasswordField txtPassword;
	
	public ClientConsole client;
	
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
	public void LoginButton(ActionEvent e)
	{
		String uName=txtUserNAME.getText();
		String uPass=txtPassword.getText();
		User userToConnect=new User(uName,uPass); //create a new user to make sure
		Msg userToCheck=new Msg(Msg.qSELECTALL); // create a new msg
		userToCheck.setSentObj(userToConnect); //put the user into msg
		client=new ClientConsole(Server.HOST,Server.DEFAULT_PORT);
		client.accept(userToCheck);
		User returnUsr=(User)userToCheck.getReturnObj();
		if(returnUsr.getUserName().compareTo(userToConnect.getUserName())==0)
		{
			System.out.println("user name exist");
			if(returnUsr.getPassword().compareTo(userToConnect.getPassword())==0)
			{
				System.out.println("User Connected succesfuly!");
				/*Need To update the Online status*/
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
