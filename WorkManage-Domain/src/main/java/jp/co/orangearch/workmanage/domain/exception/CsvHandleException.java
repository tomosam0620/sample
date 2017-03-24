package jp.co.orangearch.workmanage.domain.exception;

import java.util.ArrayList;
import java.util.List;

public class CsvHandleException extends Exception {

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;

	private List<String> errors = new ArrayList<String>();
	
	public CsvHandleException(){
		super();
	}
	
	public CsvHandleException(List<String> errors){
		super();
		this.errors = errors;
	}
	
	public CsvHandleException(List<String> errors, String message){
		super(message);
		this.errors = errors;
	}
	
	public CsvHandleException(List<String> errors, String message, Throwable e){
		super(message, e);
		this.errors = errors;
	}
	
	public List<String> getErrors(){
		return errors;
	}
	
}
