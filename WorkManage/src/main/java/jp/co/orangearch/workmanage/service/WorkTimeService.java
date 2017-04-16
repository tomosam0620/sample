package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.dto.OperationTime;

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
	List<OperationTime> selectWorkTimeInfoInMonth(String userId, LocalDate date);

	List<TransportionExpense> selectTransportionInfo(String userId, LocalDate date);
	
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
	 * CSVを作成します。
	 * @param userId ユーザID
	 * @param from_date 開始日
	 * @param to_date 終了日
	 * @return csvのbyte配列
	 */
	byte[] createCsv(String userId, LocalDate from_date, LocalDate to_date);

	List<WorkTimeType> getWorkTimeType();
}
