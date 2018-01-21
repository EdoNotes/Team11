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
import java.rmi.server.LoaderHandler;
import java.util.ArrayList;
import java.util.TreeMap;

import Entities.User;
import Login.LoginController;
import Login.WelcomeController;
import Server.EchoServer;
import client.ChatClient;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CustomerMenuController 
{
	public ClientConsole client;
	public ChatClient chat;
	private Msg LogoutMsg=new Msg();
	public String id="";
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks 
	 * on "View Catalog" Button
	 * @param event
	 */
	@FXML
	public void viewCatalogBtn(ActionEvent event)
	{
		//load catalog products images
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage CatalogStage=new Stage();
		Parent CatalogRoot;
		try {
			CatalogRoot = FXMLLoader.load(getClass().getResource("/Gui/CatalogWindow.fxml"));
			Scene CatalogScene = new Scene(CatalogRoot);
			CatalogScene.getStylesheets().add(getClass().getResource("/Gui/CatalogWindow.css").toExternalForm());
			CatalogStage.setScene(CatalogScene);
			//load catalog products images
			CatalogStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks 
	 * on "View My Profile" Button
	 * @param event
	 */
	@FXML
	public void viewMyProfileBtn(ActionEvent event)
	{
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage ViewProfileStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane ViewProfileRoot = loader.load(getClass().getResource("/Gui/CustomerViewProfile.fxml").openStream());
			CustomerViewProfileController profile=(CustomerViewProfileController)loader.getController();
			profile.getUserDetails(User.currUser);
			Scene ViewProfileScene = new Scene(ViewProfileRoot);
			ViewProfileScene.getStylesheets().add(getClass().getResource("/Gui/CustomerViewProfile.css").toExternalForm());
			ViewProfileStage.setScene(ViewProfileScene);
			ViewProfileStage.show();

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks "Open Dispute" Button
	 * @param event
	 */
	@FXML
	public void OpenDisputeBtn(ActionEvent event)
	{
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage OpenDisputeStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane OpenDisputeRoot = loader.load(getClass().getResource("/Gui/OpenDispute.fxml").openStream());
			Scene ViewProfileScene = new Scene(OpenDisputeRoot);
			ViewProfileScene.getStylesheets().add(getClass().getResource("/Gui/OpenDispute.css").toExternalForm());
			OpenDisputeStage.setScene(ViewProfileScene);
			OpenDisputeStage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks "Cancel Order" Button
	 * @param event
	 */
	@FXML
	public void OpenCancelOrder(ActionEvent event) throws InterruptedException, IOException
	{
		TreeMap<String, String> directory = new TreeMap<String, String>();
		String cmd ="SELECT  o.orderId ,o.orderPrice ,o.Date FROM zerli.order o where o.customerID=";
		//int userid = User.currUser.getID();
		int tmp =2468;
		cmd+=tmp+";";
		Msg userToCheck=new Msg(Msg.qSELECTALL,"checkUserExistence");
		userToCheck.setClassType("Ask_order");// create a new msg
		userToCheck.setQueryQuestion(cmd);
		System.out.println(cmd);
		client=new ClientConsole(EchoServer.HOST,EchoServer.DEFAULT_PORT);
		client.accept((Object)userToCheck);
		directory = (TreeMap<String, String> )client.msg;
		System.out.println("good 2" +directory);
		if (directory.size()==0)
		{
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("No Orders ");
			al.setContentText("There isn't currently orders for you.");
			al.showAndWait();
		}
		else {
		
		{((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		
		
		//Load Cancel Order Window
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/Gui/CancelOrder.fxml").openStream());
		CancelOrderController report=loader.getController();
		report.load_list(directory);
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("CancelOrder.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
		}
		}
	}
	/**
	 * this function implementing the sequence of actions
	 * that happens after customer clicks "Exit" Button
	 * @param event
	 */
	@FXML
	public void ExitBtn(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Customer Panel ");
		al.setContentText("Closing Customer Panel Now");
		al.showAndWait();
		System.exit(0);
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
	public void load_costomer(String id)
	{this.id=id; 
	System.out.println("good g       "+ id);
	}
	
	
	@FXML
	public void LoadingMoney(ActionEvent event) throws IOException 
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/LoadingMoney.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("LoadingMoney.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
}
