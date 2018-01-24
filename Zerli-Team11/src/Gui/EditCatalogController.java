package Gui;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.orbutil.fsm.Action;

import Entities.Catalog;
import Entities.Product;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
/**
 * EditCatalogController conttroler that with him we can add update and delete product from catalog and from DB as well
 * @author tomer
 *
 */
public class EditCatalogController implements Initializable{
	
	ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
	private Msg LogoutMsg=new Msg();
	
	@FXML
	private TableView<Product> pTable;
	@FXML
	private TableColumn<Product,Integer> pIdtb;
	@FXML
	private TableColumn<Product,String> pNametb;
	@FXML
	private TableColumn<Product,String> pTypetb;
	@FXML
	private TableColumn<Product,String> pDesctb;
	@FXML
	private TableColumn<Product,Double> pPricetb;
	@FXML
	private TableColumn<Product,ImageView> pImgtb;
	@FXML
	private TableColumn<Product,String> pColor;
	@FXML
	private TextField pidtxt;
	@FXML
	private ComboBox<String> pTypeCb;
	@FXML
	private ComboBox<String> pColorCb;
	@FXML
	private TextField pNametxt;
	@FXML
	private TextField pPricetxt;
	@FXML
	private TextArea pDesctxt;
	@FXML
	private TextField imagePathtxt;
	@FXML
	private ComboBox<String> storeCb;
	@FXML
	private Button clrBtn;
	
	String pathToImg;
	String imgName;
	
	ObservableList<String> lType=FXCollections.observableArrayList(Catalog.bouquet,Catalog.flowerpot,Catalog.weddingBouquet,Catalog.sweetbouquet);
	ObservableList<String> lColor=FXCollections.observableArrayList(Catalog.BLACK,Catalog.BLUE,Catalog.GREEN,Catalog.PURPLE,Catalog.RED,Catalog.WHITE,Catalog.YELLOW);

