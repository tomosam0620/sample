package jp.co.orangearch.workmanage.form.workTime;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.validator.DateValid;

public class SearchForm implements Serializable{
	/** シリアルバージョン */
	private static final long serialVersionUID = 1L;
	@DateValid(pattern="uuuu-M")
	private String fromMonth;

	@DateValid(pattern="uuuu-M")
	private String toMonth;

	private String affiliation;

	private String project;

	private String userId;
	
	public String getFromMonth(){
		return StringUtils.isEmpty(fromMonth) ? null : fromMonth;
	}
	public void setFromMonth(String value){
		fromMonth = value;
	}
	
	public String getToMonth(){
		return StringUtils.isEmpty(toMonth) ? null : toMonth;
	}
	public void setToMonth(String value){
		toMonth = value;
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
	
	public String getProject(){
		return project;
	}
	public Integer getProjectAsInt(){
		return StringUtils.isEmpty(project) ? null : Integer.valueOf(project);
	}
	public void setProject(String value){
		project = value;
	}
	
	public String getUserId(){
		return userId;
	}
	public void setUserId(String value){
		userId = value;
	}
	
}
