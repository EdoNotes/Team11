package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import Entities.Customer;
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
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExpertMenuController implements Initializable  {
	public ClientConsole client;
	private Msg LogoutMsg=new Msg();
	
	@FXML
	ComboBox SurveyNumCM;
	public static Scene serverScene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		Msg allSurveyComboBoxs=new Msg(Msg.qSELECTALL,"get all surveys");
		allSurveyComboBoxs.setClassType("survey_question");
		
		try {
			client.accept(allSurveyComboBoxs);
			allSurveyComboBoxs = (Msg) client.get_msg();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObservableList<Integer> SurveyNumComboBox=FXCollections.observableArrayList();
		ArrayList<Integer> surveyQuestion=new ArrayList<Integer>((ArrayList<Integer>)allSurveyComboBoxs.getReturnObj());
		for(int surveyNum:surveyQuestion)
		{
			SurveyNumComboBox.add(surveyNum);
		}
		SurveyNumCM.setItems(SurveyNumComboBox);
	}

	@FXML
	public void ReportBtn(ActionEvent event) throws IOException, InterruptedException {
		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg SatisficationReport=new Msg();
		String cmd_survey = "SELECT avg(answer1),avg(answer2),avg(answer3),avg(answer4),avg(answer5), "
				+ "avg(answer6) FROM zerli.survey_answer where numSurvey ="+SurveyNumCM.getSelectionModel().getSelectedItem()+";";
		SatisficationReport.setClassType("View satisfaction report");
		SatisficationReport.setQueryQuestion(cmd_survey);
		
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		client.accept((Object)SatisficationReport);
		//directory = (TreeMap<String, String> )client.msg;
		SatisficationReport = (Msg) client.get_msg();
		directory = (TreeMap<String, String>)SatisficationReport.getReturnObj();
		
		//((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("report_order.fxml").openStream());
		report_orderController report=loader.getController();
		report.setdirectory(directory, "Satisfaction Report");
		report.load_dir(directory);
		serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("report_order.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
		
		
		Stage ExpertConStage=new Stage();
		FXMLLoader Conloader = new FXMLLoader();
		Pane Expertconroot = Conloader.load(getClass().getResource("ExpertConclusion.fxml").openStream());
		ExpertConclusionController ExpertCon=Conloader.getController();
		ExpertCon.loadDetails((int) SurveyNumCM.getSelectionModel().getSelectedItem());
		Scene ExpertconScene = new Scene(Expertconroot);
		ExpertconScene.getStylesheets().add(getClass().getResource("ExpertConclusion.css").toExternalForm());
		ExpertConStage.setScene(ExpertconScene);
		ExpertConStage.show();
		
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
