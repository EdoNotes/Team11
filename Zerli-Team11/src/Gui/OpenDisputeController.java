package Gui;

import Entities.Complaint;
import Entities.Customer;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OpenDisputeController {
	
	@FXML
	private TextArea txtAreaComplaintDetails;
	@FXML
	private TextField txtBranchID;
	@FXML
	private TextField txtUserName;

	public void SubmitBtn(ActionEvent event)
	{
		int BranchID=Integer.parseInt(txtBranchID.getText());
		String ComplaintDetails=txtAreaComplaintDetails.getText();
		Customer customerToFind=new Customer(txtUserName.getText());
		Msg CustomerToCheck=new Msg(Msg.qSELECTALL,"checkCustomerExistence");
		CustomerToCheck.setSentObj(customerToFind);
		CustomerToCheck.setClassType("Customer");
		ClientConsole client=new ClientConsole(WelcomeController.IP,WelcomeController.port);
		try {
			client.accept((Object) customerToFind);
			CustomerToCheck = (Msg) client.get_msg();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Customer returnCustomer=(Customer)CustomerToCheck.getReturnObj();
		//Complaint complaint=new Complaint(cusId, StorId, Details, date)
		
	}
}
