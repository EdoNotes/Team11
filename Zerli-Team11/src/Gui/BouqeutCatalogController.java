/************************************************************************** 
 * Copyright (©) Zerli System 2017-2018 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Edo Notes <Edoono24@gmail.com>
 * 			  Tomer Arzuan <Tomerarzu@gmail.com>
 * 			  Matan Sabag <19matan@gmail.com>
 * 			  Ido Kalir <idotehila@gmail.com>
 * 			  Elinor Faddoul<elinor.faddoul@gmail.com
 **************************************************************************/
package Gui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import Entities.Catalog;
import Entities.ImageConverter;
import Entities.Order;
import Entities.Product;
import Entities.ProductInOrder;
import Entities.User;
import Login.LoginController;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class BouqeutCatalogController implements Initializable {
	
	//public static ArrayList<ProductInOrder> CurCart;

	ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
	//private ArrayList<ProductInOrder> CurCart;
	
	@FXML
	private TableView<Catalog> prodTable;
	@FXML
	private TableColumn<Catalog, Integer> prodId;
	@FXML
	private TableColumn<Catalog, String> prodtName;
	@FXML
	private TableColumn<Catalog, String> prodColor;
	@FXML
	private TableColumn<Catalog, String> prodDesc;
	@FXML 
	private TableColumn<Catalog, String> prodType;
	@FXML
	private TableColumn<Catalog, Double> prodPrice;
	@FXML
	private TableColumn<Catalog, ImageView> Image;
	/*to add- picture col*/
	
	@FXML
	public ComboBox<String> sType;
	@FXML
	public ComboBox<String> sColor;
	@FXML
	public ComboBox<String> sPrice;
	
	ObservableList<String> lType=FXCollections.observableArrayList(" ",Catalog.bouquet,Catalog.flowerpot,Catalog.weddingBouquet,Catalog.sweetbouquet);
	ObservableList<String> lColor=FXCollections.observableArrayList(" ",Catalog.BLACK,Catalog.BLUE,Catalog.GREEN,Catalog.PURPLE,Catalog.RED,Catalog.WHITE,Catalog.YELLOW);
	ObservableList<String> lPrice=FXCollections.observableArrayList(" ","1. 20-49","2. 50-69","3. 70-99","4. 100-129","5. 130-159","6. 160 or more...");
	
	public ObservableList<Catalog> getProducts()
	{
		try {
			Product triger=new Product();
			triger.setStoreName(User.currUser.getBranchName()); /*I need to get the store id from user on comboBox*/
			Msg productLoderMsg=new Msg(Msg.qSELECTALL,"load all flowers");
			productLoderMsg.setClassType("Product");
			productLoderMsg.setSentObj(triger);
			clientSender.accept(productLoderMsg);
			productLoderMsg=(Msg)clientSender.get_msg();
			ArrayList<Product> products=((ArrayList<Product>)productLoderMsg.getReturnObj());
			products=ConvertAllProductImage(products);
			ArrayList<Catalog> converted=convertProductsToCatlogs(products);
			ObservableList<Catalog> catalogs =FXCollections.observableArrayList();
			catalogs.addAll(converted);
			return catalogs;
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null; /*If we can arrive until here there is some problem*/
	}

	
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


	private ArrayList<Catalog> convertProductsToCatlogs(ArrayList<Product> products) {
		ArrayList<Catalog> catalog=new ArrayList<Catalog>();
		for (int i=0;i<products.size();i++)
		{
				catalog.add(new Catalog(products.get(i)));
		}
		return catalog;
	}
	
	public void backToCustomerMenu(ActionEvent event)
	{
		((Node)event.getSource()).getScene().getWindow().hide();//Hide Menu
		Stage CustomerStage=new Stage();
		Parent CustomerRoot;
		try {
			CustomerRoot = FXMLLoader.load(getClass().getResource("/Gui/CustomerMenu.fxml"));
			Scene CustomerScene = new Scene(CustomerRoot);
			CustomerScene.getStylesheets().add(getClass().getResource("/Gui/CustomerMenu.css").toExternalForm());
			CustomerStage.setScene(CustomerScene);
			CustomerStage.show();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	//* this method dosen't works  *//
	public void searchCustomizedItem(ActionEvent event) throws InterruptedException
	{
		//clientSender=new ClientConsole(WelcomeController.IP,WelcomeController.port);
		//ClientConsole sender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
		Product toSearch=new Product();

		String reqType,reqStringPrice,reqColor;
		reqType= sType.getValue();
		reqStringPrice= sPrice.getValue();
		reqColor=sColor.getValue();
		if(reqStringPrice!=null && (reqStringPrice.compareTo(" ")!=0))
		{
			getPriceRange(toSearch,reqStringPrice);
		}
		else if(reqStringPrice==null || reqStringPrice.compareTo(" ")==0)
		{
			toSearch.setPrice(0);
			toSearch.setEndPrice(0);
		}
		if(reqType!=null && (reqType.compareTo(" ")!=0))
		{
			toSearch.setProductType(reqType);
		}
		else if(reqType==null || reqType.compareTo(" ")==0)
		{
			reqType=null;
		}
		if(reqColor!=null && (reqColor.compareTo(" ")!=0))
		{
			toSearch.setProductColor(reqColor);
		}
		else if(reqColor==null || reqColor.compareTo(" ")==0)
		{
			reqColor=null;
		}
		Msg productToSendMsg=new Msg(Msg.qSELECTALL,"Search Products");
		productToSendMsg.setClassType("Product");
		productToSendMsg.setSentObj(toSearch);
		clientSender.accept((Object)productToSendMsg);
		productToSendMsg=(Msg)clientSender.get_msg();
		if(((ArrayList<Product>)productToSendMsg.getReturnObj()).size()!=0)
		{
			ArrayList<Product> customedPro=((ArrayList<Product>)productToSendMsg.getReturnObj());
			customedPro=ConvertAllProductImage(customedPro);
			ArrayList<Catalog> convertedCustomizedCatalog=convertProductsToCatlogs(customedPro);
			ObservableList<Catalog> customizedCatalog =FXCollections.observableArrayList();
			customizedCatalog.addAll(convertedCustomizedCatalog);
			prodTable.getItems().clear();
			prodTable.getItems().addAll(customizedCatalog);
			prodTable.setItems(customizedCatalog);
		}
		else {
			/* i have exception in this term ! if one of the is " " */
			if(reqStringPrice==null && reqColor==null && reqType==null) /*if there is no search filters and someone pressed search*/
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Search Filters Error");
				alert.setHeaderText("Ops, search filters are empty!");
				alert.setContentText("Please, fill them to find your favorite bouqeut");
				alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Not Reasults");
				alert.setHeaderText(null);
				alert.setContentText("Your bouqeut didn't found, try somthing else :)");
				alert.showAndWait();
			}
			prodTable.setItems(getProducts());
		}

	}
	
	private void getPriceRange(Product toSearch, String toNum) {
		if(toNum.startsWith("1")) {
			toSearch.setStartPrice(20);
			toSearch.setEndPrice(49);
		}
		if(toNum.startsWith("2")) {
			toSearch.setStartPrice(50);
			toSearch.setEndPrice(69);
		}
		if(toNum.startsWith("3")) {
			toSearch.setStartPrice(70);
			toSearch.setEndPrice(99);
		}
		if(toNum.startsWith("4")) {
			toSearch.setStartPrice(100);
			toSearch.setEndPrice(129);
		}
		if(toNum.startsWith("5")) {
			toSearch.setStartPrice(130);
			toSearch.setEndPrice(159);
		}
		if(toNum.startsWith("6")) {
			toSearch.setStartPrice(160);
			toSearch.setEndPrice(999999999);
		}
	}
	
	public void addToCartBtn(ActionEvent event)
	{
		String stringquantity;
		int quantity;
		Product choosenBouquetProduct;
		Catalog choosenBouqeutCatlog=prodTable.getSelectionModel().getSelectedItem();
		if(choosenBouqeutCatlog==null) /*if the customer did not choose nothing*/
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Choose Bouqeut");
			alert.setContentText("You Must Choose Bouqeut To Order Befor Adding Him");
			alert.showAndWait();
			return;
		}
		else { /*make it Catalog to product*/
			choosenBouquetProduct=new Product(choosenBouqeutCatlog);
		}
		/*----------------------------prompt Msg----------------------------------*/
		TextInputDialog dialog = new TextInputDialog("Enter Quantity...");
		dialog.setTitle("Product Confirmtion");
		dialog.setHeaderText("Are You Sure To Add Bouquet? \n Enter Quantity Between 1 to 10 WITHOUT Charcters");
		dialog.setContentText("Quantity");
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			stringquantity=result.get();
			if(checkquatity(stringquantity))
			{
				quantity=Integer.parseInt(stringquantity);
				addToCart(choosenBouquetProduct,quantity);
			}
		}

		// The Java 8 way to get the response value (with lambda expression).
		//result.ifPresent(reqQuantity-> addOrMakeNewOrder(reqQuantity));
		
		
		/*----------------------------prompt Msg----------------------------------*/
	}
	
	public boolean checkquatity(String reqQuantity) {
		String tmp=reqQuantity;
		while(tmp.startsWith(" "))
		{
			tmp=tmp.substring(1);
		}
		if(tmp.length()==0||tmp.charAt(0)<'1' || tmp.charAt(0)>'9')
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong Input Error");
			alert.setHeaderText("You Must fill quantity");
			alert.setContentText("Choose quatity Between 1 to 10 WITHOUT Charcters");
			alert.showAndWait();
			return false;
		}
		if(Integer.parseInt(tmp)>10)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Quantity Error");
			alert.setHeaderText("quatity Must Be Between 1 to 10");
			alert.setContentText("Choose quatity Between 1 to 10 WITHOUT Charcters");
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
	public void addToCart(Product pToAdd,int quantity)
	{
		double totalPriceForProduct=pToAdd.getPrice()*quantity;
		ProductInOrder pToCart=new ProductInOrder(pToAdd,quantity,totalPriceForProduct);
		if(ProductInOrder.CurCart==null)
		{
			ProductInOrder.CurCart=new ArrayList<ProductInOrder>();
		}
		if(ProductInOrder.CurCart.size()==0) /*for the first product in cart*/
		{
			//this.CurCart=ProductInOrder.CurCart;
			ProductInOrder.CurCart.add(pToCart);
		}
		else if(ProductInOrder.CurCart.contains(pToCart)){
			for(ProductInOrder p:ProductInOrder.CurCart) {
				if(p.getProductId()==pToAdd.getProductId()) /*if the product exist */
				{
					if(p.getPIOquantity()>10 || p.getPIOquantity()+quantity>10) /*if his quatity is bigger then 10*/
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Product In Order Limit");
						alert.setHeaderText("You Can Order Just 10 Pieces Of The Same Product");
						alert.setContentText("You Have Already Orderd: "+p.getPIOquantity());
						alert.showAndWait();
						return;
					}
					else { /*add to product quntity*/
						p.setPIOquantity(p.getPIOquantity()+quantity);
						p.setPIOtotalProductPrice(p.getPIOtotalProductPrice()+totalPriceForProduct);
					}
				}
			}
		}
		else { /*add a new product*/
			ProductInOrder.CurCart.add(pToCart);
		}
		System.out.println(ProductInOrder.CurCart);

	}
	
	public void showCartBtn(ActionEvent event)
	{
		if(ProductInOrder.CurCart!=null && ProductInOrder.CurCart.size()>0) /*If there is something in the cart we can view it*/
		{
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
		else { /*if the cart are empty we cant look at our order*/
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Order");
			alert.setHeaderText("Empty Cart");
			alert.setContentText("First, Add Bouqeuts To Your Cart");
			alert.showAndWait();
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prodId.setCellValueFactory(new PropertyValueFactory<Catalog, Integer>("ProductId"));
		prodtName.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductName"));
		prodColor.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductColor"));
		prodDesc.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductDescription"));
		prodType.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductType"));
		prodPrice.setCellValueFactory(new PropertyValueFactory<Catalog, Double>("Price"));
		Image.setCellValueFactory(new PropertyValueFactory<Catalog, ImageView>("CatlogImage"));
		prodTable.setItems(getProducts());
		sType.setItems(lType);
		sPrice.setItems(lPrice);
		sColor.setItems(lColor);
	}

}
