package jp.co.orangearch.workmanage.service;

import org.springframework.validation.BindingResult;

/**
 * パスワード変更サービスのIFクラスです。
 *
 * @author t-otsuka
 *
 */
public interface PasswordChangeService {

	/**
	 * ユーザー情報のバージョンを取得します。
	 *
	 * @param userId ユーザーID
	 * @return ユーザー情報のバージョン
	 */
	Integer selectUserVersion(String userId);
	
	/**
	 * ユーザー情報を更新します。
	 * 
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @param newPassword 新しいパスワード
	 * @param version バージョン
	 * @param bindingResult 入力エラー情報
	 */
	void update(String userId, String password, String newPassword, Integer version, BindingResult bindingResult);

	/**
	 * パスワードを暗号化します。
	 * 
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @return 暗号化されたパスワード
	 */
	String encrypt(String userId, String password);

}
