package Gui;


import java.io.IOException;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


public class ManagerSystemMenuController {

	public ClientConsole client;
	private Msg LogoutMsg=new Msg();
	
	@FXML
	TextField txtId;
	
	
	@FXML
	public void ShowDetails(ActionEvent event) throws IOException
	{
		User userToChange = new User(); // create a new user to make sure
		if(txtId.getText().equals("")) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("User problem");
			al.setContentText("Fill number User");
			al.showAndWait();
		}
		else {
			userToChange.setID(Integer.parseInt(txtId.getText()));
			Msg userToCheck = new Msg(Msg.qSELECTALL, "check User By ID Existence"); // create a new msg
			userToCheck.setSentObj(userToChange); // put the user into msg
			userToCheck.setClassType("User");
			client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
			try {
				client.accept((Object) userToCheck);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userToCheck = (Msg) client.get_msg();
			User returnUsr = (User) userToCheck.getReturnObj();
		
			if (returnUsr.getUserName()==null) { 
				Alert al2 = new Alert(Alert.AlertType.ERROR);
				al2.setTitle("User problem");
				al2.setContentText("User not exist!");
				al2.showAndWait();
			}
			else {
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage primaryStage=new Stage();
			
				FXMLLoader loader=new FXMLLoader();
				Pane root=loader.load(getClass().getResource("/Gui/ChangeUserDetails.fxml").openStream());
				ChangeUserDetailsController showDetailsUser = (ChangeUserDetailsController)loader.getController();
				showDetailsUser.getDetailsUser(returnUsr.getUserName(), returnUsr.getPassword(),Integer.toString(returnUsr.getID()), returnUsr.getFirstName(), returnUsr.getLastName(), 
						returnUsr.getPhone(), returnUsr.getGender(), returnUsr.getEmail(), returnUsr.getUserType(), returnUsr.getConnectionStatus());
			

				Scene serverScene = new Scene(root);
				serverScene.getStylesheets().add(getClass().getResource("ChangeUserDetails.css").toExternalForm());
				primaryStage.setScene(serverScene);
				primaryStage.show();
			}
		}	
	}
	
	
	@FXML
	public void LogoutBtn(ActionEvent event)
	{
		LogoutMsg.setqueryToDo("update user");
		LogoutMsg.setSentObj(User.currUser);
		LogoutMsg.setQueryQuestion(Msg.qUPDATE);
		LogoutMsg.setColumnToUpdate("ConnectionStatus");
		LogoutMsg.setValueToUpdate("Offline");	
		LogoutMsg.setClassType("User");
		ClientConsole client=new ClientConsole(WelcomeController.IP, WelcomeController.port);
		try {
			client.accept(LogoutMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /* Update the connection status of the user from online  to offline */
		
		Stage primaryStage=new Stage();
		Parent root;
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("/Login/login_application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	

	
	
	
}
