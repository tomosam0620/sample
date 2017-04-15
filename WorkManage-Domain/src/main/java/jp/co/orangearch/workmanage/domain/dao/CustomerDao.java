package jp.co.orangearch.workmanage.domain.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.orangearch.workmanage.domain.entity.Customer;

/**
 * CustomerのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface CustomerDao {

    /**
     * @param customerId
     * @return the Customer entity
     */
    @Select
    Optional<Customer> selectById(Integer customerId);

    /**
     * @param customerId
     * @param version
     * @return the Customer entity
     */
    @Select(ensureResult = true)
    Optional<Customer> selectByIdAndVersion(Integer customerId, Integer version);

    /**
     * 顧客一覧取得。
     * @return list of Customer entity
     */
    @Select
    List<Customer> selectAll();

    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(Customer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(Customer entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(Customer entity);
}