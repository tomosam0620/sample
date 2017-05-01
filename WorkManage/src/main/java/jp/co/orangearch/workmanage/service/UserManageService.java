package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jp.co.orangearch.workmanage.domain.entity.JoinProject;
import jp.co.orangearch.workmanage.domain.entity.User;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

/**
 * ユーザー管理サービスのインターフェースクラスです。
 * 
 * @author t-otsuka
 *
 */
public interface UserManageService {

	/**
	 * ユーザー情報を取得します。
	 * 
	 * @param roleId ロールID
	 * @param affiliation 所属
	 * @param position 役職
	 * @param projectId プロジェクトID
	 * @return ユーザー情報のリスト
	 */
	List<JoinProjectUser> select(String roleId, Integer affiliation, Integer position, Integer projectId);

	/**
	 * ユーザー情報を取得します。
	 * 
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	List<JoinProjectUser> select(String userId);
	
	/**
	 * ユーザー情報を取得します。
	 * 
	 * @param affiliation 所属
	 * @param projectId プロジェクトID
	 * @param userId ユーザーID
	 * @param fromDate 開始日
	 * @param toDate 終了日
	 * @return ユーザー情報
	 */
	Optional<JoinProjectUser> select(Integer affiliationCd, Integer projectId, String userId,
			LocalDate fromDate, LocalDate toDate);
	
	/**
	 * ユーザー情報を更新します。
	 * 該当ユーザーが登録されていない場合は登録します。
	 * 
	 * @param userEntity ユーザー情報
	 * @param joinProjectEntity 参画プロジェクト情報
	 * @param isInitializePassword パスワード初期化フラグ
	 */
	void update(User userEntity, JoinProject joinProjectEntity, boolean isInitializePassword);
}
