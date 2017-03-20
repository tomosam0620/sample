package jp.co.orangearch.workmanage.common.util;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import org.springframework.util.StringUtils;

import jp.co.orangearch.workmanage.common.constant.MessageId;
import jp.co.orangearch.workmanage.common.exception.SystemException;

public class DateUtils {

	/** 日時の書式定義ENUMです。 */
	public enum DateTimeFormat{
		/** yyyy/MM/dd */
		YYYY_MM_DD("uuuu/MM/dd"),
		/** uuuu-MM-dd HH:mm:ss.SSS */
		UUUU_MM_DD_HH_MM_SS_SSS("uuuu-MM-dd HH:mm:ss.SSS"),
		/** uuuu-MM-dd HH:mm:ss */
		UUUU_MM_DD_HH_MM_SS("uuuu-MM-dd HH:mm:ss"),
		/** uuuu-MM-dd */
		UUUU_MM_DD("uuuu-MM-dd"),
		/** uuuu-MM */
		UUUU_MM("uuuu-MM"),
		/** uuuu */
		UUUU("uuuu"),
		/** HH:mm:ss.SSS */
		HH_MM_SS_SSS("HH:mm:ss.SSS"),
		/** HH:mm:ss */
		HH_MM_SS("HH:mm:ss"),
		/** HH:mm */
		HH_MM("HH:mm");

		private String value;

		DateTimeFormat(String value){
			this.value = value;
		}

		public String getValue(){
			return value;
		}

		/**
		 * enumのvalueからenumの要素を取得します。
		 *
		 * @param value enumの値
		 * @return enumの要素
		 */
		public static DateTimeFormat getInstance(String value){
			for(DateTimeFormat item : DateTimeFormat.values()){
				if(item.getValue().equals(value)){
					return item;
				}
			}
			throw new SystemException(MessageId.S002, new String[]{value, DateTimeFormat.class.getName()});
		}

	}

	/**
	 * 日付型変換。
	 * <br>
	 * 文字列を{@link LocalDate}型に変換します。
	 *
	 * @param date 日付文字列(yyyy-MM-dd)
	 * @return 日付
	 */
	public static Date convert(String date){
		return Date.valueOf(date);
	}

	/**
	 * 日付型変換。
	 * <br>
	 * {@link LocalDate}型を文字列に変換します。
	 * 引数がnullの場合nullを返します。
	 *
	 * @param date 日付
	 * @return 日付文字列(yyyy-MM-dd)
	 */
	public static String convert(LocalDate date){
		return convert(date, DateTimeFormat.UUUU_MM_DD);
	}

