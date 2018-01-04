package Entities;

import java.io.Serializable;

public class Survey implements Serializable {
	
	private String Question1="";
	private String Question2="";
	private String Question3="";
	private String Question4="";
	private String Question5="";
	private String Question6="";
	
	private String Answer1;
	private String Answer2;
	private String Answer3;
	private String Answer4;
	private String Answer5;
	private String Answer6;
	
//	public Survey(String question1,String question2,String question3,String question4,String question5,String question6,
//			String answer1,String answer2,String answer3,String answer4,String answer5,String answer6) {
//		
//		this.Question1=question1;
//		this.Question2=question2;
//		this.Question3=question3;
//		this.Question4=question4;
//		this.Question5=question5;
//		this.Question6=question6;
//		
//		this.Answer1=answer1;
//		this.Answer2=answer2;
//		this.Answer3=answer3;
//		this.Answer4=answer4;
//		this.Answer5=answer5;
//		this.Answer6=answer6;
//		
//		
//	}
	
	public Survey() {
		// TODO Auto-generated constructor stub
	}

	public String getQuestion1() {
		return Question1;
	}

	public void setQuestion1(String question1) {
		Question1 = question1;
	}

	public String getQuestion2() {
		return Question2;
	}

	public void setQuestion2(String question2) {
		Question2 = question2;
	}

	public String getQuestion3() {
		return Question3;
	}

	public void setQuestion3(String question3) {
		Question3 = question3;
	}

	public String getQuestion4() {
		return Question4;
	}

	public void setQuestion4(String question4) {
		Question4 = question4;
	}

	public String getQuestion5() {
		return Question5;
	}

	public void setQuestion5(String question5) {
		Question5 = question5;
	}

	public String getQuestion6() {
		return Question6;
	}

	public void setQuestion6(String question6) {
		Question6 = question6;
	}

	public String getAnswer1() {
		return Answer1;
	}

	public void setAnswer1(String answer1) {
		Answer1 = answer1;
	}

	public String getAnswer2() {
		return Answer2;
	}

	public void setAnswer2(String answer2) {
		Answer2 = answer2;
	}

	public String getAnswer3() {
		return Answer3;
	}

	public void setAnswer3(String answer3) {
		Answer3 = answer3;
	}

	public String getAnswer4() {
		return Answer4;
	}

	public void setAnswer4(String answer4) {
		Answer4 = answer4;
	}

	public String getAnswer5() {
		return Answer5;
	}

	public void setAnswer5(String answer5) {
		Answer5 = answer5;
	}

	public String getAnswer6() {
		return Answer6;
	}

	public void setAnswer6(String answer6) {
		Answer6 = answer6;
	}

}
