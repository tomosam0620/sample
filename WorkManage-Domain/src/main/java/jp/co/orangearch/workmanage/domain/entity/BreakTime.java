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
@Entity(listener = BreakTimeListener.class)
@Table(name = "BREAK_TIME")
public class BreakTime extends TableSuffix {

    /** 勤務帯コード */
    @Id
    @Column(name = "WORK_TIME_CD")
    WorkTimeCode workTimeCd;

    /** 休憩開始時刻 */
    @Id
    @Column(name = "BREAK_START_TIME")
    LocalTime breakStartTime;

    /** 休憩終了時刻 */
    @Column(name = "BREAK_END_TIME")
    LocalTime breakEndTime;

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
     * Returns the breakStartTime.
     * 
     * @return the breakStartTime
     */
    public LocalTime getBreakStartTime() {
        return breakStartTime;
    }

    /** 
     * Sets the breakStartTime.
     * 
     * @param breakStartTime the breakStartTime
     */
    public void setBreakStartTime(LocalTime breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    /** 
     * Returns the breakEndTime.
     * 
     * @return the breakEndTime
     */
    public LocalTime getBreakEndTime() {
        return breakEndTime;
    }

    /** 
     * Sets the breakEndTime.
     * 
     * @param breakEndTime the breakEndTime
     */
    public void setBreakEndTime(LocalTime breakEndTime) {
        this.breakEndTime = breakEndTime;
    }
}