package common;

import java.io.Serializable;

public class Msg implements Serializable {
	/*class att. :*/
	
	private Object sentObj; /*The object holds the kind of object that we want to send to server*/
	private Object returnObj; /*this obj is the obj that we are receive after the query*/
	private String queryQuestion; /*The query that we send to server(DB)*/
	private boolean queryExist=false; /*flag to check if there is any query msg must have a query before we send her*/
	private boolean queryAns; /*will check id there is some answer from DB*/
	private String ClassType;
	private String queryNeedTo;
	/*Constants word at any query that we will use */
	final public static String qSELECTALL = "SELECT *";
	final static public String qSELECT="SELECT";
	final static public String qUPDATE="UPDATE";
	final static public String qINSERT="INSERT TO";
	/*final public static String qSET = " SET ";
	final public static String qFROM = " FROM ";
	final public static String qWHERE = " WHERE ";
	final public static String qVALUES =" VALUES "; */
	
	/*constructor*/
	public Msg(String queryQ,String query) 
	{
		sentObj=null;
		returnObj=null;
		queryQuestion=queryQ;
		if(this.queryQuestion!=null) /*we cant create a Msg without question*/
		{
			this.queryExist=true;
		}
		this.queryAns=false; /*init to false while the Msg even did not sent to any server*/
		queryNeedTo=query;
	}
	public Msg()
	{
		sentObj=null;
		returnObj=null;
		this.queryAns=false;
	}

	public String getQueryNeedTo() {
		return queryNeedTo;
	}
	public void setQueryNeedTo(String queryNeedTo) {
		this.queryNeedTo = queryNeedTo;
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

	public boolean isQueryExist() {
		return queryExist;
	}

	public void setQueryExist(boolean queryExist) {
		this.queryExist = queryExist;
	}

	public boolean isQueryAns() {
		return queryAns;
	}

	public void setQueryAns(boolean queryAns) {
		this.queryAns = queryAns;
	}
	public String getClassType()
	{
		return this.ClassType;
	}
	public void setClassType(String cl)
	{
		this.ClassType=cl;
	}
	
	
}
