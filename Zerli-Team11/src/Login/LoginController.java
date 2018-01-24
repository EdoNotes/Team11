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
package Login;

import java.io.IOException;

import com.mysql.jdbc.util.ServerController;

import Entities.Customer;
import Entities.Order;
import Entities.OrderSupply;
import Entities.ProductInOrder;
import Entities.User;
import Gui.ApprovalCancelationController;
import Gui.CustomerMenuController;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController 
{
	/**
	 * <Instance Variables>
	 */
	@FXML
	TextField txtUsername;
	@FXML
	PasswordField txtPassword;

	private ClientConsole client;
	public ChatClient chat;
	/**
	 * This Fucntion Acts When User Clicks On "Exit" Button
	 * @param e
	 */
	@FXML
	public void ExitBtn(ActionEvent e) {
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Login Panel ");
		al.setContentText("Closing Login Panel Now");
		al.showAndWait();
		System.exit(0);
	}
	/**
	 * This Fucntion Acts When The User Enters Connection Details(UserName+Password)
	 * On Login Window
	 * @param event
	 * @throws InterruptedException
	 */
	@FXML
	public void LoginButton(ActionEvent event) throws InterruptedException {
		String uName = txtUsername.getText();
		String uPass = txtPassword.getText();
		User userToConnect = new User(uName, uPass); // create a new user to make sure
		Msg userToCheck = new Msg(Msg.qSELECTALL, "checkUserExistence"); // create a new msg
		userToCheck.setSentObj(userToConnect); // put the user into msg
		userToCheck.setClassType("User");
		client = new ClientConsole(WelcomeController.IP,WelcomeController.port);
		client.accept((Object) userToCheck);//Get Specific User From DB If Exists
		userToCheck = (Msg) client.get_msg();
		System.out.println(""+userToCheck);
		User returnUsr = (User) userToCheck.getReturnObj();
		if (returnUsr.getUserName() != null) //returnUsr=null means not existing On DB
		{
			if (returnUsr.getConnectionStatus().compareTo("Online") != 0) //Multiply connection is not allowed
			{
				if (returnUsr.getUserName().compareTo(userToConnect.getUserName()) == 0) 
				{
					if (returnUsr.getPassword().compareTo(userToConnect.getPassword()) == 0)
					{
						System.out.println("User Connected succesfuly!");
						returnUsr.setConnectionStatus("Online");
						userToCheck.setqueryToDo("update user");
						userToCheck.setSentObj(userToCheck.getReturnObj());
						userToCheck.setQueryQuestion(Msg.qUPDATE);
						userToCheck.setColumnToUpdate("ConnectionStatus");
						userToCheck.setValueToUpdate("Online");
						client.accept(userToCheck); /* Update the connection stause of the user from offline to online */
						Alert al = new Alert(Alert.AlertType.INFORMATION);
						al.setTitle("Connecttion Succeed");
						al.setContentText("Welcome " + returnUsr.getFirstName());
						al.showAndWait();
						Order.curOrder=null;
						ProductInOrder.CurCart=null;
						Customer.curCustomer=null;
						OrderSupply.curSupply=null; //init all statics to the next action
						User.currUser=returnUsr;
						//Open Appropriate menu
						String userType=returnUsr.getUserType();
						userToCheck=null;
						switch(userType)
						{
						case "Customer":
						{
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage CustomerStage=new Stage();
							Parent CustomerRoot;
							try {
								FXMLLoader loader = new FXMLLoader();
								Pane root = loader.load(getClass().getResource("/Gui/CustomerMenu.fxml").openStream());
								CustomerMenuController report=loader.getController();
								report.load_costomer("2468");
								
								
//								CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
								Scene CustomerScene = new Scene(root);
								CustomerScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
								CustomerStage.setScene(CustomerScene);
								CustomerStage.show();
				
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();	
							}
							break;
						}
						case "StoreManager":
						{
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage primaryStage=new Stage();
							Parent root;
							try {
								root = FXMLLoader.load(getClass().getResource("/Gui/StoreManagerMenu.fxml"));
								Scene Scene = new Scene(root);
								Scene.getStylesheets().add(getClass().getResource("/Gui/StoreManagerMenu.css").toExternalForm());
								primaryStage.setScene(Scene);
								primaryStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						}
						
						case "StoreEmployee":
							//StoreEmployee Menu
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage StoreEmployeeprimaryStage=new Stage();
							Parent StoreEmployeeroot;
							try {
								StoreEmployeeroot = FXMLLoader.load(getClass().getResource("/Gui/StoreEmployeeMenu.fxml"));
								Scene Scene = new Scene(StoreEmployeeroot);
								Scene.getStylesheets().add(getClass().getResource("/Gui/StoreEmployeeMenu.css").toExternalForm());
								StoreEmployeeprimaryStage.setScene(Scene);
								StoreEmployeeprimaryStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
							
						
							
							
						case "SystemManager":
							//SystemManager Menu;
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage SystemManagerStage=new Stage();
							Parent SystemManagerroot;
							try {
								StoreEmployeeroot = FXMLLoader.load(getClass().getResource("/Gui/ManagerSystemMenu.fxml"));
								Scene Scene = new Scene(StoreEmployeeroot);
								Scene.getStylesheets().add(getClass().getResource("/Gui/ManagerSystemMenu.css").toExternalForm());
								SystemManagerStage.setScene(Scene);
								SystemManagerStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
							
							
						case "CustomerService":
							//CustomerService Menu
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage CustomerServiceStage=new Stage();
							Parent CustomerServiceroot;
							try {
								CustomerServiceroot = FXMLLoader.load(getClass().getResource("/Gui/CustomerServiceMenu.fxml"));
								Scene Scene = new Scene(CustomerServiceroot);
								Scene.getStylesheets().add(getClass().getResource("/Gui/CustomerServiceMenu.css").toExternalForm());
								CustomerServiceStage.setScene(Scene);
								CustomerServiceStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
							
						case "Expert":
							//Expert Menu;
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage ExpertStage=new Stage();
							Parent Expertroot;
							try {
								Expertroot = FXMLLoader.load(getClass().getResource("/Gui/ExpertMenu.fxml"));
								Scene Scene = new Scene(Expertroot);
								Scene.getStylesheets().add(getClass().getResource("/Gui/ExpertMenu.css").toExternalForm());
								ExpertStage.setScene(Scene);
								ExpertStage.show();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
							
						case "CompanyEmployee":{
							((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
							Stage CEStage=new Stage();
							Parent CEroot;
							try {
								CEroot = FXMLLoader.load(getClass().getResource("/Gui/EditCatalog.fxml"));
								Scene CEScene = new Scene(CEroot);
								CEStage.setScene(CEScene);
								CEStage.show();
							} 
							catch (IOException e1) {
								e1.printStackTrace();
							}
							break;
						}
						case "CompanyManager":
							Stage primaryStage=new Stage();
							Parent root;
							try {
								((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
								root = FXMLLoader.load(getClass().getResource("/Gui/CompanyManager.fxml"));
								Scene Scene = new Scene(root);
								Scene.getStylesheets().add(getClass().getResource("/Gui/CompanyManager.css").toExternalForm());
								primaryStage.setScene(Scene);
								primaryStage.show();
								break;
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}	
					}
				} 
				else 
				{
					System.out.println("Wrong password");

					Alert al = new Alert(Alert.AlertType.ERROR);
					al.setTitle("Connecttion problem");
					al.setContentText("Wrong Password!");
					al.showAndWait();
				}
			}
			else {
				Alert al=new Alert(Alert.AlertType.WARNING);
				al.setTitle("Notice !");
				al.setContentText("User Alredy connected");
				al.showAndWait();
			}
		} 
		else {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Connecttion problem");
			al.setContentText("Unexist Username!");
			al.showAndWait();
		}
	}
}