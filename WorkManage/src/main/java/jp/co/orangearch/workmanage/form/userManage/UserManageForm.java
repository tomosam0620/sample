package jp.co.orangearch.workmanage.form.userManage;

import java.time.LocalDate;
import java.util.Optional;

import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

public class UserManageForm {

	private String userId;
	private String roleId;
	private Integer affiliation;
	private Integer position;
	private Integer deleteFlag;
	private Integer projectId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer version;

	public UserManageForm() {
	}
	
	public UserManageForm(Optional<JoinProjectUser> user) {
		if(user.isPresent()){
			userId = user.get().getUserId();
			roleId = user.get().getRoleId();
			affiliation = user.get().getAffiliation();
			position = user.get().getPosition();
			deleteFlag = user.get().getDeleteFlag();
			projectId = user.get().getProjectId();
			startDate = user.get().getStartDate();
			endDate = user.get().getEndDate();
			version = user.get().getVersion();
		}
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
	
	public Integer getVersion(){
		return version;
	}
	
	public void setVersion(Integer value){
		version = value;
	}
}
