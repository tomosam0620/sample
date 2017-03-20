package jp.co.orangearch.workmanage.common.constant;

/**
 * 出勤コードを定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
public enum AttendanceCode implements Values{
	/** 有給。 */
	PAID_VACATION(1,"有給"),
	/** 年特。 */
	SPECIAL_VACATION(2,"年特"),
	/** 午前半休。 */
	MORNING_VACATION(3,"午前休"),
	/** 午後半休。 */
	AFTERNOON_VACATION(4,"午後休"),
	/** 欠勤。 */
	ABSENCE(5,"欠勤");

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
	@Override
	public String getValue(){
		return String.valueOf(value);
	}
	
	/** 値取得。 */
	public Integer getKey(){
		return value;
	}
	
	/** テキスト取得。 */
	@Override
	public String getText(){
		return text;
	}
}
