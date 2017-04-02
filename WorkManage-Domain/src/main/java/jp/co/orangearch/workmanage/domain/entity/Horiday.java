package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

/**
 * 
 */
@Entity(listener = HoridayListener.class)
@Table(name = "HORIDAY")
public class Horiday extends TableSuffix {

    /** 日付 */
    @Id
    @Column(name = "DATE")
    LocalDate date;

    /** 祝日名 */
    @Column(name = "HORIDAY_NAME")
    String horidayName;

    /** 
     * Returns the date.
     * 
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /** 
     * Sets the date.
     * 
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /** 
     * Returns the horidayName.
     * 
     * @return the horidayName
     */
    public String getHoridayName() {
        return horidayName;
    }

    /** 
     * Sets the horidayName.
     * 
     * @param horidayName the horidayName
     */
    public void setHoridayName(String horidayName) {
        this.horidayName = horidayName;
    }
}