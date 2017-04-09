package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * 交通手段を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum TransportionType implements Values{
	/** 電車。 */
	電車(1,"電車"),
	/** バス。 */
	バス(2,"バス"),
	/** 飛行機。 */
	飛行機(3,"飛行機"),
	/** タクシー。 */
	タクシー(4,"タクシー"),
	/** その他。 */
	その他(5,"その他");


	/** 値 */
	private Integer value;

	/** 値 */
	private String text;

	/** コンストラクタ。 */
	TransportionType(Integer value, String text){
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
	public static TransportionType of(Integer value){
		 for(TransportionType item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
