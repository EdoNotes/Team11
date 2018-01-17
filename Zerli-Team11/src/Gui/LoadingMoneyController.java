package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class LoadingMoneyController implements Initializable{
	
	public ClientConsole client;
	
	@FXML
	TextField txtBalance;
	@FXML
	TextField txtAmount;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Msg pullBalance = new Msg(Msg.qSELECTALL, "pull the balance customer"); // create a new msg
		pullBalance.setSentObj(User.currUser); 
		pullBalance.setClassType("customer");
		pullBalance.setColumnToUpdate("UserName");
		pullBalance.setValueToUpdate(User.currUser.getUserName());
		ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
		try {
			client.accept((Object) pullBalance);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pullBalance = (Msg) client.get_msg();
		Customer returnCustomer = (Customer) pullBalance.getReturnObj();
		
		txtBalance.setText(Double.toString(returnCustomer.getBalance()));
		
		
	}
	@FXML
	public void ContinueBtn(ActionEvent event) throws InterruptedException, IOException {
		
		if(Double.parseDouble(txtAmount.getText())<=0)
		{

			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle("Money Loading");
			al.setContentText("Amount must be a positive amount!");
			al.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Money Loading");
			alert.setHeaderText("Confirm money loading");
			alert.setContentText("Are you sure you want loading money?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				// ... user chose OK
				Msg pullBalance = new Msg(Msg.qSELECTALL, "pull the balance customer"); // create a new msg
				pullBalance.setSentObj(User.currUser); 
				pullBalance.setClassType("customer");
				pullBalance.setColumnToUpdate("UserName");
				pullBalance.setValueToUpdate(User.currUser.getUserName());
				ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
				client.accept((Object) pullBalance);
				pullBalance = (Msg) client.get_msg();
				Customer returnCustomer = (Customer) pullBalance.getReturnObj();
				double newSumBalance= (returnCustomer.getBalance() + Double.parseDouble(txtAmount.getText()));
			
				returnCustomer.setBalance(newSumBalance);
				Msg updateBalance = new Msg(Msg.qUPDATE, "updateBalance for loading money");
				updateBalance.setClassType("customer");
				updateBalance.setColumnToUpdate("balance");
				updateBalance.setValueToUpdate(Double.toString(newSumBalance));
				client.accept((Object) returnCustomer);
			
				Alert al2 = new Alert(Alert.AlertType.INFORMATION);
				al2.setTitle("Money Loading ");
				al2.setContentText("Money Loading Succeed \n"+newSumBalance+"  NIS was added to youer account\n\nThank you "+User.currUser.getFirstName());
				al2.showAndWait();
			
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage primaryStage=new Stage();
				Parent root=FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
				Scene serverScene = new Scene(root);
				serverScene.getStylesheets().add(getClass().getResource("CustomerMenu.css").toExternalForm());
				primaryStage.setScene(serverScene);
				primaryStage.show();
			
			} else {
					// ... user chose CANCEL or closed the dialog
			}
		}
	}
}
