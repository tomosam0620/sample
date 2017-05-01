package jp.co.orangearch.workmanage.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jp.co.orangearch.workmanage.domain.constant.HoridayType;

/**
 * カレンダーコンポーネントのIFクラスです。
 *
 * @author t-otsuka
 *
 */
public interface CalendarComponent {

	/** 日付未指定 */
	public static LocalDate NOT_SPECIFIED_DATE = LocalDate.of(9999, 12, 31);
	
	/**
	 * 休日種別を取得します。
	 *
	 * @param date 日付
	 * @return 休日種別{@link HoridayType}
	 */
	HoridayType getHoridayType(LocalDate date);
	
	/**
	 * 営業日判定。
	 *
	 * @param date 評価日(yyyy-MM-dd)
	 * @return true(営業日)／false(休日)
	 */
	public boolean isBusinessDay(String date);
	
	/**
	 * 営業日判定。
	 *
	 * @param date 評価日
	 * @return true(営業日)／false(休日)
	 */
	boolean isBusinessDay(LocalDate date);
	
	/**
	 * システム日時取得。
	 * @return システム日時
	 */
	LocalDateTime getSystemDatetime();
	

	/**
	 * システム日取得。
	 * @return システム日
	 */
	LocalDate getSystemDate();
	
	/**
	 * システム時刻取得。
	 * @return システム時刻
	 */
	LocalTime getSystemime();
	
}
