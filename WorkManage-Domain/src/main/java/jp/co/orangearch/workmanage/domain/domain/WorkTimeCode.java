package jp.co.orangearch.workmanage.domain.domain;

import org.seasar.doma.Domain;

/**
 * 勤務帯を定義するENUMクラスです。
 *
 * @author t-otsuka
 *
 */
@Domain(valueType = Integer.class)
public class WorkTimeCode {
	/** 値 */
	private Integer value;

	/** コンストラクタ。 */
	public WorkTimeCode(Integer value){
		this.value = value;
	}

	/** 値取得。 */
	public Integer getValue(){
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null && value == null){
			return true;
		}else if(obj != null || value != null){
			if(!(obj instanceof WorkTimeCode)){
				return false;
			}
			WorkTimeCode target = (WorkTimeCode)obj;
			return target.getValue().equals(value);
		}else{
			return false;
		}
	}
}
