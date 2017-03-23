package jp.co.orangearch.workmanage.domain.exception;

import jp.co.orangearch.workmanage.domain.constant.MessageId;

/**
 * システム例外クラスです。
 * <br>
 * システム例外の場合本クラスをスローしてください。
 *
 * @author t-otsuka
 *
 */
public class SystemException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MessageId messageId;
	private String[] fillChars;

	@Deprecated
	public SystemException(){
	}

	@Deprecated
	public SystemException(String message){
		super(message);
	}

	public SystemException(MessageId messageId){
		super();
		this.messageId = messageId;
	}

	public SystemException(MessageId messageId, String[] fillchars){
		super();
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public SystemException(MessageId messageId, String[] fillchars, String message){
		super(message);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public SystemException(MessageId messageId, Throwable exception){
		super(exception);
		this.messageId = messageId;
	}

	public SystemException(MessageId messageId, String[] fillchars, Throwable exception){
		super(exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public SystemException(MessageId messageId, String[] fillchars, String message, Throwable exception){
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
