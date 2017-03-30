package jp.co.orangearch.workmanage.domain.dao;

import java.time.LocalDate;
import jp.co.orangearch.workmanage.domain.entity.Horiday;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * HoridayのDaoインターフェースクラスです。
 * 
 */
@ConfigAutowireable
@Dao
public interface HoridayDao {

    /**
     * @param date
     * @return the Horiday entity
     */
    @Select
    Optional<Horiday> selectById(LocalDate date);

    /**
     * @param date
     * @param version
     * @return the Horiday entity
     */
    @Select(ensureResult = true)
    Optional<Horiday> selectByIdAndVersion(LocalDate date, Integer version);

    @Select
	List<Horiday> selectAll();
    
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(Horiday entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Horiday entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Horiday entity);
}