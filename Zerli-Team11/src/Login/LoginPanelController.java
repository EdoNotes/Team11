package Login;

import java.awt.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class LoginPanelController 
{
	@FXML
	private TextField txtUserNAME;
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	public void ExitBtn(ActionEvent e)
	{
		Alert al=new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Login Panel ");
		al.setContentText("Closing Login Panel Now");
		al.showAndWait();
		System.exit(0);
	}
	
//	@FXML
//	public void LoginButton(ActionEvent e)
//	{
//		Array
//		
//	}
	
	
}
