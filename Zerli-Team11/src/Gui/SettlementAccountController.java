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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Customer;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * @author Matan
 * this is application for changing and update settlement account
 */
public class SettlementAccountController {
	
	public ClientConsole client;
	
	private int flagUpdate=0;
	
	
	/**
	 * GUI Fields
	 */
	@FXML
	private Label txtCustomerID;
	@FXML
	private Label txtUserName;
	@FXML
	private RadioButton IsSettlement;
	@FXML
	private ToggleGroup Settlement;
	@FXML
	private RadioButton isNOTSettlement;
	@FXML
	private RadioButton member;
	@FXML
	private ToggleGroup Member;
	@FXML
	private RadioButton NOTmember;
	@FXML
	private TextField txtCreditCard;

	
	/**
	 * This method save details customer(if the account is settlement or not and if the customer is member or not and number credit card)
	 * @param event Button that save new customer details in the DB 
	 * @throws InterruptedException
	 */
	@FXML
	public void SaveBut(ActionEvent event) throws InterruptedException
	{
		Customer NewCustomerDB= new Customer();
		
		if(IsSettlement.isSelected()) {  //check if selected - account is settlement or not
			NewCustomerDB.setIsSettlement(1);
			}
		else { NewCustomerDB.setIsSettlement(0);}
		
		if(member.isSelected()) {  //check if selected - customer member or not
			NewCustomerDB.setIsMember(1);
		}
		else {
			NewCustomerDB.setIsMember(0);
		}
		
		NewCustomerDB.setCustomerID(Integer.parseInt(txtCustomerID.getText()));//setting details customer from text fields
		NewCustomerDB.setUserName(txtUserName.getText());
		NewCustomerDB.setCreditCard(txtCreditCard.getText());
		NewCustomerDB.setBalance(100.0);  //default for new customer
		
		if(txtCreditCard.getText().equals("")) //check if the text field credit card are empty (must to be credit card)
		{
			Alert al = new Alert(Alert.AlertType.ERROR); //if the text field credit card are empty show error massage
			al.setTitle("Adding credit card problem");
			al.setContentText("You have to enter credit card!");
			al.showAndWait();
		}
		else if(flagUpdate==0){  //if it is about new registration 
			Msg SaveCustomerInDB = new Msg(Msg.qINSERT, "Save New Customer Settlement and Member"); // create a new msg
			SaveCustomerInDB.setSentObj(NewCustomerDB); // put the Survey into msg
			SaveCustomerInDB.setClassType("Customer");
		
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) SaveCustomerInDB); //adding the survey to DB
	
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("Customer Number ID: "+ NewCustomerDB.getCustomerID());
			al.setContentText("Save Succeed ");
			al.showAndWait();
		
		
			Stage primaryStage=new Stage();//after the registration pass you back to New User Registration screen
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			try {
				root = FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
			}
		else { //if it is about update user details 
			Msg UpdateCustomerInDB = new Msg(Msg.qUPDATE, "Update Customer Settlement and Member and credit card"); // create a new msg
			UpdateCustomerInDB.setSentObj(NewCustomerDB); // put the customer into msg
			UpdateCustomerInDB.setClassType("Customer");
			
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) UpdateCustomerInDB); //adding the survey to DB
	
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("Customer Number ID: "+ NewCustomerDB.getCustomerID());
			al.setContentText("Update Succeed ");
			al.showAndWait();
			
		
			Stage primaryStage=new Stage(); //after the update pass you back to Manager System Menu screen
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			try {
				root = FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();		
		}	
	}
	
	/**
	 * 
	 * @param event Button that pass you back to Manager System Menu screen or pass you back to New User Registration screen
	 * @throws IOException
	 */
	@FXML
	public void BackBut(ActionEvent event) throws IOException 
	{
		if(flagUpdate==1) { //if it is about update user details 
			flagUpdate=0;
			Stage primaryStage=new Stage();
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();	
		}
		else {
			Stage primaryStage=new Stage(); ////if it is about new registration 
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();	
		}
	}
		
		
		
		
	
	/**
	 * The method getting the customer ID and the User Name from the StoreManageMenuController and setting them on the textFields  
	 * @param custonerID customer ID
	 * @param userName User Name of the customer
	 */
	public void getCustomerIdANDuserName(String custonerID ,String userName) 
	{
		txtCustomerID.setText(custonerID);
		txtUserName.setText(userName);
		
	}
	
	/**
	 * The method getting the details and setting them on the textFields  
	 * @param settl 1-if isSettlement selected and 0 if not
	 * @param mem  1- if is Member selected and 0 if not
	 * @param credit number credit card
	 */
	public void getCustomerSettlementANDmember(int settl ,int mem ,String credit)
	{
		txtCreditCard.setText(credit);
		if(settl==1) 
			IsSettlement.setSelected(true);
		else IsSettlement.setSelected(false);
		
		if(mem==1) 
			member.setSelected(true);
		else member.setSelected(false);
		
		flagUpdate=1;//for know that is about update user
	}

}
