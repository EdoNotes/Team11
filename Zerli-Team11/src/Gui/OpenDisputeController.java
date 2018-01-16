package Gui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

import Entities.Complaint;
import Entities.Customer;
import Entities.Store;
import Entities.User;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OpenDisputeController {

	@FXML
	private TextArea txtAreaComplaintDetails;
	@FXML
	private TextField txtBranchID;
	@FXML
	private TextField txtUserName;

	@FXML
	public void SubmitBtn(ActionEvent event) {
		try {
			if(txtBranchID.getText().compareTo("")!=0)
			{
				int BranchID = Integer.parseInt(txtBranchID.getText());
				String ComplaintDetails = txtAreaComplaintDetails.getText();
				String UserName = "'";
				UserName += txtUserName.getText() + "'";
				if (ComplaintDetails.compareTo("") != 0)// Empty Complaint
				{
					ClientConsole client = new ClientConsole(WelcomeController.IP, WelcomeController.port);
					// verify BranchID ON DB
					Store storeToFind = new Store(BranchID);
					Msg StoreToCheck = new Msg(Msg.qSELECTALL, "checkStoreExistence");
					StoreToCheck.setSentObj(storeToFind);
					StoreToCheck.setClassType("Store");
					StoreToCheck.setColumnToUpdate("storeID");// Search Via UserName
					StoreToCheck.setValueToUpdate("" + BranchID);// Get Specific UserName
					client.accept((Object) StoreToCheck);
					StoreToCheck = (Msg) client.get_msg();
					Store returnStore = (Store) StoreToCheck.getReturnObj();
					if (returnStore.getStoreID() == BranchID) {
						// verify Customer On DB
						Customer customerToFind = new Customer(txtUserName.getText());
						Msg CustomerToCheck = new Msg(Msg.qSELECTALL, "checkCustomerExistence");
						CustomerToCheck.setSentObj(customerToFind);
						CustomerToCheck.setClassType("Customer");
						CustomerToCheck.setColumnToUpdate("UserName");// Search Via UserName
						CustomerToCheck.setValueToUpdate(txtUserName.getText());// Get Specific UserName

						client.accept((Object) CustomerToCheck);
						CustomerToCheck = (Msg) client.get_msg();
						Customer returnCustomer = (Customer) CustomerToCheck.getReturnObj();
						if (returnCustomer.getUserName() != null) {
							if (returnCustomer.getUserName().toLowerCase()
									.compareTo(customerToFind.getUserName().toLowerCase()) == 0) {

								String date = LocalDate.now().toString();
								Complaint newComplaint = new Complaint(Complaint.ComplaintIndex,
										returnCustomer.getCustomerID(), BranchID, txtAreaComplaintDetails.getText(), date);
								Msg ComplaintToCheck = new Msg(Msg.qINSERT, "insertNewComplaint");
								ComplaintToCheck.setSentObj(newComplaint);
								ComplaintToCheck.setClassType("Complaint");
								client.accept(ComplaintToCheck);
								// Show Alert
								Alert al = new Alert(Alert.AlertType.INFORMATION);
								al.setTitle("Complaint Submission");
								int complaintNumber = Complaint.ComplaintIndex - 1;
								al.setContentText(
										"Complaint Was Successfully Added\nYour Complaint Number is:" + complaintNumber);
								al.showAndWait();
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
						} else {
							Alert al = new Alert(Alert.AlertType.ERROR);
							al.setTitle("Complaint Error");
							al.setContentText("Unexisting UserName");
							al.showAndWait();
						}
					} else if (returnStore.getStoreID() != BranchID) {
						Alert al = new Alert(Alert.AlertType.ERROR);
						al.setTitle("Complaint Error");
						al.setContentText("Unexisting Branch");
						al.showAndWait();
					}
				} else {//Empty Complaint
					Alert al = new Alert(Alert.AlertType.ERROR);
					al.setTitle("Complaint Error");
					al.setContentText("Cannot Submit Empty Complaint");
					al.showAndWait();
				}

			}
			else//Empty Branch
			{
				Alert al = new Alert(Alert.AlertType.ERROR);
				al.setTitle("Complaint Error");
				al.setContentText("Cannot Submit Empty Branch");
				al.showAndWait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
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
}


