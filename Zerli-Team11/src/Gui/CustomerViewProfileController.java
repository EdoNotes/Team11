package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerViewProfileController implements Initializable
{
	private User UserDetails;

	@FXML
	private Label usenNameLab;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtGender;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPassword;

	@FXML
	public void BackBtn(ActionEvent event)
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage CustomerStage=new Stage();
		Parent CustomerRoot;
		try {
			CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
			Scene CustomerScene = new Scene(CustomerRoot);
			CustomerScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			CustomerStage.setScene(CustomerScene);
			CustomerStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
//	@FXML
//	public void loadProfile(User u1)
//	{
//		this.UserDetails=u1;
//		System.out.println(User.currUser.getUserName().toString());
//		txtUserName=new TextField();
//		txtPassword=new TextField();
//		txtFirstName=new TextField();
//		txtPhone=new TextField();
//		txtGender=new TextField();
//		txtEmail=new TextField();
//		txtUserName.setText(User.currUser.getUserName());
//		txtPassword.setText(User.currUser.getPassword());
//		txtFirstName.setText(User.currUser.getFirstName());
//		txtPhone.setText(User.currUser.getPhone());
//		txtGender.setText(User.currUser.getGender());
//		txtEmail.setText(User.currUser.getEmail());
//	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void getUserDetails(User user)
	{
		//txtUserName=new TextField();
		txtPassword=new TextField();
		txtFirstName=new TextField();
		txtPhone=new TextField();
		txtGender=new TextField();
		txtEmail=new TextField();
		usenNameLab.setText(user.getUserName());
		txtPassword.setText(User.currUser.getPassword());
		txtFirstName.setText(User.currUser.getFirstName());
		txtPhone.setText(User.currUser.getPhone());
		txtGender.setText(User.currUser.getGender());
		txtEmail.setText(User.currUser.getEmail());
	}


}
