package jp.co.orangearch.workmanage.domain.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.domain.entity.Project;

/**
 * ProjectのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface ProjectDao {

    /**
     * @param projectId
     * @return the Project entity
     */
    @Select
    Optional<Project> selectById(Integer projectId);

    /**
     * @param projectId
     * @param version
     * @return the Project entity
     */
    @Select(ensureResult = true)
    Optional<Project> selectByIdAndVersion(Integer projectId, Integer version);

    /**
     * プロジェクト一覧取得。
     * @return list of Project entity
     */
    @Select
    List<Project> selectAll();

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(Project entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Project entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Project entity);
}