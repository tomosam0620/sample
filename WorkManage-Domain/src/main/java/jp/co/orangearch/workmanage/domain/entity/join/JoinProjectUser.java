package jp.co.orangearch.workmanage.domain.entity.join;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;

/**
 * 
 */
@Entity(listener = JoinProjectUserListener.class)
public class JoinProjectUser {

    /** ユーザーID */
    @Id
    @Column(name = "USER_ID")
    String userId;

    /** パスワード */
    @Column(name = "PASSWORD")
    String password;

    /** ロールID */
    @Column(name = "ROLE_ID")
    String roleId;

    /** 所属 */
    @Column(name = "AFFILIATION")
    Integer affiliation;

    /** 役職 */
    @Column(name = "POSITION")
    Integer position;

    /** パスワード最終更新日 */
    @Column(name = "PASSWORDLAST_CHANGE_DATE")
    LocalDate passwordlastChangeDate;

    /** パスワード誤入力回数 */
    @Column(name = "PASSWORD_MISS_COUNT")
    Integer passwordMissCount;

    /** パスワード初期フラグ */
    @Column(name = "PASSWORD_INITIAL_FLAG")
    Integer passwordInitialFlag;

    /** 削除フラグ */
    @Column(name = "DELETE_FLAG")
    Integer deleteFlag;

    /** USER情報バージョン */
    @Column(name = "U_VERSION")
    Integer uVersion;
    
    //JoinProject
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

    /** JOIN_PROJECT情報バージョン */
    @Column(name = "J_VERSION")
    Integer jVersion;
    
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
     * Returns the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /** 
     * Sets the password.
     * 
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     * Returns the roleId.
     * 
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /** 
     * Sets the roleId.
     * 
     * @param roleId the roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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
     * Sets the affiliation.
     * 
     * @param affiliation the affiliation
     */
    public void setAffiliation(Integer affiliation) {
        this.affiliation = affiliation;
    }

    /** 
     * Returns the position.
     * 
     * @return the position
     */
    public Integer getPosition() {
        return position;
    }

    /** 
     * Sets the position.
     * 
     * @param position the position
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /** 
     * Returns the passwordlastChangeDate.
     * 
     * @return the passwordlastChangeDate
     */
    public LocalDate getPasswordlastChangeDate() {
        return passwordlastChangeDate;
    }

    /** 
     * Sets the passwordlastChangeDate.
     * 
     * @param passwordlastChangeDate the passwordlastChangeDate
     */
    public void setPasswordlastChangeDate(LocalDate passwordlastChangeDate) {
        this.passwordlastChangeDate = passwordlastChangeDate;
    }

    /** 
     * Returns the passwordMissCount.
     * 
     * @return the passwordMissCount
     */
    public Integer getPasswordMissCount() {
        return passwordMissCount;
    }

    /** 
     * Sets the passwordMissCount.
     * 
     * @param passwordMissCount the passwordMissCount
     */
    public void setPasswordMissCount(Integer passwordMissCount) {
        this.passwordMissCount = passwordMissCount;
    }

    /** 
     * Returns the passwordInitialFlag.
     * 
     * @return the passwordInitialFlag
     */
    public Integer getPasswordInitialFlag() {
        return passwordInitialFlag;
    }

    /** 
     * Sets the passwordInitialFlag.
     * 
     * @param passwordInitialFlag the passwordInitialFlag
     */
    public void setPasswordInitialFlag(Integer passwordInitialFlag) {
        this.passwordInitialFlag = passwordInitialFlag;
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
    
    /** 
     * Returns the uVersion.
     * 
     * @return the uVersion
     */
    public Integer getUVersion() {
        return uVersion;
    }

    /** 
     * Sets the uVersion.
     * 
     * @param uVersion the uVersion
     */
    public void setUVersion(Integer uVersion) {
        this.uVersion = uVersion;
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
    
    /** 
     * Returns the jVersion.
     * 
     * @return the jVersion
     */
    public Integer getJVersion() {
        return jVersion;
    }

    /** 
     * Sets the jVersion.
     * 
     * @param jVersion the jVersion
     */
    public void setJVersion(Integer jVersion) {
        this.jVersion = jVersion;
    }


}