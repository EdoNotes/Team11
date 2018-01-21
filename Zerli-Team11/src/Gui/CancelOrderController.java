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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CancelOrderController implements Initializable
{
	public TreeMap<String, String> directory = new TreeMap<String, String>();

	ObservableList<String> orderList=FXCollections.observableArrayList();

	public ClientConsole client;
	public ChatClient chat;
	private Msg LogoutMsg=new Msg();
	@FXML
	ComboBox CBcancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		CBcancel.setItems(orderList);

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
		
		
		float refund = 0;
		String cmd ="DELETE FROM zerli.`order` WHERE orderId=  ";
		String val = (String) CBcancel.getValue();
		String id = this.directory.get(val);
		cmd+=id+" ;";
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence");
		userToCheck.setClassType("CancelO");// create a new msg
		userToCheck.setQueryQuestion(cmd);
		userToCheck.setqueryToDo(id);
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		System.out.println(cmd);
		ArrayList<String> dir_return = (ArrayList< String> )client.msg;
		System.out.println(dir_return);
		if (dir_return.get(2).equals("1"))refund=1;
		if (dir_return.get(3).equals("1"))refund=1;
		if (dir_return.get(4).equals("1"))refund=(float) 0.5;
		if (dir_return.get(5).equals("1"))refund=0;
		String cmd_refund= "update zerli.customer c,zerli.order o set c.balance=c.balance- o.orderPrice*"
				+ refund+"  where c.customerID=\"2468\" and o.orderId ="+id+";";
		System.out.println(cmd_refund);

		

		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("ApprovalCancelation.fxml").openStream());
		
		ApprovalCancelationController report=loader.getController();
		String msg = "nothing";
		if (dir_return.get(2).equals("1")||dir_return.get(3).equals("1")) msg="The order was been canceled, the refund was 100% of the order price";
		if (dir_return.get(4).equals("1")) msg="The order was been canceled, the refund was 50% of the order price because the order cancel was later than 3 hours before the date";
		if (dir_return.get(5).equals("1")) msg="The order was been canceled, their is no refund because the order cancel was later than 1 hours before the date";
		if (msg.equals("nothing"))msg = "The Order time was passed.";
		report.load_list(msg);
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setTitle("Refund ");
		al.setContentText(msg);
		al.showAndWait();

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
	public void load_list(TreeMap<String, String> directory)
	{this.directory=directory;
	for(String s: directory.keySet())
	{
		this.orderList.add(s);
	}
	 
	}
	
	/**
	 * @param event
	 * Button that pass you back to Store Manager Menu
	 * @throws IOException
	 */
	@FXML
	public void BackBtn(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();// Hide Menu
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("CustomerMenu.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		

	}
	
}

