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
import Entities.Survey;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StoreEmployeeMenuController implements Initializable {
	
	public ClientConsole client;
	private Msg LogoutMsg=new Msg();
	@FXML
	ComboBox cmbNumSurvey;
	
	
	/**
	 * 
	 * @param event Button the pass you to the next screen the there the employee insert the 
	 * answer customer
	 * @throws IOException
	 */
	@FXML
	public void FillSurveyBtn(ActionEvent event) throws IOException
	{
		if(cmbNumSurvey.getValue()==null)//check if the combo box number survey are not empty
		{
			Alert al=new Alert(Alert.AlertType.ERROR);
			al.setTitle("Error");
			al.setContentText("Combo Box Cannot Remain Empty");
			al.showAndWait();
		}
		else{
			Msg QuestionSurvey = new Msg(Msg.qSELECTALL, "select survey by numer survey"); // create a new msg
			String cbNumS=""+cmbNumSurvey.getSelectionModel().getSelectedItem();
			QuestionSurvey.setSentObj(cbNumS); // put the user into msg
			QuestionSurvey.setClassType("survey_question");
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			try {
				client.accept((Object) QuestionSurvey);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			QuestionSurvey = (Msg) client.get_msg();
			Survey returnSurveyQ = (Survey) QuestionSurvey.getReturnObj();
			
			if(returnSurveyQ.getQuestion1().equals("")) {
				Alert al2 = new Alert(Alert.AlertType.ERROR);
				al2.setTitle("Survey User problem");
				al2.setContentText("Survey does not exist \nplease Enter another survey number");
				al2.showAndWait();
				
			}
			else {
			
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage primaryStage=new Stage();
		
				FXMLLoader loader=new FXMLLoader();
				Pane root=loader.load(getClass().getResource("/Gui/Survey.fxml").openStream());
				SurveyController surveyController = (SurveyController)loader.getController();
				surveyController.getNumSurvey(""+cmbNumSurvey.getSelectionModel().getSelectedItem());
				surveyController.getQues(returnSurveyQ.getNumSurvey(),returnSurveyQ.getQuestion1(), returnSurveyQ.getQuestion2(), returnSurveyQ.getQuestion3(), returnSurveyQ.getQuestion4(), returnSurveyQ.getQuestion5(), returnSurveyQ.getQuestion6());

				Scene serverScene = new Scene(root);
				serverScene.getStylesheets().add(getClass().getResource("Survey.css").toExternalForm());
				primaryStage.setScene(serverScene);
				primaryStage.show();
			}
		}
		
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		Msg numSurveyCheck=new Msg(Msg.qSELECTALL,"get all surveys");
		numSurveyCheck.setClassType("survey_question");
		try {
			client.accept(numSurveyCheck);
			numSurveyCheck = (Msg) client.get_msg();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ObservableList<Integer> ComboBoxNumSurveyQuest=FXCollections.observableArrayList();
		ArrayList<Integer> returnNumQuest = new ArrayList<Integer>((ArrayList<Integer>) numSurveyCheck.getReturnObj());
		for(int surveyNum:returnNumQuest)
		{
			ComboBoxNumSurveyQuest.add(surveyNum);
		}
		cmbNumSurvey.setItems(ComboBoxNumSurveyQuest);
		
	}
	

}
