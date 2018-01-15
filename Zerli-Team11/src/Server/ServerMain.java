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
package Server;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import client.ClientConsole;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ServerMain extends Application   
{
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root=FXMLLoader.load(getClass().getResource("/Server/Server.fxml"));
			Scene serverScene = new Scene(root);
			serverScene.getStylesheets().add(getClass().getResource("server_application.css").toExternalForm());
			primaryStage.setScene(serverScene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException 
	{

		launch(args);
	}
}
