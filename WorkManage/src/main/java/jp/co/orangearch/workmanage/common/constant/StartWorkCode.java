package jp.co.orangearch.workmanage.common.constant;

import org.seasar.doma.Domain;

/**
 * 始業コードを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum StartWorkCode implements Values{
	/** なし。 */
	NOMAL(1,""),
	/** 遅刻1(交通機関の遅れ)。 */
	LATE1(1,"遅刻1(交通機関の遅れ)"),
	/** 遅刻2。 */
	LATE2(2,"遅刻2");


	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	StartWorkCode(Integer value, String text){
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
	public static StartWorkCode of(Integer value){
		 for(StartWorkCode item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
