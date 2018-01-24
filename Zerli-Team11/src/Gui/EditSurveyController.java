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
import java.util.ResourceBundle;

import Entities.Survey;
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

public class EditSurveyController 
{
	
	public ClientConsole client;
	
	@FXML
	TextField txtQuestion1;
	@FXML
	TextField txtQuestion2;
	@FXML
	TextField txtQuestion3;
	@FXML
	TextField txtQuestion4;
	@FXML
	TextField txtQuestion5;
	@FXML
	TextField txtQuestion6;
	
	
	/**
	 * This button getting the information from the textField and setting in new Survey
	 * and sanding question to the server that save the new Survey in the DB 
	 * @param event Button that save in the DB the new survey we build 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	@FXML
	public void SaveBtn(ActionEvent event) throws InterruptedException, IOException
	{
		Survey SendNewSurvey = new Survey();
		if(txtQuestion1.getText().compareTo("")==0 || txtQuestion2.getText().compareTo("")==0|| txtQuestion3.getText().compareTo("")==0 || 
				txtQuestion4.getText().compareTo("")==0 || txtQuestion5.getText().compareTo("")==0 || txtQuestion6.getText().compareTo("")==0)
		{
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Save problem");
			al.setContentText("One or more are empty questions");
			al.showAndWait();
		}
		else {
			SendNewSurvey.setQuestion1(txtQuestion1.getText());
			SendNewSurvey.setQuestion2(txtQuestion2.getText());
			SendNewSurvey.setQuestion3(txtQuestion3.getText());
			SendNewSurvey.setQuestion4(txtQuestion4.getText());
			SendNewSurvey.setQuestion5(txtQuestion5.getText());
			SendNewSurvey.setQuestion6(txtQuestion6.getText());
			
			
			Msg NewSurveyToDB = new Msg(Msg.qINSERT, "SendNewQuestionSurveyToDB"); // create a new msg
			NewSurveyToDB.setSentObj(SendNewSurvey); // put the Survey into msg
			NewSurveyToDB.setClassType("survey_question");
			ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
			client.accept((Object) NewSurveyToDB); //adding the survey to DB
			Alert al = new Alert(Alert.AlertType.INFORMATION);
			al.setTitle("New Survey");
			al.setContentText("Save Succeed ");
			al.showAndWait();
			
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage primaryStage=new Stage();
			Parent root=FXMLLoader.load(getClass().getResource("/Gui/CustomerServiceMenu.fxml"));
			Scene serverScene = new Scene(root);
			serverScene.getStylesheets().add(getClass().getResource("CustomerServiceMenu.css").toExternalForm());
			primaryStage.setScene(serverScene);
			primaryStage.show();
		}
		
	}
		
		
	
	/**
	 * 
	 * @param event Button that pass you back to Customer Service Menu
	 * @throws IOException
	 */
	@FXML
	public void BackBtn(ActionEvent event) throws IOException
	{
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		Parent Root;
		try {
			Root = FXMLLoader.load(getClass().getResource("/Gui/CustomerServiceMenu.fxml"));
			Scene scene = new Scene(Root);
			scene.getStylesheets()
					.add(getClass().getResource("/Gui/CustomerServiceMenu.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	
	
	/**
	 * The method getting the questions from the SurveyCuntroller and setting them on the textFields questions 
	 * @param ques1 the question number one in survey
	 * @param ques2 the question number tow in survey
	 * @param ques3 the question number three in survey
	 * @param ques4 the question number four in survey
	 * @param ques5 the question number Five in survey
	 * @param ques6 the question number six in survey
	 */
	public void getEditQues(String ques1,String ques2,String ques3,String ques4,String ques5,String ques6)
	{
		txtQuestion1.setText(ques1);
		txtQuestion2.setText(ques2);
		txtQuestion3.setText(ques3);
		txtQuestion4.setText(ques4);
		txtQuestion5.setText(ques5);
		txtQuestion6.setText(ques6);
	}

}
