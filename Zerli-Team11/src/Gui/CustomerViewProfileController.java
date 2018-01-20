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

public class CustomerViewProfileController
{
	/**
	 * 			<Instance Variables>
	 */
	@FXML
	private Label lblUserName;
	@FXML
	private Label lblPassword;
	@FXML
	private Label lblFirstName;
	@FXML
	private Label lblLastName;
	@FXML
	private Label lblPhone;
	@FXML
	private Label lblGender;
	@FXML
	private Label lblEmail;

	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks 
	 * on "Back" Button
	 * @param event
	 */
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
	/**'
	 * Prepares Units On Screen Before Showing 
	 * All The Data Is A Specific Customer's Data
	 * @param user
	 */
	public void getUserDetails(User user)
	{

		lblUserName.setText(user.getUserName());
		lblPassword.setText(user.currUser.getPassword());
		lblLastName.setText(user.currUser.getLastName());
		lblFirstName.setText(user.currUser.getFirstName());
		lblPhone.setText(user.currUser.getPhone());
		lblGender.setText(user.currUser.getGender());
		lblEmail.setText(user.currUser.getEmail());
	}


}
