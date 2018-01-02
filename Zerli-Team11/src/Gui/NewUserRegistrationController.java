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

import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserRegistrationController 
{
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
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/ShopManagerMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("ShopManagerMenu.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
	@FXML
	public void RegisterBtn(ActionEvent event)
	{
		User NewUser= new User();
		NewUser.setUserName(txtUserName.getText());
		NewUser.setPassword(txtUserPassword.getText());
		NewUser.setID(Integer.parseInt(txtID.getText()));
		NewUser.setFirstName(txtFirstName.getText());
		NewUser.setLastName(txtLastName.getText());
		NewUser.setPhone(txtPhone.getText());
		NewUser.setGender(txtGender.getText());
		NewUser.setEmail(txtEmail.getText());
		NewUser.setConnectionStatus("Offline");
		NewUser.setUserType("Client");
		
		//toDo Send To DB//
	}
	
}
