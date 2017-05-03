package jp.co.orangearch.workmanage.form.workTime;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import jp.co.orangearch.workmanage.common.validator.DateValid;
import jp.co.orangearch.workmanage.common.validator.EnumValue;
import jp.co.orangearch.workmanage.component.util.DateUtils;
import jp.co.orangearch.workmanage.domain.constant.OnewayRoundType;
import jp.co.orangearch.workmanage.domain.constant.TransportionType;
import jp.co.orangearch.workmanage.domain.entity.TransportionExpense;

/**
 * 交通費情報入力フォームクラス。
 * 
 * @author t-otsuka
 *
 */
public class TransportExpenseForm {

    /** 勤務日 */
	@NotEmpty
	@DateValid(pattern="uuuu-M-d")
	private String workDate;
	
    /** ユーザーID */
	@NotEmpty
	@Length(max=7)
	private String userId;
	
    /** 交通費通番 */
	@NotEmpty
	@Pattern(regexp="[0-9]+")
	private String transportNumber;

    /** 交通手段 */
	@NotEmpty
	@EnumValue(type=TransportionType.class)
    private String transportType;

    /** 片道往復 */
	@NotEmpty
	@EnumValue(type=OnewayRoundType.class)
    private String onewayRoundType;

    /** 乗車 */
	@NotEmpty
	@Length(max=25)
    private String rideFrom;

    /** 下車 */
	@NotEmpty
	@Length(max=25)
    private String rideTo;

    /** 金額 */
	@NotEmpty
	@Pattern(regexp="[0-9]+")
    private String expense;

    /** 目的地 */
	@NotEmpty
	@Length(max=25)
    private String destination;

    /** 理由 */
	@NotEmpty
	@Length(max=25)
    private String reason;

    /** バージョン(楽観排他用) */
	private Integer version;
	
	/**
	 * コンストラクタ
	 */
	public TransportExpenseForm() {
	}

	/**
	 * コンストラクタ
	 * 
	 * @param transportionExpense 交通費情報entity
	 */
	public TransportExpenseForm(TransportionExpense transportionExpense) {
		setWorkDate(transportionExpense.getWorkDate());
		setUserId(transportionExpense.getUserId());
		setTransportNumber(transportionExpense.getTransportNumber());
		setTranseportType(transportionExpense.getTranseportType());
		setOnewayRoundType(transportionExpense.getOneWayRoundType());
		setRideFrom(transportionExpense.getRideFrom());
		setRideTo(transportionExpense.getRideTo());
		setExpense(transportionExpense.getExpense());
		setDestination(transportionExpense.getDestination());
		setReason(transportionExpense.getReason());
		setVersion(transportionExpense.getVersion());
	}

	public String getWorkDate() {
		return workDate;
	}

	public LocalDate getWorkDateAsLocalDate() {
		return DateUtils.convertToLocalDate(workDate);
	}

	public void setWorkDate(String value) {
		workDate = value;
	}

	private void setWorkDate(LocalDate value) {
		workDate = DateUtils.convert(value);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String value) {
		userId = value;
	}

	/** 交通費通番 */
	public String getTransportNumber(){
		return transportNumber;
	}

	private Integer getTransportNumberAsInteger() {
		return Integer.valueOf(transportNumber);
	}

	public void setTransportNumber(String value){
		transportNumber = value;
	}

	private void setTransportNumber(Integer value) {
		transportNumber = String.valueOf(value);
		
	}

	/** 交通手段 */
	public String getTransportType(){
		return transportType;
	}

	private TransportionType getTransportTypeAsEnum() {
		return TransportionType.of(Integer.valueOf(transportType));
	}

	public void setTransportType(String value){
		transportType = value;
	}

	private void setTranseportType(TransportionType value) {
		transportType = value.getKey();
		
	}

	/** 片道往復 */
	public String getOnewayRoundType(){
		return onewayRoundType;
	}

	private OnewayRoundType getOnewayRoundTypeAsEnum() {
		return OnewayRoundType.of(Integer.valueOf(onewayRoundType));
	}

	public void setOnewayRoundType(String value){
		onewayRoundType = value;
	}

	private void setOnewayRoundType(OnewayRoundType value) {
		onewayRoundType = value.getKey();
		
	}

	/** 乗車 */
	public String getRideFrom(){
		return rideFrom;
	}

	public void setRideFrom(String value){
		rideFrom = value;
	}

	/** 下車 */
	public String getRideTo(){
		return rideTo;
	}

	public void setRideTo(String value){
		rideTo = value;
	}

	/** 金額 */
	public String getExpense(){
		return expense;
	}

	private Integer getExpenseAsInt() {
		return Integer.valueOf(expense);
	}

	public void setExpense(String value){
		expense = value;
	}

	private void setExpense(Integer value) {
		expense = String.valueOf(value);
	}

	/** 目的地 */
	public String getDestination(){
		return destination;
	}

	public void setDestination(String value){
		destination = value;
	}

	/** 理由 */
	public String getReason(){
		return reason;
	}

	public void setReason(String value){
		reason = value;
	}

	/** バージョン */
	public void setVersion(Integer value) {
		version = value;
	}

	public Integer getVersion() {
		return version;
	}

	public TransportionExpense toEntity() {
		TransportionExpense entity = new TransportionExpense();
		entity.setWorkDate(getWorkDateAsLocalDate());
		entity.setUserId(getUserId());
		entity.setTransportNumber(getTransportNumberAsInteger());
		entity.setTranseportType(getTransportTypeAsEnum());
		entity.setOneWayRoundType(getOnewayRoundTypeAsEnum());
		entity.setRideFrom(getRideFrom());
		entity.setRideTo(getRideTo());
		entity.setExpense(getExpenseAsInt());
		entity.setDestination(getDestination());
		entity.setReason(getReason());
		entity.setVersion(getVersion());
		return entity;
	}

}
