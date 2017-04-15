package jp.co.orangearch.workmanage.domain.entity.join;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

import jp.co.orangearch.workmanage.domain.entity.User;

/**
 * 
 */
@Entity(listener = JoinProjectUserListener.class)
//@Table(name = "CUSTOMER")
public class JoinProjectUser extends User {

//    /** ユーザーID */
//    @Id
//    @Column(name = "USER_ID")
//    String userId;
//
    /** プロジェクトID */
    @Id
    @Column(name = "PROJECT_ID")
    Integer projectId;

    /** 開始日付 */
    @Id
    @Column(name = "START_DATE")
    LocalDate startDate;

    /** 終了日付 */
    @Column(name = "END_DATE")
    LocalDate endDate;

//    /** 
//     * Returns the userId.
//     * 
//     * @return the userId
//     */
//    public String getUserId() {
//        return userId;
//    }
//
//    /** 
//     * Sets the userId.
//     * 
//     * @param userId the userId
//     */
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
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
     * @param projectId the projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /** 
     * Returns the startDate.
     * 
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** 
     * Sets the startDate.
     * 
     * @param startDate the startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** 
     * Returns the endDate.
     * 
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** 
     * Sets the endDate.
     * 
     * @param endDate the endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}