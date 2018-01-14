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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StoreEmployeeMenuController {
	
	public ClientConsole client;
	
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
			client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
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

}
