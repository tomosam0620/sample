package jp.co.orangearch.workmanage.component;

import java.util.List;

import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.BreakTime;

/**
 * 休憩時間コンポーネントのIFクラスです。
 *
 * @author t-otsuka
 *
 */
public interface BreakTimeComponent {

	/**
	 * 勤務帯の休憩時間を取得します。
	 *
	 * @param operation 勤務帯
	 * @return 休憩時間のリスト
	 */
	List<BreakTime> getBreakTimes(WorkTimeCode workTimeCd);
	
}
