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
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

/**
 * @author Matan
 * this is application for changing and update settlement account
 */
public class SettlementAccountController implements Initializable {
	
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
	private RadioButton member;
	@FXML
	private ToggleGroup Member;
	@FXML
	private RadioButton NOTmember;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
//		//txtCustomerID
//		//txtUserName
//		
	}
	
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
		
		
		System.out.println("<<<<<<<<<<>>>>>>>");
		System.out.println(NewCustomerDB.getCustomerID());
		System.out.println(NewCustomerDB.getUserName());
		System.out.println(NewCustomerDB.getIsMember());
		System.out.println(NewCustomerDB.getIsSettlement());
		System.out.println("<<<<<<<<<<>>>>>>>");
		
		Msg SaveCustomerInDB = new Msg(Msg.qINSERT, "Save New Customer Settlement and Member"); // create a new msg
		SaveCustomerInDB.setSentObj(NewCustomerDB); // put the Survey into msg
		SaveCustomerInDB.setClassType("Customer");
		
		System.out.println(SaveCustomerInDB.getClassType());
		
		client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
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
	
	
	public void getCustomerIdANDuserName(String custonerID ,String userName) 
	{
		txtCustomerID.setText(custonerID);
		txtUserName.setText(userName);
		
	}

}
