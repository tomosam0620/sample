package jp.co.orangearch.workmanage.domain.logger;


/**
 * メッセージ情報管理クラスです。
 * @author t-otsuka
 *
 */
public class MessageInfo {
	/** ログレベル */
	private String level;
	/** メッセージ */
	private String message;
	
	/**
	 * コンストラクタ
	 * @param level ログレベル
	 * @param message メッセージ
	 */
	public MessageInfo(String level, String message){
		this.level = level;
		this.message = message;
	}
	
	/**
	 * ログレベル設定
	 * @param value ログレベル
	 */
	public void setLevel(String value){
		level = value;
	}
	
	/**
	 * ログレベル取得
	 * @return ログレベル
	 */
	public String getLevel(){
		return level;
	}
	
	/**
	 * メッセージ設定
	 * @param value メッセージ
	 */
	public void setMessage(String value){
		message = value;
	}
	
	/**
	 * メッセージ取得
	 * @return メッセージ
	 */
	public String getMessage(){
		return message;
	}

}
