package jp.co.orangearch.workmanage.dto;

import java.math.BigDecimal;

import jp.co.orangearch.workmanage.domain.entity.WorkTime;

/**
 * 1日の稼働情報を保持するクラスです。
 * 週計の時間は週終わり(土曜日)のみ値を設定します。
 * @author t-otsika
 *
 */
public class OperationTime extends WorkTime{
	
	/** 稼働時間 */
	private BigDecimal operationHours;
	/** 法内残業時間 */
	private BigDecimal overtimeWithinStatutoryWorkingHours;
	/** 法外残業時間 */
	private BigDecimal overtimeBeyondStatutoryWorkingHours;
	/** 深夜稼働時間 */
	private BigDecimal nightHours;
	/** 休日稼働時間 */
	private BigDecimal horidayHours;
	/** 遅刻時間 */
	private BigDecimal lateHours;
	/** 早退時間 */
	private BigDecimal leaveEaryHours;
	/** 週計稼働時間 */
	private BigDecimal operationHoursInWeek;
	/** 週計法内残業時間 */
	private BigDecimal overtimeWithinStatutoryWorkingHoursInWeek;
	/** 週計法外残業時間 */
	private BigDecimal overtimeBeyondStatutoryWorkingHoursInWeek;
	
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

	public BigDecimal getOperationHours(){
		return operationHours;
	}
	
	public void setOperationHours(BigDecimal value){
		operationHours = value;
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

	public BigDecimal getOperationHoursInWeek() {
		return operationHoursInWeek;
	}

	public void setOperationHoursInWeek(BigDecimal value) {
		operationHoursInWeek = value;
	}

	public BigDecimal getOvertimeWithinStatutoryWorkingHoursInWeek() {
		return overtimeWithinStatutoryWorkingHoursInWeek;
	}

	public void setOvertimeWithinStatutoryWorkingHoursInWeek(BigDecimal value) {
		overtimeWithinStatutoryWorkingHoursInWeek = value;
	}

	public BigDecimal getOvertimeBeyondStatutoryWorkingHoursInWeek(){
		return overtimeBeyondStatutoryWorkingHoursInWeek;
	}
	
	public void setOvertimeBeyondStatutoryWorkingHoursInWeek(BigDecimal value){
		overtimeBeyondStatutoryWorkingHoursInWeek = value;
	}

	public BigDecimal getLateHours() {
		return lateHours;
	}

	public void setLateHours(BigDecimal value) {
		lateHours = value;
	}

	public BigDecimal getLeaveEaryHours() {
		return leaveEaryHours;
	}
	
	public void setLeaveEaryHours(BigDecimal value) {
		leaveEaryHours = value;
	}
	
}
