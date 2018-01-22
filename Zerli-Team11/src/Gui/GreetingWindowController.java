package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Order;
import Entities.OrderSupply;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

public class GreetingWindowController{
	@FXML
	private TextArea grttxt;
	
	public void attachGreeting(ActionEvent event) {
//		if(Order.curOrder.getGreeting()!=null)
//		{
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Greeting Change");
//			alert.setHeaderText(null);
//			alert.setContentText("Your Greeting Will be Changed,\nPress 'Back' To Stay With The Last One");
//			alert.showAndWait();
//		}
		String greeting=grttxt.getText();
		if(greeting==null || greeting.length()==0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Empty Greeting");
			alert.setHeaderText(null);
			alert.setContentText("Your Greeting is empty");
			alert.showAndWait();
		}
		else {
			if(Order.curOrder.getGreeting()!=null)
			{
				grttxt.setText(Order.curOrder.getGreeting());
			}
				Order.curOrder.setGreeting(greeting);
				System.out.println("Greeting: "+Order.curOrder.getGreeting());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Greeting Attached!");
				alert.setHeaderText(null);
				alert.setContentText("Greeting Successfully Attached");
				alert.showAndWait();
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		}
	}
	public void backBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
	}
}