	/**
	 * 日付型変換。
	 * <br>
	 * {@link LocalDate}型をpatternで指定した書式の文字列に変換します。
	 * 日付がnullの場合nullを返します。
	 *
	 * @param date 日付
	 * @param pattern 書式
	 * @return 日付文字列
	 */
	public static String convert(LocalDate date, DateTimeFormat pattern){
		if(date != null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getValue());
			return date.format(formatter);
		}
		return null;
	}

	/**
	 * 日付型変換。
	 * <br>
	 * {@link LocalDate}型をpatternで指定した書式の文字列に変換します。
	 * 日付がnullの場合nullを返します。
	 *
	 * @param date 日付
	 * @param pattern 書式
	 * @return 日付文字列
	 */
	public static String convert(Date date, DateTimeFormat pattern){
		if(date != null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getValue());
			return date.toLocalDate().format(formatter);
		}
		return null;
	}

	/**
	 * 時刻型変換。
	 * <br>
	 * {@link LocalTime}型をpatternで指定した書式の文字列に変換します。
	 * 時刻がnullの場合nullを返します。
	 *
	 * @param time 日付
	 * @param pattern 書式
	 * @return 日付文字列
	 */
	public static String convert(LocalTime time, DateTimeFormat pattern){
		if(time != null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getValue());
			return time.format(formatter);
		}
		return null;
	}

	/**
	 * 日付型変換。
	 * <br>
	 * 文字列を{@link LocalDate}型に変換します。
	 *
	 * @param date 日付文字列(yyyy-MM-dd)
	 * @return 日付
	 */
	public static LocalDate convertToLocalDate(String date){
		return convertToLocalDate(date, DateTimeFormat.UUUU_MM_DD);
	}

	/**
	 * 日付型変換。
	 * <br>
	 * 文字列を{@link LocalDate}型に変換します。
	 *
	 * @param date 日付文字列(yyyy-MM-dd)
	 * @param pattern 書式
	 * @return 日付
	 */
	public static LocalDate convertToLocalDate(String date, DateTimeFormat pattern){
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(pattern.getValue());
		return LocalDate.parse(date, datePattern);
	}

	/**
	 * 時刻型変換。
	 * <br>
	 * 文字列を{@link LocalTime}型に変換します。
	 *
	 * @param time 時刻文字列(HH:mm:ss)
	 * @return 時刻
	 */
	public static LocalTime convertToLocalTime(String time){
		return convertToLocalTime(time, DateTimeFormat.HH_MM_SS);
	}

	/**
	 * 時刻型変換。
	 * <br>
	 * 文字列を{@link LocalTime}型に変換します。
	 *
	 * @param time 時刻文字列(HH:mm:ss)
	 * @param pattern 書式
	 * @return 時刻
	 */
	public static LocalTime convertToLocalTime(String date, DateTimeFormat pattern){
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(pattern.getValue());
		return LocalTime.parse(date, datePattern);
	}

	/**
	 * 月初日を取得します。
	 * パラメータがnullの場合は当月の月初日を返却します。
	 *
	 * @param date 日付文字列(yyyy-MM-dd)
	 * @return 月初日(yyyy-MM-dd)
	 */
	public static LocalDate getFirstDayOfMonth(String date){
		LocalDate targetDay = convertToLocalDate(date);
		return getFirstDayOfMonth(targetDay);
	}

	/**
	 * 月初日を取得します。
	 * パラメータがnullの場合は当月の月初日を返却します。
	 *
	 * @param date 日付文字列
	 * @param pattern 書式
	 * @return 月初日(yyyy-MM-dd)
	 */
	public static LocalDate getFirstDayOfMonth(String date, DateTimeFormat pattern){
		LocalDate targetDay = convertToLocalDate(date, pattern);
		return getFirstDayOfMonth(targetDay);
	}

	/**
	 * 月初日を取得します。
	 * パラメータがnullの場合は当月の月初日を返却します。
	 *
	 * @return 月初日
	 */
	public static LocalDate getFirstDayOfMonth(Date date){
		return getFirstDayOfMonth(date == null ? null : date.toLocalDate());
	}

	/**
	 * 月初日を取得します。
	 * パラメータがnullの場合は当月の月初日を返却します。
	 *
	 * @return 月初日
	 */
	public static LocalDate getFirstDayOfMonth(LocalDate date){

		LocalDate targetDay = getCurrentDate();
		if(date != null){
			targetDay = date;
		}
		return LocalDate.of(targetDay.getYear(), targetDay.getMonth(), 1);
	}


	/**
	 * 月末日を取得します。
	 * パラメータがnullの場合は当月の月末日を返却します。
	 *
	 * @return 月末日(yyyy-MM-dd)
	 */
	public static LocalDate getFinalDayOfMonth(String date){
		LocalDate targetDay = convertToLocalDate(date);
		return getFinalDayOfMonth(targetDay);
	}

	/**
	 * 月末日を取得します。
	 * パラメータがnullの場合は当月の月末日を返却します。
	 *
	 * @return 月末日(yyyy-MM-dd)
	 */
	public static LocalDate getFinalDayOfMonth(Date date){
		return getFinalDayOfMonth(date == null ? null : date.toLocalDate());
	}

	/**
	 * 月末日を取得します。
	 * パラメータがnullの場合は当月の月末日を返却します。
	 *
	 * @return 当月の月末日
	 */
	public static LocalDate getFinalDayOfMonth(LocalDate date){
		LocalDate targetDay = getCurrentDate();
		if(date != null){
			targetDay = date;
		}
		int lastDayOfMonth = LocalDate.of(targetDay.getYear(), targetDay.getMonth(), 1).lengthOfMonth();
		return LocalDate.of(targetDay.getYear(), targetDay.getMonth(), lastDayOfMonth);
	}


	/**
	 * 営業日フラグ取得
	 *
	 * @return 営業日フラグ(1：営業日／2：土曜日／3：休日(日曜日、祝日)）
	 */
	public static int getBusinessDayFlag(LocalDate date){
		int businessDayFlag = 1;
		if(DayOfWeek.SUNDAY.equals(date.getDayOfWeek())){
			businessDayFlag = 3;
		} else if(DayOfWeek.SATURDAY.equals(date.getDayOfWeek())){
			businessDayFlag = 2;
		}
		//TODO:祝日カレンダー対応
		return businessDayFlag;
	}
	
	/**
	 * 休日判定。
	 *
	 * @param date 評価日(yyyy-MM-dd)
	 * @return true(休日)／false(営業日)
	 */
	public static boolean isHoliday(String date){
		LocalDate targetDate = convertToLocalDate(date);
		return isHoliday(targetDate);
	}

	/**
	 * 休日判定。
	 *
	 * @param date 評価日
	 * @return true(休日)／false(営業日)
	 */
	public static boolean isHoliday(LocalDate date){
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return !(DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek));

	}

