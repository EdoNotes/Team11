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
import java.util.ResourceBundle;

import Entities.Complaint;
import Entities.User;
import Login.WelcomeController;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CustomerServiceMenuController implements Initializable {
	
	private Msg LogoutMsg=new Msg();
	@FXML
	private ComboBox cmbComplaints;
	private ClientConsole client;

	public void OpenComplaintDetails(ActionEvent event)
	{
		Complaint toLoad;
		Complaint complaintToGet=null;
		if(cmbComplaints.getSelectionModel().getSelectedItem()!=null)
		{
			int selectedComplaint=(int) cmbComplaints.getSelectionModel().getSelectedItem();
			complaintToGet=new Complaint(selectedComplaint);
			Msg ComplaintToCheck=new Msg(Msg.qSELECTALL,"checkComplaintExistence");
			ComplaintToCheck.setClassType("Complaint");
			ComplaintToCheck.setColumnToUpdate("complaintID");
			ComplaintToCheck.setValueToUpdate(""+selectedComplaint);
			client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			try {
				client.accept(ComplaintToCheck);
				ComplaintToCheck= (Msg) client.get_msg();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				toLoad=(Complaint)(ComplaintToCheck.getReturnObj());
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage OpenComplaintDetailsStage=new Stage();
				FXMLLoader loader=new FXMLLoader();
				Pane OpenComplaintDetailsRoot = loader.load(getClass().getResource("/Gui/CustomerServiceHandleComplaint.fxml").openStream());
				CustomerServiceHandleComplaintController ComplaintHandlerWindow=(CustomerServiceHandleComplaintController)loader.getController();
				ComplaintHandlerWindow.LoadComplaint(toLoad);
				Scene ViewProfileScene = new Scene(OpenComplaintDetailsRoot);
				ViewProfileScene.getStylesheets().add(getClass().getResource("/Gui/CustomerServiceHandleComplaint.css").toExternalForm());
				OpenComplaintDetailsStage.setScene(ViewProfileScene);
				OpenComplaintDetailsStage.show();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		Msg dataForComboBox=new Msg(Msg.qSELECTALL,"getAllComplaints");
		dataForComboBox.setClassType("Complaint");
		try {
			client.accept(dataForComboBox);
			dataForComboBox = (Msg) client.get_msg();
			ArrayList<Integer> ComplaintsIDS=new ArrayList<Integer>((ArrayList<Integer>)dataForComboBox.getReturnObj());
			ObservableList<Integer> ComboBoxComplaintsList=FXCollections.observableArrayList();
			for(Integer complaint:ComplaintsIDS)
			{
				ComboBoxComplaintsList.add(complaint);
			}
			cmbComplaints.setItems(ComboBoxComplaintsList);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
	public void BuildSurveyBtn(ActionEvent event) throws IOException
	{	
		{((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/EditSurvey.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("EditSurvey.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();}

	}
	
	
	@FXML
	public void ComplaintBtn(ActionEvent event) throws IOException
	{	
		{((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/OpenDispute.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("OpenDispute.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();}

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
