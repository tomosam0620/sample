package jp.co.orangearch.workmanage.domain.constant;

import org.seasar.doma.Domain;

/**
 * flagの値を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class, factoryMethod = "of")
public enum Flag implements Values{
	/** false。 */
	FALSE(0),
	/** true。 */
	TRUE(1);


	/** 値 */
	private Integer value;

	/** コンストラクタ。 */
	Flag(Integer value){
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

	/**
	 * domain用ファクトリメソッド。
	 * valueからインスタンスを返します。
	 * @param value 値
	 * @return Flag
	 */
	public static Flag of(Integer value){
		 for(Flag item : values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		 }
		 return null;
	}
}
