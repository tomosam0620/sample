package jp.co.orangearch.workmanage.common.exception;

import jp.co.orangearch.workmanage.common.constant.MessageId;

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
	private MessageId messageId;
	private String[] fillChars;

	@Deprecated
	public BussinessException(){
	}

	@Deprecated
	public BussinessException(String message){
		super(message);
	}

	public BussinessException(MessageId messageId, String[] fillchars){
		super();
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(MessageId messageId, String[] fillchars, String message){
		super(message);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(MessageId messageId, String[] fillchars, Throwable exception){
		super(exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BussinessException(MessageId messageId, String[] fillchars, String message, Throwable exception){
		super(message, exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public MessageId getMessageId(){
		return messageId;
	}

	public String[] getFillChars(){
		return fillChars;
	}
}
