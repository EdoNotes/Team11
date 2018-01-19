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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CancelOrderGui extends Application 
{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try 
		{
			Parent root=FXMLLoader.load(getClass().getResource("/Gui/CancelOrder.fxml"));
			Scene Scene = new Scene(root);
			Scene.getStylesheets().add(getClass().getResource("CancelOrder.css").toExternalForm());
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


