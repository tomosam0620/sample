package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.co.orangearch.workmanage.domain.entity.WorkTime;

/**
 * 勤務時間サービスのIFクラスです。
 *
 * @author t-otsuka
 *
 */
public interface WorkTimeService {

	/**
	 * 指定日付と同一月の勤務情報を取得します。
	 *
	 * @param userId ユーザーID
	 * @param date 日付
	 * @return 勤務情報のリスト
	 */
	List<WorkTime> selectAll(String userId, LocalDate date);

	/**
	 * 指定日付の勤務情報を取得します。
	 *
	 * @param userId ユーザーID
	 * @param date 日付
	 * @return 勤務情報
	 */
	Optional<WorkTime> select(String userId, LocalDate date);

	/**
	 * 勤務情報を更新します。
	 *
	 * @param workTimes 勤務情報
	 */
	void update(WorkTime workTimes);

	/**
	 * 勤務情報表示月のリストを取得します。
	 *
	 * @return 勤務情報表示月(yyyy-MM形式)のmap
	 */
	Map<String, String> getMonthList();


}
