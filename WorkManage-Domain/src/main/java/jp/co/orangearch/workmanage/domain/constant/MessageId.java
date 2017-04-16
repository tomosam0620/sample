package jp.co.orangearch.workmanage.domain.constant;

/**
 * メッセージIDを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
public enum MessageId implements Values{
	/** yyyy-MM-ddで入力してください。 */
	V001("V001"),
	/** HH:mmで入力してください。 */
	V002("V002"),
	/** yyyy/MM/ddで入力してください。 */
	V003("V003"),
	/** {0}で入力してください。 */
	V004("V004"),
	/** ERROR|不正な操作です。 */
	M001("M001"),
	/** WORN|既に更新されています。 */
	M002("M002"),
	/** DEBUG|アップロードファイル：{0} サイズ：{1}。 */
	M003("M003"),
	/** INFO|登録しました。 */
	M004("M004"),
	/** M005=INFO|パスワード有効期限を過ぎています。パスワードを変更してください。 */
	M005("M005"),
	/** ERROR|{0}が失敗しました。 */
	S001("S001"),
	/** ERROR|{0}を{1}に変換できませんでした。 */
	S002("S002"),
	/** ERROR|予期せぬエラーが発生しました。 */
	S003("S003");

	/** 値 */
	private String value;

	/** コンストラクタ。 */
	MessageId(String value){
		this.value = value;
	}

	/** 値取得。 */
	public String getValue(){
		return value;
	}

	/** 値取得。 */
	@Override
	public String getKey(){
		return value;
	}

	@Override
	public String getText() {
		return value;
	}
}
