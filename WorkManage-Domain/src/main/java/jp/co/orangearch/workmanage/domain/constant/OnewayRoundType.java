package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 片道往復を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum OnewayRoundType implements Values{
	/** 片道。 */
	片道(1,"片道"),
	/** 往復。 */
	往復(2,"往復");


	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	OnewayRoundType(Integer value, String text){
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
	 * @return OnewayRoundType
	 */
	public static OnewayRoundType of(Integer value){
		 for(OnewayRoundType item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
