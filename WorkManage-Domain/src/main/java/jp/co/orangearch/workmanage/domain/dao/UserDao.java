package jp.co.orangearch.workmanage.domain.dao;

import jp.co.orangearch.workmanage.domain.entity.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;
import java.util.Optional;

/**
 * UserのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 * 
 * @author t-otsuka
 */
@ConfigAutowireable
@Dao
public interface UserDao {

    /**
     * @param userId
     * @return the User entity
     */
    @Select
    Optional<User> selectById(String userId);

    /**
     * @param userId
     * @param version
     * @return the User entity
     */
    @Select(ensureResult = true)
    Optional<User> selectByIdAndVersion(String userId, Integer version);

	@Select
	List<User> selectAll();
	
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(User entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(User entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(User entity);
}