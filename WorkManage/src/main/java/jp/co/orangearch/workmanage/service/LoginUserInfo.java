package jp.co.orangearch.workmanage.service;

import org.springframework.security.core.authority.AuthorityUtils;

import jp.co.orangearch.workmanage.domain.entity.User;

/**
 * ログインユーザ情報を保持するクラスです。
 *
 * @author t-otsuka
 *
 */
public class LoginUserInfo extends org.springframework.security.core.userdetails.User{

	/** シリアルバージョン。 */
	private static final long serialVersionUID = 1L;

	/** ロールID。 */
	private String roleId;

	/** 所属。 */
	private Integer affiliation;

	/** 役職。 */
	private Integer position;

	/**
	 * コンストラクタ。
	 *
	 * @param user ユーザーテーブル情報
	 */
	public LoginUserInfo(User user){
		super(user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + user.getRoleId()));
		roleId = user.getRoleId();
		affiliation = user.getAffiliation();
		position = user.getPosition();
	}


	/**
	 * ロールID取得。
	 *
	 * @return ロールID
	 */
	public String getRoleId(){
		return roleId;
	}

	/**
	 * 所属取得。
	 *
	 * @return 所属
	 */
	public Integer getAffiliation(){
		return affiliation;
	}

	/**
	 * 役職取得。
	 *
	 * @return 役職
	 */
	public Integer getPosition(){
		return position;
	}

}
