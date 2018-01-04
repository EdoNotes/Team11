package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Survey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditSurveyController 
{
	
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
	public void SaveBtn(ActionEvent event)
	{
		
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage primaryStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane root=loader.load(getClass().getResource("/Gui/Survey.fxml").openStream());
			SurveyController surveyController = (SurveyController)loader.getController();
			surveyController.getQues(txtQuestion1.getText(),txtQuestion2.getText(),txtQuestion3.getText(),txtQuestion4.getText(),txtQuestion5.getText(),txtQuestion6.getText());
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("Survey.css").toExternalForm());
			primaryStage.setScene(Scene);
			primaryStage.show();
		}catch(Exception e) 
			{
				e.printStackTrace();
			}
	}
		
		
		//toDo Send To DB//

	@FXML
	public void BackBtn(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		FXMLLoader loader=new FXMLLoader();
		Pane root=loader.load(getClass().getResource("/Gui/Survey.fxml").openStream());
		SurveyController surveyController = (SurveyController)loader.getController();
		//surveyController.getQues(txtQuestion1.getText(),txtQuestion2.getText(),txtQuestion3.getText(),txtQuestion4.getText(),txtQuestion5.getText(),txtQuestion6.getText());
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("Survey.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
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
