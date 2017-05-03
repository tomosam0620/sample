package jp.co.orangearch.workmanage.form.passwordChange;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

public class ChangePassowrdForm implements Serializable{

	/** シリアルバージョン。 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String userId;
	@NotEmpty
	private String password;
	@NotEmpty
	private String newPassword;
	@NotEmpty
	private String confirmPassword;
	private Integer version;
	
	/**
	 * 新しいパスワードと確認パスワードが等しいことをチェックします。
	 * <br>
	 * 新しいパスワード、確認パスワードいづれかが未入力の場合はチェックせずtrueを返します。
	 * 
	 * @return true(等しい)／false(異なる)
	 */
	@AssertTrue(message="{V006}")
	public boolean isSameWordNewAndConfirm(){
		if(StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)){
			return true;
		}
		return newPassword.equals(confirmPassword);
	}
	
	/**
	 * パスワードと新しいパスワードが異なることをチェックします。
	 * <br>
	 * パスワード、新しいパスワードいづれかが未入力の場合はチェックせずtrueを返します。
	 * 
	 * @return true(異なる)／false(等しい)
	 */
	@AssertTrue(message="{V007}")
	public boolean isNotSameOldAndNew(){
		if(StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)){
			return true;
		}
		return !password.equals(newPassword);
	}
	
	public String getUserId(){
		return userId;
	}

	public void setUserId(String value){
		userId = value;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String value){
		password = value;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setNewPassword(String value){
		newPassword = value;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}

	public void setConfirmPassword(String value){
		confirmPassword = value;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer value) {
		version = value;
	}

}
