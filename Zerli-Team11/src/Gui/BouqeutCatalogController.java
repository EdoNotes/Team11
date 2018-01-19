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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entities.Catalog;
import Entities.Product;
import Login.LoginController;
import Login.WelcomeController;
import client.ClientConsole;
import common.Msg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class BouqeutCatalogController implements Initializable {

	public ClientConsole clientSender = new ClientConsole(WelcomeController.IP,WelcomeController.port);
	
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
	private TableColumn<Catalog, Integer> quantity;
	/*to add- picture col*/
	
	public ObservableList<Catalog> getProducts()
	{
		try {
			Product triger=new Product();
			triger.setStoreId(3); /*I need to get the store id from user on comboBox*/
			Msg productLoderMsg=new Msg(Msg.qSELECT,"load Bouqeut to catalog");
			productLoderMsg.setClassType("Product");
			productLoderMsg.setSentObj(triger);
			clientSender.accept(productLoderMsg);
			productLoderMsg=(Msg)clientSender.get_msg();
			ArrayList<Product> products=((ArrayList<Product>)productLoderMsg.getReturnObj());
			System.out.println(products.get(0).getProductName());
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

	
	private ArrayList<Catalog> convertProductsToCatlogs(ArrayList<Product> products) {
		ArrayList<Catalog> catalog=new ArrayList<Catalog>();
		for (int i=0;i<products.size();i++)
		{
			if(products.get(i).getQuantity()!=0)  /*Shows all the product that the quantity is more than zero*/
				catalog.add(new Catalog(products.get(i)));
		}
		return catalog;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prodId.setCellValueFactory(new PropertyValueFactory<Catalog, Integer>("ProductId"));
		prodtName.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductName"));
		prodColor.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductColor"));
		prodDesc.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductDescription"));
		prodType.setCellValueFactory(new PropertyValueFactory<Catalog, String>("ProductType"));
		prodPrice.setCellValueFactory(new PropertyValueFactory<Catalog, Double>("Price"));
		quantity.setCellValueFactory(new PropertyValueFactory<Catalog, Integer>("Quantity"));
		prodTable.setItems(getProducts());

	}

}
