package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 絞めの状態コードを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum ClosingState implements Values{
	/** オープン。 */
	OPEN(1),
	/** 入力確定、締め処理中。 */
	CLOSING(2),
	/** 確定済み。 */
	CLOSED(3);


	/** 値 */
	private Integer value;

	/** コンストラクタ。 */
	ClosingState(Integer value){
		this.value = value;
	}

	/** 値取得。 */
	public Integer getValue(){
		return value;
	}

	/** 値取得。 */
	@Override
	public String getKey(){
		return String.valueOf(value);
	}

	/** テキスト取得。 */
	@Override
	public String getText(){
		return String.valueOf(value);
	}

	/**
	 * domain用ファクトリメソッド。
	 * valueからインスタンスを返します。
	 * @param value 値
	 * @return ClosingState
	 */
	public static ClosingState of(Integer value){
		 for(ClosingState item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
