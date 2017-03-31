package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.validation.beanvalidation.CsvBeanValidator;

@CsvBean(header=true, validators=CsvBeanValidator.class)
public class WorkTimeCsvBean {
    /** ユーザーID */
	@CsvColumn(number=1, label="ユーザーID")
	@NotEmpty
    String userId;

    /** 日付 */
	@CsvColumn(number=2, label="日付")
    LocalDate workDate;

    /** 勤務帯 */
	@CsvColumn(number=3, label="勤務帯")
    Integer workTimeType;
//    WorkTimeType workTimeType;
    
    /** 出社時刻 */
	@CsvColumn(number=4, label="出社時刻")
    LocalTime startTime;

    /** 退社時刻 */
	@CsvColumn(number=5, label="退社時刻")
    LocalTime endTime;

    /** 出勤コード */
	@CsvColumn(number=6, label="出勤コード")
    Integer attendanceCode;

    /** 備考 */
	@CsvColumn(number=7, label="備考")
    String notes;

    /** 休日タイプ */
	@CsvColumn(number=8, label="休日タイプ")
    Integer horidayType;

    /** 
     * Returns the userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /** 
     * Sets the userId.
     * 
     * @param userId the userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 
     * Returns the workDate.
     * 
     * @return the workDate
     */
    public LocalDate getWorkDate() {
        return workDate;
    }

    /** 
     * Sets the workDate.
     * 
     * @param workDate the workDate
     */
    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    /** 
     * Returns the workTimeType.
     * 
     * @return the workTimeType
     */
    public Integer getWorkTimeType() {
//    public WorkTimeType getWorkTimeType(){
        return workTimeType;
    }

    /** 
     * Sets the workTimeType.
     * 
     * @param workTimeType the workTimeType
     */
    public void setWorkTimeType(Integer workTimeType) {
//    public void setWorkTimeType(WorkTimeType workTimeType) {
        this.workTimeType = workTimeType;
    }

    /** 
     * Returns the startTime.
     * 
     * @return the startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /** 
     * Sets the startTime.
     * 
     * @param startTime the startTime
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /** 
     * Returns the endTime.
     * 
     * @return the endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /** 
     * Sets the endTime.
     * 
     * @param endTime the endTime
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /** 
     * Returns the attendanceCode.
     * 
     * @return the attendanceCode
     */
    public Integer getAttendanceCode() {
        return attendanceCode;
    }

    /** 
     * Sets the attendanceCode.
     * 
     * @param attendanceCode the attendanceCode
     */
    public void setAttendanceCode(Integer attendanceCode) {
        this.attendanceCode = attendanceCode;
    }

    /** 
     * Returns the notes.
     * 
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /** 
     * Sets the notes.
     * 
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /** 
     * Returns the horidayType.
     * 
     * @return the horidayType
     */
    public Integer getHoridayType() {
        return horidayType;
    }

    /** 
     * Sets the horidayType.
     * 
     * @param horidayType the horidayType
     */
    public void setHoridayType(Integer horidayType) {
        this.horidayType = horidayType;
    }
}
