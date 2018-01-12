package Gui;

import java.io.IOException;
import java.rmi.server.LoaderHandler;

import Entities.User;
import Login.LoginController;
import Login.WelcomeController;
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
	private Msg LogoutMsg=new Msg();
	@FXML
	public void viewCatalogBtn(ActionEvent event)
	{
		//load catalog products images
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage CatalogStage=new Stage();
		Parent CatalogRoot;
		try {
			CatalogRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
			Scene CatalogScene = new Scene(CatalogRoot);
			CatalogScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			CatalogStage.setScene(CatalogScene);
			//load catalog products images
			CatalogStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
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
	@FXML
	public void ExitBtn(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Customer Panel ");
		al.setContentText("Closing Customer Panel Now");
		al.showAndWait();
		System.exit(0);
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
