package jp.co.orangearch.workmanage.domain.entity.join;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import jp.co.orangearch.workmanage.domain.constant.ClosingState;

/**
 * 
 */
@Entity(listener = JoinProjectWorkTimeStatusUserListener.class)
public class JoinProjectWorkTimeStatusUser {

    /** ユーザーID */
    @Id
    @Column(name = "USER_ID")
    String userId;

    /** 稼働月 */
    @Id
    @Column(name = "WORK_MONTH")
    String workMonth;

    /** プロジェクトID */
    @Column(name = "PROJECT_ID")
    Integer projectId;

    /** ステータス */
    @Column(name = "STATUS")
    ClosingState status;

    /** 所属 */
    @Column(name = "AFFILIATION")
	private Integer affiliation;

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
     * Returns the projectId.
     * 
     * @return the projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /** 
     * Sets the projectId.
     * 
     * @param projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    /** 
     * Returns the affiliation.
     * 
     * @return the affiliation
     */
	public Integer getAffiliation() {
		return affiliation;
	}
    /** 
     * Sets the status.
     * 
     * @param status the status
     */
    public void setStatus(Integer affiliation) {
        this.affiliation = affiliation;
    }

}