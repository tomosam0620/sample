package jp.co.orangearch.workmanage.common.exception;

/**
 * 業務例外クラスです。
 * <br>
 * 業務例外の場合本クラスをスローしてください。
 *
 * @author t-otsuka
 *
 */
public class BussinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageId;
	private String[] fillChars;

	@Deprecated
	public BussinessException(){
	}

	@Deprecated
	public BussinessException(String message){
		super(message);
	}

	public BussinessException(String messageId, String[] fillchars){
		super();
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(String messageId, String[] fillchars, String message){
		super(message);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(String messageId, String[] fillchars, Throwable exception){
		super(exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(String messageId, String[] fillchars, String message, Throwable exception){
		super(message, exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public String getMessageId(){
		return messageId;
	}

	public String[] getFillChars(){
		return fillChars;
	}
}
