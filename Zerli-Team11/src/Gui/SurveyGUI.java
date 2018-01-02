package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SurveyGUI extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		try 
		{
			Stage primaryStage=new Stage();
			Parent root=FXMLLoader.load(getClass().getResource("/Gui/Survey.fxml"));
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("Survey.css").toExternalForm());
			primaryStage.setScene(Scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	} 
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}


