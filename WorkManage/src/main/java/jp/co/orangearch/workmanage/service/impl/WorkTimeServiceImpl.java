package jp.co.orangearch.workmanage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.BeanUtils;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import jp.co.orangearch.workmanage.component.BreakTimeComponent;
import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.CsvComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.AttendanceCode;
import jp.co.orangearch.workmanage.domain.constant.HoridayType;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.TransportionExpenseDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeTypeDao;
import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.BreakTime;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.dto.OperationTime;
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

	/** 稼働時間のスケール(小数点以下の桁数)。 */
	private static int OPERATION_SCALE = 2;
	
	/** 法定労働時間。 */
	private static BigDecimal STATUTORY_WORKING_HOURS = BigDecimal.valueOf(8).setScale(OPERATION_SCALE);
	
	/** 0時間。 */
	private static BigDecimal ZERO_HOUR = BigDecimal.ZERO.setScale(OPERATION_SCALE);
	
	/** 1時間。 */
	private static BigDecimal ONE_HOUR = BigDecimal.valueOf(60).setScale(OPERATION_SCALE);
	
	/** 深夜単価開始時刻。 */
	private static LocalTime START_NIGHT_TIME = LocalTime.of(22, 0);
	
	/** 深夜単価終了時刻。 */
	private static LocalTime END_NIGHT_TIME = LocalTime.of(5, 0);
	
	/** 勤務帯キャッシュ。 */
	private static Map<Integer, WorkTimeType> workTimeTypeCache = new HashMap<Integer, WorkTimeType>();
	
	@Autowired
	private WorkTimeDao workTimeDao;

	@Autowired
	private TransportionExpenseDao transportionExpenseDao;

	/** 勤務帯テーブルDao。 */
	@Autowired
	private WorkTimeTypeDao workTimeTypeDao;

	@Autowired
	private CalendarComponent calendarComponent;

	@Autowired
	private BreakTimeComponent breakTimeComponent;
	
	@Autowired
	private CsvComponent csvComponent;

	@Override
	public Optional<WorkTime> select(String userId, LocalDate date) {
		return workTimeDao.selectByIdAndDate(userId, date, SelectOptions.get());
	}

	@Override
	public List<OperationTime> selectWorkTimeInfoInMonth(String userId, LocalDate date) {
		LocalDate fistdate = DateUtils.getFirstDayOfMonth(date);
		LocalDate lastdate = DateUtils.getFinalDayOfMonth(date);
		List<WorkTime> registedList = workTimeDao.selectByIdAndMonth(userId, fistdate, lastdate);
		int lastDate = DateUtils.getFinalDayOfMonth(date).getDayOfMonth();

		List<OperationTime> showList = new ArrayList<OperationTime>();
		for(int i= 1; i <lastDate +1; i++){
			OperationTime newItem = new OperationTime();
			newItem.setWorkDate(LocalDate.of(date.getYear(), date.getMonth(), i));
			newItem.setHoridayType(calendarComponent.getHoridayType(newItem.getWorkDate()));

			for(WorkTime item : registedList){
				if(item.getWorkDate().getDayOfMonth() == i){
					newItem =new OperationTime(item);
					
					//稼働時間計算
					BigDecimal operationTime = getOperationTime(newItem);
					BigDecimal overtimeWithinStatutoryWorkingHours = getOvertimeWithinStatutoryWorkingHours(newItem, operationTime);
					BigDecimal overtimeBeyondStatutoryWorkingHours = getOvertimeBeyondStatutoryWorkingHours(newItem, operationTime);
					BigDecimal nightHours = getNightWorkingHours(newItem);
					BigDecimal horidayHours = getHoridayWorkingHours(newItem);
					newItem.setOperationTime(operationTime);
					newItem.setOvertimeWithinStatutoryWorkingHours(overtimeWithinStatutoryWorkingHours);
					newItem.setOvertimeBeyondStatutoryWorkingHours(overtimeBeyondStatutoryWorkingHours);
					newItem.setNightHours(nightHours);
					newItem.setHoridayHours(horidayHours);
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
	public List<WorkTimeType> getWorkTimeType(){
		return workTimeTypeDao.selectAll();
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
				BigDecimal operationTime = getOperationTime(item);
				bean.setOperationTime(operationTime);
				bean.setOvertimeWithinStatutoryWorkingHours(getOvertimeWithinStatutoryWorkingHours(item, operationTime));
				bean.setOvertimeBeyondStatutoryWorkingHours(getOvertimeBeyondStatutoryWorkingHours(item, operationTime));
				list.add(bean);
			}
			stream = csvComponent.write(list, WorkTimeCsvBean.class, "MS932");
		} catch (IOException e) {
			throw new SystemException(MessageId.S001, new String[]{"CSV作成"});
		}
		
		return stream.toByteArray();
	}

	@Override
	public List<TransportionExpense> selectTransportionInfo(String userId, LocalDate date) {
		LocalDate fistdate = DateUtils.getFirstDayOfMonth(date);
		LocalDate lastdate = DateUtils.getFinalDayOfMonth(date);
		return transportionExpenseDao.selectByUserIdAndDateRange(userId, fistdate, lastdate);
	}

	private BigDecimal getOperationTime(WorkTime workTimeInfo) {
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeInfo.getWorkTimeType().getValue());
		BigDecimal scheduledWorkingHours = getScheduledWorkingHours(workTimeInfo.getWorkTimeType());
		switch(workTimeInfo.getAttendanceCode()){
		case 有給:
		case 年特:
		case 特休:
		case 欠勤:
		case 傷病:
			return ZERO_HOUR;
		case 代休:
			return scheduledWorkingHours;
		}
		
		BigDecimal operationTime;
		//= calcurateOperationTime(workTimeInfo);
		
		if(ObjectUtils.isEmpty(workTimeInfo.getStartTime()) || ObjectUtils.isEmpty(workTimeInfo.getEndTime())){
			operationTime = ZERO_HOUR;
		}else{
			LocalTime workStartTime = getWorkStartTime(workTimeInfo.getWorkTimeType());
	
			LocalDateTime startTime = LocalDateTime.of(workTimeInfo.getWorkDate(), workTimeInfo.getStartTime());
			if(workTimeInfo.getStartTime().isBefore(workStartTime)){
				startTime = LocalDateTime.of(workTimeInfo.getWorkDate(), workStartTime);
			}
			LocalDateTime endTime = LocalDateTime.of(workTimeInfo.getWorkDate(), workTimeInfo.getEndTime());
			if(endTime.isBefore(startTime)){
				endTime = endTime.plusDays(1);
			}
			operationTime = calcurateOperationTime(workTimeInfo.getWorkTimeType(), workTimeInfo.getWorkDate(), startTime, endTime);
		}
		

		//代出の場合は所定就業時間分は代休日にまわすので所定就業時間分を減算する。TODO：稼働が足りなければ早退扱い。
		if(workTimeInfo.getAttendanceCode() == AttendanceCode.代出){
			operationTime = operationTime.subtract(scheduledWorkingHours);
		}
	
		return operationTime;
	}

	/**
	 * 稼働時間を計算します。
	 * <br>
	 * 始業時間あるいは終業時間が未入力の場合は0を返します。<br>
	 * 勤務帯毎に定義されている休憩時間と稼働は重なった分だけ稼働時間から減算します。
	 * 
	 * @param date 日付
	 * @return 休日種別{@link HoridayType}
	 */
	private BigDecimal calcurateOperationTime(WorkTime operation){
		if(ObjectUtils.isEmpty(operation.getStartTime()) || ObjectUtils.isEmpty(operation.getEndTime())){
			return ZERO_HOUR;
		}
		LocalTime workStartTime = getWorkStartTime(operation.getWorkTimeType());

		LocalDateTime startTime = LocalDateTime.of(operation.getWorkDate(), operation.getStartTime());
		if(operation.getStartTime().isBefore(workStartTime)){
			startTime = LocalDateTime.of(operation.getWorkDate(), workStartTime);
		}
		LocalDateTime endTime = LocalDateTime.of(operation.getWorkDate(), operation.getEndTime());
		if(operation.getEndTime().isBefore(operation.getStartTime())){
			endTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), operation.getEndTime());
		}
		
		Duration duration = Duration.between(startTime, endTime);
		List<BreakTime> breaksOfWorkTime = breakTimeComponent.getBreakTimes(operation.getWorkTimeType());
		for(BreakTime item : breaksOfWorkTime){
			LocalDateTime breakStartTime = LocalDateTime.of(operation.getWorkDate(), item.getBreakStartTime());
			LocalDateTime breakEndTime = LocalDateTime.of(operation.getWorkDate(), item.getBreakEndTime());
			if(item.getBreakStartTime().isBefore(workStartTime)){
				breakStartTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), item.getBreakStartTime());
			}
			if(item.getBreakEndTime().isBefore(workStartTime)){
				breakEndTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), item.getBreakEndTime());
			}

			//重なった休憩時間分を稼働から引く
			if(startTime.isBefore(breakEndTime) && endTime.isAfter(breakStartTime)){
				Duration breakTime;
				if(endTime.isBefore(breakEndTime)){
					breakTime = Duration.between(breakStartTime, endTime);
				}else if(startTime.isAfter(breakStartTime)){
					breakTime = Duration.between(startTime, breakEndTime);
				}else{
					breakTime = Duration.between(breakStartTime, breakEndTime);
				}
				duration = duration.minus(breakTime);
			}
		}
		BigDecimal minutes = BigDecimal.valueOf(duration.toMinutes()).setScale(OPERATION_SCALE).divide(ONE_HOUR, BigDecimal.ROUND_DOWN);
		
		return minutes.max(ZERO_HOUR);
	}

	/**
	 * 稼働時間を計算します。
	 * <br>
	 * 開始時刻から終了時刻までの稼働時間を返します。<br>
	 * 勤務帯毎に定義されている休憩時間と稼働は重なった分だけ稼働時間から減算します。
	 * 
	 * @param worcTimeCd 勤務帯コード
	 * @param workDate 勤務日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 稼働時間
	 */
	private BigDecimal calcurateOperationTime(WorkTimeCode worcTimeCd, LocalDate workDate, LocalDateTime startTime, LocalDateTime endTime){
		LocalDateTime workStartTime = LocalDateTime.of(workDate, getWorkStartTime(worcTimeCd));

		LocalDateTime calcurateStartTime = startTime;
		LocalDateTime calcurateEndTime = endTime;
		
		Duration duration = Duration.between(calcurateStartTime, calcurateEndTime);
		List<BreakTime> breaksOfWorkTime = breakTimeComponent.getBreakTimes(worcTimeCd);
		for(BreakTime item : breaksOfWorkTime){
			LocalDateTime breakStartTime = LocalDateTime.of(workDate, item.getBreakStartTime());
			LocalDateTime breakEndTime = LocalDateTime.of(workDate, item.getBreakEndTime());
			if(breakStartTime.isBefore(workStartTime)){
				breakStartTime = breakStartTime.plusDays(1);
			}
			if(breakEndTime.isBefore(workStartTime)){
				breakEndTime = breakEndTime.plusDays(1);
			}

			//重なった休憩時間分を稼働から引く
			if(calcurateStartTime.isBefore(breakEndTime) && calcurateEndTime.isAfter(breakStartTime)){
				Duration breakTime;
				if(calcurateEndTime.isBefore(breakEndTime)){
					breakTime = Duration.between(breakStartTime, calcurateEndTime);
				}else if(calcurateStartTime.isAfter(breakStartTime)){
					breakTime = Duration.between(calcurateStartTime, breakEndTime);
				}else{
					breakTime = Duration.between(breakStartTime, breakEndTime);
				}
				duration = duration.minus(breakTime);
			}
		}
		BigDecimal minutes = BigDecimal.valueOf(duration.toMinutes()).setScale(OPERATION_SCALE).divide(ONE_HOUR, BigDecimal.ROUND_DOWN);
		
		return minutes;
	}

	/**
	 * 法内残業時間を取得します。
	 * @param operation 稼働情報
	 * @param operationTime 稼働時間
	 * @return 法内残業時間
	 */
	private BigDecimal getOvertimeWithinStatutoryWorkingHours(WorkTime operation, BigDecimal operationTime){
		return operationTime.min(STATUTORY_WORKING_HOURS).subtract(getScheduledWorkingHours(operation.getWorkTimeType())).max(ZERO_HOUR);
	}
	
	/**
	 * 法外残業時間を取得します。
	 * 
	 * @param operation 稼働情報
	 * @param operationTime 稼働時間
	 * @return 法外残業時間
	 */
	private BigDecimal getOvertimeBeyondStatutoryWorkingHours(WorkTime operation, BigDecimal operationTime){
		return operationTime.subtract(STATUTORY_WORKING_HOURS).max(ZERO_HOUR);
	}

	
	private BigDecimal getNightWorkingHours(WorkTime operation){
		LocalDateTime startTime = LocalDateTime.of(operation.getWorkDate(), operation.getStartTime());
		LocalDateTime endTime = LocalDateTime.of(operation.getWorkDate(), operation.getEndTime());
		if(operation.getStartTime().isAfter(operation.getEndTime())){
			endTime = endTime.plusDays(1);
		}
		
		LocalDateTime nightStartTime = LocalDateTime.of(operation.getWorkDate(), START_NIGHT_TIME);
		LocalDateTime nightEndTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), END_NIGHT_TIME);
		
		BigDecimal nightOperation = ZERO_HOUR;
		//稼働時間が深夜時間を含んでいる場合のみ計算
		if(startTime.isBefore(nightEndTime) && endTime.isAfter(nightStartTime)){
			if(startTime.isBefore(nightStartTime)){
				startTime = nightStartTime;
			}
			if(endTime.isAfter(nightEndTime)){
				endTime = nightEndTime;
			}
			
			nightOperation = calcurateOperationTime(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime);
		}
		
		return nightOperation;
	}
	
	/**
	 * 休日の稼働時間を取得します。
	 * <br>
	 * 当日が休日の場合は24時までの稼働を、翌日が休日の場合は0時からの稼働を合算した値を返却します。
	 * @param operation 勤務情報
	 * @return 休日の稼働時間
	 */
	private BigDecimal getHoridayWorkingHours(WorkTime operation){
		LocalDateTime startTime = LocalDateTime.of(operation.getWorkDate(), operation.getStartTime());
		LocalDateTime endTime = LocalDateTime.of(operation.getWorkDate(), operation.getEndTime());
		BigDecimal horidayWorkinfHours = ZERO_HOUR;

		//当日が休日の場合24(0)時までの稼働を計算
		if(operation.getHoridayType() == HoridayType.HORIDAY){
			if(operation.getEndTime().isBefore(getWorkStartTime(operation.getWorkTimeType()))){
				endTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), LocalTime.of(0, 0));
			}
			
			horidayWorkinfHours = calcurateOperationTime(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime);
		}
		
		if(calendarComponent.getHoridayType(operation.getWorkDate().plusDays(1)) == HoridayType.HORIDAY){
			if(operation.getEndTime().isBefore(getWorkStartTime(operation.getWorkTimeType()))){
				startTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), LocalTime.of(0, 0));
				endTime = endTime.plusDays(1);
				horidayWorkinfHours = horidayWorkinfHours.add(calcurateOperationTime(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime));
			}
		}
		return horidayWorkinfHours;
	}
	
	/**
	 * 勤務帯の所定労働時間を取得します。
	 * @param workTimeCd 勤務帯
	 * @return 所定労働時間
	 */
	private BigDecimal getScheduledWorkingHours(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new IllegalArgumentException("not exist in cache workTimeCd:" + workTimeCd.getValue());
		}
		return BigDecimal.valueOf(Duration.between(workTimeType.getStartTime(), workTimeType.getEndTime()).toMinutes())
						.setScale(OPERATION_SCALE)
						.subtract(BigDecimal.valueOf(workTimeType.getLunchBreakTime()))
						.divide(ONE_HOUR, BigDecimal.ROUND_DOWN);
	}

	/**
	 * 勤務帯の始業時間を取得します。
	 * @param workTimeCd 勤務帯コード
	 * @return 始業時間
	 */
	private LocalTime getWorkStartTime(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new SystemException(MessageId.S003);
		}
		return workTimeType.getStartTime();
	}
	
	/**
	 * 勤務帯の終業時間を取得します。
	 * @param workTimeCd 勤務帯コード
	 * @return 終業時間
	 */
	private LocalTime getWorkEndTime(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new SystemException(MessageId.S003);
		}
		return workTimeType.getEndTime();
	}
	
	@PostConstruct
	private void cache(){
		List<WorkTimeType> workTimeTypes = workTimeTypeDao.selectAll();
		for(WorkTimeType workTimeType : workTimeTypes){
			workTimeTypeCache.put(workTimeType.getWorkTimeCd().getValue(), workTimeType);
		}
	}

}
