package jp.co.orangearch.workmanage.dao;

import java.time.LocalDate;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.entity.Horiday;

/**
 */
@ConfigAutowireable
@Dao
public interface HoridayDao {

    /**
     * @param date
     * @return the Horiday entity
     */
    @Select
    Horiday selectById(LocalDate date);

    /**
     * @param date
     * @param version
     * @return the Horiday entity
     */
    @Select(ensureResult = true)
    Horiday selectByIdAndVersion(LocalDate date, Integer version);

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

    @Select
	List<Horiday> selectAll();
}