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

	
	
	@FXML
	public void SaveBut(ActionEvent event) throws InterruptedException
	{
		Customer NewCustomerDB= new Customer();
		
		if(IsSettlement.isSelected()) {
			NewCustomerDB.setIsSettlement(1);
			}
		else { NewCustomerDB.setIsSettlement(0);}
		
		if(member.isSelected()) {
			NewCustomerDB.setIsMember(1);
		}
		else {
			NewCustomerDB.setIsMember(0);
		}
		
		NewCustomerDB.setCustomerID(Integer.parseInt(txtCustomerID.getText()));
		NewCustomerDB.setUserName(txtUserName.getText());
		NewCustomerDB.setCreditCard(txtCreditCard.getText());
		NewCustomerDB.setBalance(100.0);  //default for new customer
		
		if(txtCreditCard.getText().equals(""))
		{
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Adding credit card problem");
			al.setContentText("You have to enter credit card!");
			al.showAndWait();
		}
		else if(flagUpdate==0){
			Msg SaveCustomerInDB = new Msg(Msg.qINSERT, "Save New Customer Settlement and Member"); // create a new msg
			SaveCustomerInDB.setSentObj(NewCustomerDB); // put the Survey into msg
			SaveCustomerInDB.setClassType("Customer");
		
		
			//client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) SaveCustomerInDB); //adding the survey to DB
	
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("Customer Number ID: "+ NewCustomerDB.getCustomerID());
			al.setContentText("Save Succeed ");
			al.showAndWait();
		
		
			Stage primaryStage=new Stage();
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
		else {
			Msg UpdateCustomerInDB = new Msg(Msg.qUPDATE, "Update Customer Settlement and Member and credit card"); // create a new msg
			UpdateCustomerInDB.setSentObj(NewCustomerDB); // put the Survey into msg
			UpdateCustomerInDB.setClassType("Customer");
		
		
			client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
			client.accept((Object) UpdateCustomerInDB); //adding the survey to DB
	
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("Customer Number ID: "+ NewCustomerDB.getCustomerID());
			al.setContentText("Update Succeed ");
			al.showAndWait();
			
	//		flagUpdate=0;
		
		
			Stage primaryStage=new Stage();
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
	
	
	@FXML
	public void BackBut(ActionEvent event) throws IOException 
	{
		if(flagUpdate==1) {
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
			Stage primaryStage=new Stage();
			Parent root = null;
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();	
		}
	}
		
		
		
		
	
	
	public void getCustomerIdANDuserName(String custonerID ,String userName) 
	{
		txtCustomerID.setText(custonerID);
		txtUserName.setText(userName);
		
	}
	public void getCustomerSettlementANDmember(int settl ,int mem ,String credit)
	{
		txtCreditCard.setText(credit);
		if(settl==1) 
			IsSettlement.setSelected(true);
		else IsSettlement.setSelected(false);
		
		if(mem==1) 
			member.setSelected(true);
		else member.setSelected(false);
		
		flagUpdate=1;
	}

}
