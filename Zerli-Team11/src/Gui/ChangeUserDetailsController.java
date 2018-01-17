package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.User;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class ChangeUserDetailsController {
	
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
	TextField txtPermission;
	@FXML
	TextField txtConnectionStatus;
	
	
	
	@FXML
	public void SaveBtn(ActionEvent event) throws InterruptedException, IOException 
	{
		User UpdateUser= new User();                
		UpdateUser.setUserName(txtUserName.getText());    //setting the user Details
		UpdateUser.setPassword(txtUserPassword.getText());
		UpdateUser.setID(Integer.parseInt(txtID.getText()));
		UpdateUser.setFirstName(txtFirstName.getText());
		UpdateUser.setLastName(txtLastName.getText());
		UpdateUser.setPhone(txtPhone.getText());
		UpdateUser.setGender(txtGender.getText());
		UpdateUser.setEmail(txtEmail.getText());
		UpdateUser.setUserType(txtPermission.getText());
		UpdateUser.setConnectionStatus(txtConnectionStatus.getText());
		
		Msg UpdateUserDB = new Msg(Msg.qUPDATE, "UpadateUserInDB"); // create a new msg
		UpdateUserDB.setSentObj(UpdateUser); // put the Survey into msg
		UpdateUserDB.setClassType("User");
		client = new ClientConsole("127.0.0.1",5555);/////����� ��� welcomeController �� ������ ����
		client.accept((Object) UpdateUserDB); //adding the survey to DB
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setTitle("User ID: "+ UpdateUser.getID());
		al.setContentText("User Save Succeed ");
		al.showAndWait();
		
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
	
	@FXML
	public void BackBtn(ActionEvent event) throws InterruptedException 
	{
		Stage primaryStage=new Stage();
		Parent root;
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
			primaryStage.setScene(Scene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
	}
	
	
	public void getDetailsUser(String userName ,String pass,String id ,String Fname,String Lname
			,String phone,String gender ,String email,String premission ,String connentionS) 
	{
		txtUserName.setText(userName);
		txtUserPassword.setText(pass);
		txtID.setText(id);
		txtFirstName.setText(Fname);
		txtLastName.setText(Lname);
		txtPhone.setText(phone);
		txtGender.setText(gender);
		txtEmail.setText(email);
		txtPermission.setText(premission);
		txtConnectionStatus.setText(connentionS);
		
	}

}