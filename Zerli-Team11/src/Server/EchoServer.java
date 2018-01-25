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
package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import Entities.*;
import common.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class EchoServer extends AbstractServer {

	
	/**
	 * The default port and host to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	final public static String HOST = "localhost";

	/*
	 * Methods Area
	 * =============================================================================
	 * ==
	 */

	public EchoServer(int port) {
		super(port);
	}
	/**
	 *All The Communication Between Client To server
	 *Is Moving Through This Function,
	 *in this func we do classification
	 *for any msg that send from, client
	 *from this func we go to specific handler
	 *and from there to the required func we need 
	 *
	 *@param msg convert from Object to Msg and contains the class type to handle
	 *
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
			Msg msgRecived = (Msg) msg;
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/zerli", "root", "root");
			System.out.println("SQL connection succeed 222");
			if ((msgRecived.getClassType()).equalsIgnoreCase("User")) {
				userHandeler(msgRecived, "user", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_question")) {
				SurveyHandeler(msgRecived, "survey_question", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_answer")) {
				SurveyHandeler(msgRecived, "survey_answer", client, conn);
			}else if(msgRecived.getClassType().equalsIgnoreCase("Product")) {
				productHandler(msgRecived,client,conn);
			}else if ((msgRecived.getClassType()).equalsIgnoreCase("report")) {
				get_order_report(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Ask_order")) {
				get_consumer_order(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("CancelO")) {
				cancel_order(msgRecived, conn, client);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("survey_report")) {
				get_order_survey_report(msgRecived, conn, client);
			}else if((msgRecived.getClassType()).equalsIgnoreCase("View satisfaction report")) {
				get_order_survey_report(msgRecived, conn, client);
			}
			else if ((msgRecived.getClassType()).equalsIgnoreCase("Customer")) {
				customerHandeler(msgRecived, "customer", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Complaint")) {
				ComplaintHandeler(msgRecived, "complaint", client, conn);
			} else if ((msgRecived.getClassType()).equalsIgnoreCase("Store")) {
				StoreHandeler(msgRecived, "store", client, conn);
			}
			else if((msgRecived.getClassType()).equalsIgnoreCase("Order"))
			{
				orderHandler(msgRecived, "order", client, conn);
			}
			
		} 
		catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * orderHandler- sort the msg with class type "order" to functions by the query that need to do
	 * @param msg contains the query to do
	 * @param client the connection connected to the client that sent the message.
	 * @param conn the conntion to DB
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void orderHandler(Msg msg, String tableName, ConnectionToClient client, Connection con) throws IOException, SQLException {
		String queryToDo = msg.getqueryToDo();
		if(queryToDo.compareTo("Insert new order")==0)
		{
			insertNewOrder(msg,tableName, client, con);
		}
		else if(queryToDo.compareTo("Insert Products In Order")==0) {
			insertProductInOrder(msg,"product_in_order", client, con);
		}
		else if(queryToDo.compareTo("Insert Supply Order Method")==0)
		{
			insertOrderSupply(msg,"delivery_details", client, con);
		}
	}

	
	/**
	 * by using PreparedStatement we insert a new supply of order to data base
	 * @param msg contain OrderSupply instace
	 * @param tableName
	 * @param client the connection connected to the client that sent the message.
	 * @param con the connection to DB
	 */
	private static void insertOrderSupply(Msg msg, String tableName, ConnectionToClient client, Connection con) {
		OrderSupply supToInset=(OrderSupply)msg.getSentObj();
		String query= msg.getQueryQuestion()+" zerli."+tableName+" (`IDOrder`, `StoreID`, `supplyMethod`, `dateOfSupply`, `TimeOfSupply`, `contactName`, `Contact address`, `contacPhonet`, `isInstant`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, supToInset.getOrderId());//order id
			stmt.setInt(2, supToInset.getStoreId());//store id
			stmt.setString(3, supToInset.getSupplyMethod());//supply method
			stmt.setString(4, supToInset.getDateToSupp());//date
			stmt.setString(5, supToInset.getTimeToSupp());//time
			stmt.setString(6, supToInset.getContactName());//Contact name 
			stmt.setString(7,supToInset.getContactAddress()); //Contact address
			stmt.setString(8,supToInset.getContactPhone());//Contact phone
			stmt.setInt(9,supToInset.getIsInstant());//is instant
			stmt.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * using PreparedStatement we push to DB product in order
	 * @param msg contins ProductInOrder instance
	 * @param tableName
	 * @param client the connection connected to the client that sent the message.
	 * @param con the connection to DB
	 * @throws SQLException
	 */
	private static void insertProductInOrder(Msg msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		ProductInOrder productToInset=(ProductInOrder)msg.getSentObj();
		String query= msg.getQueryQuestion()+" zerli."+tableName+"(`OrderID`, `productID`, `quantity`, `totalPrice`) VALUES (?,?,?,?);";
		PreparedStatement stmt = con.prepareStatement(query);
		try {
			stmt.setInt(1,productToInset.getOrderId()); //order id
			stmt.setInt(2, productToInset.getProductId());
			stmt.setInt(3, productToInset.getPIOquantity());
			stmt.setDouble(4, productToInset.getPrice()*productToInset.getPIOquantity());
			stmt.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * insert to the data base new order with PreparedStatement
	 * @param msg contains product to update and query question
	 * @param tableName
	 * @param client the connection connected to the client that sent the message.
	 * @param con the connection to DB
	 * @throws IOException
	 */
	private static void insertNewOrder(Msg msg, String tableName, ConnectionToClient client, Connection con) throws IOException {
		Order OrderToSet=(Order)msg.getSentObj();
		String query=msg.getQueryQuestion()+" zerli."+tableName+" (`customerID`, `supplyMethod`, `orderPrice`, `greeting`, `Date`, `orderTime`, `isPaid`, `storeID`) VALUES (?,?,?,?,?,?,?,?);";
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, OrderToSet.getCustomerId());//customer id
			stmt.setString(2, OrderToSet.getSupplyMethod());//supply method
			stmt.setDouble(3, OrderToSet.getOrderPrice());//order Price
			stmt.setString(4, OrderToSet.getGreeting());//greeting
			stmt.setString(5, OrderToSet.getOrderDate());//order date
			stmt.setString(6, OrderToSet.getOrferTime());//order time
			stmt.setInt(7,OrderToSet.getIsPaid()); //is Paid
			stmt.setInt(8,OrderToSet.getStoreId());//store id
			stmt.executeUpdate();
			//con.close();
			Order orderId=new Order();
			String nextQuery="SELECT * FROM zerli.order WHERE customerID= ? AND Date= ? AND orderTime= ?";
			PreparedStatement stmt1 = con.prepareStatement(nextQuery);
			stmt1.setInt(1, OrderToSet.getCustomerId());
			stmt1.setString(2, OrderToSet.getOrderDate());//order date
			stmt1.setString(3, OrderToSet.getOrferTime());//order time
			ResultSet rs=stmt1.executeQuery();
			if(rs.next())
			{
				orderId.setOrderId(rs.getInt("orderId"));
			}
			rs.close();
			con.close();
			msg.setReturnObj(orderId);
			client.sendToClient(msg);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * the handler of the products- sort msg from class type product
	 * @param msg
	 * @param client
	 * @param conn
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void productHandler(Msg msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		String queryToDo = msg.getqueryToDo();
		if(queryToDo.compareTo("load all flowers")==0)
		{
			loadProductsToCatalogByType(msg,client,conn);
		}
		else if(queryToDo.compareTo("Search Products")==0)
		{
			SearchProductInCatalog(msg,client,conn);
		}
		else if(queryToDo.compareTo("Update Product")==0) {
			updateProduct(msg,"product",client,conn);
		}
		else if(queryToDo.compareTo("Add Product")==0) {
			addProduct(msg,"product",client,conn);
		}
		else if(queryToDo.compareTo("Delete Product")==0) {
			DeleteProduct(msg,"product",client,conn);
		}
	}
	
	/**
	 * Delete product from data base by given the product id
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void DeleteProduct(Msg msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Product toDelete=(Product)msg.getSentObj();
		String query= msg.getQueryQuestion()+" FROM zerli."+tableName +" WHERE productID = ? " ;
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setInt(1, toDelete.getProductId()); //product ID
		stmt.executeUpdate();
		con.close();
	}
	
	/**
	 * add product to DB with product image
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void addProduct(Msg msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Product toAdd=(Product)msg.getSentObj();
		String query= msg.getQueryQuestion()+" zerli."+tableName +" (productName, productType, productDescription, price, dominantColor, BranchName, image) VALUES (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, toAdd.getProductName()); //name
		stmt.setString(2, toAdd.getProductType()); //type
		stmt.setString(3, toAdd.getProductDescription()); //desc
		stmt.setDouble(4, toAdd.getPrice()); //price
		stmt.setString(5, toAdd.getProductColor()); //color
		stmt.setString(6, toAdd.getStoreName()); //store name
		stmt.setBlob(7,ImageConverter.convertByteArrayToInputStream(toAdd.getProductImage())); //image
		stmt.executeUpdate();
		con.close();
		
	}
	
	/**
	 * update product details by given product id
	 * the product can be with or without image
	 * if the product the image include
	 * add to ValueToUpdate param at Msg class the string "with image"
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void updateProduct(Msg msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Product pToUpdate=(Product)msg.getSentObj();
		String query;
		int indexProductId=8;
		PreparedStatement stmt;
		if(msg.getValueToUpdate().compareTo("With image")==0) {
			query=msg.getQueryQuestion()+" zerli."+tableName+" SET productName= ? ,productType= ? , productDescription= ? ,price= ? , dominantColor = ? , BranchName= ? , image = ? WHERE productID = ? ;";
			stmt = con.prepareStatement(query);
			stmt.setBlob(7,ImageConverter.convertByteArrayToInputStream(pToUpdate.getProductImage()));
		}
		else {
			query=msg.getQueryQuestion()+" zerli."+tableName+" SET productName= ? ,productType= ? , productDescription= ? ,price= ? , dominantColor = ? , BranchName= ? WHERE productID = ? ;";
			indexProductId=7;
			stmt = con.prepareStatement(query);
		}

		stmt.setString(1, pToUpdate.getProductName()); //name
		stmt.setString(2, pToUpdate.getProductType()); //type
		stmt.setString(3, pToUpdate.getProductDescription()); //desc
		stmt.setDouble(4, pToUpdate.getPrice()); //price
		stmt.setString(5, pToUpdate.getProductColor()); //color
		stmt.setString(6, pToUpdate.getStoreName()); //store name
		stmt.setInt(indexProductId, pToUpdate.getProductId());
		stmt.executeUpdate();
		con.close();
	}

		
/**
 * The 6 type of search to the customize item
 * this method bring the product that the user exactly
 * want to look the filters are: price range, type and color
 * @param msg
 * @param client
 * @param con
 * @throws SQLException
 * @throws IOException
 */
	private static void SearchProductInCatalog(Msg msg, ConnectionToClient client, Connection con) throws SQLException, IOException {
		Product parmsToSearch=((Product)msg.getSentObj());
		ArrayList<Product> productsFounded=new ArrayList<Product>();
		int rangeStart=parmsToSearch.getStartPrice(), rangeEnd=parmsToSearch.getEndPrice(),i=0;
		Blob img;
		InputStream input=null;
		img=con.createBlob();
		String Type=parmsToSearch.getProductType();
		String color=parmsToSearch.getProductColor();
		String query;
		boolean emptyResFlag=true; //this flag is go down when result of the query is null of not found
		ResultSet rs = null;
		PreparedStatement stmt = null;
		if(rangeStart!=0 && Type!=null && color!=null) /*if all search field are filled*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND `productType`= ? AND (price>= ? AND price<= ? ) AND `dominantColor` = ?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setString(2,Type);
			stmt.setInt(3,rangeStart);
			stmt.setInt(4,rangeEnd);
			stmt.setString(5,color);
		}
		else if(rangeStart==0 && Type!=null && color!=null) /*If the user search only by Type and Color*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND `productType`= ? AND `dominantColor` = ?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setString(2,Type);
			stmt.setString(3,color);
		}
		else if(rangeStart==0 && Type==null && color!=null)/*if the user search only by color*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND `dominantColor` = ?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setString(2,color);
		}
		else if(rangeStart==0 && Type!=null && color==null) /*if user search only by type*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND `productType`= ?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setString(2,Type);
		}
		else if(rangeStart!=0 && Type==null && color==null) /*search by price*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND (price>= ? AND price<= ? );";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setInt(2,rangeStart);
			stmt.setInt(3,rangeEnd);
		}
		else if(rangeStart!=0 && Type==null && color!=null) /*search by price and color*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND (price>= ? AND price<= ? ) AND `dominantColor` = ?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setInt(2,rangeStart);
			stmt.setInt(3,rangeEnd);
			stmt.setString(4,color);
		}
		else if(rangeStart!=0 && Type!=null && color==null) /*search by type and price*/
		{
			query=msg.getQueryQuestion()+" FROM zerli.product WHERE branchName= ? AND `productType`= ? AND (price>= ? AND price<= ? );";
			stmt = con.prepareStatement(query);
			stmt.setString(1, User.currUser.getBranchName());
			stmt.setString(2,Type);
			stmt.setInt(3,rangeStart);
			stmt.setInt(4,rangeEnd);
		}
		else if(rangeStart==0 && Type==null && color==null)
		{
			emptyResFlag=false;
		}
		if(emptyResFlag)
		{
			rs = stmt.executeQuery();
			while (rs.next()) {
				productsFounded.add(new Product());
				productsFounded.get(i).setProductId(rs.getInt("productID"));
				productsFounded.get(i).setProductName(rs.getString("productName"));
				productsFounded.get(i).setProductType(rs.getString("productType"));
				productsFounded.get(i).setProductDescription(rs.getString("productDescription"));
				productsFounded.get(i).setPrice(rs.getDouble("price"));
				productsFounded.get(i).setProductColor(rs.getString("dominantColor"));
				productsFounded.get(i).setStoreName(rs.getString("BranchName"));
				if(rs.getBlob("image")!=null)
				{
					img=rs.getBlob("image");
					input=img.getBinaryStream();
					byte[] imgToConv=ImageConverter.convertInputStreamToByteArray(input);
					productsFounded.get(i).setProductImage(imgToConv);
				}
				i++;
			}
			rs.close();
			con.close();
		}
		msg.setReturnObj(productsFounded);
		client.sendToClient(msg);
	}
	/**
	 * load all products from catalog
	 * if we want to load products from specific store the put the store name in the storeName of product
	 * @param msg
	 * @param client
	 * @param conn
	 * @throws SQLException
	 * @throws IOException
	 */
	private static void loadProductsToCatalogByType(Msg msg,ConnectionToClient client, Connection conn) throws SQLException, IOException {
		ArrayList<Product> productList= new ArrayList<Product>();
		int i=0;
		Blob img;
		InputStream input=null;
		img=conn.createBlob();
		String que;
		String storeName=((Product)msg.getSentObj()).getStoreName(); 
		if(storeName.compareTo("all")!=0) /*load from specific store */
			 que=msg.getQueryQuestion()+" FROM zerli.product WHERE BranchName= '" + storeName+"';";
		else /*load from all stores*/
			que=msg.getQueryQuestion()+" FROM zerli.product;";
		Statement stmt = conn.createStatement();		
		ResultSet rs=stmt.executeQuery(que);
		while(rs.next())
		{
			productList.add(new Product());
			productList.get(i).setProductId(rs.getInt("productID"));
			productList.get(i).setProductName(rs.getString("productName"));
			productList.get(i).setProductType(rs.getString("productType"));
			productList.get(i).setProductDescription(rs.getString("productDescription"));
			productList.get(i).setPrice(rs.getDouble("price"));
			productList.get(i).setProductColor(rs.getString("dominantColor"));
			productList.get(i).setStoreName(rs.getString("BranchName"));
			if(rs.getBlob("image")!=null)
			{
				img=rs.getBlob("image");
				input=img.getBinaryStream();
				byte[] imgToConv=ImageConverter.convertInputStreamToByteArray(input);
				productList.get(i).setProductImage(imgToConv);
			}
			i++;
		}
		rs.close();
		conn.close();
		msg.setReturnObj(productList);
		client.sendToClient(msg);
	}
	/**
	 * orderHandler- sort the msg with class type "Store" to functions by the query that need to do
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException 
	 * @throws IOException 
	 */
	private void StoreHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		String queryToDo = ((Msg) msg).getqueryToDo();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkStoreExistence") == 0) {
			searchStoreInDB(msg, tableName, client, con);
		}
		else if(queryToDo.compareTo("bring all stores")==0) {
			getStoresName(requestMsg, tableName, client, con);
		}

	}
	
	/**
	 * this func send back to the user all the stores's name from DB
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 * @throws IOException
	 */
	private void getStoresName(Msg msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		ArrayList<String> storesName=new ArrayList<String>();
		String query=msg.getQueryQuestion()+" branchName From zerli."+ tableName;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			storesName.add(rs.getString("branchName"));
		}
		rs.close();
		con.close();
		msg.setReturnObj(storesName);
		client.sendToClient(msg);
	}
	/**
	 * search spesific store in DB with a search filter that we can put in ColumnToUpdate at msg
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws IOException 
	 * @throws SQLException 
	 */
	private void searchStoreInDB(Object msg, String tableName, ConnectionToClient client, Connection con) throws IOException, SQLException {
		Store tmpStore = new Store();
		Msg message = (Msg) msg;
		String Query = (message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "
				+ message.getColumnToUpdate() + "=" + "?;");
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());// get Specific Field's value
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				tmpStore.setStoreID(rs.getInt(1));
				tmpStore.setBranchName(rs.getString(2));
			}
			con.close();
			rs.close();
		((Msg) msg).setReturnObj(tmpStore);
			client.sendToClient(msg);
	}
	
	
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void ComplaintHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("insertNewComplaint") == 0) {
			InsertComplaintToDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("getAllComplaints") == 0)
		{
			GetAllComplaintsIDsFromDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("checkComplaintExistence")==0)
		{
			SelectComplaintFromDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("UpdateComplaint")==0)
		{
			UpdateComplaintFromDB(msg, tableName, client, con);
		}
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void UpdateComplaintFromDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		
		Msg message = (Msg) msg;
		Complaint complaintToUpdate=((Complaint)message.getSentObj());
		String Query=(message.getQueryQuestion() + " zerli."+tableName+" SET "+message.getColumnToUpdate()+"=?" +" WHERE complaintID=?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			stmt.setString(2,""+complaintToUpdate.getComplaintId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void SelectComplaintFromDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		Complaint tmpComplaint=new Complaint();
		Msg message = (Msg) msg;
		String Query=(message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "+ message.getColumnToUpdate() + "=" + "?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				tmpComplaint.setComplaintId(rs.getInt(1));
				tmpComplaint.setCustomerId(rs.getInt(2));
				tmpComplaint.setStoreId(rs.getInt(3));
				tmpComplaint.setComplaintDetails(rs.getString(4));
				tmpComplaint.setAssigningDate(rs.getString(5));
				tmpComplaint.setAssigningTime(rs.getString(6));
				tmpComplaint.setGotTreatment(rs.getInt(7));
				tmpComplaint.setGotRefund(rs.getInt(8));
			}
			con.close();
			rs.close();
			message.setReturnObj(tmpComplaint);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void GetAllComplaintsIDsFromDB(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		Msg message = (Msg) msg;
		ArrayList<Integer> ComplaintsIDS=new ArrayList<Integer>();
		String Query=(message.getQueryQuestion()+" FROM zerli."+tableName+";");
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while(rs.next())
			{
				if(rs.getInt(7)==0)//1 Means Complaint GotTreatment
				{
				ComplaintsIDS.add(rs.getInt(1));
				}
			}
			rs.close();
			con.close();
			message.setReturnObj((Object)ComplaintsIDS);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void customerHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkCustomerExistence") == 0) {
			searchCustomerInDB(msg, tableName, client, con);
		}
		else if (requestMsg.getqueryToDo().compareTo("Save New Customer Settlement and Member") == 0) {
			CustomerToDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("UpdateCustomerDetails") == 0)
		{
			UpdateCustomerInDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("Select DB customer") == 0) {
			searchCustomerInDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("update DB customer") == 0) {
			UpdateCustomer(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("Update Customer Settlement and Member and credit card") == 0) {
			UpdateCustomerSettlMemberCreditInDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("Update Cus") == 0) {
			UpdateCustomerInDATABASE(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("pull the balance customer") == 0) {
			searchCustomerInDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("update Balance for loading money") == 0) {
			UpdateCustomerBalanceDB(msg, tableName, client, con);
		}
		else if(requestMsg.getqueryToDo().compareTo("Upadate Customer Field") == 0)
		{
			UpdateCustomeR(msg, tableName, client, con);
		}
	}
	/**
	 * update customer field
	 * the field is selected by ColumnToUpdate in msg 
	 * and by filter of customer id
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void UpdateCustomerInDB(Object msg, String tableName, ConnectionToClient client, Connection con)
	{
		Msg message = (Msg) msg;
		Customer customerToUpdate=((Customer)message.getSentObj());
		String Query=(message.getQueryQuestion() + " zerli."+tableName+" SET "+message.getColumnToUpdate()+"=?" +" WHERE customerID=?;");
		try {
			PreparedStatement stmt=con.prepareStatement(Query);
			stmt.setString(1,message.getValueToUpdate());
			stmt.setString(2,""+customerToUpdate.getCustomerID());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * this function update double parameter in customer, like balance
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private static void UpdateCustomeR(Object msg, String tableName, ConnectionToClient client, Connection con) throws NumberFormatException, SQLException {
		Msg deatilsToUpdate=(Msg)msg;
		String query=deatilsToUpdate.getQueryQuestion()+" `zerli`."+tableName+" SET "+deatilsToUpdate.getColumnToUpdate()+"= ? WHERE customerID= ?;";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setDouble(1, Double.parseDouble(deatilsToUpdate.getValueToUpdate()));
		stmt.setInt(2, Customer.curCustomer.getCustomerID());
		stmt.executeUpdate();
		con.close();
	}
	
	
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void userHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("checkUserExistence") == 0) // If we want to check if user is exist															// e.g to logIn
			searchUserInDB(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("update user") == 0)
			updateUserDetails(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("AddNewUserToDB") == 0)
			AddNewUser(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("check User By ID Existence") == 0)
			searchUserbyID(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("UpadateUserInDB") == 0)
			UpadateUserInDB(msg, tableName, client, con);
		

	}// userHandler
	/**
	 * This method filter the questions to specific type Survey  that go to the DB
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void SurveyHandeler(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String queryToDo = ((Msg) msg).getQueryQuestion();
		Msg requestMsg = (Msg) msg;
		if (requestMsg.getqueryToDo().compareTo("SendAnswerSurveyToDB") == 0) 																		// e.g to logIn
			InsertAnswerSurveyToDB(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("select survey by numer survey") == 0)
			returnSurveyQues(msg, tableName, client, con);
		else if (requestMsg.getqueryToDo().compareTo("SendNewQuestionSurveyToDB") == 0)
			addNewSurveyToDB(msg, tableName, client, con);
		else if(requestMsg.getqueryToDo().compareTo("View satisfaction report") == 0)
			returnNumberSurveyQues(msg, tableName, client, con);
		else if(requestMsg.getqueryToDo().compareTo("get all surveys") == 0)
			GetAllSurveysQuestionsNum(msg, tableName, client, con);
		else if(requestMsg.getqueryToDo().compareTo("InsertExpertConclusions") == 0)
			InsertExpertConclusion(msg, "expertconclusions", client, con);
			
		
	}// SurveyHandler

	
	/**
	 * Method that insert the text of conclusion Expert
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void InsertExpertConclusion(Object msg, String tableName, ConnectionToClient client,
			Connection con) {
		
		Msg message = (Msg) msg;
		int surveyNum=Integer.parseInt(message.getValueToUpdate());
		String Query=message.getQueryQuestion()+" zerli."+tableName+"(conclusionTxt,numSurvey) Values(?,?);";
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setString(1,(String)message.getSentObj());
			stmt.setInt(2,surveyNum);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void searchCustomerInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Customer tmpCustomer = new Customer();
		Msg message = (Msg) msg;
		String Query = (message.getQueryQuestion() + " FROM  zerli." + tableName + " Where "
				+ message.getColumnToUpdate() + "=" + "?;");
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setString(1, message.getValueToUpdate());// get Specific Field's value
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				tmpCustomer.setCustomerID(rs.getInt("customerID"));
				tmpCustomer.setUserName(rs.getString(2));
				tmpCustomer.setIsSettlement(rs.getInt(3));
				tmpCustomer.setIsMember(rs.getInt(4));
				tmpCustomer.setBalance(rs.getDouble("balance"));
				tmpCustomer.setCreditCard(rs.getString("creditCardNumber"));
				tmpCustomer.setTypeMember(rs.getString("typeMember"));
				tmpCustomer.setExpDate(rs.getString("expDate"));
			}
			con.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Msg) msg).setReturnObj(tmpCustomer);

		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * this method are update user details on the DB 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void UpadateUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;

		try {
			PreparedStatement stmt = con
					.prepareStatement(message.getQueryQuestion() + " zerli." + tableName + "\nSET " + "Password = '"
							+ userToUpdate.getPassword() + "' , UserID = '" + userToUpdate.getID() + "' , FirstName = '"
							+ userToUpdate.getFirstName() + "' , LastName = '" + userToUpdate.getLastName()
							+ "' , ConnectionStatus = '" + userToUpdate.getConnectionStatus() + "' , Phone = '"
							+ userToUpdate.getPhone() + "' , Gender = '" + userToUpdate.getGender() + "' , Email = '"
							+ userToUpdate.getEmail() + "' , branchName = '"+userToUpdate.getBranchName() + "'\nWHERE UserName= '" + userToUpdate.getUserName() + "';");

			stmt.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void searchUserbyID(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User toSearch = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message.getQueryQuestion() + " FROM " + " zerli." + tableName
					+ " WHERE UserID='" + toSearch.getID() + "';");
			if (rs.next()) {
				toSearch.setUserName(rs.getString(1));// Set user name for returned object
				toSearch.setPassword(rs.getString(2));// Set Password for returned object
				toSearch.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
				toSearch.setFirstName(rs.getString(4));// Set FirstName for returned object
				toSearch.setLastName(rs.getString(5));// Set tLastName for returned object
				toSearch.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
				toSearch.setUserType(rs.getString(7));// Set UserType for returned object
				toSearch.setPhone(rs.getString(8));// Set Phone for returned object
				toSearch.setGender(rs.getString(9));// Set Gender for returned object
				toSearch.setEmail(rs.getString(10));// Set Email for returned object
				toSearch.setBranchName(rs.getString(11));
			}
			con.close();

			((Msg) msg).setReturnObj(toSearch);

			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void updateUserDetails(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
			PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+message.getColumnToUpdate()+"= ? WHERE UserName='"+userToUpdate.getUserName()+"'");
			stmt.setString(1, message.getValueToUpdate());
			stmt.executeUpdate();
			con.close();
	}
	/**
	 * This method search User in DB, sanding User Name and password and get all information from table "User" in DB 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void searchUserInDB(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException, IOException {
		User toSearch = (User) (((Msg) msg).getSentObj());
		User tmpUsr = new User();
			Statement stmt = con.createStatement();
			// Case 1: Username and Password Correct

			ResultSet rs = stmt.executeQuery(((Msg) msg).getQueryQuestion() + " FROM " + tableName + " WHERE UserName='"
					+ toSearch.getUserName() + "' AND Password='" + toSearch.getPassword() + "';");
			if (rs.next()) {
				tmpUsr.setUserName(rs.getString(1));// Set user name for returned object
				tmpUsr.setPassword(rs.getString(2));// Set Password for returned object
				tmpUsr.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
				tmpUsr.setFirstName(rs.getString(4));// Set FirstName for returned object
				tmpUsr.setLastName(rs.getString(5));// Set tLastName for returned object
				tmpUsr.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
				tmpUsr.setUserType(rs.getString(7));// Set UserType for returned object
				tmpUsr.setPhone(rs.getString(8));// Set Phone for returned object
				tmpUsr.setGender(rs.getString(9));// Set Gender for returned object
				tmpUsr.setEmail(rs.getString(10));// Set Email for returned object
				tmpUsr.setBranchName(rs.getString(11));
			}
			// Case 2 :UserName Correct But Password Wrong
			else if (rs.next() == false) {
				rs = stmt.executeQuery(((Msg) msg).getQueryQuestion() + " FROM " + tableName + " WHERE UserName='"
						+ toSearch.getUserName() + "';");
				if (rs.next()) {
					tmpUsr.setUserName(rs.getString(1));// Set user name for returned object
					tmpUsr.setPassword(rs.getString(2));// Set Password for returned object
					tmpUsr.setID(Integer.parseInt(rs.getString(3)));// Set ID for returned object
					tmpUsr.setFirstName(rs.getString(4));// Set FirstName for returned object
					tmpUsr.setLastName(rs.getString(5));// Set tLastName for returned object
					tmpUsr.setConnectionStatus(rs.getString(6));// Set ConnectionStatus for returned object
					tmpUsr.setUserType(rs.getString(7));// Set UserType for returned object
					tmpUsr.setPhone(rs.getString(8));// Set Phone for returned object
					tmpUsr.setGender(rs.getString(9));// Set Gender for returned object
					tmpUsr.setEmail(rs.getString(10));// Set Email for returned object
					tmpUsr.setBranchName(rs.getString(11));
				}

			}
			rs.close();
			con.close();
			((Msg) msg).setReturnObj(tmpUsr);
			client.sendToClient(msg);
	}


	/**
	 * this method adding the customer details to the DB 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void CustomerToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Customer CustomerDB = (Customer) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		String sql= message.getQueryQuestion() + " zerli." + tableName + " ("
				+ "customerID ,UserName ,isSettlement , isMember, creditCardNumber ,balance ,typeMember ,expDate)" + "\nVALUES " + "('"
				+ CustomerDB.getCustomerID() + "','" + CustomerDB.getUserName() + "','"
				+ CustomerDB.getIsSettlement() + "','" + CustomerDB.getIsMember() + "','" +CustomerDB.getCreditCard()+ "','"+CustomerDB.getBalance()+
				"','"+CustomerDB.getTypeMember() +"','"+CustomerDB.getExpDate()+ "');" ;
		try {

			Statement stmt=con.createStatement();
			
			stmt.executeUpdate(sql);
					
			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Methods that insert answer survey to DB, sending the number survey question and the answers to the questions 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void InsertAnswerSurveyToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Survey surveyDB = (Survey) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {
			
			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " zerli." + tableName + " ("
					+ "numSurvey ,answer1 ,answer2 ,answer3 ,answer4 ,answer5 ,answer6, date )" + "\nVALUES " + "('"
					+ surveyDB.getNumSurvey() + "','" + surveyDB.getAnswer1() + "','" + surveyDB.getAnswer2() + "','"
					+ surveyDB.getAnswer3() + "','" + surveyDB.getAnswer4() + "','" + surveyDB.getAnswer5() + "','"
					+ surveyDB.getAnswer6() +"','" +surveyDB.getDate() + "');");

			stmt.executeUpdate();

			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}


/**
 * this method are adding new user to DB 
 * @param msg
 * @param tableName
 * @param client
 * @param con
 */
	public static void AddNewUser(Object msg, String tableName, ConnectionToClient client, Connection con) {
		User NewUserToAdd = (User) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {

			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " " + tableName + " ("
					+ "UserName ,Password ,UserID ,FirstName ,LastName ,ConnectionStatus ,Permission ,Phone ,Gender ,Email,branchName)"
					+ "\nVALUES " + "('" + NewUserToAdd.getUserName() + "','" + NewUserToAdd.getPassword() + "','"
					+ NewUserToAdd.getID() + "','" + NewUserToAdd.getFirstName() + "','" + NewUserToAdd.getLastName()
					+ "','" + NewUserToAdd.getConnectionStatus() + "','" + NewUserToAdd.getUserType() + "','"
					+ NewUserToAdd.getPhone() + "','" + NewUserToAdd.getGender() + "','" + NewUserToAdd.getEmail() + "','" + NewUserToAdd.getBranchName()
					+ "');");
			stmt.executeUpdate();
			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}
	/**
	 * This function execute SQL query that get the orders detailes according to specific date.
	 * @param msg
	 * @param con
	 * @param client
	 * @throws SQLException
	 * @throws IOException
	 */
	public void get_order_report(Msg msg, Connection con, ConnectionToClient client) throws SQLException, IOException {
		TreeMap<String, String> directory = new TreeMap<String, String>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());

			while (rs.next()) {
				directory.put(rs.getString(1), rs.getString(2));
			}

			rs.close();
				client.sendToClient(directory);

			con.close();
	}
	/**
	 * This function execute SQL query that get the survey detailes.
	 * @param msg
	 * @param con
	 * @param client
	 */
	public void get_order_survey_report(Msg msg, Connection con, ConnectionToClient client) {
		Msg message= (Msg)msg;
		TreeMap<String, String> directory = new TreeMap<String, String>();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());

			Integer i = 1;
			rs.next();
			while (i < 7) {
				directory.put(i.toString(), rs.getString(i));
				i += 1;
			}
			
			rs.close();
			message.setReturnObj(directory);
			try {
				client.sendToClient(message);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * This function execute SQL query that get all the customer orders.
	 * @param msg
	 * @param con
	 * @param client
	 */
	public void get_consumer_order(Msg msg, Connection con, ConnectionToClient client) {
		System.out.println("great" + msg.getQueryQuestion());
		TreeMap<String, String> directory = new TreeMap<String, String>();
		Msg message = (Msg) msg;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(msg.getQueryQuestion());
			while (rs.next()) {
				String order="Order ID- "+rs.getString(1)+" , price: "+rs.getString(2)+" , date: "+rs.getString(3);
				System.out.println(order);
				directory.put(order,rs.getString(1));
			}
			rs.close();
			System.out.println(directory);
			message.setReturnObj(directory);
			try {
				client.sendToClient(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * this method return the survey question to store employee
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void returnSurveyQues(Object msg, String tableName, ConnectionToClient client, Connection con) {
		String NumSurvey = (String) (((Msg) msg).getSentObj());
		Survey surveyques = new Survey();
		Msg message = (Msg) msg;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message.getQueryQuestion() + "  FROM  zerli." + tableName
					+ " \nWHERE numSurvey= '" + NumSurvey + "';");

			if (rs.next()) {
				surveyques.setNumSurvey(rs.getInt(1));  //Set number survey for returned object
				surveyques.setQuestion1(rs.getString(2)); //Set question 1 for returned object
				surveyques.setQuestion2(rs.getString(3));//Set question 2 for returned object
				surveyques.setQuestion3(rs.getString(4));//Set question 3 for returned object
				surveyques.setQuestion4(rs.getString(5));//Set question 4 for returned object
				surveyques.setQuestion5(rs.getString(6));//Set question 5 for returned object
				surveyques.setQuestion6(rs.getString(7));//Set question 6 for returned object
			}

			rs.close();
			try {
				((Msg) msg).setReturnObj(surveyques);
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Method that add new survey to DB, survey that include 6 questions
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void addNewSurveyToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Survey NewSurveyToAdd = (Survey) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		try {

			PreparedStatement stmt = con.prepareStatement(message.getQueryQuestion() + " " + tableName + " ("
					+ "question1 ,question2 ,question3 ,question4 ,question5 ,question6 )" + "\nVALUES " + "('"
					+ NewSurveyToAdd.getQuestion1() + "','" + NewSurveyToAdd.getQuestion2() + "','"
					+ NewSurveyToAdd.getQuestion3() + "','" + NewSurveyToAdd.getQuestion4() + "','"
					+ NewSurveyToAdd.getQuestion5() + "','" + NewSurveyToAdd.getQuestion6() + "');");
			stmt.executeUpdate();
			con.close();
			try {
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void InsertComplaintToDB(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Complaint userToUpdate = (Complaint) (((Msg) msg).getSentObj());
		Msg message = (Msg) msg;
		
		
		String Query = message.getQueryQuestion() + " " + tableName+" (customerID , storeID,complaintDetails ,assigningDate ,assigningTime,gotTreatment,gotRefund)" 
		+ " VALUES (?,?,?,?,?,?,?);";
		
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setInt(1, userToUpdate.getCustomerId());
			stmt.setInt(2, userToUpdate.getStoreId());
			stmt.setString(3, userToUpdate.getComplaintDetails());
			stmt.setString(4, userToUpdate.getAssigningDate());
			stmt.setString(5, userToUpdate.getAssigningTime());
			stmt.setInt(6, userToUpdate.getGotTreatment());
			stmt.setInt(7, userToUpdate.getGotRefund());
			stmt.executeUpdate();

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * this method update customer details by user name 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void UpdateCustomer(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		User userToUpdate = (User) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
			PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+message.getColumnToUpdate()+"= ? WHERE UserName='"+userToUpdate.getUserName()+"'");
			stmt.setString(1, message.getValueToUpdate());
			stmt.executeUpdate();
			con.close();
	}
	/**
	 * this method update customer details - > if is account settlement and if he is a member 
	 * and type member and expiry date of member
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void UpdateCustomerSettlMemberCreditInDB(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Customer CustomerToUpdate = (Customer) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
		PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+"isSettlement= " + CustomerToUpdate.getIsSettlement() + ",isMember= " +CustomerToUpdate.getIsMember() + ",creditCardNumber= '"
		+CustomerToUpdate.getCreditCard() +"'"+ ",balance= " +CustomerToUpdate.getBalance()+ ",typeMember= '" +CustomerToUpdate.getTypeMember()+"',expDate = '"+CustomerToUpdate.getExpDate()+ "' WHERE customerID="+CustomerToUpdate.getCustomerID()+";");

		stmt.executeUpdate();
		con.close();
	}
	
	private static void UpdateCustomerInDATABASE(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Customer CustomerToUpdate = (Customer) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
		PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+"isSettlement= " + CustomerToUpdate.getIsSettlement() + ",isMember= " +CustomerToUpdate.getIsMember() + ",creditCardNumber= '"+CustomerToUpdate.getCreditCard() +"'"
				+ " WHERE customerID="+CustomerToUpdate.getCustomerID()+";");
		stmt.executeUpdate();
		con.close();
	}

	
	
	/**
	 * 
	 * @param msg
	 * @param con
	 * @param client
	 */
	public void cancel_order(Msg msg, Connection con, ConnectionToClient client) {
		ArrayList<String> dir_result=new ArrayList <String>();
		try {
			String cmd2="SELECT orderId, orderPrice,Date,orderTime , current_date()<Date as t1,current_date()=Date and orderTime-current_time()>30000 as t2,"
				+ "current_date()=Date and orderTime-current_time() between 10000 and 30000 as t3, current_date()=Date and orderTime-current_time() between 0 and 10000 as t4,"
				+" current_time()-ordertime from zerli.`order` where orderID = ";
			cmd2+=msg.getqueryToDo()+" ;";


			
			System.out.println(cmd2);


			Statement stmt1 = con.createStatement();
				ResultSet rs = stmt1.executeQuery(cmd2);
				while (rs.next()) {
					dir_result.add(rs.getString(1));
					dir_result.add(rs.getString(2));
					dir_result.add(rs.getString(5));
					dir_result.add(rs.getString(6));
					dir_result.add(rs.getString(7));
					dir_result.add(rs.getString(8));
					System.out.println(dir_result);
				}
				System.out.println(" debug= "+dir_result);
				float refund=0;
				if (dir_result.get(2).equals("1"))refund=1;
				if (dir_result.get(3).equals("1"))refund=1;
				if (dir_result.get(4).equals("1"))refund=(float) 0.5;
				if (dir_result.get(5).equals("1"))refund=0;
				System.out.println(" dir_result.get(4)= "+dir_result.get(2)+dir_result.get(3)+dir_result.get(4)+dir_result.get(5));
				System.out.println(" refund= "+refund);

				try {
					client.sendToClient(dir_result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String cmd_refund= "update zerli.customer c,zerli.order o set c.balance=c.balance+ o.orderPrice*"
						+ refund+"  where c.customerID='"+msg.getColumnToUpdate()+"' and o.orderId ="+msg.getqueryToDo()+";";
				System.out.println(cmd_refund);
				Statement stmt3 = con.createStatement();
				int returnValue2 = stmt3.executeUpdate(cmd_refund);
				
				
				
				System.out.println("great" + msg.getQueryQuestion());
				Statement stmt = con.createStatement();
				int returnValue1 = stmt.executeUpdate(msg.getQueryQuestion());
				rs.close();

				con.close();

				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * this method update customer balance on the customer table 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 * @throws SQLException
	 */
	private static void UpdateCustomerBalanceDB(Object msg, String tableName, ConnectionToClient client, Connection con) throws SQLException {
		Customer customerToUpdate = (Customer) (((Msg) msg).getSentObj());
		Msg message=(Msg)msg;
		PreparedStatement stmt=con.prepareStatement(message.getQueryQuestion()+" zerli."+tableName+" Set "+message.getColumnToUpdate()+"= ? WHERE UserName='"+customerToUpdate.getUserName()+"'");
		stmt.setString(1, message.getValueToUpdate());
		stmt.executeUpdate();
		con.close();
	}
	/**
	 * 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	public static void returnNumberSurveyQues(Object msg, String tableName, ConnectionToClient client, Connection con) {
		Msg message = (Msg) msg;
		ArrayList<Integer> directory = new ArrayList<Integer>();
		try {
			int i=1;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(message.getQueryQuestion() + " numSurvey FROM  zerli." + tableName+ "';");
			while (rs.next()) {
				directory.add(rs.getInt(i));
				i++;
			}

			rs.close();
			try {
				((Msg) msg).setReturnObj(directory);
				client.sendToClient(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method get all surveys questions number from table "survey_question" 
	 * @param msg
	 * @param tableName
	 * @param client
	 * @param con
	 */
	private static void GetAllSurveysQuestionsNum(Object msg, String tableName, ConnectionToClient client, Connection con) 
	{
		Msg message = (Msg) msg;
		ArrayList<Integer> SurveysQuestionsNum=new ArrayList<Integer>();
		String Query=(message.getQueryQuestion()+" FROM zerli."+tableName+";");
		System.out.println(Query);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Query);
			while(rs.next())
			{
				SurveysQuestionsNum.add(rs.getInt(1));
			}
			rs.close();
			con.close();
			message.setReturnObj((Object)SurveysQuestionsNum);
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
