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

public class Msg implements Serializable {
	/*class att. :*/
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Object sentObj; /*The object holds the kind of object that we want to send to server*/
	private Object returnObj; /*this obj is the obj that we are receive after the query*/
	private String queryQuestion; /*The query that we send to server(DB)*/
	private String ClassType;
	private String queryToDo;
	private String ColumnToUpdate;
	private String ValueToUpdate;
	/*Constants word at any query that we will use */
	final public static String qSELECTALL = "SELECT *";
	final static public String qSELECT="SELECT";
	final static public String qUPDATE="UPDATE";
	final static public String qINSERT="INSERT INTO";
	/*final public static String qSET = " SET ";
	final public static String qFROM = " FROM ";
	final public static String qWHERE = " WHERE ";
	final public static String qVALUES =" VALUES "; */
	
	/*constructor*/
	public Msg(String queryQ,String queryTodo) 
	{
		sentObj=null;
		returnObj=null;
		queryQuestion=queryQ;
		queryToDo=queryTodo;
	}
	public Msg()
	{
		sentObj=null;
		returnObj=null;
	}

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
