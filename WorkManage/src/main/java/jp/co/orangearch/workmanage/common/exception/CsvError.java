package jp.co.orangearch.workmanage.common.exception;

public class CsvError{
	public int lineNum;
	public String message;
	
	public CsvError(int num, String msg){
		lineNum = num;
		message = msg;
	}
}
