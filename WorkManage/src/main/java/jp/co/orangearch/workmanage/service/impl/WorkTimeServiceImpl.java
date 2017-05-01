package jp.co.orangearch.workmanage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
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

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import jp.co.orangearch.workmanage.component.BreakTimeComponent;
import jp.co.orangearch.workmanage.component.CalendarComponent;
import jp.co.orangearch.workmanage.component.CsvComponent;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.component.util.DateUtils.DateTimeFormat;
import jp.co.orangearch.workmanage.domain.constant.AttendanceCode;
import jp.co.orangearch.workmanage.domain.constant.ClosingState;
import jp.co.orangearch.workmanage.domain.constant.HoridayType;
import jp.co.orangearch.workmanage.domain.constant.MessageId;
import jp.co.orangearch.workmanage.domain.dao.TransportionExpenseDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeStatusDao;
import jp.co.orangearch.workmanage.domain.dao.WorkTimeTypeDao;
import jp.co.orangearch.workmanage.domain.dao.join.JoinProjectWorkTimeStatusUserDao;
import jp.co.orangearch.workmanage.domain.domain.WorkTimeCode;
import jp.co.orangearch.workmanage.domain.entity.BreakTime;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;
import jp.co.orangearch.workmanage.domain.entity.WorkTime;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeStatus;
import jp.co.orangearch.workmanage.domain.entity.WorkTimeType;
import jp.co.orangearch.workmanage.domain.entity.join.JoinProjectWorkTimeStatusUser;
import jp.co.orangearch.workmanage.domain.exception.SystemException;
import jp.co.orangearch.workmanage.dto.OperationInfoOfMonth;
import jp.co.orangearch.workmanage.dto.OperationTime;
import jp.co.orangearch.workmanage.dto.WorkTimesOfMonth;
import jp.co.orangearch.workmanage.service.WorkTimeCsvBean;
import jp.co.orangearch.workmanage.service.WorkTimeService;
import sun.nio.cs.ext.MS932;


/**
 * 勤務時間サービスの実装クラスです。
 *
 * @author t-otsuka
 *
 */
@Service
public class WorkTimeServiceImpl implements WorkTimeService {

	/** 稼働時間のスケール(小数点以下の桁数)。 */
	private static int OPERATION_SCALE = 2;
	
	/** 0時間。 */
	private static BigDecimal ZERO_HOUR = BigDecimal.ZERO.setScale(OPERATION_SCALE);
	
	/** 1時間。 */
	private static BigDecimal ONE_HOUR = BigDecimal.valueOf(60).setScale(OPERATION_SCALE);
	
	/** 法定労働時間。 */
	private static BigDecimal STATUTORY_WORKING_HOURS = BigDecimal.valueOf(8).setScale(OPERATION_SCALE);

	/** 週間法定労働時間。 */
	private static BigDecimal STATUTORY_WORKING_HOURS_OF_WEEK = BigDecimal.valueOf(40);
	
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

	@Autowired
	private WorkTimeStatusDao workTimeStatusDao;
	
	@Autowired
	private JoinProjectWorkTimeStatusUserDao workTimeStatusUserDao;

	@Override
	public Optional<WorkTime> select(String userId, LocalDate date) {
		return workTimeDao.selectByIdAndDate(userId, date, SelectOptions.get());
	}

