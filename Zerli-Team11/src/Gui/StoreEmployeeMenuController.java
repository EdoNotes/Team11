package Gui;

import java.io.IOException;

import Entities.Survey;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StoreEmployeeMenuController {
	
	public ClientConsole client;
	private Msg LogoutMsg=new Msg();
	
	@FXML
	TextField txtNumSurvey;
	
	
	@FXML
	public void FillSurveyBtn(ActionEvent event) throws IOException
	{
		if(txtNumSurvey.getText().equals("")) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Survey User problem");
			al.setContentText("Fill number Survey");
			al.showAndWait();
		}
		else{
			Msg QuestionSurvey = new Msg(Msg.qSELECTALL, "select survey by numer survey"); // create a new msg
			QuestionSurvey.setSentObj(txtNumSurvey.getText()); // put the user into msg
			QuestionSurvey.setClassType("survey_question");
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			//client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
			try {
				client.accept((Object) QuestionSurvey);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
				surveyController.getNumSurvey(txtNumSurvey.getText());
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
	

}
