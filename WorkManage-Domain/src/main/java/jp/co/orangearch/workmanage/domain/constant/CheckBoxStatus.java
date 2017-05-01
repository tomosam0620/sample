package jp.co.orangearch.workmanage.domain.constant;

/**
 * checkboxの値を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
public enum CheckBoxStatus implements Values{
	/** false。 */
	FALSE(""),
	/** true。 */
	TRUE("on");


	/** 値 */
	private String value;

	/** コンストラクタ。 */
	CheckBoxStatus(String value){
		this.value = value;
	}

	/** 値取得。 */
	public String getValue(){
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
	 * @return CheckBoxStatus
	 */
	public static CheckBoxStatus of(Integer value){
		 for(CheckBoxStatus item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
