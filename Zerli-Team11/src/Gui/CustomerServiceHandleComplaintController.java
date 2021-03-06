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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

import Entities.Complaint;
import Entities.Customer;
import Login.WelcomeController;
import Login.WelcomeGui;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CustomerServiceHandleComplaintController {
	/**
	 * <Instance Variables>
	 */
	@FXML
	private Text txtComplaintDetails;
	@FXML
	private Label lblUserName;
	@FXML
	private Label lblBranchId;
	@FXML
	private Label lblAsgDate;
	@FXML
	private Label lblAsgTime;
	@FXML
	private TextField txtRefund;
	private Complaint CurrentComplaint;
	ClientConsole client;
	/**
	 * Assigns The Right Data Of The Current Complaint
	 * @param complaint
	 */
	public void LoadComplaint(Complaint complaint) {
		Customer customerToFind = new Customer(complaint.getCustomerId());
		Msg CustomerToCheck = new Msg(Msg.qSELECTALL, "checkCustomerExistence");
		CustomerToCheck.setSentObj(customerToFind);
		CustomerToCheck.setClassType("Customer");
		CustomerToCheck.setColumnToUpdate("customerID");// Search Via customerID
		CustomerToCheck.setValueToUpdate(""+complaint.getCustomerId());// Get Specific UserName
		client=new ClientConsole(WelcomeController.IP,WelcomeController.port);
		try {
			client.accept((Object) CustomerToCheck);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CustomerToCheck = (Msg) client.get_msg();
		Customer returnCustomer = (Customer) CustomerToCheck.getReturnObj();
		if (returnCustomer.getUserName() != null) {
			lblUserName.setText(returnCustomer.getUserName());
		}
		lblBranchId.setText(complaint.getStoreId()+"");
		txtComplaintDetails.setText(complaint.getComplaintDetails());
		lblAsgDate.setText(complaint.getAssigningDate());
		lblAsgTime.setText(complaint.getAssigningTime());
		CurrentComplaint=complaint;
	}

	// Event Listener on Button.onAction
	/**
	 * Cancel Button -Back To The Previous Menu
	 * @param event
	 */
	@FXML
	public void CancelBtn(ActionEvent event)
	{
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage CustomerStage = new Stage();
		Parent CustomerRoot;
		try {
			CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerServiceMenu.fxml"));
			Scene CustomerScene = new Scene(CustomerRoot);
			CustomerScene.getStylesheets()
					.add(getClass().getResource("/Gui/CustomerServiceMenu.css").toExternalForm());
			CustomerStage.setScene(CustomerScene);
			CustomerStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	/**
	 * Submit Complaint's Handel By Customer Service Employee
	 * @param event
	 */
	@FXML
	public void SubmitBtn(ActionEvent event)
	{
		double refund;
		Alert Confirmation=new Alert(Alert.AlertType.CONFIRMATION);
		Confirmation.setTitle("Submission Confirmation");
		Confirmation.setHeaderText("Complaint No."+CurrentComplaint.getComplaintId()+" is about to be confirmed");
		Confirmation.setContentText("Are you sure?");
		Optional<ButtonType> result=Confirmation.showAndWait();
		if(result.get()==ButtonType.OK)
		{
			if(txtRefund.getText().compareTo("")==0)//Empty Field -Refund(Not Acceptable)
			{
				Alert al=new Alert(Alert.AlertType.ERROR);
				al.setContentText("Refund field cannot remain Empty");
				al.setTitle("Empty TextField");
				al.showAndWait();
			}
			else
			{
				refund=Double.parseDouble(txtRefund.getText());
				//Set Got Treatment(Close Complaint)
				Msg ComplaintToUpdate=new Msg(Msg.qUPDATE,"UpdateComplaint");
				ComplaintToUpdate.setClassType("Complaint");
				ComplaintToUpdate.setSentObj(CurrentComplaint);
				ComplaintToUpdate.setColumnToUpdate("gotTreatment");
				ComplaintToUpdate.setValueToUpdate(""+1);
				try {
					client.accept(ComplaintToUpdate);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(refund!=0.0)
				{
					ComplaintToUpdate.setColumnToUpdate("gotRefund");
					try {
						client.accept(ComplaintToUpdate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//Customer's Balance Update
					Msg CustomerToFind=new Msg(Msg.qSELECTALL,"checkCustomerExistence");
					CustomerToFind.setClassType("Customer");
					Customer sentCustomer=new Customer(CurrentComplaint.getCustomerId());
					CustomerToFind.setSentObj(sentCustomer);
					CustomerToFind.setColumnToUpdate("customerID");
					CustomerToFind.setValueToUpdate(""+sentCustomer.getCustomerID());
					try {
						client.accept(CustomerToFind);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					CustomerToFind=(Msg) client.get_msg();
					Customer returnCustomer=(Customer)CustomerToFind.getReturnObj();
					double updatedBalance=returnCustomer.getBalance()+Double.parseDouble(txtRefund.getText());
					Msg CustomerToUpdate=new Msg(Msg.qUPDATE,"UpdateCustomerDetails");
					CustomerToUpdate.setClassType("Customer");
					CustomerToUpdate.setColumnToUpdate("balance");
					CustomerToUpdate.setValueToUpdate(""+updatedBalance);
					CustomerToUpdate.setSentObj(sentCustomer);
					try {
						client.accept(CustomerToUpdate);//Update Customer's Balance On DB
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Alert info=new Alert(Alert.AlertType.INFORMATION);
				info.setTitle("Complaint Handeling Complete");
				info.setContentText("Complaint Has Been Handeled Successfully");
				info.setHeaderText("Complaint Processing");
				info.showAndWait();
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage CustomerStage = new Stage();
				Parent CustomerRoot;
				try {
					CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerServiceMenu.fxml"));
					Scene CustomerScene = new Scene(CustomerRoot);
					CustomerScene.getStylesheets()
							.add(getClass().getResource("/Gui/CustomerServiceMenu.css").toExternalForm());
					CustomerStage.setScene(CustomerScene);
					CustomerStage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		else//ButtonType.Cancel
		{
			Confirmation.close();
		}
		
	}

	public Label getLblAsgDate() {
		return lblAsgDate;
	}

	public Label getLblAsgTime() {
		return lblAsgTime;
	}
	
}
