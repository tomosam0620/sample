package jp.co.orangearch.workmanage.form.userManage;

import org.springframework.util.StringUtils;

public class SearchForm {
	private String roleId;
	private String affiliation;
	private String position;
	private String project;
	
	public String getRoleId(){
		return StringUtils.isEmpty(roleId) ? null : roleId;
	}
	public void setRoleId(String value){
		roleId = value;
	}
	
	public String getAffiliation(){
		return affiliation;
	}
	public Integer getAffiliationAsInt(){
		return StringUtils.isEmpty(affiliation) ? null : Integer.valueOf(affiliation);
	}
	public void setAffiliation(String value){
		affiliation = value;
	}
	
	public String getPosition(){
		return position;
	}
	public Integer getPositionAsInt(){
		return StringUtils.isEmpty(position) ? null : Integer.valueOf(position);
	}
	public void setPosition(String value){
		position = value;
	}
	
	public String getProject(){
		return project;
	}
	public Integer getProjectAsInt(){
		return StringUtils.isEmpty(project) ? null : Integer.valueOf(project);
	}
	public void setProject(String value){
		project = value;
	}
	
}
