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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SurveyController implements Initializable
{
	
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
	
	
	ObservableList<String> OneToTen=FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		cmbSelectAnswer1.setItems(OneToTen);
		cmbSelectAnswer2.setItems(OneToTen);
		cmbSelectAnswer3.setItems(OneToTen);
		cmbSelectAnswer4.setItems(OneToTen);
		cmbSelectAnswer5.setItems(OneToTen);
		cmbSelectAnswer6.setItems(OneToTen);
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
	public void SandBtn(ActionEvent event) throws IOException
	{
		Survey SendSurvey = new Survey();
		SendSurvey.setAnswer1(((String)(cmbSelectAnswer1.getValue())));
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
	
//	@FXML
//	public void SandBtn(ActionEvent event)
//	{
//		
//		
//		
//		//toDo Send To DB//
//	}

}
