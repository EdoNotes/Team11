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
package Gui;

import java.io.IOException;

import Entities.Survey;
import Entities.User;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewUserRegistrationController  
{
	public ClientConsole client;
	
	@FXML
	TextField txtUserName;
	@FXML
	TextField txtUserPassword;
	@FXML
	TextField txtID;
	@FXML
	TextField txtFirstName;
	@FXML
	TextField txtLastName;
	@FXML
	TextField txtPhone;
	@FXML
	TextField txtGender;
	@FXML
	TextField txtEmail;
	
	@FXML
	public void BackBtn(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("StoreManagerMenu.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
	
	@FXML
	public void RegisterBtn(ActionEvent event) throws InterruptedException 
	{
		User NewUser= new User();                
		NewUser.setUserName(txtUserName.getText());    //setting the new user Details
		NewUser.setPassword(txtUserPassword.getText());
		NewUser.setID(Integer.parseInt(txtID.getText()));
		NewUser.setFirstName(txtFirstName.getText());
		NewUser.setLastName(txtLastName.getText());
		NewUser.setPhone(txtPhone.getText());
		NewUser.setGender(txtGender.getText());
		NewUser.setEmail(txtEmail.getText());
		NewUser.setConnectionStatus("Offline");
		NewUser.setUserType("Customer");
		
		
		Msg NewUserAdding = new Msg(Msg.qSELECTALL, "checkUserExistence"); // create a new msg
		NewUserAdding.setSentObj(NewUser); // put the Survey into msg
		NewUserAdding.setClassType("user");
		client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
		client.accept((Object) NewUserAdding); //adding the new user to DB
		
		NewUserAdding = (Msg) client.get_msg();
		User returnUsr = (User) NewUserAdding.getReturnObj();
		if((returnUsr.getID())==0) //check if the new user already exists
		{
			NewUserAdding.setqueryToDo("AddNewUserToDB");
			NewUserAdding.setSentObj(NewUser);
			NewUserAdding.setQueryQuestion(Msg.qINSERT);
			NewUserAdding.setClassType("user");
			client.accept((Object) NewUserAdding);
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("Adding new User: "+NewUser.getFirstName() + " "+ NewUser.getLastName() );
			al.setContentText("Adding new User Succeed ");
			al.showAndWait();
		}
		else {                                             //if user already exists show error massage
				Alert al = new Alert(Alert.AlertType.ERROR);
				al.setTitle("Adding New User problem");
				al.setContentText("User exist!");
				al.showAndWait();
		}
		
		try {
			Stage primaryStage=new Stage();
			((Node)event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/Gui/SettlementAccount.fxml").openStream());
			SettlementAccountController settlementController= (SettlementAccountController)loader.getController();
			settlementController.getCustomerIdANDuserName(txtID.getText(), txtUserName.getText());
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("SettlementAccount.css").toExternalForm());
			primaryStage.setScene(Scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	
}
