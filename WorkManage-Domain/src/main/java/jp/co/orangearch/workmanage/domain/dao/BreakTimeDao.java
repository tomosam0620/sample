package jp.co.orangearch.workmanage.domain.dao;

import java.time.LocalTime;
import jp.co.orangearch.workmanage.domain.entity.BreakTime;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * BreakTimeのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface BreakTimeDao {

    /**
     * @param workTimeCd
     * @param breakStartTime
     * @return the BreakTime entity
     */
    @Select
    Optional<BreakTime> selectById(Integer workTimeCd, LocalTime breakStartTime);

    /**
     * @param workTimeCd
     * @param breakStartTime
     * @param version
     * @return the BreakTime entity
     */
    @Select(ensureResult = true)
    Optional<BreakTime> selectByIdAndVersion(Integer workTimeCd, LocalTime breakStartTime, Integer version);

    @Select
	List<BreakTime> selectAll();
	
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(BreakTime entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(BreakTime entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(BreakTime entity);

}