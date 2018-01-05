package Login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WelcomeController {
	@FXML
	private TextField txtPort;
	@FXML
	private TextField txtIp;
	
	public static int port;
	public static String IP;
	
	@FXML
	public void ConnectBtn(ActionEvent e)
	{
		//Port
		if(txtPort.getText().compareTo("")==0)//empty text field
		{
			port=5555;
		}
		else
		{
			port=Integer.parseInt(txtPort.getText());
		}
		
		//IP
		if(txtIp.getText().compareTo("")==0)//empty text field
		{
			IP="127.0.0.1";
		}
		else
		{
			IP=txtIp.getText();
		}
		Stage primaryStage=new Stage();
		Parent root;
		try {
			
			((Node)e.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("login_application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}

}
