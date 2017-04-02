package jp.co.orangearch.workmanage.form.workTime;

import java.io.Serializable;

public class SelectMonthForm implements Serializable{
	private static final long serialVersionUID = 1L;

	private String month;

	public SelectMonthForm(){
		
	}
	
	public SelectMonthForm(String value){
		month = value;
	}
	
	public String getMonth(){
		return month;
	}
	public void setMonth(String value){
		month = value;
	}

}
