package jp.co.orangearch.workmanage.domain.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler;

/**
 * システム例外クラスです。
 * <br>
 * システム例外の場合本クラスをスローしてください。
 *
 * @author t-otsuka
 *
 */
@Component
public class SystemException extends RuntimeException {

	/** シリアルバージョン。 */
	private static final long serialVersionUID = 1L;
	/** メッセージID。 */
	private MessageId messageId;
	/** 埋め字。 */
	private String[] fillChars;

	/** メッセージ管理クラス。 */
	private static MessageHandler messageHandler;
	
	@Autowired
	public void setMessageHandler(MessageHandler messageHandler){
		SystemException.messageHandler = messageHandler;
	}
	
	/**
	 * コンストラクタ。
	 * @note メッセージIDを指定したコンストラクタを使用してください。
	 */
	@Deprecated
	public SystemException(){
	}

	/**
	 * コンストラクタ。
	 * @note メッセージIDを指定したコンストラクタを使用してください。
	 * @param message メッセージ
	 */
	@Deprecated
	public SystemException(String message){
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param messageId メッセージID
	 */
	public SystemException(MessageId messageId){
		super(messageHandler.getMessage(messageId, null, null, null).getMessage());
		this.messageId = messageId;
	}

	/**
	 * コンストラクタ
	 * @param messageId メッセージID
	 * @param fillchars 埋め字
	 */
	public SystemException(MessageId messageId, String[] fillchars){
		super(messageHandler.getMessage(messageId, fillchars, null, null).getMessage());
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	/**
	 * コンストラクタ
	 * @param messageId メッセージID
	 * @param fillchars 埋め字
	 * @param message デフォルトメッセージ
	 */
	public SystemException(MessageId messageId, String[] fillchars, String message){
		super(StringUtils.isEmpty(message)
				? messageHandler.getMessage(messageId, fillchars, message, null).getMessage()
				: message);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param exception 例外情報
	 */
	public SystemException(MessageId messageId, Throwable exception){
		super(messageHandler.getMessage(messageId, null, null, null).getMessage(), exception);
		this.messageId = messageId;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param fillchars 埋め字
	 * @param exception 例外情報
	 */
	public SystemException(MessageId messageId, String[] fillchars, Throwable exception){
		super(messageHandler.getMessage(messageId, fillchars, null, null).getMessage(), exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param fillchars 埋め字
	 * @param message デフォルトメッセージ
	 * @param exception 例外情報
	 */
	public SystemException(MessageId messageId, String[] fillchars, String message, Throwable exception){
		super(StringUtils.isEmpty(message)
				? messageHandler.getMessage(messageId, fillchars, message, null).getMessage()
				: message,
				exception);
		this.messageId = messageId;
		this.fillChars = fillchars;
	}

	/**
	 * メッセージID取得
	 * @return メッセージID
	 */
	public MessageId getMessageId(){
		return messageId;
	}

	/**
	 * 埋め字取得
	 * @return 埋め時
	 */
	public String[] getFillChars(){
		return fillChars;
	}
}
