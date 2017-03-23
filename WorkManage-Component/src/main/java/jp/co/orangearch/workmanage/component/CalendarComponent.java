package jp.co.orangearch.workmanage.component;

import java.time.LocalDate;

import jp.co.orangearch.workmanage.domain.constant.HoridayType;

/**
 * カレンダーコンポーネントのIFクラスです。
 *
 * @author t-otsuka
 *
 */
public interface CalendarComponent {

	/**
	 * 休日種別を取得します。
	 *
	 * @param date 日付
	 * @return 休日種別{@link HoridayType}
	 */
	HoridayType getHoridayType(LocalDate date);

}