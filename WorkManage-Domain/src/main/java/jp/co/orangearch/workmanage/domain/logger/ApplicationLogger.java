package jp.co.orangearch.workmanage.domain.logger;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.orangearch.workmanage.domain.constant.LogFileMarker;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.logger.MessageHandler.MessageInfo;

/**
 * アプリケーションログを出力するクラスです。<br>
 * 各機能のログを出力する際は、本クラスを使用してログ出力を行って下さい。
 * @author t-otsuka
 *
 */
@Component
public class ApplicationLogger {
	/** ロガー。 */
	private static final Logger log = LoggerFactory.getLogger(LogFileMarker.APP); 

	/** メッセージハンドラー。 */
	@Autowired
	private MessageHandler messageHandler;

	/**
	 * ログ出力を行います。
	 * @param messageId メッセージID
	 */
	public void log(MessageId messageId){
		log(messageId, null, null, null);
	}

	/**
	 * ログ出力を行います。
	 * @param messageId メッセージID
	 * @param fillchar 埋め字
	 */
	public void log(MessageId messageId, String[] fillchar){
		log(messageId, fillchar, null, null);
	}

	/**
	 * ログ出力を行います。
	 * @param messageId メッセージID
	 * @param fillchar 埋め字
	 * @param defaultMessage デフォルトメッセージ
	 */
	public void log(MessageId messageId, String[] fillchar, String defaultMessage){
		log(messageId, fillchar, defaultMessage, null);
	}
	
	/**
	 * ログ出力を行います。
	 * @param messageId メッセージID
	 * @param fillchar 埋め字
	 * @param defaultMessage デフォルトメッセージ
	 * @param e 例外情報
	 */
	public void log(MessageId messageId, String[] fillchar, String defaultMessage, Exception e){
		MessageInfo messageInfo = messageHandler.getMessage(messageId, fillchar, defaultMessage, e);
		MDC.put("LEVEL", messageInfo.getLevel());
		MDC.put("MESSAGE_ID", messageId);
		log.info(messageInfo.getMessage(), e);
	}
	
}
