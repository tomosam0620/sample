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

	/** シリアルバージョン。 */
	private static final long serialVersionUID = 1L;
	/** メッセージID。 */
	private MessageId messageId;
	/** 埋め字。 */
	private String[] fillChars;

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
		super();
		this.messageId = messageId;
	}

	/**
	 * コンストラクタ
	 * @param messageId メッセージID
	 * @param fillchars 埋め字
	 */
	public SystemException(MessageId messageId, String[] fillchars){
		super();
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
		super(message);
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
		super(exception);
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
		super(exception);
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
		super(message, exception);
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
