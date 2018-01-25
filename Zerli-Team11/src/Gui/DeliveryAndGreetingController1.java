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
import java.time.LocalDate;
import java.time.LocalTime;
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
/**
 * the controller get from the user the details of supply method and the deatils of the pick up
 * or delivery also user can add a greeting.
 * if the method already chosen and user want to back to catalog there is a prompt msg if he want to update
 * supply method or just go back ( or next-but just id the supply way was chosen earlier)
 * @author Tomer Arzuan
 *
 */
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
	private boolean validDate=true;
	private boolean orderToday=false;
	/**
	 * make the correct required fields visible
	 * @param event
	 */
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
	/**
	 * open an greeting window
	 * @param event
	 */
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
	/**
	 * user can pick a date by date picker, there is input check if the date didnt passed
	 * if he passed prompt msg
	 * @param event
	 */
	public void pickDate(ActionEvent event)
	{
		validDate=true;
		LocalDate todayDate=LocalDate.now(); /*today date*/
		LocalDate choosenDate= datePck.getValue();
		if(choosenDate.isBefore(todayDate))
		{
			validDate=false;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Wrong Date Input");
			alert.setHeaderText("Please Insert A Valid Date");
			alert.setContentText("The date you inserted is passed");
			alert.showAndWait();
			return;
			
		}
		if(validDate)
		{
			tmpSupplyDate=choosenDate.format(Order.formtDateLocal); /*collect the date*/
			validDate=true;
			if(choosenDate.isEqual(todayDate))
			{
				orderToday=true;
			}
			else
				orderToday=false;
		}
	}
	/**
	 * get from the user the time that go throw input check (as the date checked)
	 * and go to handle each one of the situations: pick up or delivery 
	 * and then goto the next window
	 * @param event
	 * @see getSupTime, pickUpSelected, deliverySelected.
	 */
	public void paymentBtn(ActionEvent event)
	{
		tmpSupplyTime=getSupTime();
		if(tmpSupplyTime.compareTo("false")==0) /*worng input*/
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Wrong Time Input");
			alert.setHeaderText("Please Insert A Valid Time");
			alert.setContentText("The date you inserted is passed\nOr not good foramt");
			alert.showAndWait();
			return;
		}
		if(!(validDate)) /*if date not ok*/
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Wrong Date Input");
			alert.setHeaderText("Please Insert A Valid Date");
			alert.setContentText("The date you inserted is passed");
			alert.showAndWait();
			return;
		}
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
	}
	
	
/**
 * check if the all text fields are fully filled get the store id from DB and init the delivery
 * if the order is instant we rising the price (cost 12.5) (also delivery as is cost 20)
 * @return boolean
 * @throws InterruptedException
 */
	public boolean deliverySelected() throws InterruptedException {
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
/**
 * check if the all text fields are fully filled
 * init the pick up
 * @throws InterruptedException
 */
	public void pickUpSelected() throws InterruptedException {
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

	
	/**
	 * func that checks the user input of time and convert him to 24H from AM/PM time
	 * also puts 0 where it need
	 * @return
	 */
	public String getSupTime() {
		String tmpHour=hourTxt.getText(),tmpMin=minTxt.getText();
		String supTime;
		int intHour;
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
				intHour=Integer.parseInt(tmpHour);
				if(intHour==12)
					intHour=0;
				intHour+=12;
				tmpHour=String.valueOf(intHour);
			}
			else {
				if ((intHour=Integer.parseInt(tmpHour))==12)
					intHour=0;
			}
			tmpHour=String.valueOf(intHour);
			if(tmpHour.length()==1)
				tmpHour="0"+tmpHour;
			if(tmpMin.length()==1)
				tmpMin="0"+tmpMin;
			supTime=tmpHour+":"+tmpMin;
		}
		if(orderToday)
		{
			LocalTime currentTime=LocalTime.now(); /*current time*/
			LocalTime chosenHour=LocalTime.parse(supTime, Order.formtTimeLocal); /* time that chosen as instance of LocalTime */
			if(chosenHour.isBefore(currentTime))
				return "false";
		}
		return supTime;
	}
	
	
	
	public void backBtn(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage cartStage=new Stage();
		Parent cartRoot;
		try {
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
	
	/**
	 * this button will appear only if orderSupply is empty
	 * @param event
	 */
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

	/**
	 * in the initialize we check if the Order supply is exist (the user have chosen) and if no so we keep as usual
	 * if it isn't null we prompt user msg if he want to change something
	 * if the user chosen to upadate supply details we update the total price of order to the original without the delivery (or what was there before)
	 */
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
			if (result.get() == ButtonType.OK){
				if(OrderSupply.curSupply.getSupplyMethod().compareTo(OrderSupply.delivery)==0)
					Order.curOrder.setOrderPrice(Order.curOrder.getOrderPrice()-OrderSupply.deliveryPrice);
				if(OrderSupply.curSupply.getIsInstant()==1)
					Order.curOrder.setOrderPrice(Order.curOrder.getOrderPrice()-OrderSupply.instantPrice);
			}
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
