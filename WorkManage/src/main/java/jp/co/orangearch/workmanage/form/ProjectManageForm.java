package jp.co.orangearch.workmanage.form;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.entity.Project;

public class ProjectManageForm {

	@NotEmpty
	private String projectName;
	@NotEmpty
	private String customerId;
	@NotEmpty
	private String affiliation;
	@DateValid
	private String startDate;
	@DateValid
	private String endDate;
	
	private Integer version;

	@AssertTrue(message="終了日は開始日より未来の日付を入力してください。")
	boolean isValidDateOrder(){
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
			return true;
		}
		return DateUtils.convertToLocalDate(startDate).isBefore(DateUtils.convertToLocalDate(endDate));
	}
	
	public String getProjectName(){
		return projectName;
	}
	public void setProjectName(String value){
		projectName = value;
	}
	
	public String getAffiliation(){
		return affiliation;
	}
	public void setAffiliation(String value){
		affiliation = value;
	}

	public String getCustomerId(){
		return customerId;
	}
	public void setCustomerId(String value){
		customerId = value;
	}

	public String getStartDate(){
		return startDate;
	}
	public void setStartDate(String value){
		startDate = value;
	}
	
	public String getEndDate(){
		return endDate;
	}
	public void setEndDate(String value){
		endDate = value;
	}

	public Integer getVersion(){
		return version;
	}
	public void setVersion(Integer value){
		version = value;
	}

	public Project toEntity() {
		Project entity = new Project();
		entity.setAffiliation(Integer.valueOf(affiliation));
		entity.setProjectName(projectName);
		entity.setCustomerId(Integer.valueOf(customerId));
		entity.setStartDate(DateUtils.convertToLocalDate(startDate));
		entity.setEndDate(DateUtils.convertToLocalDate(endDate));
		return entity;
	}
	
}
