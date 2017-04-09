package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalDate;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

import jp.co.orangearch.workmanage.domain.constant.OnewayRoundType;
import jp.co.orangearch.workmanage.domain.constant.TransportionType;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;

/**
 * 
 */
@Entity(listener = TransportionExpenseListener.class)
@Table(name = "TRANSPORTION_EXPENSE")
public class TransportionExpense extends TableSuffix {

    /** ユーザーID */
    @Column(name = "USER_ID")
    String userId;

    /** 日付 */
    @Column(name = "WORK_DATE")
    LocalDate workDate;

    /** 交通手段 */
    @Column(name = "TRANSEPORT_TYPE")
    TransportionType transeportType;

    /** 片道往復 */
    @Column(name = "ONE_WAY_ROUND_TYPE")
    OnewayRoundType oneWayRoundType;

    /** 乗車 */
    @Column(name = "RIDE_FROM")
    String rideFrom;

    /** 下車 */
    @Column(name = "RIDE_TO")
    String rideTo;

    /** 金額 */
    @Column(name = "EXPENSE")
    Integer expense;

    /** 目的地 */
    @Column(name = "DESTINATION")
    String destination;

    /** 理由 */
    @Column(name = "REASON")
    String reason;

    /** 
     * Returns the userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /** 
     * Sets the userId.
     * 
     * @param userId the userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 
     * Returns the workDate.
     * 
     * @return the workDate
     */
    public LocalDate getWorkDate() {
        return workDate;
    }

    /** 
     * Sets the workDate.
     * 
     * @param workDate the workDate
     */
    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    /** 
     * Returns the transeportType.
     * 
     * @return the transeportType
     */
    public TransportionType getTranseportType() {
        return transeportType;
    }

    /** 
     * Sets the transeportType.
     * 
     * @param transeportType the transeportType
     */
    public void setTranseportType(TransportionType transeportType) {
        this.transeportType = transeportType;
    }

    /** 
     * Returns the oneWayRoundType.
     * 
     * @return the oneWayRoundType
     */
    public OnewayRoundType getOneWayRoundType() {
        return oneWayRoundType;
    }

    /** 
     * Sets the oneWayRoundType.
     * 
     * @param oneWayRoundType the oneWayRoundType
     */
    public void setOneWayRoundType(OnewayRoundType oneWayRoundType) {
        this.oneWayRoundType = oneWayRoundType;
    }

    /** 
     * Returns the rideFrom.
     * 
     * @return the rideFrom
     */
    public String getRideFrom() {
        return rideFrom;
    }

    /** 
     * Sets the rideFrom.
     * 
     * @param rideFrom the rideFrom
     */
    public void setRideFrom(String rideFrom) {
        this.rideFrom = rideFrom;
    }

    /** 
     * Returns the rideTo.
     * 
     * @return the rideTo
     */
    public String getRideTo() {
        return rideTo;
    }

    /** 
     * Sets the rideTo.
     * 
     * @param rideTo the rideTo
     */
    public void setRideTo(String rideTo) {
        this.rideTo = rideTo;
    }

    /** 
     * Returns the expense.
     * 
     * @return the expense
     */
    public Integer getExpense() {
        return expense;
    }

    /** 
     * Sets the expense.
     * 
     * @param expense the expense
     */
    public void setExpense(Integer expense) {
        this.expense = expense;
    }

    /** 
     * Returns the destination.
     * 
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /** 
     * Sets the destination.
     * 
     * @param destination the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /** 
     * Returns the reason.
     * 
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /** 
     * Sets the reason.
     * 
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}