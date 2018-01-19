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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		Msg allSurveyComboBoxs=new Msg(Msg.qSELECT,"get all surveys");
		allSurveyComboBoxs.setClassType("survey_question");
		
		try {
			client.accept(allSurveyComboBoxs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		allSurveyComboBoxs = (Msg) client.get_msg();
		ArrayList<Integer> surveyQuestion=new ArrayList<Integer>((ArrayList<Integer>)allSurveyComboBoxs.getReturnObj());
		ObservableList<Integer> SurveyNumComboBox=FXCollections.observableArrayList();
		for(int i=0;i<SurveyNumComboBox.size();i++)
		{
			SurveyNumComboBox.add(i);
		}
		SurveyNumCM.setItems(SurveyNumComboBox);
		
	}

	@FXML
	public void ReportBtn(ActionEvent event) throws IOException, InterruptedException {
		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg SatisficationReport=new Msg(Msg.qSELECTALL,"View satisfaction report");
		String cmd_survey = "SELECT avg(answer1),avg(answer2),avg(answer3),avg(answer4),avg(answer5), "
				+ "avg(answer6) FROM zerli.survey_answer where numSurvey = 1;";
		SatisficationReport.setClassType("survey_question");
		SatisficationReport.setQueryQuestion(cmd_survey);
		
		client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		client.accept((Object)SatisficationReport);
		directory = (TreeMap<String, String> )client.msg;
		//SatisficationReport = (Msg) client.get_msg();
		
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("report_order.fxml").openStream());
		report_orderController report=loader.getController();
		report.setdirectory(directory, "Satisfaction Report");
		report.load_dir(directory);
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("report_order.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
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
