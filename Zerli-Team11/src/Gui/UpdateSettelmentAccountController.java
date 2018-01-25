package Gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Entities.Customer;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

public class UpdateSettelmentAccountController {
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
	private RadioButton doNotChange;
	@FXML
	private ToggleGroup Member;
	@FXML
	private RadioButton NOTmember;
	@FXML
	private TextField txtCreditCard;
	@FXML
	private RadioButton memberYEARLY;
	@FXML
	private TextField txtExpDate;

	public int flagSystemM;
	public int flagOk=1;

	/**
	 * This method save details customer(if the account is settlement or not and if the customer is member or not and number credit card)
	 * @param event Button that save customer details in the DB 
	 * @throws InterruptedException
	 */
	@FXML
	public void SaveBut(ActionEvent event) {
		Customer NewCustomerDB= new Customer();
		String crOrCash="";
		
		if(IsSettlement.isSelected()) {  //check if selected - account is settlement or not
			NewCustomerDB.setIsSettlement(1);
			}
		else { NewCustomerDB.setIsSettlement(0);}
		
		if(NOTmember.isSelected()) {  //check if selected - customer member 
			NewCustomerDB.setIsMember(0);
			NewCustomerDB.setTypeMember(Customer.none);
		}
		else if(memberMONTHLY.isSelected()) {  //check if selected - customer member monthly or not
				NewCustomerDB.setIsMember(1); //setting  is member
				NewCustomerDB.setTypeMember(Customer.monthly);
		}	
		else if(memberYEARLY.isSelected()) {  //check if selected - customer member yearly or not
				NewCustomerDB.setIsMember(1);
				NewCustomerDB.setTypeMember(Customer.yearly);
			}
		NewCustomerDB.setCustomerID(Integer.parseInt(txtCustomerID.getText()));//setting details customer from text fields
		NewCustomerDB.setUserName(txtUserName.getText());
		NewCustomerDB.setCreditCard(txtCreditCard.getText());
			
		if(txtCreditCard.getText().equals("")) //check if the text field credit card are empty (must to be credit card)
		{
			Alert al = new Alert(Alert.AlertType.ERROR); //if the text field credit card are empty show error massage
			al.setTitle("Adding credit card problem");
			al.setContentText("You have to enter credit card!");
			al.showAndWait();
		}
		else
				SetttlementCustomer(NewCustomerDB,event);
	}
		
		
		/**
		 * this method update the customer details in DB
		 * @param updateCustomer 
		 * @param event
		 */
		public void updateUser(Customer updateCustomer,ActionEvent event) 
		{
			Msg UpdateCustomerInDB = new Msg(Msg.qUPDATE, "Update Customer Settlement and Member and credit card"); // create a new msg
			UpdateCustomerInDB.setSentObj(updateCustomer); // put the customer into msg
			UpdateCustomerInDB.setClassType("Customer");
			
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			try {
				client.accept((Object) UpdateCustomerInDB);
			} 
			catch (InterruptedException e1) {
				e1.printStackTrace();
			} 
			if(flagOk==1) {
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Customer Number ID: "+ updateCustomer.getCustomerID());
				al.setContentText("Update Succeed ");
				al.showAndWait();
			}
			if(flagSystemM==0) {//if it is about store manager
				Stage primaryStage=new Stage(); //after the update pass you back to Manager System Menu screen
				Parent root = null;
				((Node)event.getSource()).getScene().getWindow().hide();
				try {
					root = FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Scene loginScene = new Scene(root);
				loginScene.getStylesheets().add(getClass().getResource("StoreManagerMenu.css").toExternalForm());
				primaryStage.setScene(loginScene);
				primaryStage.show();
			}
			else {//if it is about system manager
				Stage primaryStage=new Stage(); //after the update pass you back to Manager System Menu screen
				Parent root = null;
				((Node)event.getSource()).getScene().getWindow().hide();
				try {
					root = FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				Scene loginScene = new Scene(root);
				loginScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
				primaryStage.setScene(loginScene);
				primaryStage.show();
				
			}
			
		}

		/**
		 * get the customer details from the DB and update them in the new one details
		 * @param settlementCustomer
		 * @param event
		 */
		public void SetttlementCustomer(Customer settlementCustomer,ActionEvent event) 
		{
		
			String crOrCash;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //date of today
			LocalDate localDate = LocalDate.now();
			LocalDate time = null;
			
			Msg SaveCustomerInDB = new Msg(Msg.qSELECTALL, "pull the balance customer"); // create a new msg
			SaveCustomerInDB.setSentObj(settlementCustomer); // put the Survey into msg
			SaveCustomerInDB.setClassType("Customer");
			SaveCustomerInDB.setColumnToUpdate("customerID");
			SaveCustomerInDB.setValueToUpdate(""+settlementCustomer.getCustomerID());
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			try {
				client.accept((Object) SaveCustomerInDB);
			} 
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			SaveCustomerInDB = (Msg) client.get_msg();
			Customer returnCustById = (Customer) SaveCustomerInDB.getReturnObj();
			time=LocalDate.parse(returnCustById.getExpDate(), dtf);
			
			
			if(NOTmember.isSelected() || memberMONTHLY.isSelected() || memberYEARLY.isSelected()) {
				/*checking it is type member*/
				if(settlementCustomer.getTypeMember().compareTo(Customer.none)==0) //if it is none 
				{
					settlementCustomer.setBalance(returnCustById.getBalance()); //setting the old details
					settlementCustomer.setExpDate(time.format(dtf));
				}
				else if(settlementCustomer.getTypeMember().compareTo(Customer.monthly)==0) //if it is monthly member
					{
						if(flagSystemM ==0) {
							crOrCash=creditOrCash("Price for monthly subscription is 10.0 NIS"); // the price monthly member
							if(crOrCash.compareTo("Balance Account")==0) {
								if((returnCustById.getBalance())>=10) {
									settlementCustomer.setExpDate((time.plusMonths(1)).format(dtf)); //setting the new details -->monthly member
									settlementCustomer.setBalance((returnCustById.getBalance()-10));
								}
								else {
									flagOk=0; //flag that say that it is a problem
									Alert al = new Alert(Alert.AlertType.ERROR); //if the text field credit card are empty show error massage
									al.setTitle("Balance problem");
									al.setContentText("Your balance low than 10 NIS!");
									al.showAndWait();
								}
							}
							else if(crOrCash.compareTo("cash")==0){
								settlementCustomer.setExpDate((time.plusMonths(1)).format(dtf));
								settlementCustomer.setBalance((returnCustById.getBalance()));
							}
							else if(crOrCash.compareTo("cancel")==0){
								flagOk=0;
							}
						}
						else {
							settlementCustomer.setExpDate((time.plusMonths(1)).format(dtf));
							settlementCustomer.setBalance((returnCustById.getBalance()));
						}
				}
				else if(settlementCustomer.getTypeMember().compareTo(Customer.yearly)==0) 
				{
					if(flagSystemM ==0) {
						crOrCash=creditOrCash("Price for yearly subscription is 80.0 NIS");
						if(crOrCash.compareTo("Balance Account")==0) {
							if(returnCustById.getBalance()>=80) {  //setting the new details -->yerly member
								settlementCustomer.setExpDate((time.plusYears(1)).format(dtf));
								settlementCustomer.setBalance((returnCustById.getBalance()-80));
							}
							else {
								flagOk=0;
								Alert al = new Alert(Alert.AlertType.ERROR); //if the text field credit card are empty show error massage
								al.setTitle("Balance problem");
								al.setContentText("Your balance low than 80 NIS!");
								al.showAndWait();
							}
						}
						else if(crOrCash.compareTo("cash")==0){
							settlementCustomer.setExpDate((time.plusYears(1)).format(dtf));
							settlementCustomer.setBalance((returnCustById.getBalance()));
						}
						else if(crOrCash.compareTo("cancel")==0){
							flagOk=0;
						}
					}
					else {
						settlementCustomer.setExpDate((time.plusYears(1)).format(dtf));
						settlementCustomer.setBalance((returnCustById.getBalance()));
					}
				}
			}
			else if(doNotChange.isSelected()){ //if he don't want to change nothing
				settlementCustomer.setTypeMember(returnCustById.getTypeMember());
				settlementCustomer.setExpDate(time.format(dtf));
			}
			updateUser(settlementCustomer,event); //update customer details
		}
		
		
		/**
		 * 
		 * @param event Button that pass you back to Manager System Menu screen or pass you back to New User Registration screen
		 * @throws IOException
		 */
		@FXML
		public void BackBut(ActionEvent event) throws IOException 
		{
			if(flagSystemM ==0) {
				Stage primaryStage=new Stage();
				Parent root = null;
				((Node)event.getSource()).getScene().getWindow().hide();
				root = FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
				Scene loginScene = new Scene(root);
				loginScene.getStylesheets().add(getClass().getResource("StoreManagerMenu.css").toExternalForm());
				primaryStage.setScene(loginScene);
				primaryStage.show();
			}
			else {
				Stage primaryStage=new Stage();
				Parent root = null;
				((Node)event.getSource()).getScene().getWindow().hide();
				root = FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
				Scene loginScene = new Scene(root);
				loginScene.getStylesheets().add(getClass().getResource("ManagerSystemMenu.css").toExternalForm());
				primaryStage.setScene(loginScene);
				primaryStage.show();
			}
		}
	
	
	/**
	 * this method ask the customer if he want to pay whit cash or credit card
	 * @param ques String whit the price and the type member
	 * @return
	 */
	public String creditOrCash(String ques) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Balance Account Or Cash ");
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
	 * The method getting the customer ID and the User Name from the StoreManageMenuController and setting them on the textFields  
	 * @param custonerID customer ID
	 * @param userName User Name of the customer
	 */
	public void getCustomerIDuserName(String custonerID ,String userName) 
	{
		txtCustomerID.setText(custonerID);
		txtUserName.setText(userName);
		
	}
	
	/**
	 *  The method getting the details and setting them on the textFields
	 * @param settl settlement account if it is 1 the account are settlement and 0 not settlement
	 * @param credit 
	 * @param typeMem if type member is monthly or yearly or none 
	 * @param expDate
	 */
	public void getCustomerSettlement(int settl ,String credit ,String expDate)
	{
		txtCreditCard.setText(credit);
		if(settl==1) 
			IsSettlement.setSelected(true);
		else IsSettlement.setSelected(false);		
		txtExpDate.setText(expDate);
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
	 *  The method getting the details and setting them on the textFields
	 * @param settl settlement account if it is 1 the account are settlement and 0 not settlement
	 * @param credit 
	 * @param typeMem if type member is monthly or yearly or none 
	 * @param expDate
	 */
	public void getCustomerSettlementANDmember(int settl ,String credit  ,String expDate,int flag)
	{
		txtCreditCard.setText(credit);
		if(settl==1) 
			IsSettlement.setSelected(true);
		else IsSettlement.setSelected(false);	
		txtExpDate.setText(expDate);
		if(flag==1)
			flagSystemM=1;//for know that is about update user
	}
	
}