	@Override
	public WorkTimesOfMonth selectWorkTimeInfoInMonth(String userId, LocalDate date) {
		LocalDate firstdate = DateUtils.getFirstDayOfMonth(date);
		LocalDate lastdate = DateUtils.getFinalDayOfMonth(date);
		
		BigDecimal operationSumOfWeek = ZERO_HOUR;
		BigDecimal overtimeWithinSWHSumOfWeek = ZERO_HOUR;
		BigDecimal overtimeBeyondSWHSumOfWeek = ZERO_HOUR;
		int dayOffCount = 0;
		int halfDayOffCount = 0;
		//前月に入らなかった分(土曜を迎えなかった最終週日曜～)を取得。
		DayOfWeek dayOfWeek = firstdate.getDayOfWeek();
		if(dayOfWeek != DayOfWeek.SUNDAY){
			WorkTimesOfMonth preMonthInfo = selectWorkTimeInfo(userId,
															firstdate.minusDays(dayOfWeek.getValue()),
															firstdate.minusDays(1),
															operationSumOfWeek,
															overtimeWithinSWHSumOfWeek,
															overtimeBeyondSWHSumOfWeek,
															dayOffCount,
															halfDayOffCount);
			operationSumOfWeek = preMonthInfo.getOperationHours();
			overtimeWithinSWHSumOfWeek = preMonthInfo.getOvertimeWithinSWHours();
			overtimeBeyondSWHSumOfWeek = preMonthInfo.getOvertimeBeyondSWHours();
			dayOffCount = preMonthInfo.getDayOffCount();
			halfDayOffCount = preMonthInfo.getHalfDayOffCount();
		}

		WorkTimesOfMonth workTimeInfo = selectWorkTimeInfo(userId,
															firstdate,
															lastdate,
															operationSumOfWeek,
															overtimeWithinSWHSumOfWeek,
															overtimeBeyondSWHSumOfWeek,
															dayOffCount,
															halfDayOffCount);
		return workTimeInfo;
	}

