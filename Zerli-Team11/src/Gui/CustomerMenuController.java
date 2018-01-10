package Gui;

import java.io.IOException;

import Entities.User;
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
	@FXML
	public void viewMyProfileBtn(ActionEvent event)
	{

		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage ViewProfileStage=new Stage();
		Parent ViewProfileRoot;
		try {
			ViewProfileRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerViewProfileMenu.fxml"));
			Scene ViewProfileScene = new Scene(ViewProfileRoot);
			ViewProfileScene.getStylesheets().add(getClass().getResource("/Gui/CustomerViewProfileMenu.css").toExternalForm());
			ViewProfileStage.setScene(ViewProfileScene);
			//load catalog products images
			ViewProfileStage.show();
			CustomerViewProfileController profile=new CustomerViewProfileController();
			profile.setTxtUserName(User.currUser.getUserName());
			profile.setTxtPassword(User.currUser.getPassword());
			profile.setTxtFirstName(User.currUser.getFirstName());
			profile.setTxtLastName(User.currUser.getLastName());
			profile.setTxtPhone(User.currUser.getPhone());
			profile.setTxtGender(User.currUser.getGender());
			profile.setTxtEmail(User.currUser.getEmail());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
}
