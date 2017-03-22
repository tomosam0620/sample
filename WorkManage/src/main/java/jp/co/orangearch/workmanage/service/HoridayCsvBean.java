package jp.co.orangearch.workmanage.service;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import com.github.mygreen.supercsv.annotation.CsvBean;
import com.github.mygreen.supercsv.annotation.CsvColumn;
import com.github.mygreen.supercsv.validation.beanvalidation.CsvBeanValidator;

import jp.co.orangearch.workmanage.common.validator.DateValid;

@CsvBean(header=true, validators=CsvBeanValidator.class)
public class HoridayCsvBean {
    /** 日付 */
	@CsvColumn(number=1)
	@NotEmpty
	@DateValid(pattern="uuuu-MM-dd")
	String date;

    /** 祝日名 */
	@CsvColumn(number=2)
	@NotEmpty
    String horidayName;

	public HoridayCsvBean(){
	}
	
    /** 
     * Returns the date.
     * 
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /** 
     * Sets the date.
     * 
     * @param date the date
     */
    public void setDate(String date) {
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
    
    public String toString(){
    	return (StringUtils.isEmpty(date)? "" : date.toString()) + ":" + horidayName;
    }

}
