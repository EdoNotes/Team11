package Gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Entities.Customer;
import Entities.Order;
import Entities.OrderSupply;
import Entities.ProductInOrder;
import Entities.Store;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

public class DeliveryAndGreetingController1 implements Initializable{
	
	ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
	
	@FXML
	private RadioButton pckUpRb;
	@FXML
	private ToggleGroup supplyGroup;
	@FXML
	private RadioButton dlvryRb;
	@FXML
	private AnchorPane pickupgroup;
	@FXML
	private AnchorPane allOthers;
	@FXML
	private TextField minTxt;
	@FXML
	private TextField hourTxt;
	@FXML
	private DatePicker datePck;
	@FXML
	private Button paybtn;
	@FXML
	private Button nextBtn;
	@FXML
	private AnchorPane dlvrgroup;
	@FXML
	private TextField addrsTxt;
	@FXML
	private TextField nameTxt;
	@FXML
	private TextField prePhoneTxt;
	@FXML
	private TextField sufPhoneText;
	@FXML
	private RadioButton yesRb;
	@FXML
	private ToggleGroup Immediate;
	@FXML
	private RadioButton noRb;
	@FXML
	private RadioButton amRb;
	@FXML
	private ToggleGroup ampmGroup;
	@FXML
	private RadioButton pmRb;

	private String tmpSupplyDate;
	private String tmpSupplyTime;
	private String tmpAdrs;
	private String tmpRcvName;
	private String tmpRcvPhone;
	
	public void RbSelected(ActionEvent event) {
		if(pckUpRb.isSelected()) { /*Pick up selected*/
			paybtn.setVisible(true);
			dlvrgroup.setVisible(false);
			pickupgroup.setVisible(true);
		}
		else if(dlvryRb.isSelected()) { /*delivery selected*/
			paybtn.setVisible(true);
			dlvrgroup.setVisible(true);
			pickupgroup.setVisible(true);
		}	
}
	
