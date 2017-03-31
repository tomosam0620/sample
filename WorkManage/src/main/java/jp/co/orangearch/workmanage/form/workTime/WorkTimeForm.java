package jp.co.orangearch.workmanage.form.workTime;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.common.validator.EnumValue;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.domain.constant.AttendanceCode;
import jp.co.orangearch.workmanage.domain.constant.EndWorkCode;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.constant.StartWorkCode;
import jp.co.orangearch.workmanage.domain.constant.WorkTimeType;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.exception.SystemException;

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

	/** 始業コード。 */
	@EnumValue(type=StartWorkCode.class)
	private String startWorkCode;

	/** 終業コード。 */
	@EnumValue(type=EndWorkCode.class)
	private String endWorkCode;

	/** 代休出日。 */
	@DateValid(pattern="uuuu-MM-dd", message="{V001}")
	private String compensatoryAttendanceDate;

	/** 備考。 */
	@Size(max=25)
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
	
	public String getStartWorkCode(){
		return startWorkCode;
	}

	public void setStartWorkCode(String value){
		startWorkCode = value;
	}

	/** 終業コード。 */
	public String getEndWorkCode(){
		return endWorkCode;
	}

	public void setEndWorkCode(String value){
		endWorkCode = value;
	}

	/** 代休出日。 */
	/** 終業コード。 */
	public String getCompensatoryAttendanceDate(){
		return compensatoryAttendanceDate;
	}

	public LocalDate getCompensatoryAttendanceDateAsLocalDate(){
		return DateUtils.convertToLocalDate(compensatoryAttendanceDate);
	}

	public void setCompensatoryAttendanceDate(String value){
		compensatoryAttendanceDate = value;
	}


	/**
	 * デフォルトコンストラクタ
	 */
	public WorkTimeForm(){
	}
	
	/**
	 * コンストラクタ
	 * entityからformを生成します。
	 * 
	 * @param entity 勤務時間エンティティ
	 */
	public WorkTimeForm(WorkTime entity){
		try {
			BeanUtils.copyProperties(entity, this);
			
		} catch (Exception e) {
			//DBに取りうる値の範囲外が入っている場合
			throw new SystemException(MessageId.S003);
		}
	}
	
	public WorkTime toEntity(){
		WorkTime entity = new WorkTime();
		entity.setUserId(userId);
		entity.setWorkTimeType(Integer.valueOf(workTimeType));
		entity.setWorkDate(DateUtils.convertToLocalDate(workDate));
		entity.setAttendanceCode(Integer.valueOf(attendanceCode));
		entity.setCompensatoryAttendanceDate(DateUtils.convertToLocalDate(compensatoryAttendanceDate));
		entity.setStartTime(DateUtils.convertToLocalTime(startTime, DateTimeFormat.H_M));
		entity.setEndTime(DateUtils.convertToLocalTime(endTime, DateTimeFormat.H_M));
		entity.setStartWorkCode(Integer.valueOf(startWorkCode));
		entity.setEndWorkCode(Integer.valueOf(endWorkCode));
		entity.setNotes(note);
		entity.setVersion(version);
		return entity;
	}

	public void convert(WorkTime workTime) {
		setUserId(workTime.getUserId());
		setWorkTimeType(String.valueOf(workTime.getWorkTimeType()));
		setWorkDate(DateUtils.convert(workTime.getWorkDate()));
		setAttendanceCode(String.valueOf(workTime.getAttendanceCode()));
		setCompensatoryAttendanceDate(DateUtils.convert(workTime.getCompensatoryAttendanceDate()));
		setStartTime(DateUtils.convert(workTime.getStartTime(), DateTimeFormat.H_M));
		setEndTime(DateUtils.convert(workTime.getEndTime(), DateTimeFormat.H_M));
		setStartWorkCode(String.valueOf(workTime.getStartWorkCode()));
		setEndWorkCode(String.valueOf(workTime.getEndWorkCode()));
		setNote(workTime.getNotes());
		setVersion(workTime.getVersion());
	}
}
