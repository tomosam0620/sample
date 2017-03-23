package jp.co.orangearch.workmanage.form.workTime;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.constant.AttendanceCode;
import jp.co.orangearch.workmanage.common.constant.WorkTimeType;
import jp.co.orangearch.workmanage.common.util.DateUtils;
import jp.co.orangearch.workmanage.common.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.common.validator.EnumValue;

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
	@DateValid(pattern="uuuu-MM-dd", message="{V001}")
	private String workDate;

	/** 勤務帯。 */
	@NotNull
	@EnumValue(type=WorkTimeType.class)
	private String workTimeType;

	/** 出勤コード */
	@NotNull
	@EnumValue(type=AttendanceCode.class)
	private String attendanceCode;

	/** 出社時刻。 */
	@DateValid(pattern="H:m", message="{V002}")
	private String startTime;

	/** 退社時刻。 */
	@DateValid(pattern="H:m", message="{V002}")
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

	public String getAttendanceCode(){
		return attendanceCode;
	}

	public AttendanceCode getAttendanceCodeAsEnum(){
		return AttendanceCode.of(Integer.parseInt(attendanceCode));
	}

	public void setAttendanceCode(String value){
		attendanceCode = value;
	}

	public void setAttendanceCodeAsEnum(AttendanceCode value){
		attendanceCode = value.getKey();
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

	public String getWorkTimeType(){
		return workTimeType;
	}

	public Integer getWorkTimeTypeAsInt(){
			return StringUtils.isEmpty(workTimeType) ? null : Integer.parseInt(workTimeType);
	}

	public void setWorkTimeType(String value){
		workTimeType = value;
	}

	public void setWorkTimeTypeAsInt(Integer value){
		workTimeType = value == null ? null : String.valueOf(value);
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
