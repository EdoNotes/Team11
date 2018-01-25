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
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.TreeMap;

import Entities.Customer;
import Entities.User;
import Login.WelcomeController;
import client.ChatClient;
import Server.EchoServer;
import client.ClientConsole;
import common.Msg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class StoreManagerMenuController implements Initializable
{
	@FXML
	ComboBox cmbSelectReport1;
	@FXML
	ComboBox cmbS1;
	@FXML
	ComboBox cmbQ1;
	@FXML
	Button Breport1;
	@FXML
	ComboBox<String> year1;
	@FXML
	Button BNU;
	@FXML
	TextField CustomerIDtext;
	@FXML
	Button SettelementAccount;
	ObservableList<String> yearlist=FXCollections.observableArrayList("2009","2010","2011","2012","2013","2014","2015","2016","2017","2018");
	ObservableList <String >quarterly = FXCollections.observableArrayList("1","2","3","4");
	ObservableList<String> ReportsList=FXCollections.observableArrayList("Quarter's Incomes","Quarter's Order","Customers Complaints","Customer Satisfication");
	ObservableList<String> ShopList=FXCollections.observableArrayList("Haifa","Ako","Tel Aviv");
	public ClientConsole client;
	public ChatClient chat;
	private Msg LogoutMsg=new Msg();
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		cmbSelectReport1.setItems(ReportsList);
		cmbQ1.setItems(quarterly);
		year1.setItems(yearlist);
		//cmbS1.setItems(ShopList);
	}
	
	/**
	 * 
	 * @param event Button that pass you to New User Registration
	 * @throws IOException
	 */
	@FXML
	public void RegisterBtn(ActionEvent event) throws IOException
	{	
		{((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();}

	}
	

	@FXML
	public void askReport(ActionEvent event) throws Exception
{		
		if(cmbSelectReport1.getValue()==null) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("misses details ");
			al.setContentText("misses order request details ");
			al.showAndWait();}
		else {

			if((cmbSelectReport1.getValue()!="Customer Satisfication")&&(cmbQ1.getValue()==null||year1.getValue()==null))
			 {
				Alert al = new Alert(Alert.AlertType.ERROR);
				al.setTitle("misses details ");
				al.setContentText("misses order request details ");
				al.showAndWait();}
			else {
		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence");
		userToCheck.setClassType("report");// create a new msg
		int qutere =0;
		String qutr = "";
		if(cmbSelectReport1.getValue()!="Customer Satisfication")
		{qutere =Integer.parseInt((String)cmbQ1.getValue());  //build the computable string for the chosen quarterly.
		int monthStart= (qutere*3) -2;
		int monthEnd= (qutere*3);
		qutr="'"+(String)year1.getValue()+"-";
		if (monthStart<10) qutr+="0";
		qutr+=monthStart+"-01' AND '"+(String)year1.getValue()+"-";
		if (monthEnd<10) qutr+="0";
		qutr+=monthEnd+"-31'";
		System.out.println(qutr);}
		int userid = User.currUser.getID();
		String cmd = "";
		String cmd_count ="SELECT  p.productType, sum(pid.quantity) FROM zerli.order o, zerli.product_in_order pid, zerli.product p ,zerli.store st, zerli.user us where o.Date BETWEEN";
		String cmd_sum = "SELECT  p.productType, sum(p.price) FROM zerli.order o, zerli.product_in_order pid, zerli.product p ,zerli.store st, zerli.user us where o.Date BETWEEN";
		String cmd_complain = "SELECT month(c.assigningDate) as M ,count(*) as count FROM zerli.complaint c, zerli.store st, zerli.user us WHERE c.assigningDate  BETWEEN";
		String cmd_survey = "SELECT avg(answer1),avg(answer2),avg(answer3),avg(answer4),avg(answer5), "
				+ "avg(answer6) FROM zerli.survey_answer where numSurvey = 1;";
		if((String)cmbSelectReport1.getValue()=="Quarter's Incomes") cmd=cmd_sum;
		if((String)cmbSelectReport1.getValue()=="Quarter's Order") cmd=cmd_count;
		if((String)cmbSelectReport1.getValue()=="Customers Complaints") cmd=cmd_complain; 
		cmd +=qutr;
		if ((String)cmbSelectReport1.getValue()!="Customers Complaints")
		{		cmd+="and o.storeID = st.storeID and st.branchName= us.branchName and us.userID= "+ userid;
		cmd+=" and o.orderId=pid.OrderID and pid.productID = p.productID group by p.productType;";}
		
		else {cmd += "and c.storeID = st.storeID and st.branchName= us.branchName and us.userID="+userid+" group by month(c.assigningDate);";}
		
		if ((String)cmbSelectReport1.getValue()!="Customer Satisfication")
		{userToCheck.setQueryQuestion(cmd);}
		else
		{userToCheck.setQueryQuestion(cmd_survey);
		userToCheck.setClassType("survey_report");	
		}
		System.out.println(cmd);
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		directory = (TreeMap<String, String> )client.msg;
		System.out.println("good 2" +directory);
		
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("report_order.fxml").openStream());
		report_orderController report=loader.getController();
		report.setdirectory(directory, (String)cmbSelectReport1.getValue());
		report.load_dir(directory);
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("report_order.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
			}
		}
	}


	/**
	 * This method get customer from DB by ID customer and if customer exits than show in the next window 
	 * the details of settlement and if is member and number credit card 
	 * @param event Button that check if the customer exits and if exits move to Settlement Account window 
	 * @throws Exception
	 */
	@FXML
	public void SettelementAccountBut(ActionEvent event) throws Exception
	{
		Customer CustomerDB= new Customer();
		if(checkNumFiedl()) 
		{
			Alert al = new Alert(Alert.AlertType.ERROR); // if one of the text field is empty ,jumping a alert
			// error message
			al.setTitle("Customer ID problem");
			al.setContentText("Customer ID field is empty!");
			al.showAndWait();
		}
		else {
			CustomerDB.setCustomerID(Integer.parseInt(CustomerIDtext.getText()));
			
			Msg exsitCustomer = new Msg(Msg.qSELECTALL, "checkCustomerExistence"); // create a new msg
			exsitCustomer.setSentObj(CustomerDB); // put the customer into msg
			exsitCustomer.setClassType("Customer");
			exsitCustomer.setColumnToUpdate("customerID");
			exsitCustomer.setValueToUpdate(CustomerIDtext.getText());
			
			
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) exsitCustomer); 
			
			exsitCustomer = (Msg) client.get_msg();
			Customer returnCustomer = (Customer) exsitCustomer.getReturnObj();
			if(returnCustomer.getCustomerID()!=0) //check if the customer exist by ID check
			{
				
				Stage primaryStage=new Stage();
				((Node)event.getSource()).getScene().getWindow().hide();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Gui/UpdateSettelmentAccount.fxml").openStream());
				UpdateSettelmentAccountController settlementController= (UpdateSettelmentAccountController)loader.getController();
				settlementController.getCustomerIDuserName(Integer.toString(returnCustomer.getCustomerID()), returnCustomer.getUserName());
				
				settlementController.getCustomerSettlement(returnCustomer.getIsSettlement(),returnCustomer.getCreditCard(),returnCustomer.getExpDate());  
				
				Scene Scene = new Scene(root);
				primaryStage.setScene(Scene);
				primaryStage.show();
			}
			else {
				Alert al = new Alert(Alert.AlertType.ERROR); // if customer Unexist,jumping a alert
				// error message
				al.setTitle("Customer Connection problem");
				al.setContentText("Unexist Custoner!");
				al.showAndWait();
			}
		}	
	}
	
	
	
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks "Logout" Button
	 * @param event
	 */
	@FXML
	public void LogoutBtn(ActionEvent event)
	{
		LogoutMsg.setqueryToDo("update user");
		LogoutMsg.setSentObj(User.currUser);
		LogoutMsg.setQueryQuestion(Msg.qUPDATE);
		LogoutMsg.setColumnToUpdate("ConnectionStatus");
		LogoutMsg.setValueToUpdate("Offline");	
		LogoutMsg.setClassType("User");
		ClientConsole client=new ClientConsole(WelcomeController.IP, WelcomeController.port);
		try {
			client.accept(LogoutMsg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /* Update the connection status of the user from online  to offline */
		
		Stage primaryStage=new Stage();
		Parent root;
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("/Login/login_application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
	/**
	 * method that check if the text field is legal number 
	 * @return
	 */
	public boolean checkNumFiedl()
	{
		if(CustomerIDtext.getText().compareTo("")==0
				||CustomerIDtext.getText().charAt(0)<'0' 
				||CustomerIDtext.getText().charAt(0)>'9' 
				||CustomerIDtext.getText().charAt(0)==' '
				||CustomerIDtext.getText().compareTo("")==0)
			return true;
		else return false;
	}
}

