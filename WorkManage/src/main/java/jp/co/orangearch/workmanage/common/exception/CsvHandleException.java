package jp.co.orangearch.workmanage.common.exception;

import java.util.ArrayList;
import java.util.List;

public class CsvHandleException extends RuntimeException {

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;

	private List<CsvError> errors = new ArrayList<CsvError>();
	
	public CsvHandleException(){
		super();
	}
	
	public CsvHandleException(List<CsvError> errors){
		super();
		this.errors = errors;
	}
	
	public CsvHandleException(List<CsvError> errors, String message){
		super(message);
		this.errors = errors;
	}
	
	public CsvHandleException(List<CsvError> errors, String message, Throwable e){
		super(message, e);
		this.errors = errors;
	}
	
	public List<CsvError> getErrors(){
		return errors;
	}
	
}
