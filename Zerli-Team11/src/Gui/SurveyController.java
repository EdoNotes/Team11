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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	
	private String NumSurvey;
	
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
	Label txtDateOfTody;

	@FXML
	Label txtNumSurvey;

	
	
	ObservableList<String> OneToTen=FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10"); //comboBox one to ten
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) //initialize cmboBox answers (1-10)
	{
		cmbSelectAnswer1.setItems(OneToTen);
		cmbSelectAnswer2.setItems(OneToTen);
		cmbSelectAnswer3.setItems(OneToTen);
		cmbSelectAnswer4.setItems(OneToTen);
		cmbSelectAnswer5.setItems(OneToTen);
		cmbSelectAnswer6.setItems(OneToTen);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //date of today
		LocalDate localDate = LocalDate.now();
		txtDateOfTody.setText(dtf.format(localDate));


	}
	
	
	/**
	 * This method sanding to server question that saving the answers (1-10) of the survey of all 6 questions there is a Survey 
	 * and pass you back to Store Employee Menu screen
	 * @param event Button that save in the DB the Answers of the survey
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@FXML
	public void SandBtn(ActionEvent event) throws InterruptedException, IOException
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
				SendSurvey.setNumSurvey(Integer.parseInt(txtNumSurvey.getText()));	
				SendSurvey.setDate(txtDateOfTody.getText());
		
		
				Msg SurveyToDB = new Msg(Msg.qINSERT, "SendAnswerSurveyToDB"); // create a new msg
				SurveyToDB.setSentObj(SendSurvey); // put the Survey into msg
				SurveyToDB.setClassType("survey_answer");
				ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
				client.accept((Object) SurveyToDB); //adding the survey to DB
				Alert al = new Alert(Alert.AlertType.INFORMATION); //alert information that adding survey succeed
				al.setTitle("Survey number: "+ SendSurvey.getNumSurvey());
				al.setContentText("Sand Succeed ");
				al.showAndWait();
				
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage primaryStage=new Stage();
				Parent root=FXMLLoader.load(getClass().getResource("/Gui/StoreEmployeeMenu.fxml"));
				Scene serverScene = new Scene(root);
				serverScene.getStylesheets().add(getClass().getResource("StoreEmployeeMenu.css").toExternalForm());
				primaryStage.setScene(serverScene);
				primaryStage.show();

			}
	}
	
	
/**
 * @param event Button that pass you back to Store Employee Menu
 * @throws IOException
 */
	@FXML
	public void BackBtn(ActionEvent event) throws IOException 
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/StoreEmployeeMenu.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("StoreEmployeeMenu.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
	
	
	/**
	 * The method getting the questions from the StoreEmployeeMenuCuntroller and setting them on the textFields questions 
	 * @param numSurvey number survey that is in DB
	 * @param ques1 the question number one in survey
	 * @param ques2 the question number tow in survey
	 * @param ques3 the question number three in survey
	 * @param ques4 the question number four in survey
	 * @param ques5 the question number Five in survey
	 * @param ques6 the question number six in survey
	 */
	public void getQues(int numSurvey,String ques1,String ques2,String ques3,String ques4,String ques5,String ques6)
	{
		txtQuestion1.setText(ques1);
		txtQuestion2.setText(ques2);
		txtQuestion3.setText(ques3);
		txtQuestion4.setText(ques4);
		txtQuestion5.setText(ques5);
		txtQuestion6.setText(ques6);
		txtNumSurvey.setText(Integer.toString(numSurvey));
	}
	
	/**
	 * The method getting the number survey from the StoreEmployeeMenuCuntroller and setting him in the textField NumSurvey 
	 * @param numS Number survey that we field
	 */
	public void getNumSurvey(String numS) {
		NumSurvey=numS;
	}



}
