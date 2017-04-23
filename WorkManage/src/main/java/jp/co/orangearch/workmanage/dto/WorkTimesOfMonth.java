package jp.co.orangearch.workmanage.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 月の稼働情報を保持するクラスです。
 * 
 * @author t-otska
 *
 */
public class WorkTimesOfMonth {
	/** 稼働情報のリスト */
	private List<OperationTime> operaionTimes;
	/** 稼働時間(月) */
	private BigDecimal operationHours;
	/** 法内残業時間(月) */
	private BigDecimal overtimeWithinSWHours;
	/** 法外業時間(月) */
	private BigDecimal overtimeBeyondSWHours;
	
	private boolean isExist = false;
	private int dayOffCount;
	private int halfDayOffCount;
	/**
	 * コンストラクタ
	 * 
	 * @param operaionTimes 稼働情報のリスト
	 * @param operationHours 稼働時間(月)
	 * @param overtimeWithinSWHours 法内残業時間(月)
	 * @param overtimeBeyondSWHours 法外残業時間(月)
	 */
	public WorkTimesOfMonth(List<OperationTime> operaionTimes, BigDecimal operationHours,
			BigDecimal overtimeWithinSWHours, BigDecimal overtimeBeyondSWHours) {
		this.operaionTimes = operaionTimes;
		this.operationHours = operationHours;
		this.overtimeWithinSWHours = overtimeWithinSWHours;
		this.overtimeBeyondSWHours = overtimeBeyondSWHours;
	}

	/**
	 * 稼働情報のリストを取得します。
	 * 
	 * @return 稼働情報のリスト
	 */
	public List<OperationTime> getOperaionTimes(){
		return operaionTimes;
	}
	
	/**
	 * 稼働情報のリストを設定します。
	 * 
	 * @param value 稼働情報のリスト
	 */
	public void setOperaionTimes(List<OperationTime> value){
		operaionTimes = value;
	}
	
	/**
	 * 稼働時間(月)を取得します。
	 * 
	 * @return 稼働時間(月)
	 */
	public BigDecimal getOperationHours(){
		return operationHours;
	}
	
	/**
	 * 稼働時間(月)を設定します。
	 * 
	 * @param value 稼働時間(月)
	 */
	public void setOperationHours(BigDecimal value){
		operationHours = value;
	}
	
	/**
	 * 法内残業時間(月)を取得します。
	 * 
	 * @return 法内残業時間(月)
	 */
	public BigDecimal getOvertimeWithinSWHours(){
		return overtimeWithinSWHours;
	}
	
	/**
	 * 法内残業時間(月)を設定します。
	 * 
	 * @param value 法内残業時間(月)
	 */
	public void setOvertimeWithinSWHours(BigDecimal value){
		overtimeWithinSWHours = value;
	}
	
	/**
	 * 法外残業時間(月)を取得します。
	 * 
	 * @return 法外残業時間(月)
	 */
	public BigDecimal getOvertimeBeyondSWHours(){
		return overtimeBeyondSWHours;
	}
	
	/**
	 * 法外残業時間(月)を設定します。
	 * 
	 * @param value 法外残業時間(月)
	 */
	public void setOvertimeBeyondSWHours(BigDecimal value){
		overtimeBeyondSWHours = value;
	}

	public void setIsExist(boolean value) {
		isExist = value;
	}

	public boolean isExist() {
		return isExist;
	}

	public int getDayOffCount() {
		return dayOffCount;
	}

	public void setDayOffCount(int value) {
		dayOffCount = value;
	}

	public int getHalfDayOffCount() {
		return halfDayOffCount;
	}

	public void setHalfDayOffCount(int value) {
		halfDayOffCount = value;
	}

}
