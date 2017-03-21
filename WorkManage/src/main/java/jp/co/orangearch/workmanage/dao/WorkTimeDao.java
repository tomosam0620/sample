package jp.co.orangearch.workmanage.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.orangearch.workmanage.entity.WorkTime;

/**
 * WORK_TIMEテーブルのDaoインターフェースクラスです。
 * 実体は「.apt_generated」配下に自動生成されます。
 * 本クラスで定義したメソッドと同名のsqlファイルをsrc/main/resources/META-INF配下に本クラスと同一階層に配置すること。
 *
 *
 * @author t-otsuka
 *
 */
@ConfigAutowireable
@Dao
public interface WorkTimeDao {

	/**
	 * 勤務情報を取得します。
	 *
	 * @param userid ユーザーID
	 * @param date 勤務日
	 * @param options sql option
	 * @return 勤務情報のリスト
	 */
	@Select
//	WorkTime selectByIdAndDate(String userId, Date date, SelectOptions options);
	Optional<WorkTime> selectByIdAndDate(String userId, LocalDate date, SelectOptions options);


	/**
	 * ユーザの勤務情報を取得します。
	 *
	 * @param userid ユーザーID
	 * @param from_date 取得開始日
	 * @param to_date 取得終了日
	 * @return 勤務情報のリスト
	 */
	@Select
	List<WorkTime> selectByIdAndMonth(String userId, LocalDate from_date, LocalDate to_date);


	@Insert(excludeNull=true)
	int insert(WorkTime entity);

	@Update
//	@Update(excludeNull=true)
	int update(WorkTime entity);

}
