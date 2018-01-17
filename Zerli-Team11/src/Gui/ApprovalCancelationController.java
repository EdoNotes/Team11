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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApprovalCancelationController implements Initializable
{
	
	ObservableList<String> orderList=FXCollections.observableArrayList();

	public ClientConsole client;
	public ChatClient chat;
	private Msg LogoutMsg=new Msg();
	public String message="";
	@FXML
	TextField resT;
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

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
	
	
	public void cancel(ActionEvent event) throws Exception
{	
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
	public void load_list(String msg)
	{
		this.message=msg;
		System.out.println(msg);
		this.resT.setText(msg);

		
	}
	
}

