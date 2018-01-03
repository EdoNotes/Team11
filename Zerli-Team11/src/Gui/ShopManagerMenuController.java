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
import javafx.stage.Stage;

public class ShopManagerMenuController implements Initializable
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
{		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence");
		userToCheck.setClassType("report");// create a new msg
		String[] date= {"'2011-01-01' AND '2011-3-31'", "'2011-4-01' AND '2011-12-06'","'2011-07-01' AND '2011-09-31'", "'2011-10-01' AND '2011-12-31'"}; 
		String cmd ="SELECT orders.type,count(*) as count FROM zerli.orders WHERE date BETWEEN";
		System.out.println((String)cmbQ1.getValue());
		int q2 =Integer.parseInt((String)cmbQ1.getValue());
		cmd += date[q2-1];
		cmd +=" and orders.shop = '" + cmbS1.getValue() +"' group by orders.type ;";
		System.out.println(cmd);
//
		userToCheck.setQueryQuestion(cmd);
		userToCheck.setQueryExist(true);
		
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		directory = (TreeMap<String, String> )client.msg;
//		String d = (String )client.msg;
		System.out.println("good 2" +directory);
	}
	@FXML
	public void ExitBtn(ActionEvent event)
	{
		Alert al=new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Menu  ");
		al.setContentText("Closing Menu Panel Now");
		al.showAndWait();
		System.exit(0);
	}
	
}

