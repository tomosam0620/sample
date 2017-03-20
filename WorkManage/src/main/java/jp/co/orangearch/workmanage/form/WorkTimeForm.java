package jp.co.orangearch.workmanage.form;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.util.DateUtils;
import jp.co.orangearch.workmanage.common.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.common.validator.DateValid;

/**
 * 勤務時間のフォームクラスです。
 *
 * @author t-otsuka
 *
 */
public class WorkTimeForm implements Serializable{

	private static final long serialVersionUID = 1L;

	/** ユーザID。 */
	@Pattern(regexp = "^$|^[0-9]{7}$")
	private String userId;

	/** 勤務日付。 */
	@NotEmpty
	@DateValid(pattern="uuuu-MM-dd", message="yyyy-MM-dd")
	private String workDate;

	/** 勤務帯。 */
	@NotNull
	@Range(min = 1, max=5)
	private Integer workTimeType;

	@NotNull
	@Range(min = 1, max=4)
	private Integer attendanceCode;
	
	/** 出社時刻。 */
	@DateValid(pattern="HH:mm", message="HH:mm")
	private String startTime;

	/** 退社時刻。 */
	@DateValid(pattern="HH:mm", message="HH:mm")
	private String endTime;

	/** 備考。 */
	private String note;

	/** バージョン(楽観排他用)。 */
	private Integer version;
	
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String value){
		userId = value;
	}
	
	public String getWorkDate(){
		return workDate;
	}
	
	public LocalDate getWorkDateAsLocalDate(){
		return DateUtils.convertToLocalDate(workDate);
	}
	
	public void setWorkDate(String value){
		workDate = value;
	}

	public Integer getAttendanceCode(){
		return attendanceCode;
	}
	
	public void setAttendanceCode(Integer value){
		attendanceCode = value;
	}
	
	public String getStartTime(){
		return startTime;
	}

	public LocalTime getStartTimeAsLocalTime(){
		return StringUtils.isEmpty(startTime) ? null: DateUtils.convertToLocalTime(startTime +":00");
	}

	
	public void setStartTime(String value){
		startTime = value;
	}

	public void setStartTime(LocalTime value){
		startTime = DateUtils.convert(value, DateTimeFormat.HH_MM);
	}

	public String getEndTime(){
		return endTime;
	}

	public LocalTime getEndTimeAsLocalTime(){
		return StringUtils.isEmpty(endTime) ? null: DateUtils.convertToLocalTime(endTime +":00");
	}

	
	public void setEndTime(String value){
		endTime = value;
	}

	public void setEndTime(LocalTime value){
		endTime = DateUtils.convert(value, DateTimeFormat.HH_MM);
	}

	public Integer getWorkTimeType(){
		return workTimeType;
	}

	public void setWorkTimeType(Integer value){
		workTimeType = value;
	}

	public String getNote(){
		return note;
	}

	public void setNote(String value){
		note = value;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer value) {
		version = value;
	}
}
