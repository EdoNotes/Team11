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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomerViewProfileController 
{
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


	public TextField getTxtUserName() {
		return txtUserName;
	}


	public void setTxtUserName(String txtUserName) {
		this.txtUserName.setText(txtUserName);
	}


	public TextField getTxtLastName() {
		return txtLastName;
	}


	public void setTxtLastName(String txtLastName) {
		this.txtLastName.setText(txtLastName);
	}


	public TextField getTxtFirstName() {
		return txtFirstName;
	}


	public void setTxtFirstName(String txtFirstName) {
		this.txtFirstName.setText(txtFirstName);
	}


	public TextField getTxtGender() {
		return txtGender;
	}


	public void setTxtGender(String txtGender) {
		this.txtGender.setText(txtGender);
	}


	public TextField getTxtPhone() {
		return txtPhone;
	}


	public void setTxtPhone(String txtPhone) {
		this.txtPhone.setText(txtPhone);
	}


	public TextField getTxtEmail() {
		return txtEmail;
	}


	public void setTxtEmail(String txtEmail) {
		this.txtEmail.setText(txtEmail);
	}


	public TextField getTxtPassword() {
		return txtPassword;
	}


	public void setTxtPassword(String txtPassword) {
		this.txtPassword.setText(txtPassword);
	}
	
	

}
