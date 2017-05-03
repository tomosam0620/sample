package jp.co.orangearch.workmanage.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.validation.BindingResult;

import jp.co.orangearch.workmanage.domain.constant.ClosingState;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeStatus;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.dto.OperationInfoOfMonth;
import jp.co.orangearch.workmanage.dto.WorkTimesOfMonth;

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
	WorkTimesOfMonth selectWorkTimeInfoInMonth(String userId, LocalDate date);

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
	void update(WorkTime workTimes, BindingResult errors);

	/**
	 * CSVを作成します。
	 * @param userId ユーザID
	 * @param from_date 開始日
	 * @param to_date 終了日
	 * @return csvのbyte配列
	 */
	byte[] createCsv(String userId, LocalDate from_date, LocalDate to_date);

	/**
	 * 勤務帯情報を取得します。
	 * 
	 * @return 勤務帯情報のリスト
	 */
	List<WorkTimeType> getWorkTimeType();

	/**
	 * 勤務一覧情報を取得します。
	 * 
	 * @param fromMonth 開始月
	 * @param toMonth 終業月
	 * @param affiliationCd 所属
	 * @param projectId プロジェクトID
	 * @param userId ユーザーID
	 * @param options 検索オプション情報
	 * @return 勤務一覧情報
	 */
	List<OperationInfoOfMonth> selectSummary(String fromMonth, String toMonth, Integer affiliationCd, Integer projectId, String userId, SelectOptions options);

	/**
	 * ステータスを更新します。
	 * 
	 * @param userId ユーザーID
	 * @param month 月
	 * @param status ステータス 
	 * @param version バージョン
	 * @param errors エラー情報
	 */
	void updateStatus(String userId, String month, ClosingState statusAsEnum, Integer version, BindingResult errors);

	/**
	 * ステータスを取得します。
	 * 
	 * @param userId ユーザーID
	 * @param month 月
	 * @return ステータス情報
	 */
	Optional<WorkTimeStatus> selectStatusVersion(String userId, String showMonthDate);
}
