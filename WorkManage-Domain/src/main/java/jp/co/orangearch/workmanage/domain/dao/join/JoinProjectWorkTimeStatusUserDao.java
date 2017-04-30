package jp.co.orangearch.workmanage.domain.dao.join;

import java.time.LocalDate;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectWorkTimeStatusUser;

/**
 * JoinProjectとUserの結合テーブルのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface JoinProjectWorkTimeStatusUserDao {

	/**
	 * 勤務報告の締め状態を取得します。
	 * 
	 * @param affiliation 所属
	 * @param projectId プロジェクトID
	 * @param userId ユーザーID
	 * @param fromDate 開始日
	 * @param toDate 終了日
	 * @param option 検索オプション
	 * @return 勤務報告の締め状態のリスト
	 */
    @Select
	List<JoinProjectWorkTimeStatusUser> selectByCondition(Integer affiliation, Integer projectId, String userId, LocalDate fromDate, LocalDate toDate, SelectOptions option);

}
