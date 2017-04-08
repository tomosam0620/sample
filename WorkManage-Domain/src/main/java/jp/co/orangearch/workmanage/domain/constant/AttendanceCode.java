package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 出勤コードを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum AttendanceCode implements Values{
	/** 出勤。 */
	出勤(1,"出勤"),
	/** 有給。 */
	有給(2,"有給"),
	/** 年特。 */
	年特(3,"年特"),
	/** 特休。 */
	特休(4,"特休"),
	/** 代休。 */
	代休(5,"代休"),
	/** 休出。 */
	休出(6,"休出"),
	/** 欠勤。 */
	欠勤(7,"欠勤"),
	/** 明け休み。 */
	明け休み(8,"明休"),
	/** 傷病。 */
	傷病(9,"傷病"),
	/** 代出。 */
	代出(10,"代出"),
	/** 午前半休。 */
	午前半休(11,"午前休"),
	/** 午後半休。 */
	午後半休(12,"午後休"),
	/** シフト。 */
	シフト(13,"シフト");


	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	AttendanceCode(Integer value, String text){
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
	public static AttendanceCode of(Integer value){
		 for(AttendanceCode item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
