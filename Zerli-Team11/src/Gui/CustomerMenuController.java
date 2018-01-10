package Gui;

import java.io.IOException;
import java.rmi.server.LoaderHandler;

import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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
	@FXML
	public void viewMyProfileBtn(ActionEvent event)
	{


		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage ViewProfileStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane ViewProfileRoot = loader.load(getClass().getResource("/Gui/CustomerViewProfile.fxml").openStream());
			CustomerViewProfileController profile=(CustomerViewProfileController)loader.getController();
			profile.getUserDetails(User.currUser);
			Scene ViewProfileScene = new Scene(ViewProfileRoot);
			ViewProfileScene.getStylesheets().add(getClass().getResource("/Gui/CustomerViewProfile.css").toExternalForm());
			ViewProfileStage.setScene(ViewProfileScene);
			ViewProfileStage.show();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
}