	@Transactional
	@Override
	public void update(WorkTime workTime, BindingResult errors) {
		checkInput(workTime, errors);
		if(errors.hasErrors()){
			return;
		}
		
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
	
	public void checkInput(WorkTime workTime, BindingResult errors){
		
		// ステータスチェック
		String month = DateUtils.convert(workTime.getWorkDate(), DateTimeFormat.UUUU_MM);
		Optional<WorkTimeStatus> statusEntity = workTimeStatusDao.selectById(workTime.getUserId(), month);
		if(statusEntity.isPresent()){
			if(ClosingState.CLOSED.equals(statusEntity.get().getStatus())){
				errors.reject(MessageId.M006.getValue());
			}
		}
		
		//出勤コードチェック
		switch(workTime.getAttendanceCode()){
		case 出勤:
			break;
		case 有給:
		case 年特:
		case 特休:
		case 代休:
		case 欠勤:
		case 傷病:
		case 明け休み:
			if(!calendarComponent.isBusinessDay(workTime.getWorkDate())){
				errors.reject(MessageId.M012.getValue(), workTime.getAttendanceCode().getText());
				return;
			}
			break;
		case 休出:
		case 代出:
			if(calendarComponent.isBusinessDay(workTime.getWorkDate())){
				errors.reject(MessageId.M011.getValue(), workTime.getAttendanceCode().getText());
				return;
			}
			break;
		default:
			break;
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
				BigDecimal operationTime = getOperationHours(item);
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

	@Override
	public List<OperationInfoOfMonth> selectSummary(String fromMonth, String toMonth, Integer affiliationCd, Integer projectId, String userId, SelectOptions options) {
		List<OperationInfoOfMonth> list = new ArrayList<OperationInfoOfMonth>();
		
		LocalDate from = StringUtils.isEmpty(fromMonth) ? null : DateUtils.getFirstDayOfMonth(fromMonth + "-01");
		LocalDate to = StringUtils.isEmpty(toMonth) ? null : DateUtils.getFinalDayOfMonth(toMonth + "-01");

		List<JoinProjectWorkTimeStatusUser> statuses = workTimeStatusUserDao.selectByCondition(affiliationCd, projectId, userId, from, to, options);
		for(JoinProjectWorkTimeStatusUser status : statuses){
			WorkTimesOfMonth operationTimes = selectWorkTimeInfoInMonth(status.getUserId(), DateUtils.convertToLocalDate(status.getWorkMonth() + "-01"));
			OperationInfoOfMonth info = new OperationInfoOfMonth();
			info.setAffiliationCd(status.getAffiliation());
			info.setMonth(status.getWorkMonth());
			info.setOperationHours(operationTimes.getOperationHours());
			info.setOvertimeWithinStatutoryWorkingHours(operationTimes.getOvertimeWithinSWHours());
			info.setOvertimeBeyondStatutoryWorkingHours(operationTimes.getOvertimeBeyondSWHours());
			info.setProjectId(status.getProjectId());
			info.setDayOff(operationTimes.getDayOffCount());
			info.setHalfDayOff(operationTimes.getHalfDayOffCount());
			info.setStatus(status.getStatus());
			info.setUserId(status.getUserId());
			list.add(info);
		}
		
		return list;
	}
	
	@Override
	public void updateStatus(String userId, String month, ClosingState status, Integer version, BindingResult errors) {
		LocalDate from = DateUtils.convertToLocalDate(month + "-01", DateTimeFormat.UUUU_M_D);
		LocalDate to = DateUtils.getFinalDayOfMonth(from);
		List<WorkTime> operations = workTimeDao.selectByIdAndMonth(userId, from, to);
		
		//営業日で何も入力されていない日があればエラー
		for(LocalDate current = from; (current.isBefore(to) || current.equals(to)); current = current.plusDays(1)){
			if(calendarComponent.isBusinessDay(current)){
				
				boolean isExist = false;
				for(WorkTime operation : operations){
					if(operation.getWorkDate().equals(current)){
						isExist = true;
						break;
					}
				}
				
				if(!isExist){
					errors.reject(MessageId.M007.getValue());
					break;
				}
				
			}
		}
		
		//始業時間と終業時間入力チェック
		for(WorkTime operation : operations){
			switch(operation.getAttendanceCode()){
			//休みの出勤コードの場合入力不可
			case 代休:
				if(operation.getCompensatoryAttendanceDate() == null){
					errors.reject(MessageId.M010.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "代出日"}, null);
				}
				if(operation.getStartTime() != null || operation.getEndTime() != null){
					errors.reject(MessageId.M009.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "始業時間と終業時間"}, null);
				}
				break;
			case 有給:
			case 年特:
			case 特休:
			case 欠勤:
			case 傷病:
				if(operation.getCompensatoryAttendanceDate() != null){
					errors.reject(MessageId.M011.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "代出日"}, null);
				}
				if(operation.getStartTime() != null || operation.getEndTime() != null){
					errors.reject(MessageId.M009.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "始業時間と終業時間"}, null);
				}
				break;
			//代出の場合、代休日の入力必須
			case 代出:
				if(operation.getCompensatoryAttendanceDate() == null){
					errors.reject(MessageId.M010.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "代休日"}, null);
				}
				if(operation.getStartTime() == null || operation.getEndTime() == null){
					errors.reject(MessageId.M010.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "始業時間と終業時間"}, null);
				}
				break;
			//出勤している場合は始業時間と終業時間の両方入力必須
			default:
				if(operation.getCompensatoryAttendanceDate() != null){
					errors.reject(MessageId.M009.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "代休出日"}, null);
				}
				if(operation.getStartTime() == null || operation.getEndTime() == null){
					errors.reject(MessageId.M010.getValue(), new Object[]{operation.getWorkDate(), operation.getAttendanceCode(), "始業時間と終業時間"}, null);
				}
				break;
			}
		}
		
		//TODO：交通費入力チェック
		if(errors.hasErrors()){
			return;
		}
		
		//ステータス更新
		Optional<WorkTimeStatus> statusEntity = workTimeStatusDao.selectById(userId, month);
		if(statusEntity.isPresent()){
			WorkTimeStatus entity = statusEntity.get();
			entity.setStatus(status);
			entity.setVersion(version);
			workTimeStatusDao.update(entity);
		}else{
			WorkTimeStatus entity = new WorkTimeStatus();
			entity.setWorkMonth(month);
			entity.setUserId(userId);
			entity.setStatus(status);
			workTimeStatusDao.insert(entity);
		}
	}

	@Override
	public Optional<WorkTimeStatus> selectStatusVersion(String userId, String month) {
		return workTimeStatusDao.selectById(userId, month);
	}
	
	/**
	 * 勤務情報取得。
	 * <br>
	 * 指定期間の勤務情報を取得します。
	 * 
	 * @param userId ユーザーID
	 * @param startDate 取得開始日
	 * @param endDate 取得終了日
	 * @param preOperationHours 前期間からの繰り越し分稼働(週)
	 * @param preOvertimeWithinSWHours 前期間からの繰り越し分法内残業時間(週)
	 * @param preOvertimeBeyondSWHours 前期間からの繰り越し分法外残業時間(週)
	 * @return 勤務情報のリスト
	 */
	private WorkTimesOfMonth selectWorkTimeInfo(String userId, LocalDate startDate, LocalDate endDate, BigDecimal preOperationHours, BigDecimal preOvertimeWithinSWHours, BigDecimal preOvertimeBeyondSWHours, int dayOffCount, int halfDayOffCount) {
		List<WorkTime> registedList = workTimeDao.selectByIdAndMonth(userId, startDate, endDate);

		List<OperationTime> showList = new ArrayList<OperationTime>();
		
		LocalDate currentDate = startDate;
		WorkTimesOfMonth returnVal = new WorkTimesOfMonth(showList, preOperationHours, preOvertimeWithinSWHours, preOvertimeBeyondSWHours);
		
		while(!currentDate.isEqual(endDate.plusDays(1))){
			OperationTime newItem = new OperationTime();
			newItem.setWorkDate(currentDate);
			newItem.setHoridayType(calendarComponent.getHoridayType(newItem.getWorkDate()));

			for(WorkTime item : registedList){
				if(item.getWorkDate().isEqual(currentDate)){
					newItem =new OperationTime(item);
					
					//稼働時間計算
					BigDecimal operationTime = getOperationHours(newItem);
					BigDecimal overtimeWithinStatutoryWorkingHours = getOvertimeWithinStatutoryWorkingHours(newItem, operationTime);
					BigDecimal overtimeBeyondStatutoryWorkingHours = getOvertimeBeyondStatutoryWorkingHours(newItem, operationTime);
					BigDecimal nightHours = getNightWorkingHours(newItem);
					BigDecimal horidayHours = getHoridayWorkingHours(newItem);
					dayOffCount = isDayOff(newItem) ? dayOffCount + 1 : dayOffCount;
					halfDayOffCount = isHalfDayOff(newItem) ? halfDayOffCount + 1 : halfDayOffCount;
					newItem.setOperationHours(operationTime);
					newItem.setOvertimeWithinStatutoryWorkingHours(overtimeWithinStatutoryWorkingHours);
					newItem.setOvertimeBeyondStatutoryWorkingHours(overtimeBeyondStatutoryWorkingHours);
					newItem.setNightHours(nightHours);
					newItem.setHoridayHours(horidayHours);
					newItem.setLateHours(getLateHours(newItem));
					newItem.setLeaveEaryHours(getLeaveEaryHours(newItem));
					returnVal.setIsExist(true);
					break;
				}
			}
			
			preOperationHours = preOperationHours.add(newItem.getOperationHours() != null ? newItem.getOperationHours() : ZERO_HOUR);
			preOvertimeWithinSWHours = preOvertimeWithinSWHours.add(newItem.getOvertimeWithinStatutoryWorkingHours() != null
																		? newItem.getOvertimeWithinStatutoryWorkingHours()
																		: ZERO_HOUR);
			preOvertimeBeyondSWHours = preOvertimeBeyondSWHours.add(newItem.getOvertimeBeyondStatutoryWorkingHours() != null
																		? newItem.getOvertimeBeyondStatutoryWorkingHours()
																		: ZERO_HOUR);
			//土曜日絞め
			if(newItem.getWorkDate().getDayOfWeek() == DayOfWeek.SATURDAY){
				newItem.setOperationHoursInWeek(preOperationHours);
				//法内残業は法定労働時間(週)未満の場合破棄
				if(preOperationHours.compareTo(STATUTORY_WORKING_HOURS_OF_WEEK) < 0){
					preOvertimeWithinSWHours = ZERO_HOUR;
				}
				newItem.setOvertimeWithinStatutoryWorkingHoursInWeek(preOvertimeWithinSWHours);
				newItem.setOvertimeBeyondStatutoryWorkingHoursInWeek(preOvertimeBeyondSWHours);
				returnVal.setOperationHours(returnVal.getOperationHours().add(preOperationHours));
				returnVal.setOvertimeWithinSWHours(returnVal.getOvertimeWithinSWHours().add(preOvertimeWithinSWHours));
				returnVal.setOvertimeBeyondSWHours(returnVal.getOvertimeBeyondSWHours().add(preOvertimeBeyondSWHours));
				preOperationHours = ZERO_HOUR;
				preOvertimeWithinSWHours = ZERO_HOUR;
				preOvertimeBeyondSWHours = ZERO_HOUR;
			}
			showList.add(newItem);
			currentDate = currentDate.plusDays(1);
		}
		returnVal.setOperationHours(returnVal.getOperationHours().add(preOperationHours));
		returnVal.setOvertimeWithinSWHours(returnVal.getOvertimeWithinSWHours().add(preOvertimeWithinSWHours));
		returnVal.setOvertimeBeyondSWHours(returnVal.getOvertimeBeyondSWHours().add(preOvertimeBeyondSWHours));
		returnVal.setDayOffCount(dayOffCount);
		returnVal.setHalfDayOffCount(halfDayOffCount);
		return returnVal;
	}

	/**
	 * 稼働時間取得
	 * <br>
	 * 勤務情報から、稼働時間を取得します。
	 * 始業時間、就業時間が未入力の場合0を返します。
	 * 出勤コードが休みのコード(有給、年特、特休、欠勤、傷病)の場合は0を返します。
	 * 出勤コードが代休の場合は勤務帯の所定勤務時間を返します。
	 * 代出の場合は、稼働時間から勤務帯の所定勤務時間を引いた時間を返します。
	 * 
	 * @param workTimeInfo 勤務情報
	 * @return 稼働時間
	 */
	private BigDecimal getOperationHours(WorkTime workTimeInfo) {
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
		default:
			break;
		}
		
		BigDecimal operationTime;
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
			operationTime = calcurateOperationHours(workTimeInfo.getWorkTimeType(), workTimeInfo.getWorkDate(), startTime, endTime);
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
	 * 開始時刻から終了時刻までの稼働時間を返します。<br>
	 * 勤務帯毎に定義されている休憩時間と稼働は重なった分だけ稼働時間から減算します。
	 * 
	 * @param worcTimeCd 勤務帯コード
	 * @param workDate 勤務日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @return 稼働時間(H)
	 */
	private BigDecimal calcurateOperationHours(WorkTimeCode worcTimeCd, LocalDate workDate, LocalDateTime startTime, LocalDateTime endTime){
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
	 * @param operationTime 稼働時間(H)
	 * @return 法内残業時間(H)
	 */
	private BigDecimal getOvertimeWithinStatutoryWorkingHours(WorkTime operation, BigDecimal operationTime){
		if(operation.getAttendanceCode() == AttendanceCode.代出){
			operationTime = operationTime.add(getScheduledWorkingHours(operation.getWorkTimeType()));
		}
		return operationTime.min(STATUTORY_WORKING_HOURS).subtract(getScheduledWorkingHours(operation.getWorkTimeType())).max(ZERO_HOUR);
	}
	
	/**
	 * 法外残業時間を取得します。
	 * 
	 * @param operation 稼働情報
	 * @param operationTime 稼働時間(H)
	 * @return 法外残業時間(H)
	 */
	private BigDecimal getOvertimeBeyondStatutoryWorkingHours(WorkTime operation, BigDecimal operationTime){
		if(operation.getAttendanceCode() == AttendanceCode.代出){
			operationTime = operationTime.add(getScheduledWorkingHours(operation.getWorkTimeType()));
		}
		return operationTime.subtract(STATUTORY_WORKING_HOURS).max(ZERO_HOUR);
	}

	/**
	 * 深夜帯の稼働時間を取得します。
	 * <br>
	 * 法定深夜時間帯(22:00-5:00)の稼働時間を取得します。
	 * @param operation 勤務情報
	 * @return 稼働時間(H)
	 */
	private BigDecimal getNightWorkingHours(WorkTime operation){
		if(ObjectUtils.isEmpty(operation.getWorkDate())
				|| ObjectUtils.isEmpty(operation.getStartTime())
				|| ObjectUtils.isEmpty(operation.getEndTime())){
			return ZERO_HOUR;
		}
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
			
			nightOperation = calcurateOperationHours(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime);
		}
		
		return nightOperation;
	}
	
	/**
	 * 休日(日曜日、祝日)の稼働時間を取得します。
	 * <br>
	 * 当日が休日の場合は24時までの稼働を、翌日が休日の場合は0時からの稼働を合算した値を返却します。
	 * @param operation 勤務情報
	 * @return 休日の稼働時間(H)
	 */
	private BigDecimal getHoridayWorkingHours(WorkTime operation){
		if(ObjectUtils.isEmpty(operation.getWorkDate())
				|| ObjectUtils.isEmpty(operation.getStartTime())
				|| ObjectUtils.isEmpty(operation.getEndTime())){
			return ZERO_HOUR;
		}
		LocalDateTime startTime = LocalDateTime.of(operation.getWorkDate(), operation.getStartTime());
		LocalDateTime endTime = LocalDateTime.of(operation.getWorkDate(), operation.getEndTime());
		BigDecimal horidayWorkinfHours = ZERO_HOUR;

		//当日が休日の場合24(0)時までの稼働を計算
		if(operation.getHoridayType() == HoridayType.HORIDAY){
			if(operation.getEndTime().isBefore(getWorkStartTime(operation.getWorkTimeType()))){
				endTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), LocalTime.of(0, 0));
			}
			
			horidayWorkinfHours = calcurateOperationHours(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime);
		}
		
		if(calendarComponent.getHoridayType(operation.getWorkDate().plusDays(1)) == HoridayType.HORIDAY){
			if(operation.getEndTime().isBefore(getWorkStartTime(operation.getWorkTimeType()))){
				startTime = LocalDateTime.of(operation.getWorkDate().plusDays(1), LocalTime.of(0, 0));
				endTime = endTime.plusDays(1);
				horidayWorkinfHours = horidayWorkinfHours.add(calcurateOperationHours(operation.getWorkTimeType(), operation.getWorkDate(), startTime, endTime));
			}
		}
		return horidayWorkinfHours;
	}
	
	/**
	 * 遅刻時間を取得します。
	 * 
	 * @param operation
	 * @return
	 */
	private BigDecimal getLateHours(WorkTime operation){
		//開始時刻未入力は計算しない。
		if(ObjectUtils.isEmpty(operation.getWorkDate())
				|| ObjectUtils.isEmpty(operation.getStartTime())){
			return ZERO_HOUR;
		}
		
		BigDecimal lateHoures = ZERO_HOUR;
		LocalTime workStartTime;
		//平日出勤のみ計算。
		switch(operation.getAttendanceCode()){
		case 出勤:
		case 午後半休:
			workStartTime = getWorkStartTime(operation.getWorkTimeType());
			break;
		case 午前半休:
			workStartTime = getHalfDayOffLimitTime(operation.getWorkTimeType());
			break;
		default:
			return lateHoures;
		}
		
		LocalDateTime workStartDateTime = LocalDateTime.of(operation.getWorkDate(), workStartTime);
		
		LocalDateTime startTime = LocalDateTime.of(operation.getWorkDate(), operation.getStartTime());
		if(operation.getStartTime().isBefore(workStartTime)){
			startTime = LocalDateTime.of(operation.getWorkDate(), workStartTime);
		}

		lateHoures = calcurateOperationHours(operation.getWorkTimeType(), operation.getWorkDate(), workStartDateTime, startTime);
		return lateHoures;
	}
	
	/**
	 * 早退時間を取得します。
	 * 
	 * @param operation
	 * @return
	 */
	private BigDecimal getLeaveEaryHours(WorkTime operation){
		//終了時刻未入力は計算しない。
		if(ObjectUtils.isEmpty(operation.getWorkDate())
				|| ObjectUtils.isEmpty(operation.getEndTime())){
			return ZERO_HOUR;
		}

		LocalTime workEndTime;
		//代出の場合は開始時刻が未入力の場合計算しない。
		switch(operation.getAttendanceCode()){
		case 出勤:
		case 午後半休:
		case 午前半休:
		case 代出:
			workEndTime = getWorkEndTime(operation.getWorkTimeType());
			break;
		default:
			return ZERO_HOUR;
		}
		
		LocalDateTime workEndDateTime = LocalDateTime.of(operation.getWorkDate(), workEndTime);
		LocalDateTime workStartTime = LocalDateTime.of(operation.getWorkDate(), getWorkStartTime(operation.getWorkTimeType()));
		
		LocalDateTime endTime = LocalDateTime.of(operation.getWorkDate(), operation.getEndTime());
		//日が変わっていたら+1日する
		if(endTime.isBefore(workStartTime)){
			endTime = endTime.plusDays(1);
		}
		//所定終業時間より終了時刻が遅ければ0
		if(endTime.isAfter(workEndDateTime)){
			return ZERO_HOUR;
		}

		return calcurateOperationHours(operation.getWorkTimeType(), operation.getWorkDate(), endTime, workEndDateTime);
	}
	
	/**
	 * 有給か判定します。
	 * 
	 * @param operation 勤務情報
	 * @return true：有給／false：有給でない
	 */
	private boolean isDayOff(WorkTime operation){
		boolean isDayOff = false;
		switch(operation.getAttendanceCode()){
		case 有給:
		case 年特:
			isDayOff = true;
			break;
		default:
			break;
		}
		return isDayOff;
	}
	
	/**
	 * 半休か判定します。
	 * 
	 * @param operation 勤務情報
	 * @return true：半休／false：半休でない
	 */
	private boolean isHalfDayOff(WorkTime operation){
		boolean isHalfDayOff = false;
		switch(operation.getAttendanceCode()){
		case 午前半休:
		case 午後半休:
			isHalfDayOff = true;
			break;
		default:
			break;
		}
		return isHalfDayOff;
	}
	
	/**
	 * 勤務帯の所定労働時間を取得します。
	 * 
	 * @param workTimeCd 勤務帯
	 * @return 所定労働時間(H)
	 * @throws IllegalArgumentException 勤務帯未指定
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
	 * 勤務帯の始業時間を取得します
	 * 。
	 * @param workTimeCd 勤務帯コード
	 * @return 始業時間
	 * @throws IllegalArgumentException 勤務帯未指定
	 */
	private LocalTime getWorkStartTime(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new IllegalArgumentException("not exist in cache workTimeCd:" + workTimeCd.getValue());
		}
		return workTimeType.getStartTime();
	}
	
	/**
	 * 勤務帯の終業時間を取得します。
	 * @param workTimeCd 勤務帯コード
	 * @return 終業時間
	 * @throws IllegalArgumentException 勤務帯未指定
	 */
	private LocalTime getWorkEndTime(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new IllegalArgumentException("not exist in cache workTimeCd:" + workTimeCd.getValue());
		}
		return workTimeType.getEndTime();
	}
	
	/**
	 * 勤務帯の半休境界時間を取得します
	 * 。
	 * @param workTimeCd 勤務帯コード
	 * @return 半休境界時間
	 * @throws IllegalArgumentException 勤務帯未指定
	 */
	private LocalTime getHalfDayOffLimitTime(WorkTimeCode workTimeCd){
		WorkTimeType workTimeType = workTimeTypeCache.get(workTimeCd.getValue());
		if(workTimeType == null){
			throw new IllegalArgumentException("not exist in cache workTimeCd:" + workTimeCd.getValue());
		}
		return workTimeType.getHalfDayOffLimitTime();
	}
	
	/**
	 * 勤務帯情報をキャッシュします。
	 * <br>
	 * 本メソッドは、{@link PostConstruct}により、アプリケーション起動時に実行されます。
	 */
	@PostConstruct
	private void cache(){
		List<WorkTimeType> workTimeTypes = workTimeTypeDao.selectAll();
		for(WorkTimeType workTimeType : workTimeTypes){
			workTimeTypeCache.put(workTimeType.getWorkTimeCd().getValue(), workTimeType);
		}
	}
}
