package Gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ExpertConclusionController implements Initializable {
	@FXML
	private Label lblSurveyNum;
	@FXML
	private Label lblDate;
	@FXML
	private TextArea txtAreaConclusionDetails;
	
	public ClientConsole client;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //date of today
		LocalDate localDate = LocalDate.now();
		lblDate.setText(dtf.format(localDate));
	}
	
	
	/**
	 * @param event Button that save in DB the conclusion Expert
	 */
	@FXML
	public void SubmitBtn(ActionEvent event) 
	{
		Msg SurveyCon=new Msg(Msg.qINSERT,"InsertExpertConclusions");
		SurveyCon.setClassType("survey_answer");
		String Conclusion=txtAreaConclusionDetails.getText();
		if(Conclusion.compareTo("")==0)//Empty
		{
			Alert al=new Alert(Alert.AlertType.ERROR);
			al.setTitle("Error");
			al.setContentText("Text Area Cannot Remain Empty");
			al.showAndWait();
		}
		else
		{
			SurveyCon.setSentObj(Conclusion);
			SurveyCon.setColumnToUpdate("numSurvey");
			SurveyCon.setValueToUpdate(lblSurveyNum.getText());
			client=new ClientConsole(WelcomeController.IP, WelcomeController.port);
			try {
				client.accept((Object)SurveyCon);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			((Node) event.getSource()).getScene().getWindow().hide();
			ExpertMenuController.serverScene.getWindow().hide();
			Stage CustomerStage = new Stage();
			Parent CustomerRoot;
			try {
				CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/ExpertMenu.fxml"));
				Scene CustomerScene = new Scene(CustomerRoot);
				CustomerScene.getStylesheets()
						.add(getClass().getResource("/Gui/ExpertMenu.css").toExternalForm());
				CustomerStage.setScene(CustomerScene);
				CustomerStage.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 *
	 * @param event Button that pass you back to ExpertMenu
	 */
	@FXML
	public void BackBtn(ActionEvent event)
	{
		((Node) event.getSource()).getScene().getWindow().hide();
		ExpertMenuController.serverScene.getWindow().hide();
		Stage CustomerStage = new Stage();
		Parent CustomerRoot;
		try {
			CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/ExpertMenu.fxml"));
			Scene CustomerScene = new Scene(CustomerRoot);
			CustomerScene.getStylesheets()
					.add(getClass().getResource("/Gui/ExpertMenu.css").toExternalForm());
			CustomerStage.setScene(CustomerScene);
			CustomerStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param surveyNum number survey question
	 */
	public void loadDetails(int surveyNum)
	{
		lblSurveyNum.setText(""+surveyNum);
	}

	
}
