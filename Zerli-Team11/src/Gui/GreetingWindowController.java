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
/**
 * GreetingWindowController to the user greeting to save
 * @author tomer
 *
 */
public class GreetingWindowController{
	@FXML
	private TextArea grttxt;
	/**
	 * Take the greeting and set her at the order
	 * @param event
	 */
	public void attachGreeting(ActionEvent event) {
		String greeting=grttxt.getText();
		Order.curOrder.setGreeting(greeting);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Greeting Attached!");
		alert.setHeaderText(null);
		alert.setContentText("Greeting Successfully Attached");
		alert.showAndWait();
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		}
	public void backBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
	}
}

