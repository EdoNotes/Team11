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
package common;

import java.io.Serializable;

import Login.WelcomeController;
import client.ClientConsole;
/**
 * The Class Msg is a message box that the user
 * prepare the object he wants to send and which action
 * he want to do with the DB and insert the object he want to take handle
 * to 'sentObj' (the object in 'sentObj' must be serializable)
 * and the object that return (if there is-also must be serializable)
 * return in 'returnMsg' 
 * 
 *	@author Edo Notes
 *	@author Tomer Arzuan
 */
public class Msg implements Serializable {

	
	/**Constants word at any query that we will use */
	final public static String qSELECTALL = "SELECT *";
	final static public String qSELECT="SELECT";
	final static public String qUPDATE="UPDATE";
	final static public String qINSERT="INSERT INTO";
	
	private static final long serialVersionUID = 1L;
	private Object sentObj; /*The object holds the kind of object that we want to send to server*/
	private Object returnObj; /*this obj is the obj that we are receive after the query*/
	private String queryQuestion; /*The query that we send to server(DB)*/
	private String ClassType; //to sort the msg in echo server
	private String queryToDo; // which action we want to do 
	private String ColumnToUpdate; 
	private String ValueToUpdate; //this two variables help us to make the SQL query more generic

	/**
	 * partial constructor
	 * @param queryQ- the question of the query, can be: SELECT *, SELECT, UPDATE, INSERT, INTO
	 * @param queryTodo- what we realy want to do in DB in word
	 */
	public Msg(String queryQ,String queryTodo) 
	{
		sentObj=null;
		returnObj=null;
		queryQuestion=queryQ;
		queryToDo=queryTodo;
	}
	
	/**
	 * Empty constructor
	 * sets sentObj and returnObj to null that we can put objects inside
	 */
	public Msg()
	{
		sentObj=null;
		returnObj=null;
	}
/**
 * Getters and setters of Msg Class
 * 
 */
	public String getqueryToDo() {
		return queryToDo;
	}
	public void setqueryToDo(String queryNeedTo) {
		this.queryToDo = queryNeedTo;
	}
	public Object getSentObj() {
		return sentObj;
	}

	public void setSentObj(Object sentObj) {
		this.sentObj = sentObj;
	}
	
	public Object getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Object returnObj) {
		this.returnObj = returnObj;
	}

	public String getQueryQuestion() {
		return queryQuestion;
	}

	public void setQueryQuestion(String queryQuestion) {
		this.queryQuestion = queryQuestion;
	}

	public String getClassType()
	{
		return this.ClassType;
	}
	public void setClassType(String cl)
	{
		this.ClassType=cl;
	}
	public String getColumnToUpdate() {
		return ColumnToUpdate;
	}
	public void setColumnToUpdate(String columnToUpdate) {
		ColumnToUpdate = columnToUpdate;
	}
	public String getValueToUpdate() {
		return ValueToUpdate;
	}
	public void setValueToUpdate(String valueToUpdate) {
		ValueToUpdate = valueToUpdate;
	}
}