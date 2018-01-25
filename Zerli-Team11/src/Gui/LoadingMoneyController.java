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
	
	/**
	 * This method get the text field from the amount the customer entered 
	 * and adding to the balance that already have the customer in the DB.
	 * if the customer entered a negative amount, jump error massage 
	 * @param event Button that update the balance customer in DB 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	//(txtAmount.getText().compareTo("")==0) || (Double.parseDouble(txtAmount.getText())<=0)
	@FXML
	public void ContinueBtn(ActionEvent event) throws InterruptedException, IOException {
		
		if(checkNumFiedl()) //check if the customer entered a negative amount
		{

			Alert al = new Alert(Alert.AlertType.ERROR); // jump error massage 
			al.setTitle("Money Loading");
			al.setContentText("Amount must be a positive amount and numbers only!");
			al.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Money Loading");
			alert.setHeaderText("Confirm money loading");
			alert.setContentText("Are you sure you want loading money?");

			Optional<ButtonType> result = alert.showAndWait(); // Confirmation message
			if (result.get() == ButtonType.OK){
				// customer chose OK
				Msg pullBalance = new Msg(Msg.qSELECTALL, "pull the balance customer"); //create a new massages
				pullBalance.setSentObj(User.currUser); 
				pullBalance.setClassType("Customer");
				pullBalance.setColumnToUpdate("UserName");
				pullBalance.setValueToUpdate(User.currUser.getUserName());
				ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
				client.accept((Object) pullBalance); //sanding to server
				pullBalance = (Msg) client.get_msg();
				Customer returnCustomer = (Customer) pullBalance.getReturnObj();
				double newSumBalance= (returnCustomer.getBalance() + Double.parseDouble(txtAmount.getText())); //  new balance = (old balance) + (amount that customer entered)
			
				returnCustomer.setBalance(newSumBalance);
				Msg updateBalance = new Msg(Msg.qUPDATE, "update Balance for loading money");  //create a new massages
				updateBalance.setSentObj(returnCustomer);
				updateBalance.setClassType("Customer");
				updateBalance.setColumnToUpdate("balance");
				updateBalance.setValueToUpdate(Double.toString(newSumBalance));
				client.accept((Object) updateBalance); //sanding to server
			
				Alert al2 = new Alert(Alert.AlertType.INFORMATION); //Money loading summary message
				al2.setTitle("Money Loading ");
				al2.setContentText("Money Loading Succeed \n\nCredit card number : "+returnCustomer.getCreditCard() +" , Have been charged for " +
				Double.parseDouble(txtAmount.getText())+ " NIS\n\n\n"+Double.parseDouble(txtAmount.getText())+"  NIS was added to youer account and "+
						"\n\nNow You have Total :"+newSumBalance+" NIS \n\nThank you "+User.currUser.getFirstName()+" ,And have a nice day ");
				al2.showAndWait();
			
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				Stage primaryStage=new Stage();
				Parent root=FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml")); //pass back to Customer Menu
				Scene serverScene = new Scene(root);
				serverScene.getStylesheets().add(getClass().getResource("CustomerMenu.css").toExternalForm());
				primaryStage.setScene(serverScene);
				primaryStage.show();
			
			} else {
					// customer chose CANCEL or closed the dialog
			}
		}
	}
	
	/**
	 * @param event Button that pass you back to Customer Menu
	 * @throws IOException
	 */
	@FXML
	public void BackBtn(ActionEvent event) throws IOException 
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage primaryStage=new Stage();
		Parent root=FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
		Scene serverScene = new Scene(root);
		serverScene.getStylesheets().add(getClass().getResource("CustomerMenu.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.show();
	}
	
	
	/**
	 * method that check if the text field is legal number 
	 * @return
	 */
	public boolean checkNumFiedl()
	{
		if(txtAmount.getText().compareTo("")==0
				||txtAmount.getText().charAt(0)<'0' 
				||txtAmount.getText().charAt(0)>'9' 
				||txtAmount.getText().charAt(0)==' '
				||txtAmount.getText().compareTo("")==0
				||Double.parseDouble(txtAmount.getText())<0)
			return true;
		else return false;
	}
}
