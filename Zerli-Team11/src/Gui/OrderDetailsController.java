package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.Order;
import Entities.OrderSupply;
import Entities.ProductInOrder;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;

public class OrderDetailsController implements Initializable{
	
	ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
	
	@FXML
	private Label cusIdlbl;
	@FXML
	private Label cusNamelbl;
	@FXML
	private Label cusCreditCardlbl;
	@FXML
	private Label cusSubslbl;
	@FXML
	private Label supDate;
	@FXML
	private Label supMethodlbl;
	@FXML
	private Label supStoreNamelbl;
	@FXML
	private Label supDatelbl;
	@FXML
	private Label supTime;
	@FXML
	private Label supTimelbl;
	@FXML
	private AnchorPane dlvrInfo;
	@FXML
	private Label dlvPhonelbl;
	@FXML
	private Label dlvAdrslbl;
	@FXML
	private Label dlvNamelbl;
	@FXML
	private Label orderTotallbl;
	@FXML
	private RadioButton cashRb;
	@FXML
	private ToggleGroup cashCreditGroup;
	@FXML
	private RadioButton CreditRb;
	@FXML
	private Label balancelbl;
	@FXML
	private ListView itemList;
	
	
	ObservableList<ProductInOrder> cartProductsList = FXCollections.observableArrayList(Order.curOrder.getPIO());
	
	public void getOrderDetails()
	{
		cusIdlbl.setText(String.valueOf(Customer.curCustomer.getCustomerID()));
		cusNamelbl.setText(User.currUser.getFirstName() +" " +User.currUser.getLastName());
		cusCreditCardlbl.setText(Customer.curCustomer.getCreditCard());
		if(Customer.curCustomer.getIsMember()==1)
			cusSubslbl.setText("YES");
		else
			cusSubslbl.setText("NO");
		supStoreNamelbl.setText(User.currUser.getBranchName());
		supMethodlbl.setText(OrderSupply.curSupply.getSupplyMethod());
		supDatelbl.setText(OrderSupply.curSupply.getDateToSupp());
		supTimelbl.setText(OrderSupply.curSupply.getTimeToSupp());
		if(OrderSupply.curSupply.getSupplyMethod().compareTo(OrderSupply.delivery)==0)
		{
			dlvrInfo.setVisible(true);
			dlvNamelbl.setText(OrderSupply.curSupply.getContactName());
			dlvAdrslbl.setText(OrderSupply.curSupply.getContactAddress());
			dlvPhonelbl.setText(OrderSupply.curSupply.getContactPhone());
		}
		orderTotallbl.setText(String.valueOf(Order.curOrder.getOrderPrice()));
		balancelbl.setText(String.valueOf(Customer.curCustomer.getBalance()));
	}
	
	public void backBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		try {
			Stage DeliveryStage=new Stage();
			Parent DeliveryRoot =FXMLLoader.load(getClass().getResource("/Gui/DeliveryAndGreeting.fxml"));
			Scene DeliveryScene = new Scene(DeliveryRoot);
			DeliveryStage.setScene(DeliveryScene);
			DeliveryStage.show();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void payBtn(ActionEvent event) throws InterruptedException
	{
		if(!(CreditRb.isSelected())&& !(cashRb.isSelected()))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Choose Payment");
			alert.setHeaderText("You Must Choose Payment Way");
			alert.setContentText("Cash Or Credit");
			alert.showAndWait();
			return;
		}
		if(CreditRb.isSelected()) /*if payed by credit*/
		{
			if(Customer.curCustomer.getBalance()-Order.curOrder.getOrderPrice()>0) /*If the customer have balance*/
			{
				Customer.curCustomer.setBalance(Customer.curCustomer.getBalance()-Order.curOrder.getOrderPrice());
				Msg customerUpdateMsg=new Msg();
				customerUpdateMsg.setClassType("Customer");
				customerUpdateMsg.setQueryQuestion(Msg.qUPDATE);
				customerUpdateMsg.setqueryToDo("Upadate Customer Field");
				customerUpdateMsg.setColumnToUpdate("balance");
				customerUpdateMsg.setValueToUpdate(String.valueOf(Customer.curCustomer.getBalance()));
				clientSender.accept(customerUpdateMsg);
			}
			else { /*If the customer's balance is low*/
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Low Balance");
				alert.setHeaderText("You Have To Recharge Money");
				alert.setContentText("Your Balance not Enough for this Order");
				alert.showAndWait();
				return;
			}
		}
		else { /* if pay by cash */
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cash Payment");
			alert.setHeaderText(null);
			alert.setContentText("You Choose To Pay By Cash!\nTo Pay, Go To Your Home Store: "+User.currUser.getBranchName());
			alert.showAndWait();
		}
		
		try {
			Msg orderMsg=new Msg();
			/*inset order data*/
			orderMsg.setSentObj(Order.curOrder);
			orderMsg.setQueryQuestion(Msg.qINSERT);
			orderMsg.setClassType("Order");
			orderMsg.setqueryToDo("Insert new order");
			clientSender.accept(orderMsg);
			orderMsg=(Msg)clientSender.get_msg();
			int Orderid=((Order)orderMsg.getReturnObj()).getOrderId();
			System.out.println("Order id is: "+Orderid);
			Order.curOrder.setOrderId(Orderid);

			/*insert product in order data*/
			orderMsg.setQueryQuestion(Msg.qINSERT);
			orderMsg.setClassType("Order");
			orderMsg.setqueryToDo("Insert Products In Order");
			for(ProductInOrder p: ProductInOrder.CurCart) /* set all Product in order to the currnt order and send each one to DB*/
			{
				p.setOrderId(Orderid);
				orderMsg.setSentObj(p);
				clientSender.accept(orderMsg);
			}

			/*insert delivery in order data*/
			orderMsg.setQueryQuestion(Msg.qINSERT);
			orderMsg.setClassType("Order");
			orderMsg.setqueryToDo("Insert Supply Order Method");
			OrderSupply.curSupply.setOrderId(Order.curOrder.getOrderId());
			orderMsg.setSentObj(OrderSupply.curSupply);
			clientSender.accept(orderMsg);

		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage ordersumdStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane ordersumRoot = loader.load(getClass().getResource("/Gui/OderSummery.fxml").openStream());
			OderSummeryController orderSum=(OderSummeryController)loader.getController();
			orderSum.showDetails();
			Scene ordersumscene = new Scene(ordersumRoot);
			ordersumdStage.setScene(ordersumscene);
			ordersumdStage.show();

		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dlvrInfo.setVisible(false);
		itemList.setItems(cartProductsList);
		itemList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

}
