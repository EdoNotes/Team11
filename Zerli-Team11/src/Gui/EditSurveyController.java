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
	
	@FXML
	public void SaveBtn(ActionEvent event) throws InterruptedException
	{
		Survey SendNewSurvey = new Survey();
		
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
		//client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
		client.accept((Object) NewSurveyToDB); //adding the survey to DB
		Alert al = new Alert(Alert.AlertType.INFORMATION);
		al.setTitle("New Survey");
		al.setContentText("Save Succeed ");
		al.showAndWait();
		
	}
		
		
		//toDo Send To DB//

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
