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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * @author Matan
 * this is application for changing and update settlement account
 */
public class SettlementAccountController {
	
	public ClientConsole client;
	
	
	
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
	private RadioButton memberMONTHLY;
	@FXML
	private RadioButton memberYEARLY;
	@FXML
	private ToggleGroup Member;
	@FXML
	private RadioButton NOTmember;
	@FXML
	private TextField txtCreditCard;
	@FXML
	private TextField txtExpDate;

	
	/**
	 * This method save details customer(if the account is settlement or not and if the customer is member or not and number credit card)
	 * @param event Button that save new customer details in the DB 
	 * @throws InterruptedException
	 */
	@FXML
	public void SaveBut(ActionEvent event) throws InterruptedException
	{
		Customer NewCustomerDB= new Customer();
		String crOrCash="";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //date of today
		LocalDate localDate = LocalDate.now();
		LocalDate time = null;
		
		if(IsSettlement.isSelected()) {  //check if selected - account is settlement or not
			NewCustomerDB.setIsSettlement(1);
			}
		else { NewCustomerDB.setIsSettlement(0);}
		
		if(NOTmember.isSelected()) {  //check if selected - customer member 
			NewCustomerDB.setIsMember(0);
			NewCustomerDB.setTypeMember(Customer.none);
			NewCustomerDB.setTypeMember(Customer.none);
			NewCustomerDB.setBalance(100.0);
		}
		else if(memberMONTHLY.isSelected()) {  //check if selected - customer member monthly or not
				NewCustomerDB.setIsMember(1); //setting  is member
				NewCustomerDB.setTypeMember(Customer.monthly);
				crOrCash=creditOrCash("Price for monthly subscription is 10.0 NIS"); //check if it is about new Registration
				if(crOrCash.compareTo("Balance Account")==0) //if he want to pay whit is account balance
					NewCustomerDB.setBalance(90.0); //setting balance after the customer pay whit is account balance after he pay 10 NIS
				else if(crOrCash.compareTo("cash")==0)//if he want to pay in cash
					NewCustomerDB.setBalance(100.0); //if he pay whit cash
	
				if(crOrCash.compareTo("cancel")!=0) {	
					time=localDate.plusMonths(1);
					NewCustomerDB.setExpDate(time.format(dtf));
				}
		}	
		else if(memberYEARLY.isSelected()) {  //check if selected - customer member yearly or not
				NewCustomerDB.setIsMember(1);
				NewCustomerDB.setTypeMember(Customer.yearly);
				crOrCash=creditOrCash("Price for yearly subscription is 80.0 NIS");
				if(crOrCash.compareTo("Balance Account")==0) //if he want to pay whit is account balance
					NewCustomerDB.setBalance(20.0);   //after he pay 80 NIS
				else if(crOrCash.compareTo("cash")==0) //if he want to pay in cash
					NewCustomerDB.setBalance(100.0);
			
				if(crOrCash.compareTo("cancel")!=0) {	
					time=localDate.plusYears(1);
					NewCustomerDB.setExpDate(time.format(dtf));
				}
			}
		if(crOrCash.compareTo("cancel")!=0) {//check if he cancel the action
			NewCustomerDB.setCustomerID(Integer.parseInt(txtCustomerID.getText()));//setting details customer from text fields
			NewCustomerDB.setUserName(txtUserName.getText());
			NewCustomerDB.setCreditCard(txtCreditCard.getText());
			
			if(checkNumFiedl()) //check if the text field credit card are empty (must to be credit card)
			{
				Alert al = new Alert(Alert.AlertType.ERROR); //if the text field credit card are empty show error massage
				al.setTitle("Adding credit card problem");
				al.setContentText("You have to enter credit card!");
				al.showAndWait();
			}
			else newRegistration(NewCustomerDB,event);  //if it is about new registration
					
		}
	}

/*****************************************NEW REGISTRATION***********************************************************/	
	/**
	 * this method adding the new Customer to the DB
	 * @param addCustomer a new Customer that initialized on new details 
	 * @param event the action event that belongs to the this window
	 */
	public void newRegistration(Customer addCustomer,ActionEvent event) 
	{
		Msg SaveCustomerInDB = new Msg(Msg.qINSERT, "Save New Customer Settlement and Member"); // create a new msg
		SaveCustomerInDB.setSentObj(addCustomer); // put the Survey into msg
		SaveCustomerInDB.setClassType("Customer");
	
		ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		try {
			client.accept((Object) SaveCustomerInDB);
		} 
		catch (InterruptedException e1) {
			e1.printStackTrace();
		} 
		
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setTitle("Customer Number ID: "+ addCustomer.getCustomerID());
		al.setContentText("Save Succeed ");
		al.showAndWait();
	
	
		Stage primaryStage=new Stage();//after the registration pass you back to New User Registration screen
		Parent root = null;
		((Node)event.getSource()).getScene().getWindow().hide();
		try {
			root = FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene loginScene = new Scene(root);
		loginScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
		primaryStage.setScene(loginScene);
		primaryStage.show();
		
	}
	
	/**
	 * 
	 * @param event Button that pass you back to Manager System Menu screen or pass you back to New User Registration screen
	 * @throws IOException
	 */
	@FXML
	public void BackBut(ActionEvent event) throws IOException 
	{
	
		 
			Stage primaryStage=new Stage(); ////if it is about new registration 
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();	
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
	 * this method ask the customer if he want to pay whit cash or credit card
	 * @param ques String whit the price and the type member
	 * @return
	 */
	public String creditOrCash(String ques) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Balance Account Or Cash");
		alert.setHeaderText(ques);
		alert.setContentText("Do you want to pay whit Balance Account or Cash ?");

		ButtonType buttonBalance = new ButtonType("Balance Account");
		ButtonType buttonCash = new ButtonType("Cash");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonBalance, buttonCash, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonBalance){
		    return "Balance Account";
		} else if (result.get() == buttonCash) {
			return "cash";
		} else  {
		    return "cancel";
		}
		

	}
	
	
	/**
	 * method that check if the text field is legal number 
	 * @return
	 */
	public boolean checkNumFiedl()
	{
		if(txtCreditCard.getText().compareTo("")==0
				||txtCreditCard.getText().charAt(0)<'0' 
				||txtCreditCard.getText().charAt(0)>'9' 
				||txtCreditCard.getText().charAt(0)==' '
				||txtCreditCard.getText().compareTo("")==0)
			return true;
		else return false;
	}

}
