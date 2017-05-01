package jp.co.orangearch.workmanage.form.userManage;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.CheckBoxStatus;
import jp.co.orangearch.workmanage.domain.constant.Flag;
import jp.co.orangearch.workmanage.domain.entity.JoinProject;
import jp.co.orangearch.workmanage.domain.entity.User;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

public class UserManageForm {

	@NotEmpty
	@Length(max=7)
	private String userId;
	@NotEmpty
	private String roleId;
	@NotEmpty
	private String affiliation;
	@NotEmpty
	private String position;

	@Pattern(regexp = "on")
	private String isPasswordInitialize;

	@Pattern(regexp = "on")
	private String isDeleted;

	private Integer uVersion;

	//project
	@NotEmpty
	private String projectId;
	
	@NotEmpty
	@DateValid(pattern="uuuu-M-d")
	private String fromDate;

	@DateValid(pattern="uuuu-M-d")
	private String toDate;

	private Integer jVersion;
	
	public UserManageForm() {
	}
	
	public UserManageForm(JoinProjectUser user) {
		userId = user.getUserId();
		roleId = user.getRoleId();
		affiliation = user.getAffiliation().toString();
		position = user.getPosition().toString();
		isDeleted = user.getDeleteFlag() == 0 ? "off" : "on";
		uVersion = user.getUVersion();
		
		projectId = user.getProjectId() == null ? null : user.getProjectId().toString();
		fromDate = user.getStartDate() == null ? null : user.getStartDate().toString();
		toDate = user.getEndDate() == LocalDate.MAX ? null : user.getEndDate().toString();
		jVersion = user.getJVersion();
	}

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
	public String getAffiliation() {
	    return affiliation;
	}

	/** 
	 * Sets the affiliation.
	 * 
	 * @param affiliation the affiliation
	 */
	public void setAffiliation(String affiliation) {
	    this.affiliation = affiliation;
	}

	/** 
	 * Returns the position.
	 * 
	 * @return the position
	 */
	public String getPosition() {
	    return position;
	}

	/** 
	 * Sets the position.
	 * 
	 * @param position the position
	 */
	public void setPosition(String position) {
	    this.position = position;
	}
	
	/** 
	 * Returns the isPasswordInitialize.
	 * 
	 * @return the isPasswordInitialize
	 */
	public String getIsPasswordInitialize() {
	    return isPasswordInitialize;
	}

	/**
	 * パスワード初期化フラグ取得
	 * 
	 * @return パスワード初期化フラグ
	 */
	public boolean isPasswordInitialize(){
		return StringUtils.isEmpty(isPasswordInitialize) ? false : isPasswordInitialize.equals("on");
	}
	
	/** 
	 * Sets the isPasswordInitialize.
	 * 
	 * @param isPasswordInitialize the isPasswordInitialize
	 */
	public void setIsPasswordInitialize(String isPasswordInitialize) {
	    this.isPasswordInitialize = isPasswordInitialize;
	}

	/** 
	 * Returns the isDeleted.
	 * 
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
	    return isDeleted;
	}

	/** 
	 * Sets the isDeleted.
	 * 
	 * @param isDeleted the isDeleted
	 */
	public void setIsDeleted(String isDeleted) {
	    this.isDeleted = isDeleted;
	}

	public Integer getUVersion(){
		return uVersion;
	}
	
	public void setUVersion(Integer value){
		uVersion = value;
	}

	/** 
	 * Returns the projectId.
	 * 
	 * @return the projectId
	 */
	public String getProjectId() {
	    return projectId;
	}

	/** 
	 * Sets the projectId.
	 * 
	 * @param projectId the projectId
	 */
	public void setProjectId(String projectId) {
	    this.projectId = projectId;
	}

	/** 
	 * Returns the fromDate.
	 * 
	 * @return the fromDate
	 */
	public String getFromDate() {
	    return fromDate;
	}

	/** 
	 * Sets the fromDate.
	 * 
	 * @param fromDate the fromDate
	 */
	public void setFromDate(String fromDate) {
	    this.fromDate = fromDate;
	}

	/** 
	 * Returns the toDate.
	 * 
	 * @return the toDate
	 */
	public String getToDate() {
	    return toDate;
	}

	/** 
	 * Sets the toDate.
	 * 
	 * @param toDate the toDate
	 */
	public void setToDate(String toDate) {
	    this.toDate = toDate;
	}


	public Integer getJVersion(){
		return jVersion;
	}
	
	public void setJVersion(Integer value){
		jVersion = value;
	}

	public User toUserEntity() {
		User entity = new User();
		entity.setAffiliation(Integer.valueOf(affiliation));
		entity.setDeleteFlag(CheckBoxStatus.TRUE.getValue().equals(isDeleted) ? Flag.TRUE : Flag.FALSE);
		entity.setPosition(Integer.valueOf(position));
		entity.setRoleId(roleId);
		entity.setUserId(userId);
		entity.setVersion(uVersion);
		return entity;
	}

	public JoinProject toJoinProjectEntity() {
		JoinProject entity = new JoinProject();
		entity.setUserId(userId);
		entity.setProjectId(Integer.valueOf(projectId));
		entity.setStartDate(DateUtils.convertToLocalDate(fromDate));
		entity.setEndDate(StringUtils.isEmpty(toDate) ? CalendarComponent.NOT_SPECIFIED_DATE : DateUtils.convertToLocalDate(toDate));
		entity.setVersion(jVersion);
		return entity;
	}
}
