package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jp.co.orangearch.workmanage.domain.constant.AttendanceCode;
import jp.co.orangearch.workmanage.domain.constant.EndWorkCode;
import jp.co.orangearch.workmanage.domain.constant.HoridayType;
import jp.co.orangearch.workmanage.domain.constant.StartWorkCode;
import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;

/**
 * 
 */
@Entity(listener = WorkTimeListener.class)
@Table(name = "WORK_TIME")
public class WorkTime extends TableSuffix {

    /** ユーザーID */
    @Id
    @Column(name = "USER_ID")
    String userId;

    /** 日付 */
    @Id
    @Column(name = "WORK_DATE")
    LocalDate workDate;

    /** 勤務帯 */
    @Column(name = "WORK_TIME_TYPE")
    WorkTimeCode workTimeType;

    /** 出社時刻 */
    @Column(name = "START_TIME")
    LocalTime startTime;

    /** 退社時刻 */
    @Column(name = "END_TIME")
    LocalTime endTime;

    /** 始業コード */
    @Column(name = "START_WORK_CODE")
    StartWorkCode startWorkCode;
    
    /** 終業コード */
    @Column(name = "END_WORK_CODE")
    EndWorkCode endWorkCode;

    /** 代休出日 */
    @Column(name = "COMPENSATORY_ATTENDANCE_DATE")
    LocalDate compensatoryAttendanceDate;

    /** 出勤コード */
    @Column(name = "ATTENDANCE_CODE")
    AttendanceCode attendanceCode;

    /** 備考 */
    @Column(name = "NOTES")
    String notes;

    /** 休日種別 */
    @Column(name = "HORIDAY_TYPE")
    HoridayType horidayType;

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
    public WorkTimeCode getWorkTimeType() {
        return workTimeType;
    }

    /** 
     * Sets the workTimeType.
     * 
     * @param workTimeType the workTimeType
     */
    public void setWorkTimeType(WorkTimeCode workTimeType) {
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
     * Returns the startWorkCode.
     * 
     * @return the startWorkCode
     */
    public StartWorkCode getStartWorkCode() {
    	return startWorkCode;
    }

    /** 
     * Sets the startWorkCode.
     * 
     * @param startWorkCode the startWorkCode
     */
    public void setStartWorkCode(StartWorkCode startWorkCode) {
        this.startWorkCode = startWorkCode;
    }

    /** 
     * Returns the endWorkCode.
     * 
     * @return the endWorkCode
     */
    public EndWorkCode getEndWorkCode() {
        return endWorkCode;
    }

    /** 
     * Sets the endWorkCode.
     * 
     * @param endWorkCode the endWorkCode
     */
    public void setEndWorkCode(EndWorkCode endWorkCode) {
        this.endWorkCode = endWorkCode;
    }

    /** 
     * Returns the compensatoryAttendanceDate.
     * 
     * @return the compensatoryAttendanceDate
     */
    public LocalDate getCompensatoryAttendanceDate() {
        return compensatoryAttendanceDate;
    }

    /** 
     * Sets the compensatoryAttendanceDate.
     * 
     * @param compensatoryAttendanceDate the compensatoryAttendanceDate
     */
    public void setCompensatoryAttendanceDate(LocalDate compensatoryAttendanceDate) {
        this.compensatoryAttendanceDate = compensatoryAttendanceDate;
    }

    /** 
     * Returns the attendanceCode.
     * 
     * @return the attendanceCode
     */
    public AttendanceCode getAttendanceCode() {
        return attendanceCode;
    }

    /** 
     * Sets the attendanceCode.
     * 
     * @param attendanceCode the attendanceCode
     */
    public void setAttendanceCode(AttendanceCode attendanceCode) {
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
    public HoridayType getHoridayType() {
        return horidayType;
    }

    /** 
     * Sets the horidayType.
     * 
     * @param horidayType the horidayType
     */
    public void setHoridayType(HoridayType horidayType) {
        this.horidayType = horidayType;
    }
}