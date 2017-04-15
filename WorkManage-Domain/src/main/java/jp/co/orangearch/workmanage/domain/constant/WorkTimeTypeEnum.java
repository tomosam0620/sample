package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 勤務帯を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum WorkTimeTypeEnum implements Values {
	/** 通常1(9：00～17:30(1h))。 */
	通常1(1,"通常1(9：00～17:30(1h))"),
	/** 通常1(9：00～18:00(1h))。 */
	通常2(2,"通常1(9：00～18:00(1h))"),
	/** 通常1(9：00～17:45(45m))。 */
	通常3(3,"通常1(9：00～17:45(45m))"),
	/** パターン1(9：30～18:00(1h))。 */
	パターン1(4,"パターン1(9：30～18:00(1h))"),
	/** シフト。 */
	シフト(5,"シフト");

	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	private WorkTimeTypeEnum(Integer value){
		this.value = value;
		this.text = getTextByValue(value);
	}

	/** コンストラクタ。 */
	private WorkTimeTypeEnum(Integer value, String text){
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
		for(WorkTimeTypeEnum item : WorkTimeTypeEnum.values()){
			if(item.value.equals(value)){
				return item.text;
			}
		}
		throw new IllegalArgumentException("ないわ。" + value);
	}
	
	/**
	 * domain用ファクトリメソッド。
	 * valueからインスタンスを返します。
	 * @param value 値
	 * @return AttendanceCode
	 */
	public static WorkTimeTypeEnum of(Integer value){
		 for(WorkTimeTypeEnum item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
			throw new IllegalArgumentException(String.valueOf(value));
	}
}
