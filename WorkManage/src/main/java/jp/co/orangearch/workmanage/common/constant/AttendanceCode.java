package jp.co.orangearch.workmanage.common.constant;

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
	ATTENDANCE(1,""),
	/** 有給。 */
	PAID_VACATION(2,"有給"),
	/** 年特。 */
	SPECIAL_VACATION_OF_YEAR(3,"年特"),
	/** 特休。 */
	SPECIAL_VACATION(4,"特休"),
	/** 代休。 */
	COMPENSATORY_DAY_OFF(5,"代休"),
	/** 休出。 */
	WORKING_DAY_OFF(6,"休出"),
	/** 欠勤。 */
	ABSENCE(7,"欠勤"),
	/** 明け休み。 */
	AFTER_NIGHT_SHIFT(8,"明休"),
	/** 傷病。 */
	MEDICAL_LEAVE(9,"傷病"),
	/** 代出。 */
	COMPENSATORY_ATTENDANCE(10,"代出"),
	/** 午前半休。 */
	MORNING_VACATION(11,"午前休"),
	/** 午後半休。 */
	AFTERNOON_VACATION(12,"午後休"),
	/** シフト。 */
	SHIFT(13,"シフト");


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