	public void writeGriting(ActionEvent event)
	{
		try {
			Stage GreetingStage=new Stage();
			Parent GreetingRoot =FXMLLoader.load(getClass().getResource("/Gui/GreetingWindow.fxml"));
			Scene GreetingScene = new Scene(GreetingRoot);
			GreetingStage.setScene(GreetingScene);
			GreetingStage.show();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void pickDate(ActionEvent event)
	{
		tmpSupplyDate=datePck.getValue().format(Order.formtDateLocal); /*collect the date*/
	}
	
	public void paymentBtn(ActionEvent event)
	{
		tmpSupplyTime=getSupTime();
		if(tmpSupplyTime.compareTo("false")==0) /*worng input*/
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Wrong Time Input");
			alert.setHeaderText("Please Insert A Good format of Time");
			alert.setContentText("Time Format: hh:mm \nOnly Digits Between 1 to 12 and select am\\pm");
			alert.showAndWait();
			return;
		}
		System.out.println(tmpSupplyTime);
		System.out.println(tmpSupplyDate);
		if(pckUpRb.isSelected()) /*if we talk about self pick up*/
		{
			try {
				pickUpSelected();
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if (dlvryRb.isSelected()) /*if we talk delivery self pick up*/
		{
			try {
				if(!(deliverySelected()))
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Fill all fields");
					alert.setHeaderText("Fill all fields with * ");
					alert.setContentText("This is MUST fields");
					alert.showAndWait();
					return;
				}
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(User.currUser);
		System.out.println(Customer.curCustomer);
		System.out.println(Order.curOrder);
		System.out.println(OrderSupply.curSupply);
		/*Open next window*/
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage orderdStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane orderdRoot = loader.load(getClass().getResource("/Gui/OrderDetails.fxml").openStream());
			OrderDetailsController orderdetails=(OrderDetailsController)loader.getController();
			orderdetails.getOrderDetails();
			Scene orderdscene = new Scene(orderdRoot);
			orderdStage.setScene(orderdscene);
			orderdStage.show();

		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

		/*ENTER THE ORDER AND DELEVERY--->OPEN THE PAYMENT WINDOW-ALL ORDERS DETAILS*/
	}
	
	

	private boolean deliverySelected() throws InterruptedException {
		tmpAdrs=addrsTxt.getText();
		tmpRcvName=nameTxt.getText();
		tmpRcvPhone= prePhoneTxt.getText()+sufPhoneText.getText();
		if(!(noRb.isSelected()) && !(yesRb.isSelected()))
		{
			return false;
		}
		if(tmpAdrs.length()!=0 || tmpRcvName.length()!=0 || tmpRcvPhone.length()!=0)
		{
			OrderSupply supOrder=new OrderSupply();
			Msg supMsg = new Msg();
			/*Set Store id - bring the store from DB  */
			Store zerlistore=new Store();
			supMsg.setClassType("Store");
			supMsg.setqueryToDo("checkStoreExistence");
			supMsg.setQueryQuestion(Msg.qSELECTALL);
			supMsg.setColumnToUpdate("branchName");
			supMsg.setValueToUpdate(User.currUser.getBranchName());
			clientSender.accept(supMsg);
			supMsg=(Msg)clientSender.get_msg();
			zerlistore=(Store)supMsg.getReturnObj();
			/*prepare the order supply-pickup*/
			supOrder.setStoreId(zerlistore.getStoreID());
			supOrder.setSupplyMethod(OrderSupply.delivery);
			Order.curOrder.setSupplyMethod(OrderSupply.delivery);
			supOrder.setDateToSupp(tmpSupplyDate);
			supOrder.setTimeToSupp(tmpSupplyTime);
			supOrder.setContactAddress(tmpAdrs);
			supOrder.setContactName(tmpRcvName);
			supOrder.setContactPhone(tmpRcvPhone);
			if(yesRb.isSelected())
			{
				supOrder.setIsInstant(1);
				Order.curOrder.setOrderPrice(Order.curOrder.getOrderPrice()+OrderSupply.instantPrice);
			}
			Order.curOrder.setOrderPrice(Order.curOrder.getOrderPrice()+OrderSupply.deliveryPrice);
			OrderSupply.curSupply=supOrder;
			return true;
		}
		else {
			return false;
		}
	}

	private void pickUpSelected() throws InterruptedException {
		OrderSupply supOrder=new OrderSupply();
		Msg supMsg = new Msg();
		/*Set Store id - bring the store from DB  */
		Store zerlistore=new Store();
		supMsg.setClassType("Store");
		supMsg.setqueryToDo("checkStoreExistence");
		supMsg.setQueryQuestion(Msg.qSELECTALL);
		supMsg.setColumnToUpdate("branchName");
		supMsg.setValueToUpdate(User.currUser.getBranchName());
		clientSender.accept(supMsg);
		supMsg=(Msg)clientSender.get_msg();
		zerlistore=(Store)supMsg.getReturnObj();
		/*prepare the order supply-pickup*/
		supOrder.setStoreId(zerlistore.getStoreID());
		supOrder.setSupplyMethod(OrderSupply.pickUp);
		Order.curOrder.setSupplyMethod(OrderSupply.pickUp);
		supOrder.setDateToSupp(tmpSupplyDate);
		supOrder.setTimeToSupp(tmpSupplyTime);
		OrderSupply.curSupply=supOrder;
	}

	private String getSupTime() {
		String tmpHour=hourTxt.getText(),tmpMin=minTxt.getText();
		String supTime;
		if(tmpHour.length()==0||tmpHour.charAt(0)<'0' || tmpHour.charAt(0)>'9' || tmpMin.length()==0||tmpMin.charAt(0)<'0' || tmpMin.charAt(0)>'9')
		{
			return supTime="false";
		}
		else {
			if(Integer.parseInt(tmpHour)<0 || Integer.parseInt(tmpHour)>12 || Integer.parseInt(tmpMin)<0 || Integer.parseInt(tmpMin)>59) {
				return supTime="false";
			}
			if(pmRb.isSelected())
			{
				int intHour=Integer.parseInt(tmpHour);
				if(intHour==12)
					intHour=0;
				else {
					intHour+=12;
				}
				tmpHour=String.valueOf(intHour);
			}
			if(tmpHour.length()==1)
				tmpHour="0"+tmpHour;
			if(tmpMin.length()==1)
				tmpMin="0"+tmpMin;
			supTime=tmpHour+":"+tmpMin;
		}
		return supTime;
	}
	
	public void payBtn() {
		
	}
	
	
	
	public void backBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage cartStage=new Stage();
		Parent cartRoot;
		try {
			System.out.println(ProductInOrder.CurCart);
			System.out.println(ProductInOrder.CurCart);
			cartRoot = FXMLLoader.load(getClass().getResource("/Gui/CartWindow.fxml"));
			Scene Cartscene = new Scene(cartRoot);
			Cartscene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			cartStage.setScene(Cartscene);
			cartStage.show();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void nextBtn(ActionEvent event)
	{
		try {
			((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
			Stage orderdStage=new Stage();
			FXMLLoader loader=new FXMLLoader();
			Pane orderdRoot = loader.load(getClass().getResource("/Gui/OrderDetails.fxml").openStream());
			OrderDetailsController orderdetails=(OrderDetailsController)loader.getController();
			orderdetails.getOrderDetails();
			Scene orderdscene = new Scene(orderdRoot);
			orderdStage.setScene(orderdscene);
			orderdStage.show();

		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paybtn.setVisible(false);
		dlvrgroup.setVisible(false);
		pickupgroup.setVisible(false);
		nextBtn.setVisible(false);
		if(OrderSupply.curSupply!=null)
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Warning! Changing Supply Info");
			alert.setHeaderText("You Are About To Change The Supply Details");
			alert.setContentText("To Change Click 'OK' else Click 'Cancel'");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){ /* goto change...*/}
			else { 
				allOthers.setVisible(false);
				paybtn.setVisible(false);
				dlvrgroup.setVisible(false);
				pickupgroup.setVisible(false);
				nextBtn.setVisible(true);
			}

		}
	}
}
