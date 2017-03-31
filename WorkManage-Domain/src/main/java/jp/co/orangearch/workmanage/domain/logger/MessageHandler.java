package jp.co.orangearch.workmanage.domain.logger;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.exception.SystemException;

@Component
public class MessageHandler {
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * メッセージを取得します。<br>
	 * プロパティファイルからメッセージを取得します。<br>
	 * メッセージ文言から、ログレベルを切り離してメッセージ文言を返却します。
	 * 
	 * @param messageId メッセージID
	 * @param fillchar 埋め字
	 * @param defaultMessage デフォルトメッセージ
	 * @param e 例外情報
	 * @return メッセージ
	 * @exception SystemException メッセージプロパティから該当メッセージが取得できない場合。または、取得したメッセージにログレベルの項目が存在していない場合。
	 */
	public MessageInfo getMessage(MessageId messageId, String[] fillchar, String defaultMessage, Exception e){
		String msg = defaultMessage;
		String level = "ERROR";
		if(StringUtils.isEmpty(defaultMessage)){
			msg = messageSource.getMessage(messageId.getValue(), fillchar, defaultMessage, Locale.getDefault());
			if(StringUtils.isEmpty(msg)){
				throw new SystemException(MessageId.S003, e);
			}
	
			String[] messages = msg.split("\\|");
			if(messages.length < 2){
				throw new SystemException(MessageId.S003, e);
			}
			level = messages[0];
			msg = messages[1];
		}

		MessageInfo messageInfo = new MessageInfo(level, msg);
		return messageInfo;
	}

	public class MessageInfo{
		private String level;
		private String message;
		
		public MessageInfo(String level, String message){
			this.level = level;
			this.message = message;
		}
		
		public void setLevel(String value){
			level = value;
		}
		
		public String getLevel(){
			return level;
		}
		
		public void setMessage(String value){
			message = value;
		}
		public String getMessage(){
			return message;
		}
	}
}
