package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import client.ClientConsole;
import common.Msg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ServerController {

	@FXML
	Label lblIsConnected;
	@FXML
	Button btnConnect;
	@FXML
	TextField txtPort;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	Connection con;// A variable that will store our Connection pointer with SQL
	boolean serverIsUp=false;
	private EchoServer server;

	
	@FXML
	public void ClickToConnect(ActionEvent e) 
	{		
		if(serverIsUp==false)
		{
			if(txtPort.getText().equals(""))
			{
				this.ConnectToServer(0);
			}
			else
			{
				this.ConnectToServer(Integer.parseInt(txtPort.getText()));
			}
			lblIsConnected.setText("Connected");
			lblIsConnected.setTextFill(Color.GREEN.brighter());
			serverIsUp=true;
		}
		else
		{
			Alert al=new Alert(Alert.AlertType.WARNING);
			al.setContentText("Server is Alreay Up");
			al.show();
		}
	}
	@FXML
	public void ExitBtn(ActionEvent e)
	{
		Alert al=new Alert(Alert.AlertType.INFORMATION);
		al.setHeaderText("Closing Server");
		al.setContentText("Closing Server Now");
		al.showAndWait();
		System.exit(0);
	}
	public void ConnectToServer(int EnteredPort) 
	{
		int port = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			/* handle the error */}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/zerli","root", "root");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		if (EnteredPort != 0) {
			port = EnteredPort;
		} else {
			port = DEFAULT_PORT;
		}
		server = new EchoServer(port);
		try {
			server.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	public EchoServer getServer()
	{
		return this.server;
	}

}