//	/**
//	 * 現在日付取得
//	 *
//	 * @return 現在日付
//	 */
//	public static Date getCurrentDate(){
//		return Date.valueOf(LocalDate.now());
//	}

	/**
	 * 現在日付取得
	 *
	 * @return 現在日付
	 */
	public static LocalDate getCurrentDate(){
		return LocalDate.now();
	}

	/**
	 * 日付文字列の妥当性チェックを行います。
	 * <br>
	 * 書式と異なる日付文字列や存在しない日付文字列を指定した場合falseを返します。
	 * <p>
	 * 例)
	 * <li>date："2017-01-01"、format：uuuu-MM-dd の場合true
	 * <li>date："2017-02-28"、format：uuuu-MM-dd の場合true
	 * <li>date："2017-02-29"、format：uuuu-MM-dd の場合false
	 * <li>date："2017/01/01"、format：uuuu-MM-dd の場合false
	 * <li>date："2017-1-1"、format：uuuu-MM-dd の場合false
	 * <li>date："2017-01-1a"、format：uuuu-MM-dd の場合false
	 * <li>date："2017-01-001"、format：uuuu-MM-dd の場合false
	 * <li>date：""、format：uuuu-MM-dd の場合false
	 * <li>date：null、format：uuuu-MM-dd の場合false
	 * </p>
	 * @param date 日付文字列
	 * @param pattern 書式({@link DateTimeFormat}参照)
	 * @return true(妥当な文字列)／false(妥当でない文字列)
	 * @see {@link LocalDate} {@link DateTimeFormatter}
	 */
	public static boolean isValidDate(String date, DateTimeFormat pattern){
		if(StringUtils.isEmpty(date)){
			return false;
		}

		try{
			switch(pattern){
			case YYYY_MM_DD:
			case UUUU_MM_DD:
				LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern.getValue()).withResolverStyle(ResolverStyle.STRICT));
				break;
			case UUUU_MM:
			{
				String value = date + "-01";
				LocalDate.parse(value, DateTimeFormatter.ofPattern(DateTimeFormat.UUUU_MM_DD.getValue()).withResolverStyle(ResolverStyle.STRICT));
				break;
			}
			case UUUU:
			{
				String value = date + "-01-01";
				LocalDate.parse(value, DateTimeFormatter.ofPattern(DateTimeFormat.UUUU_MM_DD.getValue()).withResolverStyle(ResolverStyle.STRICT));
				break;
			}
			case UUUU_MM_DD_HH_MM_SS_SSS:
			case UUUU_MM_DD_HH_MM_SS:
				LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern.getValue()).withResolverStyle(ResolverStyle.STRICT));
				break;
			case HH_MM:
			case HH_MM_SS:
			case HH_MM_SS_SSS:
				LocalTime.parse(date, DateTimeFormatter.ofPattern(pattern.getValue()).withResolverStyle(ResolverStyle.STRICT));
				break;
			default:
				return false;
			}
			return true;
		}catch (DateTimeParseException e){
			return false;
		}
	}

	/**
	 * 日時の順序性をチェックします。
	 *
	 * @param from 開始日時
	 * @param to 終了日時
	 * @param pattern 日付文字列の書式。{@link DateTimeFormat}参照)
	 * @return true(終了日時が開始日時より未来)／false(終了日時が開始日時より過去)
	 * @see {@link LocalDate} {@link DateTimeFormatter}
	 */
	public static boolean isAfter(String from, String to, DateTimeFormat pattern) {
		LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern(pattern.getValue()));
		LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern(pattern.getValue()));
		return toDate.isAfter(fromDate);
	}

}
