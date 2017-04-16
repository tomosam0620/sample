package jp.co.orangearch.workmanage.dto;

import java.math.BigDecimal;

import jp.co.orangearch.workmanage.domain.entity.WorkTime;

public class OperationTime extends WorkTime{
	
	/** 稼働時間 */
	private BigDecimal operationTime;
	/** 法内残業時間 */
	private BigDecimal overtimeWithinStatutoryWorkingHours;
	/** 法外残業時間 */
	private BigDecimal overtimeBeyondStatutoryWorkingHours;
	/** 深夜稼働時間 */
	private BigDecimal nightHours;
	/** 休日稼働時間 */
	private BigDecimal horidayHours;
	/** 週計稼働時間 */
	private BigDecimal operationSumOfWeek;
	/** 週計法内残業時間 */
	private BigDecimal overtimeWithinStatutoryWorkingHoursSumOfWeek;
	/** 週計法外残業時間 */
	private BigDecimal overtimeBeyondStatutoryWorkingHoursSumOfWeek;
	
	public OperationTime() {
	}
	
	public OperationTime(WorkTime item) {
	    super.setUserId(item.getUserId());
	    super.setWorkDate(item.getWorkDate());
	    super.setWorkTimeType(item.getWorkTimeType());
	    super.setStartTime(item.getStartTime());
	    super.setEndTime(item.getEndTime());
	    super.setStartWorkCode(item.getStartWorkCode());
	    super.setEndWorkCode(item.getEndWorkCode());
	    super.setCompensatoryAttendanceDate(item.getCompensatoryAttendanceDate());
	    super.setAttendanceCode(item.getAttendanceCode());
	    super.setNotes(item.getNotes());
	    super.setHoridayType(item.getHoridayType());
	}

	public BigDecimal getOperationTime(){
		return operationTime;
	}
	
	public void setOperationTime(BigDecimal value){
		operationTime = value;
	}

	public void setOvertimeWithinStatutoryWorkingHours(BigDecimal value) {
		overtimeWithinStatutoryWorkingHours = value;
	}

	public BigDecimal getOvertimeWithinStatutoryWorkingHours() {
		return overtimeWithinStatutoryWorkingHours;
	}

	public void setOvertimeBeyondStatutoryWorkingHours(BigDecimal value) {
		overtimeBeyondStatutoryWorkingHours = value;
	}
	
	public BigDecimal getOvertimeBeyondStatutoryWorkingHours() {
		return overtimeBeyondStatutoryWorkingHours;
	}

	public BigDecimal getNightHours() {
		return nightHours;
	}

	public void setNightHours(BigDecimal value) {
		nightHours = value;
	}
	
	public BigDecimal getHoridayHours() {
		return horidayHours;
	}

	public void setHoridayHours(BigDecimal value) {
		horidayHours = value;
	}

	public BigDecimal getOperationSumOfWeek() {
		return operationSumOfWeek;
	}

	public void setOperationSumOfWeek(BigDecimal value) {
		operationSumOfWeek = value;
	}

	public BigDecimal getOvertimeWithinStatutoryWorkingHoursSumOfWeek() {
		return overtimeWithinStatutoryWorkingHoursSumOfWeek;
	}

	public void setOvertimeWithinStatutoryWorkingHoursSumOfWeek(BigDecimal value) {
		overtimeWithinStatutoryWorkingHoursSumOfWeek = value;
	}

	public BigDecimal getOvertimeBeyondStatutoryWorkingHoursSumOfWeek(){
		return overtimeBeyondStatutoryWorkingHoursSumOfWeek;
	}
	
	public void setOvertimeBeyondStatutoryWorkingHoursSumOfWeek(BigDecimal value){
		overtimeBeyondStatutoryWorkingHoursSumOfWeek = value;
	}
		
}
