package jp.co.orangearch.workmanage.domain.constant;

public enum HoridayType implements Values{
	/** 営業日。 */
	BISUNESS_DAY(1),
	/** 土曜日。 */
	SATURDAY(2),
	/** 休日(日曜日、祝日)。 */
	HORIDAY(3);

	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	HoridayType(Integer value){
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
	
	public static String getTextByValue(Integer value){
		for(HoridayType item : HoridayType.values()){
			if(item.value.equals(value)){
				return item.text;
			}
		}
		throw new IllegalArgumentException();
	}

}
