package jp.co.orangearch.workmanage.component.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.orangearch.workmanage.component.BreakTimeComponent;
import jp.co.orangearch.workmanage.domain.dao.BreakTimeDao;
import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.BreakTime;

/**
 * 休憩時間コンポーネントクラスです。
 * <br>
 * 起動時に休憩時間テーブルの中身を全てキャッシュします。
 *
 * @author t-otsuka
 *
 */
@Component
public class BreakTimeComponentImpl implements BreakTimeComponent{

	/** 休憩時間マップ。 */
	private static Map<Integer, List<BreakTime>> cache = new HashMap<Integer, List<BreakTime>>();
	
	/** 休憩時間テーブルDao。 */
	@Autowired
	private BreakTimeDao breakTimeDao;

	public List<BreakTime> getBreakTimes(WorkTimeCode workTimeCd){
		return cache.get(workTimeCd.getValue());
	}
	
	/**
	 * 休憩時間テーブルから全勤務帯の休憩時間を取得し、キャッシュします。
	 * <br>
	 * 本メソッドは、{@link PostConstruct}により、アプリケーション起動時に実行されます。
	 */
	@PostConstruct
	private void cache(){
		List<BreakTime> breakTimes = breakTimeDao.selectAll();
		for(BreakTime item : breakTimes){
			List<BreakTime> breaksOfWorkTime = cache.get(item.getWorkTimeCd().getValue());
			if(breaksOfWorkTime == null){
				breaksOfWorkTime = new ArrayList<BreakTime>();
				cache.put(item.getWorkTimeCd().getValue(), breaksOfWorkTime);
			}
			breaksOfWorkTime.add(item);
		}
	}
}
