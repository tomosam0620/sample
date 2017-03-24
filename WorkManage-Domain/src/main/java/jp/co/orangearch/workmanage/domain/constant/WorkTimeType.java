package jp.co.orangearch.workmanage.domain.constant;

import jp.co.orangearch.workmanage.domain.exception.SystemException;

/**
 * 勤務帯を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
public enum WorkTimeType implements Values {
	/** 通常1(9：00～17:30(1h))。 */
	NOMAL1(1,"通常1"),
	/** 通常1(9：00～18:00(1h))。 */
	NOMAL2(2,"通常2"),
	/** 通常1(9：00～17:45(45m))。 */
	NOMAL3(3,"通常3"),
	/** 通常1(9：30～18:00(1h))。 */
	PATTEN1(4,"パターン1"),
	/** 通常1(9：00～17：30)。 */
	SHIFT(5,"シフト");

	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	WorkTimeType(Integer value, String text){
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
	
	public static String getTextByValue(Integer value){
		for(WorkTimeType item : WorkTimeType.values()){
			if(item.value.equals(value)){
				return item.text;
			}
		}
		throw new SystemException("ないわ。" + value);
	}
}
