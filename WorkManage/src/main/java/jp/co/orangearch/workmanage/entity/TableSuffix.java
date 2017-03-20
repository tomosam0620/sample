package jp.co.orangearch.workmanage.entity;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Version;

@Entity
public abstract class TableSuffix {

    /**  */
    @Column(name = "REGIST_USER")
    String registUser;

    /**  */
    @Column(name = "REGIST_DATE")
    LocalDateTime registDate;

    /**  */
    @Column(name = "UPDATE_USER")
    String updateUser;

    /**  */
    @Column(name = "UPDATE_DATE")
    LocalDateTime updateDate;

    /**  */
    @Version
    @Column(name = "VERSION")
    Integer version;
    
    /** 
     * Returns the registUser.
     * 
     * @return the registUser
     */
    public String getRegistUser() {
        return registUser;
    }

    /** 
     * Sets the registUser.
     * 
     * @param registUser the registUser
     */
    public void setRegistUser(String registUser) {
        this.registUser = registUser;
    }

    /** 
     * Returns the registDate.
     * 
     * @return the registDate
     */
    public LocalDateTime getRegistDate() {
        return registDate;
    }

    /** 
     * Sets the registDate.
     * 
     * @param registDate the registDate
     */
    public void setRegistDate(LocalDateTime registDate) {
        this.registDate = registDate;
    }

    /** 
     * Returns the updateUser.
     * 
     * @return the updateUser
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /** 
     * Sets the updateUser.
     * 
     * @param updateUser the updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /** 
     * Returns the updateDate.
     * 
     * @return the updateDate
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /** 
     * Sets the updateDate.
     * 
     * @param updateDate the updateDate
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /** 
     * Returns the version.
     * 
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /** 
     * Sets the version.
     * 
     * @param version the version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

}
