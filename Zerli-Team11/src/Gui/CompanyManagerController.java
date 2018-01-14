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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CompanyManagerController implements Initializable
{
	@FXML
	ComboBox cmbSelectReport1;
	@FXML
	ComboBox cmbSelectReport2;
	@FXML
	ComboBox cmbS1;
	@FXML
	ComboBox cmbS2;
	@FXML
	ComboBox cmbQ1;
	@FXML
	ComboBox cmbQ2;
	@FXML
	Button Breport2;
	@FXML
	Button Breport1;
	@FXML
	Button BNU;
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
		cmbSelectReport2.setItems(ReportsList);
		cmbQ1.setItems(quarterly);
		cmbQ2.setItems(quarterly);
		cmbS1.setItems(ShopList);
		cmbS2.setItems(ShopList);
	}
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
	
	
	public void askReport(ActionEvent event) throws Exception
{		
		//The report request create an sql question.
		//The code of Customers Complaints report is a bit different because of his table structure.
		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence");
		userToCheck.setClassType("report");// create a new msg
		String[] date= {"'2011-01-01' AND '2011-3-31'", "'2011-4-01' AND '2011-12-06'","'2011-07-01' AND '2011-09-31'", "'2011-10-01' AND '2011-12-31'"}; 
		String cmd = "";
		String cmd_count ="SELECT orders.type,count(*) as count FROM zerli.orders WHERE date BETWEEN";
		String cmd_sum = "SELECT orders.type,sum(price) as sum FROM zerli.orders WHERE date BETWEEN";
		String cmd_complain = "SELECT month(date) as M ,count(*) as count FROM zerli.complaint  WHERE date BETWEEN";
		String cmd_survey = "SELECT avg(answer1),avg(answer2),avg(answer3),avg(answer4),avg(answer5), "
				+ "avg(answer6) FROM zerli.survey_answer where num_survey = 1;";

			if((String)cmbSelectReport1.getValue()=="Quarter's Incomes") cmd=cmd_sum;
			if((String)cmbSelectReport1.getValue()=="Quarter's Order") cmd=cmd_count;
			if((String)cmbSelectReport1.getValue()=="Customers Complaints") cmd=cmd_complain; 
		System.out.println((String)cmbQ1.getValue());
		int q2 =Integer.parseInt((String)cmbQ1.getValue());
		cmd += date[q2-1];
		if ((String)cmbSelectReport1.getValue()!="Customers Complaints")
		{cmd +=" and orders.shop = '" + cmbS1.getValue() +"' group by orders.type ;";}
		else {cmd += "  group by month(date)";}
		System.out.println(cmd);
		if ((String)cmbSelectReport1.getValue()!="Customer Satisfication")
		{userToCheck.setQueryQuestion(cmd);}
		else
		{userToCheck.setQueryQuestion(cmd_survey);
		userToCheck.setClassType("survey_report");	
		}
		System.out.println("cmd " +userToCheck.getQueryQuestion());
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		directory = (TreeMap<String, String> )client.msg;
		System.out.println("good 2" +directory);
		
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("report_order.fxml").openStream());
		report_orderController report=loader.getController();
		report.setdirectory(directory);
		report.load_dir(directory);
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("report_order.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}

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
	
}

