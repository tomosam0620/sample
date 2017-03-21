package jp.co.orangearch.workmanage.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.orangearch.workmanage.common.constant.MessageId;
import jp.co.orangearch.workmanage.common.exception.SystemException;
import jp.co.orangearch.workmanage.common.util.DateUtils;
import jp.co.orangearch.workmanage.dao.WorkTimeDao;
import jp.co.orangearch.workmanage.entity.WorkTime;
import jp.co.orangearch.workmanage.service.WorkTimeService;


/**
 * 勤務時間サービスの実相クラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class WorkTimeServiceImpl implements WorkTimeService {

	@Autowired
	private WorkTimeDao workTimeDao;

	@Override
	public Optional<WorkTime> select(String userId, LocalDate date) {
		return workTimeDao.selectByIdAndDate(userId, date, SelectOptions.get());
	}

	@Override
	public List<WorkTime> selectAll(String userId, LocalDate date) {
		LocalDate fistdate = DateUtils.getFirstDayOfMonth(date);
		LocalDate lastdate = DateUtils.getFinalDayOfMonth(date);
		List<WorkTime> registedList = workTimeDao.selectByIdAndMonth(userId, fistdate, lastdate);
		int lastDate = DateUtils.getFinalDayOfMonth(date).getDayOfMonth();

		List<WorkTime> showList = new ArrayList<WorkTime>();
		for(int i= 1; i <lastDate +1; i++){
			WorkTime newItem = new WorkTime();
			newItem.setWorkDate(LocalDate.of(date.getYear(), date.getMonth(), i));
			newItem.setBusinessDayFlag(DateUtils.getBusinessDayFlag(newItem.getWorkDate()));

			for(WorkTime item : registedList){
				if(item.getWorkDate().getDayOfMonth() == i){
					newItem =item;
					break;
				}
			}
			showList.add(newItem);
		}

		return showList;
	}

	@Transactional
	@Override
	public void update(WorkTime workTime) {

		//CHECK MySQLが悲観ロックサポートしてなため、悲観ロックをする場合SQLに直接for updateを記載する
//		SelectOptions options = SelectOptions.get().forUpdate();
		SelectOptions options = SelectOptions.get();
		Optional<WorkTime> entity = workTimeDao.selectByIdAndDate(workTime.getUserId(), workTime.getWorkDate(), options);

		int result = 1;
		String procName;
		if(entity.isPresent()){
			//update
			workTime.setRegistUser(entity.get().getRegistUser());
			workTime.setRegistDate(entity.get().getRegistDate());
			result = workTimeDao.update(workTime);
			procName = "更新処理";
		}else{
			result = workTimeDao.insert(workTime);
			procName = "登録処理";
		}

		if(result != 1){
			throw new SystemException(MessageId.S001, new String[]{procName});
		}
	}

	@Override
	public Map<String, String> getMonthList(){
		Map<String,String> map = new HashMap<String,String>();
		//TODO:当日までの月のリスト取得(いつから？)
		map.put("2017年01月", "2017-01");
		map.put("2017年02月", "2017-02");
		map.put("2017年03月", "2017-03");
		return map;
	}

}