	/**
	 * 
	 * this func load all product from DB to edit catalog menu and accept from server ArrayList<Product>
	 * and then convert images to catalog
	 *
	 * @return ObservableList<Product>
	 */
	public ObservableList<Product> getProducts()
	{
		try {
			Product triger=new Product();
			triger.setStoreName("all"); /*I need to get the store id from user on comboBox*/
			Msg productLoderMsg=new Msg(Msg.qSELECTALL,"load all flowers");
			productLoderMsg.setClassType("Product");
			productLoderMsg.setSentObj(triger);
			clientSender.accept(productLoderMsg);
			productLoderMsg=(Msg)clientSender.get_msg();
			ArrayList<Product> products=((ArrayList<Product>)productLoderMsg.getReturnObj());
			products=ConvertAllProductImage(products);
			ObservableList<Product> productInCatalog =FXCollections.observableArrayList();
			productInCatalog.addAll(products);
			return productInCatalog;
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null; /*If we can arrive until here there is some problem*/
	}
	
	
	/**
	 * accept from DB the names of store to combo box
	 * @return
	 */
	public ObservableList<String> getStore()
	{
		Msg allStore=new Msg(Msg.qSELECT,"bring all stores");
		allStore.setClassType("Store");
		try {
			clientSender.accept(allStore);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		allStore=(Msg)clientSender.get_msg();
		ArrayList<String> storeNames=((ArrayList<String>)allStore.getReturnObj());
		ObservableList<String> stores=FXCollections.observableArrayList();
		stores.addAll(storeNames);
		return stores;
	}
	
	/**
	 * Expect to ArrayList<Product> and for each product set his image
	 * @param products
	 * @return  ArrayList<Product>
	 */
	private ArrayList<Product> ConvertAllProductImage(ArrayList<Product> products) {
		for(Product p:products)
		{
			Image img=new Image(new ByteArrayInputStream(p.getProductImage()));
			ImageView imgV=new ImageView(img);
			imgV.setFitHeight(100);
			imgV.setFitWidth(100);
			p.setImageOfproduct(imgV);
		}
		return products;
	}
	/**
	 * set all text field to be details of the selected item at the table view
	 * @param click
	 */
	public void loadDetailsToTxtBox(MouseEvent click) {
		Product toShow=pTable.getSelectionModel().getSelectedItem();
		clrBtn.setVisible(true);
		pidtxt.setText(String.valueOf(toShow.getProductId()));
		pNametxt.setText(toShow.getProductName());
		pPricetxt.setText(String.valueOf(toShow.getPrice()));
		pDesctxt.setText(toShow.getProductDescription());
		pColorCb.setPromptText(toShow.getProductColor());
		pTypeCb.setPromptText(toShow.getProductType());
		storeCb.setPromptText(toShow.getStoreName());
	}
	
	/**
	 * show the file chooser
	 * @param event
	 */
	public void imgPath(ActionEvent event) {
		FileChooser fc =new FileChooser();
		File selectedFile=fc.showOpenDialog(null);
		if(selectedFile!=null)
		{
			pathToImg=selectedFile.getAbsolutePath();
			imgName=selectedFile.getName();
			imagePathtxt.setText(pathToImg);
		}
	}
	/**UPDATE CATALOG
	 * get from text field the updated details the user change just one detail he don't need to set all other he just change him and press update
	 * user can update without new image
	 * there is check if one or more field is empty and also if item didn't selected from table view
	 * @throws InterruptedException
	 */
	public void UpdateProduct() throws InterruptedException{
		Product toUpdate=new Product();
		Msg updateProductMsg=new Msg(Msg.qUPDATE,"Update Product");
		if(pTable.getSelectionModel().getSelectedItem()==null)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Product didn't selected");
			alert.setHeaderText(null);
			alert.setContentText("You have to Choose product to item");
			alert.showAndWait();
		}
		else if(pidtxt.getText().compareTo("")==0|| pNametxt.getText().compareTo("")==0||pPricetxt.getText().compareTo("")==0||pDesctxt.getText().compareTo("")==0)
		{
			Alert al = new Alert(Alert.AlertType.ERROR); // if one of the text fields details are empty ,jumping a alert
			// error message
			al.setTitle("Error");
			al.setContentText("One or more of the feilds are empty!");
			al.showAndWait();
		}
		else {
			toUpdate.setProductId(Integer.parseInt(pidtxt.getText()));
			toUpdate.setProductName(pNametxt.getText());
			toUpdate.setPrice(Double.parseDouble(pPricetxt.getText()));
			toUpdate.setProductDescription(pDesctxt.getText());	
			if(pTypeCb.getSelectionModel().getSelectedItem()==null)
				toUpdate.setProductType(pTypeCb.getPromptText());
			else
				toUpdate.setProductType(pTypeCb.getSelectionModel().getSelectedItem());
			if(pColorCb.getSelectionModel().getSelectedItem()==null)
				toUpdate.setProductColor(pColorCb.getPromptText());
			else
				toUpdate.setProductColor(pColorCb.getSelectionModel().getSelectedItem());
			if(storeCb.getSelectionModel().getSelectedItem()==null)
				toUpdate.setStoreName(storeCb.getPromptText());
			else
				toUpdate.setStoreName(storeCb.getSelectionModel().getSelectedItem());
			if(pathToImg!=null) {
				toUpdate.setProductImage(pathToImg);
				toUpdate.setProductImage(toUpdate.getProductImage());
				updateProductMsg.setValueToUpdate("With image");
			}
			else 
				updateProductMsg.setValueToUpdate("");
			
			updateProductMsg.setClassType("product");
			updateProductMsg.setSentObj(toUpdate);
			clientSender.accept(updateProductMsg);
			pTable.setItems(getProducts());
		}
	}
	/**
	 * if user want to add new item and he have text on text fields he can clear by one click
	 * @param event
	 */
	public void clearFields(ActionEvent event) {
		pNametxt.clear();
		pPricetxt.clear();
		pDesctxt.clear();
		imagePathtxt.clear();
		pidtxt.clear();
		
	}
	/**ADD PRODUCT
	 * user can add product and need to fill all the list include the image
	 * there is a check if field is empty
	 * and before pushing him to DB we check if he already exist with our own func
	 * if exist we prompt msg
	 * @param event
	 * @see compareTwoCatalogProduct
	 */
	public void addProduct(ActionEvent event)
	{
		Product toAdd=new Product();
		Msg addProductMsg=new Msg(Msg.qINSERT,"Add Product");
		if(pNametxt.getText().compareTo("")==0||pPricetxt.getText().compareTo("")==0||pDesctxt.getText().compareTo("")==0|| pathToImg==null || pathToImg.compareTo("")==0)
		{
			Alert al = new Alert(Alert.AlertType.ERROR); // if one of the text fields details are empty ,jumping a alert
			// error message
			al.setTitle("Error");
			al.setContentText("One or more of the feilds are empty!");
			al.showAndWait();
		}
		else {
			//toAdd.setProductId(Integer.parseInt(pidtxt.getText())); //just for comparing
			toAdd.setProductName(pNametxt.getText());
			toAdd.setPrice(Double.parseDouble(pPricetxt.getText()));
			toAdd.setProductDescription(pDesctxt.getText());	
			if(pTypeCb.getSelectionModel().getSelectedItem()==null)
				toAdd.setProductType(pTypeCb.getPromptText());
			else
				toAdd.setProductType(pTypeCb.getSelectionModel().getSelectedItem());
			if(pColorCb.getSelectionModel().getSelectedItem()==null)
				toAdd.setProductColor(pColorCb.getPromptText());
			else
				toAdd.setProductColor(pColorCb.getSelectionModel().getSelectedItem());
			if(storeCb.getSelectionModel().getSelectedItem()==null)
				toAdd.setStoreName(storeCb.getPromptText());
			else
				toAdd.setStoreName(storeCb.getSelectionModel().getSelectedItem());
			toAdd.setProductImage(pathToImg);
			toAdd.setProductImage(toAdd.getProductImage());
			ObservableList<Product> DBproduct=getProducts();
			for(Product p:DBproduct) {
				if(compareTwoCatalogProduct(p,toAdd)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Product Already Exist");
					alert.setHeaderText("The Product aleady exist");
					alert.setContentText("You Can't Add Exist Product");
					alert.showAndWait();
					return;
				}
			}
			addProductMsg.setClassType("product");
			addProductMsg.setSentObj(toAdd);
			try {
				clientSender.accept(addProductMsg);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			pTable.setItems(getProducts());
		}
	}
	
	
	
	/**DELETE PRODUCT
	 * this func get the selected item and delete him from data base with confirmation 
	 * @param event
	 */
	public void deleteProduct(ActionEvent event)
	{
		Product toDelete=new Product();
		Msg deleteProductMsg=new Msg("DELETE","Delete Product");
		if(pTable.getSelectionModel().getSelectedItem()==null)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Product didn't selected");
			alert.setHeaderText(null);
			alert.setContentText("You have to Choose product to delete");
			alert.showAndWait();
		}
		else if(pidtxt.getText().compareTo("")==0|| pNametxt.getText().compareTo("")==0||pPricetxt.getText().compareTo("")==0||pDesctxt.getText().compareTo("")==0)
		{
			Alert al = new Alert(Alert.AlertType.ERROR); // if one of the text fields details are empty ,jumping a alert
			// error message
			al.setTitle("Error");
			al.setContentText("One or more of the feilds are empty!");
			al.showAndWait();
		}
		else {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Product");
			alert.setHeaderText("You are going to delete this product\nProduct ID : "+pidtxt.getText());
			alert.setContentText("Are you sure you want to delete this product ?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){  // ... user chose OK
				toDelete.setProductId(Integer.parseInt(pidtxt.getText()));		
				deleteProductMsg.setClassType("product");
				deleteProductMsg.setSentObj(toDelete);
				try {
					clientSender.accept(deleteProductMsg);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				pTable.setItems(getProducts());
				pNametxt.clear();
				pPricetxt.clear();
				pDesctxt.clear();
				imagePathtxt.clear();
				pidtxt.clear();
			} 			
			
		}
	}
	
	
	/**LOGOUT
	 * log out the user from system
	 * @param event
	 */
	@FXML
	public void LogoutBtn(ActionEvent event)
	{
		LogoutMsg.setqueryToDo("update user");
		LogoutMsg.setSentObj(User.currUser);
		LogoutMsg.setQueryQuestion(Msg.qUPDATE);
		LogoutMsg.setColumnToUpdate("ConnectionStatus");
		LogoutMsg.setValueToUpdate("Offline");	
		LogoutMsg.setClassType("User");
		ClientConsole client=new ClientConsole(WelcomeController.IP, WelcomeController.port);
		try {
			client.accept(LogoutMsg);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} /* Update the connection status of the user from online  to offline */	
		Stage primaryStage=new Stage();
		Parent root;
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
			Scene loginScene = new Scene(root);
			loginScene.getStylesheets().add(getClass().getResource("/Login/login_application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	
	
	/**
	 * this func help us to decided which two product are equal, we check the next parameters: 
	 * name, color,type,store name
	 * if are all equals we return true its mean that the two Products are equal,
	 * else we return false
	 * @param catalogP
	 * @param compProduct
	 * @return boolean
	 */
	public boolean compareTwoCatalogProduct(Product catalogP, Product compProduct) { /* if equals return true */
		boolean[] flags=new boolean[4];
			flags[0]=catalogP.getProductName().compareTo(compProduct.getProductName())==0; /* the same name*/
			flags[1]=catalogP.getProductType().compareTo(compProduct.getProductType())==0; /*the same product type */
			flags[2]=catalogP.getProductColor().compareTo(compProduct.getProductColor())==0;
			flags[3]=catalogP.getStoreName().compareTo(compProduct.getStoreName())==0;
			for(int i=0;i<4;i++)
			{
				if(flags[i]==false)
					return false;
			}
		return true;
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Open Catalog For Company Employee");
		alert.setHeaderText(null);
		alert.setContentText("Edit Catalog Smartfully :) ");
		alert.showAndWait();
		
		pIdtb.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ProductId"));
		pNametb.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductName"));
		pColor.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductColor"));
		pDesctb.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductDescription"));
		pTypetb.setCellValueFactory(new PropertyValueFactory<Product, String>("ProductType"));
		pPricetb.setCellValueFactory(new PropertyValueFactory<Product, Double>("Price"));
		pImgtb.setCellValueFactory(new PropertyValueFactory<Product, ImageView>("ImageOfproduct"));
		pTable.setItems(getProducts());
		pColorCb.setItems(lColor);
		pTypeCb.setItems(lType);
		storeCb.setItems(getStore());
		clrBtn.setVisible(false);
	}
	
	
	

}
