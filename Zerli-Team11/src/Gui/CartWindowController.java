package Gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.corba.se.impl.encoding.BufferQueue;

import Entities.Customer;
import Entities.Order;
import Entities.ProductInOrder;
import Entities.Store;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class CartWindowController implements Initializable{
	
	private Order curOrder=Order.curOrder;
	private Customer curCustomer;//=Customer.curCustomer;
	ArrayList<ProductInOrder> CurCart=ProductInOrder.CurCart;
	ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);

	
	@FXML
	private ListView<ProductInOrder> productView;
	@FXML
	private TextField nametxt;
	@FXML
	private TextField unitprctxt;
	@FXML
	private TextField colortxt;
	@FXML
	private TextField typetxt;
	@FXML
	private TextField dscrptxt;
	@FXML
	private Label totalprdcslbl;
	@FXML
	private Label totalprclbl;
	@FXML
	private Label totalmbmrlbl;
	@FXML
	private TextField qtytxt;
	
	//ObservableList<String> cartProductsList = FXCollections.observableArrayList(ProductInOrder.getAllPIONames());
	ObservableList<ProductInOrder> cartProductsList = FXCollections.observableArrayList(CurCart);
	
	public void deleteProduct(ActionEvent event)
	{
		ProductInOrder toRemove=productView.getSelectionModel().getSelectedItem();
		if(CurCart.size()==1) /*if we are about to delete the last one we go back to catalog*/
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Clear Cart");
			alert.setHeaderText("You're About To Delete All Your Items");
			alert.setContentText("Are you ok with this? \n if you will confirm you will be back to Catalog..");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				CurCart.remove(toRemove);
				System.out.println(CurCart);
				((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
				try {
					Stage bouStage=new Stage();
					Parent bouRoot =FXMLLoader.load(getClass().getResource("/Gui/BouqeutCatalog.fxml"));
					Scene bouScene = new Scene(bouRoot);
					bouStage.setScene(bouScene);
					bouStage.show();
				} 
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
			} else {
			    return;
			}
		}
		else /*if thre is more items in cart */
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Confirmation");
			alert.setHeaderText("Are You Sure You Want To Delete Product?");
			alert.setContentText("You're About To Delete "+ toRemove);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				CurCart.remove(toRemove);
				System.out.println(CurCart);
				totalprdcslbl.setText(String.valueOf(CurCart.size()));
				totalprclbl.setText(String.valueOf(cactulateTotalPrice(false)));
				totalmbmrlbl.setText(String.valueOf(cactulateTotalPrice(true)));
				productView.getItems().remove(toRemove);
			} 
			else {
			    return;
			}
		}
	}
	
	public void showSelectedProductDetails(MouseEvent Click) {
		ProductInOrder toShow=productView.getSelectionModel().getSelectedItem();
		nametxt.setText(toShow.getProductName());
		colortxt.setText(toShow.getProductColor());
		typetxt.setText(toShow.getProductType());
		dscrptxt.setText(toShow.getProductDescription());
		qtytxt.setText(String.valueOf(toShow.getPIOquantity()));
		unitprctxt.setText(String.valueOf(toShow.getPrice()));
	}
	
	public void backToCatalogBtn(ActionEvent event)
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		try {
			Stage bouStage=new Stage();
			Parent bouRoot =FXMLLoader.load(getClass().getResource("/Gui/BouqeutCatalog.fxml"));
			Scene bouScene = new Scene(bouRoot);
			bouStage.setScene(bouScene);
			bouStage.show();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public double cactulateTotalPrice(boolean discountFlag)
	{
		double totalPrice=0;
		for(ProductInOrder p: ProductInOrder.CurCart)
		{
			totalPrice+=p.getPIOtotalProductPrice();
		}
		if(discountFlag)
		{
			totalPrice=totalPrice-(totalPrice*Customer.memberDiscount);
		}
		return totalPrice;
	}
	
	public void OrderConfirmtion(ActionEvent event)
	{
		if(Order.curOrder==null)
		{
			try {
				makeNewOrder();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Customer.curCustomer.getIsSettlement()!=0)
			{
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
			else { /* if customer isnt't setallment */
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Account Is Not Settlement");
				alert.setHeaderText("Please Settlement Your Account");
				alert.setContentText("To Settlement Your Account\ncontact with the store manager");
				
				alert.showAndWait();
			}
		}
		else if(Order.curOrder!=null) /*if there is order already exist  !!!! i need to go over the case if order was changed !!!!*/
		{
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
	}
	
	
	
	
	private void makeNewOrder() throws InterruptedException {
		Msg newOrderMsg=new Msg();
		/* import from DB the current user that wants to order*/
		if(Customer.curCustomer==null)
		{
			curCustomer=new Customer();
			newOrderMsg.setqueryToDo("checkCustomerExistence");
			newOrderMsg.setClassType("Customer");
			newOrderMsg.setQueryQuestion(Msg.qSELECTALL);
			newOrderMsg.setColumnToUpdate("UserName");
			newOrderMsg.setValueToUpdate(User.currUser.getUserName());
			clientSender.accept(newOrderMsg);
			newOrderMsg=(Msg)clientSender.get_msg();
			curCustomer=(Customer)newOrderMsg.getReturnObj();
			Customer.curCustomer=curCustomer;
		}
		/*Import from DB the store id*/
		Store zerlistore=new Store();
		newOrderMsg.setClassType("Store");
		newOrderMsg.setqueryToDo("checkStoreExistence");
		newOrderMsg.setQueryQuestion(Msg.qSELECTALL);
		newOrderMsg.setColumnToUpdate("branchName");
		newOrderMsg.setValueToUpdate(User.currUser.getBranchName());
		clientSender.accept(newOrderMsg);
		newOrderMsg=(Msg)clientSender.get_msg();
		zerlistore=(Store)newOrderMsg.getReturnObj();
		/***build the Order***/
		if(Order.curOrder==null)
		{
			curOrder=new Order();
			curOrder.setCustomerId(Customer.curCustomer.getCustomerID());
			if(Customer.curCustomer.getIsMember()==1)
				curOrder.setOrderPrice(cactulateTotalPrice(true));
			else
				curOrder.setOrderPrice(cactulateTotalPrice(false));
			curOrder.setIsPaid(0);
			curOrder.setStoreId(zerlistore.getStoreID());
			curOrder.setPIO(ProductInOrder.CurCart);
			Order.curOrder=curOrder;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		totalprdcslbl.setText(String.valueOf(CurCart.size()));
		totalprclbl.setText(String.valueOf(cactulateTotalPrice(false)));
		totalmbmrlbl.setText(String.valueOf(cactulateTotalPrice(true)));
		productView.setItems(cartProductsList);
		productView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

}
