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
	/** パスワードが正しくありません。 */
	V005("V005"),
	/** 新しいパスワードと確認パスワードが異なります。 */
	V006("V006"),
	/** パスワードと新しいパスワードは違う値にしてください。 */
	V007("V007"),
	/** 休日に{1}は選択できません。 */
	V008("V008"),
	/** 営業日に{1}は選択できません。 */
	V009("V009"),
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
	/** WORN|確定済みのため更新できません。 */
	M006("M006"),
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
