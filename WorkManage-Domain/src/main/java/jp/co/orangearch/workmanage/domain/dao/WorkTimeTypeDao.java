package jp.co.orangearch.workmanage.domain.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;

/**
 * WorkTimeTypeのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface WorkTimeTypeDao {

    /**
     * @param workTimeCd
     * @return the WorkTimeType entity
     */
    @Select
    Optional<WorkTimeType> selectById(WorkTimeCode workTimeCd);

    /**
     * @param workTimeCd
     * @param version
     * @return the WorkTimeType entity
     */
    @Select(ensureResult = true)
    Optional<WorkTimeType> selectByIdAndVersion(WorkTimeCode workTimeCd, Integer version);

    @Select
	List<WorkTimeType> selectAll();
	
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(WorkTimeType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(WorkTimeType entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(WorkTimeType entity);

}