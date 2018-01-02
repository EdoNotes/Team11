/************************************************************************** 
 * Copyright (�) Zerli System 2017-2018 - All Rights Reserved
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ShopManagerMenuController implements Initializable
{
	@FXML
	ComboBox cmbSelectReport;
	
	ObservableList<String> ReportsList=FXCollections.observableArrayList("Quarter's Incomes","Quarter's Order","Customers Complaints","Customer Satisfication");
	

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		cmbSelectReport.setItems(ReportsList);
	}
	@FXML
	public void RegisterBtn(ActionEvent event) throws IOException
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/NewUserRegistration.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("NewUserRegistration.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
	@FXML
	public void ExitBtn(ActionEvent event)
	{
		Alert al=new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Menu  ");
		al.setContentText("Closing Menu Panel Now");
		al.showAndWait();
		System.exit(0);
	}
	
}

