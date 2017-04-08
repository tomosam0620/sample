package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 始業コードを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum EndWorkCode implements Values{
	/** なし。 */
	なし(1,""),
	/** 早退。 */
	早退(2,"早退");


	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	EndWorkCode(Integer value, String text){
		this.value = value;
		this.text = text;
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
		return text;
	}

	/**
	 * domain用ファクトリメソッド。
	 * valueからインスタンスを返します。
	 * @param value 値
	 * @return AttendanceCode
	 */
	public static EndWorkCode of(Integer value){
		 for(EndWorkCode item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
