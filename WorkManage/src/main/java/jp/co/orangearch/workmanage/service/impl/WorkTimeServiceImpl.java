package jp.co.orangearch.workmanage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.CsvComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeDao;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.service.WorkTimeCsvBean;
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

	@Autowired
	private CalendarComponent calendarComponent;

	@Autowired
	private CsvComponent csvComponent;

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
			newItem.setHoridayType(calendarComponent.getHoridayType(newItem.getWorkDate()));

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
	public byte[] createCsv(String userId, LocalDate from_date, LocalDate to_date) {
		List<WorkTime> workTimes = workTimeDao.selectByIdAndMonth(userId, from_date, to_date);
		
		ByteArrayOutputStream stream = null;
		try {
			List<WorkTimeCsvBean> list = new ArrayList<WorkTimeCsvBean>();
			for(WorkTime item : workTimes){
				WorkTimeCsvBean bean = new WorkTimeCsvBean();
				bean.setUserId(item.getUserId());
				bean.setWorkDate(item.getWorkDate());
				bean.setWorkTimeType(item.getWorkTimeType().getValue());
				bean.setStartTime(item.getStartTime());
				bean.setEndTime(item.getEndTime());
				bean.setAttendanceCode(item.getAttendanceCode().getValue());
				bean.setHoridayType(item.getHoridayType().getValue());
				bean.setNotes(item.getNotes());
				list.add(bean);
			}
			stream = csvComponent.write(list, WorkTimeCsvBean.class, "MS932");
		} catch (IOException e) {
			throw new SystemException(MessageId.S001, new String[]{"CSV作成"});
		}
		
		return stream.toByteArray();
	}

}
