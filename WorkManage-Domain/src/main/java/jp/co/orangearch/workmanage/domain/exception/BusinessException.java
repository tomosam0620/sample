package jp.co.orangearch.workmanage.domain.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;

/**
 * 業務例外クラスです。
 * <br>
 * 業務例外の場合本クラスをスローしてください。
 *
 * @author t-otsuka
 *
 */
@Component
public class BusinessException extends RuntimeException {

	/** シリアルバージョン。 */
	private static final long serialVersionUID = 1L;
	/** メッセージID。 */
	private MessageId messageId;
	/** 埋め字。 */
	private String[] fillChars;

	private static MessageHandler messageHandler;
	
	@Autowired
	public void setMessageHandler(MessageHandler messageHandler){
		BusinessException.messageHandler = messageHandler;
	}
	
	@Deprecated
	public BusinessException(){
	}

	@Deprecated
	public BusinessException(String message){
		super(message);
	}

	public BusinessException(MessageId messageId, String[] fillchars){
		super(messageHandler.getMessage(messageId, fillchars, null, null).getMessage());
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BusinessException(MessageId messageId, String[] fillchars, String message){
		super(StringUtils.isEmpty(message)
				? messageHandler.getMessage(messageId, fillchars, null, null).getMessage()
				: message);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BusinessException(MessageId messageId, String[] fillchars, Throwable exception){
		super(messageHandler.getMessage(messageId, fillchars, null, null).getMessage(), exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	public BusinessException(MessageId messageId, String[] fillchars, String message, Throwable exception){
		super(StringUtils.isEmpty(message)
				? messageHandler.getMessage(messageId, fillchars, null, null).getMessage()
				: message,
				exception);
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
