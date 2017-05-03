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
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;

/**
 * TransportionExpenseのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 */
@ConfigAutowireable
@Dao
public interface TransportionExpenseDao {

	/**
	 * 指定期間のユーザの交通費情報取得。
	 * @param userId ユーザーID
	 * @param workDate 日付
	 * @param transportNumber 交通費通番
	 * @return 交通費情報
	 */
	@Select
	Optional<TransportionExpense> selectById(String userId, LocalDate workDate, Integer transportNumber);
	
	/**
	 * 指定日のユーザの交通費情報取得。
	 * 
	 * @param userId ユーザーID
	 * @param workDate 日付
	 * @param transportNumber 交通費通番
	 * @param version バージョン
	 * @return 交通費情報
	 */
	@Select
	Optional<TransportionExpense> selectByIdAndVersion(String userId, LocalDate workDate, Integer transportNumber, Integer version);
	
	/**
	 * 指定期間のユーザの交通費情報取得。
	 * @param userId ユーザーID
	 * @param from 日付from
	 * @param to 日付to
	 * @return 交通費情報のリスト
	 */
	@Select
	List<TransportionExpense> selectByUserIdAndDateRange(String userId, LocalDate from, LocalDate to);
	
    /**
	 * 指定日のユーザの交通費情報取得。
	 * 
	 * @param userId ユーザーID
	 * @param workDate 日付
     * @param options 検索オプション
     * @return 交通費情報のリスト
     */
	@Select
    List<TransportionExpense> selectByUserIdAndWorkDate(String userId, LocalDate workDate, SelectOptions options);
    
    /**
     * @param entity
     * @return affected rows
     */
    @Insert(excludeNull=true)
    int insert(TransportionExpense entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(TransportionExpense entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(TransportionExpense entity);

}