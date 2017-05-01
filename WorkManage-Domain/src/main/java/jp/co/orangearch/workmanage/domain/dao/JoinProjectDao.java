package jp.co.orangearch.workmanage.domain.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.domain.entity.JoinProject;

/**
 * JoinProjectのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface JoinProjectDao {

    /**
     * @param userId
     * @param projectId
     * @param startDate
     * @return the JoinProject entity
     */
    @Select
    Optional<JoinProject> selectById(String userId, Integer projectId, LocalDate startDate);

    /**
     * @param userId
     * @param projectId
     * @param startDate
     * @param version
     * @return the JoinProject entity
     */
    @Select(ensureResult = true)
    Optional<JoinProject> selectByIdAndVersion(String userId, Integer projectId, LocalDate startDate, Integer version);

    /**
     * 参画プロジェクト情報取得。
     * 対象日に参画しているプロジェクトを取得します。
     * 
     * @param userId ユーザーID
     * @param targetDate 対象日
     * @return 参画しているプロジェクトのリスト
     */
    @Select
    List<JoinProject> selectByUserIdAndDate(String userId, LocalDate targetDate);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(JoinProject entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(JoinProject entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(JoinProject entity);
}