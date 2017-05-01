package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import jp.co.orangearch.workmanage.domain.constant.Flag;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;

/**
 * 
 */
@Entity(listener = UserListener.class)
@Table(name = "user")
public class User extends TableSuffix {

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
    Flag deleteFlag;

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
    public Flag getDeleteFlag() {
        return deleteFlag;
    }

    /** 
     * Sets the deleteFlag.
     * 
     * @param deleteFlag the deleteFlag
     */
    public void setDeleteFlag(Flag deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}