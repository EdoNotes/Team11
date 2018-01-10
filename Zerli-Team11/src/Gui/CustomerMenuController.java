package Gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomerMenuController 
{
	@FXML
	public void viewCatalogBtn(ActionEvent event)
	{
		//load catalog products images
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage CatalogStage=new Stage();
		Parent CatalogRoot;
		try {
			CatalogRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
			Scene CatalogScene = new Scene(CatalogRoot);
			CatalogScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			CatalogStage.setScene(CatalogScene);
			//load catalog products images
			CatalogStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
