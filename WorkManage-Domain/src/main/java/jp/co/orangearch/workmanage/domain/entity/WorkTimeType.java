package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;

/**
 * 
 */
@Entity(listener = WorkTimeTypeListener.class)
@Table(name = "WORK_TIME_TYPE")
public class WorkTimeType extends TableSuffix {

    /** 勤務帯コード */
    @Id
    @Column(name = "WORK_TIME_CD")
    WorkTimeCode workTimeCd;

    /** 勤務帯名称 */
    @Column(name = "WORK_TIME_NAME")
    String workTimeName;

    /** 始業時刻 */
    @Column(name = "START_TIME")
    LocalTime startTime;

    /** 終業時刻 */
    @Column(name = "END_TIME")
    LocalTime endTime;

    /** 昼休憩時間 */
    @Column(name = "LUNCH_BREAK_TIME")
    Integer lunchBreakTime;

    /** 昼休憩開始時刻 */
    @Column(name = "LUNCH_BREAK_START_TIME")
    LocalTime lunchBreakStartTime;

    /** 昼休憩終了時刻 */
    @Column(name = "LUNCH_BREAK_END_TIME")
    LocalTime lunchBreakEndTime;

    /** フレックスフラグ */
    @Column(name = "FLEX_FLAG")
    Integer flexFlag;

    /** 法定休日(曜日) */
    @Column(name = "LEAGAL_HORIDAY")
    Integer leagalHoriday;

    /** 休日(曜日) */
    @Column(name = "HORIDAY")
    Integer horiday;

    /** 半休境界時刻 */
    @Column(name = "HALF_DAY_OFF_LIMIT_TIME")
    LocalTime halfDayOffLimitTime;

    /** 削除フラグ */
    @Column(name = "DELETE_FLAG")
    Integer deleteFlag;

    /** 
     * Returns the workTimeCd.
     * 
     * @return the workTimeCd
     */
    public WorkTimeCode getWorkTimeCd() {
        return workTimeCd;
    }

    /** 
     * Sets the workTimeCd.
     * 
     * @param workTimeCd the workTimeCd
     */
    public void setWorkTimeCd(WorkTimeCode workTimeCd) {
        this.workTimeCd = workTimeCd;
    }

    /** 
     * Returns the workTimeName.
     * 
     * @return the workTimeName
     */
    public String getWorkTimeName() {
        return workTimeName;
    }

    /** 
     * Sets the workTimeName.
     * 
     * @param workTimeName the workTimeName
     */
    public void setWorkTimeName(String workTimeName) {
        this.workTimeName = workTimeName;
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
     * Returns the lunchBreakTime.
     * 
     * @return the lunchBreakTime
     */
    public Integer getLunchBreakTime() {
        return lunchBreakTime;
    }

    /** 
     * Sets the lunchBreakTime.
     * 
     * @param lunchBreakTime the lunchBreakTime
     */
    public void setLunchBreakTime(Integer lunchBreakTime) {
        this.lunchBreakTime = lunchBreakTime;
    }

    /** 
     * Returns the lunchBreakStartTime.
     * 
     * @return the lunchBreakStartTime
     */
    public LocalTime getLunchBreakStartTime() {
        return lunchBreakStartTime;
    }

    /** 
     * Sets the lunchBreakStartTime.
     * 
     * @param lunchBreakStartTime the lunchBreakStartTime
     */
    public void setLunchBreakStartTime(LocalTime lunchBreakStartTime) {
        this.lunchBreakStartTime = lunchBreakStartTime;
    }

    /** 
     * Returns the lunchBreakEndTime.
     * 
     * @return the lunchBreakEndTime
     */
    public LocalTime getLunchBreakEndTime() {
        return lunchBreakEndTime;
    }

    /** 
     * Sets the lunchBreakEndTime.
     * 
     * @param lunchBreakEndTime the lunchBreakEndTime
     */
    public void setLunchBreakEndTime(LocalTime lunchBreakEndTime) {
        this.lunchBreakEndTime = lunchBreakEndTime;
    }

    /** 
     * Returns the flexFlag.
     * 
     * @return the flexFlag
     */
    public Integer getFlexFlag() {
        return flexFlag;
    }

    /** 
     * Sets the flexFlag.
     * 
     * @param flexFlag the flexFlag
     */
    public void setFlexFlag(Integer flexFlag) {
        this.flexFlag = flexFlag;
    }

    /** 
     * Returns the leagalHoriday.
     * 
     * @return the leagalHoriday
     */
    public Integer getLeagalHoriday() {
        return leagalHoriday;
    }

    /** 
     * Sets the leagalHoriday.
     * 
     * @param leagalHoriday the leagalHoriday
     */
    public void setLeagalHoriday(Integer leagalHoriday) {
        this.leagalHoriday = leagalHoriday;
    }

    /** 
     * Returns the horiday.
     * 
     * @return the horiday
     */
    public Integer getHoriday() {
        return horiday;
    }

    /** 
     * Sets the horiday.
     * 
     * @param horiday the horiday
     */
    public void setHoriday(Integer horiday) {
        this.horiday = horiday;
    }

    /** 
     * Returns the halfDayOffLimitTime.
     * 
     * @return the halfDayOffLimitTime
     */
    public LocalTime getHalfDayOffLimitTime() {
        return halfDayOffLimitTime;
    }

    /** 
     * Sets the halfDayOffLimitTime.
     * 
     * @param halfDayOffLimitTime the halfDayOffLimitTime
     */
    public void setHalfDayOffLimitTime(LocalTime halfDayOffLimitTime) {
        this.halfDayOffLimitTime = halfDayOffLimitTime;
    }

    /** 
     * Returns the deleteFlag.
     * 
     * @return the deleteFlag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /** 
     * Sets the deleteFlag.
     * 
     * @param deleteFlag the deleteFlag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}