package jp.co.orangearch.workmanage.domain.entity;

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
@Entity(listener = CustomerListener.class)
@Table(name = "CUSTOMER")
public class Customer extends TableSuffix {

    /** 顧客ID */
    @Id
    @Column(name = "CUSTOMER_ID")
    Integer customerId;

    /** 顧客名 */
    @Column(name = "CUSTOMER_NAME")
    String customerName;

    /** 
     * Returns the customerId.
     * 
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /** 
     * Sets the customerId.
     * 
     * @param customerId the customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /** 
     * Returns the customerName.
     * 
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /** 
     * Sets the customerName.
     * 
     * @param customerName the customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}