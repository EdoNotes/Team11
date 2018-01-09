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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SurveyController implements Initializable
{
	public ClientConsole client;
	
	
	
	@FXML
	ComboBox cmbSelectAnswer1;
	@FXML
	ComboBox cmbSelectAnswer2;
	@FXML
	ComboBox cmbSelectAnswer3;
	@FXML
	ComboBox cmbSelectAnswer4;
	@FXML
	ComboBox cmbSelectAnswer5;
	@FXML
	ComboBox cmbSelectAnswer6;
	
	@FXML
	Label txtQuestion1;
	@FXML
	Label txtQuestion2;
	@FXML
	Label txtQuestion3;
	@FXML
	Label txtQuestion4;
	@FXML
	Label txtQuestion5;
	@FXML
	Label txtQuestion6;
	

	@FXML
	Label txtNumSurvey;

	
	
	ObservableList<String> OneToTen=FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10"); //comboBox one to ten
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) //initialize cmboBox and fields questions
	{
		cmbSelectAnswer1.setItems(OneToTen);
		cmbSelectAnswer2.setItems(OneToTen);
		cmbSelectAnswer3.setItems(OneToTen);
		cmbSelectAnswer4.setItems(OneToTen);
		cmbSelectAnswer5.setItems(OneToTen);
		cmbSelectAnswer6.setItems(OneToTen);

		txtQuestion1.setText("bla bla bla Question1 ");
		txtQuestion2.setText("bla bla bla Question2 ");
		txtQuestion3.setText("bla bla bla Question3 ");
		txtQuestion4.setText("bla bla bla Question4 ");
		txtQuestion5.setText("bla bla bla Question5 ");
		txtQuestion6.setText("bla bla bla Question6 ");
		
		txtNumSurvey.setText("Survey Number : " + Survey.NumSurvey);

	}
	
	@FXML
	public void EditBtn(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		
		FXMLLoader loader=new FXMLLoader();
		Pane root=loader.load(getClass().getResource("/Gui/EditSurvey.fxml").openStream());
		EditSurveyController EditsurveyController = (EditSurveyController)loader.getController();
		EditsurveyController.getEditQues(txtQuestion1.getText(),txtQuestion2.getText(),txtQuestion3.getText(),txtQuestion4.getText(),txtQuestion5.getText(),txtQuestion6.getText());
		

		//Parent root=FXMLLoader.load(getClass().getResource("/Gui/EditSurvey.fxml"));

		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("EditSurvey.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
	
	@FXML

	public void SandBtn(ActionEvent event) throws InterruptedException
	{
		Survey SendSurvey = new Survey();
		
		if(cmbSelectAnswer1.getValue()==null || cmbSelectAnswer2.getValue()==null  || cmbSelectAnswer3.getValue()==null  || cmbSelectAnswer4.getValue()==null 
				|| cmbSelectAnswer5.getValue()==null  || cmbSelectAnswer6.getValue()==null) {
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Send problem");
			al.setContentText("An empty answer");
			al.showAndWait();
			//check if one or more of the comboBox field Answer are empty -if there is empty field, showing an error message 
			
		}else {
				SendSurvey.setAnswer1(((String)(cmbSelectAnswer1.getValue()))); //setting the answers and the questions in survey
				SendSurvey.setAnswer2(((String)(cmbSelectAnswer2.getValue())));
				SendSurvey.setAnswer3(((String)(cmbSelectAnswer3.getValue())));
				SendSurvey.setAnswer4(((String)(cmbSelectAnswer4.getValue())));
				SendSurvey.setAnswer5(((String)(cmbSelectAnswer5.getValue())));
				SendSurvey.setAnswer6(((String)(cmbSelectAnswer6.getValue())));
		
				SendSurvey.setQuestion1(txtQuestion1.getText());
				SendSurvey.setQuestion2(txtQuestion2.getText());
				SendSurvey.setQuestion3(txtQuestion3.getText());
				SendSurvey.setQuestion4(txtQuestion4.getText());
				SendSurvey.setQuestion5(txtQuestion5.getText());
				SendSurvey.setQuestion6(txtQuestion6.getText());
		
		
		
				Msg SurveyToDB = new Msg(Msg.qINSERT, "SendSurveyToDB"); // create a new msg
				SurveyToDB.setSentObj(SendSurvey); // put the Survey into msg
				SurveyToDB.setClassType("survey");
				client = new ClientConsole("127.0.0.1",5555);/////לבדוק למה welcomeController לא מאותחל נכון
				client.accept((Object) SurveyToDB); //adding the survey to DB
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle("Survey number: "+ SendSurvey.NumSurvey);
				al.setContentText("Sand Succeed ");
				al.showAndWait();
		
				Survey.NumSurvey++;
				txtNumSurvey.setText("Survey Number : " + Survey.NumSurvey);
			}
	}
	
	
	public void getQues(String ques1,String ques2,String ques3,String ques4,String ques5,String ques6)
	{
		txtQuestion1.setText(ques1);
		txtQuestion2.setText(ques2);
		txtQuestion3.setText(ques3);
		txtQuestion4.setText(ques4);
		txtQuestion5.setText(ques5);
		txtQuestion6.setText(ques6);
	}
	



}
