package jp.co.orangearch.workmanage.component.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.HoridayType;
import jp.co.orangearch.workmanage.domain.dao.HoridayDao;
import jp.co.orangearch.workmanage.domain.entity.Horiday;

/**
 * カレンダーコンポーネントクラスです。
 * <br>
 * 起動時に休日テーブルの中身を全てキャッシュします。
 *
 * @author t-otsuka
 *
 */
@Component
public class CalendarComponentImpl implements CalendarComponent{

	@Autowired
	private HoridayDao horidayDao;

	private static Map<LocalDate, Horiday> cache = new HashMap<LocalDate, Horiday>();

	/**
	 * 休日種別を取得します。
	 *
	 * @param date 日付
	 * @return 休日種別{@link HoridayType}
	 */
	public HoridayType getHoridayType(LocalDate date){
		Horiday registedHoriday = cache.get(date);
		if(registedHoriday != null){
			return HoridayType.HORIDAY;
		}

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		if(DayOfWeek.SUNDAY.equals(dayOfWeek)){
			return HoridayType.HORIDAY;
		} else if(DayOfWeek.SATURDAY.equals(dayOfWeek)){
			return HoridayType.SATURDAY;
		}
		return HoridayType.BISUNESS_DAY;
	}

	/**
	 * 営業日判定。
	 *
	 * @param date 評価日(yyyy-MM-dd)
	 * @return true(営業日)／false(休日)
	 */
	public boolean isBusinessDay(String date){
		LocalDate targetDate = DateUtils.convertToLocalDate(date);
		return isBusinessDay(targetDate);
	}

	/**
	 * 営業日判定。
	 *
	 * @param date 評価日
	 * @return true(営業日)／false(休日)
	 */
	public boolean isBusinessDay(LocalDate date){
		HoridayType horydayTime = getHoridayType(date);
		return HoridayType.BISUNESS_DAY.equals(horydayTime);
	}

	@PostConstruct
	private void cache(){
		cache = horidayDao.selectAll().stream().collect(Collectors.toMap(Horiday::getDate, UnaryOperator.identity()));
	}
}
