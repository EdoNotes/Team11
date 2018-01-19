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

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CatalogWindowController {
	@FXML
	private Button bouItem;
	@FXML
	private Button custItem;

	public void showBouqeutCatalog(ActionEvent event)
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		try {
			Stage bouStage=new Stage();
			//FXMLLoader loader=new FXMLLoader();
			Parent bouRoot =FXMLLoader.load(getClass().getResource("/Gui/BouqeutCatalog.fxml"));
			//BouqeutCatalogController BouqeutCatalogController=(BouqeutCatalogController)loader.getController();
			//BouqeutCatalogController.
			Scene bouScene = new Scene(bouRoot);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			bouStage.setScene(bouScene);
			bouStage.show();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	public void showCustomizeCatalog(ActionEvent event)
	{
		
	}
}
