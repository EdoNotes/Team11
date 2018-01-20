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

import Entities.Customer;
import Entities.Survey;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewUserRegistrationController {
	/**
	 * <Instance Variables>
	 */
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
	TextField txtEmail;
	@FXML
	TextField txtBranchName;

	@FXML
	private RadioButton Male;
	@FXML
	private RadioButton Female;

	/**
	 * @param event
	 * Button that pass you back to Store Manager Menu
	 * @throws IOException
	 */
	@FXML
	public void BackBtn(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();// Hide Menu
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("StoreManagerMenu.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}

	/**
	 * This method create a new user and setting his details on User type
	 * 
	 * @param event
	 *            Button that Registering new user in the DB
	 * @throws InterruptedException
	 */
	@FXML
	public void RegisterBtn(ActionEvent event) throws InterruptedException {

		if (txtUserName.getText().equals("") || txtUserPassword.getText().equals("") || txtID.getText().equals("")
				|| txtFirstName.getText().equals("") || txtLastName.equals("") || txtPhone.getText().equals("")
				|| txtEmail.getText().equals("")) { // check if one of the text fields details are empty

			Alert al = new Alert(Alert.AlertType.ERROR); // if one of the text fields details are empty ,jumping a alert
															// error message
			al.setTitle("Register problem");
			al.setContentText("One of the feild are empty!");
			al.showAndWait();

		} else {
			User NewUser = new User();
			NewUser.setUserName(txtUserName.getText()); // setting the new user Details
			NewUser.setPassword(txtUserPassword.getText());
			NewUser.setID(Integer.parseInt(txtID.getText()));
			NewUser.setFirstName(txtFirstName.getText());
			NewUser.setLastName(txtLastName.getText());
			NewUser.setPhone(txtPhone.getText());
			NewUser.setEmail(txtEmail.getText());
			NewUser.setConnectionStatus("Offline");
			NewUser.setUserType("Customer");
			NewUser.setBranchName(txtBranchName.getText());

			if (Male.isSelected()) // RadioButton - male selected or female selected
				NewUser.setGender("M");
			else
				NewUser.setGender("F");

			Msg NewUserAdding = new Msg(Msg.qSELECTALL, "checkUserExistence"); // create a new msg for check if user
																				// exist already
			Msg UserAddingByID = new Msg(Msg.qSELECTALL, "check User By ID Existence");// for check if user exists by ID

			UserAddingByID.setSentObj(NewUser);
			NewUserAdding.setSentObj(NewUser); // put the new user into msg
			UserAddingByID.setClassType("user");
			NewUserAdding.setClassType("user");
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) NewUserAdding); // adding the new user to DB

			NewUserAdding = (Msg) client.get_msg();
			User returnUsr = (User) NewUserAdding.getReturnObj();

			client.accept((Object) UserAddingByID);
			UserAddingByID = (Msg) client.get_msg();
			User returnUsrById = (User) UserAddingByID.getReturnObj();

			if ((returnUsr.getID()) == 0 && returnUsrById.getUserName() == null) // check if the new user already exists
			{
				NewUserAdding.setqueryToDo("AddNewUserToDB");
				NewUserAdding.setSentObj(NewUser);
				NewUserAdding.setQueryQuestion(Msg.qINSERT);
				NewUserAdding.setClassType("user");
				client.accept((Object) NewUserAdding);

				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Adding new User: " + NewUser.getFirstName() + " " + NewUser.getLastName());
				al.setContentText("Adding new Customer Succeed ");
				al.showAndWait();

				try {
					Stage primaryStage = new Stage();
					((Node) event.getSource()).getScene().getWindow().hide();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Gui/SettlementAccount.fxml").openStream());
					SettlementAccountController settlementController = (SettlementAccountController) loader
							.getController();
					settlementController.getCustomerIdANDuserName(txtID.getText(), txtUserName.getText());
					Scene Scene = new Scene(root);
					Scene.getStylesheets().add(getClass().getResource("SettlementAccount.css").toExternalForm());
					primaryStage.setScene(Scene);
					primaryStage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else { // if user already exists show error massage
				Alert al = new Alert(Alert.AlertType.ERROR);
				al.setTitle("Adding New User problem");
				al.setContentText("Customer exist!");
				al.showAndWait();
			}

		}
	}

}
