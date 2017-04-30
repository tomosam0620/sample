package jp.co.orangearch.workmanage.domain.dao.join;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectUser;

/**
 * JoinProjectとUserの結合テーブルのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface JoinProjectUserDao {

    /**
     * @param targetDate
     * @return list of Join entity
     */
    @Select
    List<JoinProjectUser> selectAll(LocalDate targetDate);

    @Select
	List<JoinProjectUser> selectByCondition(String roleId, Integer affiliation, Integer position, Integer projectId,
			LocalDate targetDate, SelectOptions option);

    @Select
	Optional<JoinProjectUser> selectById(String userId);

    @Select
    List<JoinProjectUser> selectByConditions(Integer affiliationCd, Integer projectId, String userId,
			LocalDate fromDate, LocalDate toDate, SelectOptions options);

}
