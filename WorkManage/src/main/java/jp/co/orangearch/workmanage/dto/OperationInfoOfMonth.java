package jp.co.orangearch.workmanage.dto;

import java.math.BigDecimal;

import jp.co.orangearch.workmanage.domain.constant.ClosingState;

/**
 * 月の稼働情報の週計結果を保持するクラスです。
 * 
 * @author t-otsuka
 *
 */
public class OperationInfoOfMonth {
	/** 月 */
	private String month;
	/** 所属 */
	private Integer affiliationCd;
	/** プロジェクト */
	private Integer projectId;
	/** ユーザーID */
	private String userId;
	/** 稼働 */
	private BigDecimal operationHours;
	/** 法内残業(月) */
	private BigDecimal overtimeWithinStatutoryWorkingHours;
	/** 法外残業(月) */
	private BigDecimal overtimeBeyondStatutoryWorkingHours;
	/** 有給 */
	private Integer dayOff;
	/** 半休 */
	private Integer halfDayOff;
	/** ステータス */
	private ClosingState status;
	
	/** 月 */
	public String getMonth(){
		return month;
	}

	public void setMonth(String value){
		month = value;
	}

	/** 所属 */
	public Integer getAffiliationCd(){
		return affiliationCd;
	}

	public void setAffiliationCd(Integer value){
		affiliationCd = value;
	}

	/** プロジェクト */
	public Integer getProjectId(){
		return projectId;
	}

	public void setProjectId(Integer value){
		projectId = value;
	}

	/** ユーザーID */
	public String getUserId(){
		return userId;
	}

	public void setUserId(String value){
		userId = value;
	}

	/** 稼働 */
	public BigDecimal getOperationHours(){
		return operationHours;
	}

	public void setOperationHours(BigDecimal value){
		operationHours = value;
	}

	/** 法内残業(月) */
	public BigDecimal getOvertimeWithinStatutoryWorkingHours(){
		return overtimeWithinStatutoryWorkingHours;
	}

	public void setOvertimeWithinStatutoryWorkingHours(BigDecimal value){
		overtimeWithinStatutoryWorkingHours = value;
	}

	/** 法外残業(月) */
	public BigDecimal getOvertimeBeyondStatutoryWorkingHours(){
		return overtimeBeyondStatutoryWorkingHours;
	}

	public void setOvertimeBeyondStatutoryWorkingHours(BigDecimal value){
		overtimeBeyondStatutoryWorkingHours = value;
	}

	/** 有給 */
	public Integer getDayOff(){
		return dayOff;
	}

	public void setDayOff(Integer value){
		dayOff = value;
	}

	/** 半休 */
	public Integer getHalfDayOff(){
		return halfDayOff;
	}

	public void setHalfDayOff(Integer value){
		halfDayOff = value;
	}

	/** ステータス */
	public ClosingState getStatus(){
		return status;
	}

	public void setStatus(ClosingState value){
		status = value;
	}

}
