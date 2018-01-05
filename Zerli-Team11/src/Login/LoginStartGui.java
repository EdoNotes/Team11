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
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("login_application.css").toExternalForm());
			primaryStage.setScene(loginScene);
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
