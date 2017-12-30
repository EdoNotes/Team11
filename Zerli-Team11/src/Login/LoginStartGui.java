package Login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginStartGui extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root=FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene serverScene = new Scene(root);
			serverScene.getStylesheets().add(getClass().getResource("login_application.css").toExternalForm());
			primaryStage.setScene(serverScene);
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
