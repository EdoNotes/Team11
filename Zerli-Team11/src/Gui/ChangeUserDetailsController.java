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
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.User;
import Login.WelcomeController;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class ChangeUserDetailsController {
	
	public ClientConsole client;
	/**
	 * 	<Instance Variables>
	 */
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
	TextField txtPermission;
	@FXML
	TextField txtConnectionStatus;
	@FXML
	TextField txtbranchName;
	
	@FXML
	private RadioButton Male;
	@FXML
	private RadioButton Female;
	/**
	 * Save Button 
	 * @param event-Save Button Click
	 * @throws InterruptedException
	 * @throws IOException
	 */
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
		if(Male.isSelected())
			UpdateUser.setGender("M");
		else UpdateUser.setGender("F");
		UpdateUser.setEmail(txtEmail.getText());
		UpdateUser.setUserType(txtPermission.getText());
		UpdateUser.setConnectionStatus(txtConnectionStatus.getText());
		UpdateUser.setBranchName(txtbranchName.getText());
		
		Msg UpdateUserDB = new Msg(Msg.qUPDATE, "UpadateUserInDB"); // create a new msg
		
		UpdateUserDB.setSentObj(UpdateUser); // 
		UpdateUserDB.setClassType("User");
		ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
	//	client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
		client.accept((Object) UpdateUserDB);  
		
		UpdateUserDB.setClassType("Customer");
		UpdateUserDB.setqueryToDo("update DB customer");
		UpdateUserDB.setQueryQuestion(Msg.qUPDATE);
		UpdateUserDB.setColumnToUpdate("customerID");
		UpdateUserDB.setValueToUpdate(txtID.getText());
		client.accept((Object) UpdateUserDB);  
		
		
		
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setTitle("User ID: "+ UpdateUser.getID());
		al.setContentText("User Save Succeed ");
		al.showAndWait();
		
		if(txtPermission.getText().equals("Customer")) 
		{
			UpdateUserDB.setqueryToDo("Select DB customer");
			UpdateUserDB.setQueryQuestion(Msg.qSELECTALL);
			UpdateUserDB.setClassType("Customer");
			UpdateUserDB.setColumnToUpdate("customerID");
			UpdateUserDB.setValueToUpdate(txtID.getText());
			
			client.accept((Object) UpdateUserDB);
			UpdateUserDB = (Msg) client.get_msg();
			Customer returnCustomer = (Customer) UpdateUserDB.getReturnObj();
			
			Stage primaryStage=new Stage();
			((Node)event.getSource()).getScene().getWindow().hide();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/Gui/SettlementAccount.fxml").openStream());
			SettlementAccountController settlementController= (SettlementAccountController)loader.getController();
			settlementController.getCustomerIdANDuserName(txtID.getText(), txtUserName.getText());
			settlementController.getCustomerSettlementANDmember(returnCustomer.getIsSettlement(), returnCustomer.getIsMember(), returnCustomer.getCreditCard());
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("SettlementAccount.css").toExternalForm());
			primaryStage.setScene(Scene);
			primaryStage.show();
		}
		else{
		
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage primaryStage=new Stage();
			Parent root=FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
			Scene serverScene = new Scene(root);
			serverScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
			primaryStage.setScene(serverScene);
			primaryStage.show();
		}
	}
	/**
	 * 
	 * @param event
	 * @throws InterruptedException
	 */
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
	
	/**
	 * 
	 * @param userName
	 * @param pass
	 * @param id
	 * @param Fname
	 * @param Lname
	 * @param phone
	 * @param gender
	 * @param email
	 * @param premission
	 * @param connentionS
	 */
	public void getDetailsUser(String userName ,String pass,String id ,String Fname,String Lname
			,String phone,String gender ,String email,String premission ,String connentionS,String branchN) 
	{
		txtUserName.setText(userName);
		txtUserPassword.setText(pass);
		txtID.setText(id);
		txtFirstName.setText(Fname);
		txtLastName.setText(Lname);
		txtPhone.setText(phone);
		if(gender.equals("M"))
			Male.setSelected(true);
		else Male.setSelected(false);
		
		if(gender.equals("F"))
			Female.setSelected(true);
		else Female.setSelected(false);
		
		txtEmail.setText(email);
		txtPermission.setText(premission);
		txtConnectionStatus.setText(connentionS);
		txtbranchName.setText(branchN);
		
	}

}
