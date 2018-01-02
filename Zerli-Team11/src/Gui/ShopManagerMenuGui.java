package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShopManagerMenuGui extends Application 
{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try 
		{
			Parent root=FXMLLoader.load(getClass().getResource("/Gui/ShopManagerMenu.fxml"));
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("ShopManagerMenu.css").toExternalForm());
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


