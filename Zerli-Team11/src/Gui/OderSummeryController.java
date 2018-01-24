package Gui;

import java.io.IOException;

import Entities.Customer;
import Entities.Order;
import Entities.OrderSupply;
import Entities.ProductInOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * OderSummeryController show all order summary like: Order No. , total price, credit left
 * @author tomer
 *
 */
public class OderSummeryController {
	@FXML
	private Label Totalpricelbl;
	@FXML
	private Label orderNumlbl;
	@FXML
	private Label creditlbl;

	
	/**
	 * load order id,order price and user balance to lables
	 */
	public void showDetails()
	{
		Totalpricelbl.setText(String.valueOf(Order.curOrder.getOrderPrice()));
		orderNumlbl.setText(String.valueOf(Order.curOrder.getOrderId()));
		creditlbl.setText(String.valueOf(Customer.curCustomer.getBalance()));
	}
	
	
	/**
	 * init the static: Order.curOrder,ProductInOrder.CurCart,Customer.curCustomer,OrderSupply.curSupply.
	 * (to the next order)
	 * open the main menu
	 * @param event
	 */
	public void tnxBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		/*init the static vars*/
		Order.curOrder=null;
		ProductInOrder.CurCart=null;
		Customer.curCustomer=null;
		OrderSupply.curSupply=null;
		Stage CustomerStage=new Stage();
		Parent CustomerRoot;
		try {
			CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
			Scene CustomerScene = new Scene(CustomerRoot);
			CustomerScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			CustomerStage.setScene(CustomerScene);
			CustomerStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
