package jp.co.orangearch.workmanage.domain.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jp.co.orangearch.workmanage.domain.constant.ClosingState;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;

/**
 * 
 */
@Entity(listener = WorkTimeStatusListener.class)
@Table(name = "WORK_TIME_STATUS")
public class WorkTimeStatus extends TableSuffix {

    /** ユーザーID */
    @Id
    @Column(name = "USER_ID")
    String userId;

    /** 稼働月 */
    @Id
    @Column(name = "WORK_MONTH")
    String workMonth;

    /** ステータス */
    @Column(name = "STATUS")
    ClosingState status;

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
     * Returns the workMonth.
     * 
     * @return the workMonth
     */
    public String getWorkMonth() {
        return workMonth;
    }

    /** 
     * Sets the workMonth.
     * 
     * @param workMonth the workMonth
     */
    public void setWorkMonth(String workMonth) {
        this.workMonth = workMonth;
    }

    /** 
     * Returns the status.
     * 
     * @return the status
     */
    public ClosingState getStatus() {
        return status;
    }

    /** 
     * Sets the status.
     * 
     * @param status the status
     */
    public void setStatus(ClosingState status) {
        this.status = status;
    }
}