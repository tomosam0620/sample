package jp.co.orangearch.workmanage.domain.dao;

import jp.co.orangearch.workmanage.domain.entity.WorkTimeStatus;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import java.util.Optional;

/**
 * WorkTimeStatusのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface WorkTimeStatusDao {

    /**
     * @param userId
     * @param workMonth
     * @return the WorkTimeStatus entity
     */
    @Select
    Optional<WorkTimeStatus> selectById(String userId, String workMonth);

    /**
     * @param userId
     * @param workMonth
     * @param version
     * @return the WorkTimeStatus entity
     */
    @Select(ensureResult = true)
    Optional<WorkTimeStatus> selectByIdAndVersion(String userId, String workMonth, Integer version);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(WorkTimeStatus entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(WorkTimeStatus entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(WorkTimeStatus entity);
}