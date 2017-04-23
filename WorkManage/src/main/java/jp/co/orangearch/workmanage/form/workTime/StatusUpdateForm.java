package jp.co.orangearch.workmanage.form.workTime;

import java.io.Serializable;

import jp.co.orangearch.workmanage.common.validator.EnumValue;
import jp.co.orangearch.workmanage.domain.constant.ClosingState;

public class StatusUpdateForm implements Serializable{

	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String month;
	@EnumValue(type=ClosingState.class)
	private String status;
	private Integer version;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String value) {
		userId = value;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String value) {
		month = value;
	}

	public String getStatus() {
		return status;
	}

	public ClosingState getStatusAsEnum() {
		return ClosingState.of(Integer.valueOf(status));
	}

	public void setStatus(String value) {
		status = value;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer value) {
		version = value;
	}

}
