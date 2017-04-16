package jp.co.orangearch.workmanage.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jp.co.orangearch.workmanage.domain.entity.common.TableSuffix;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

/**
 * 
 */
@Entity(listener = ProjectListener.class)
@Table(name = "PROJECT")
public class Project extends TableSuffix {

    /** プロジェクトID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    Integer projectId;

    /** プロジェクト名 */
    @Column(name = "PROJECT_NAME")
    String projectName;

    /** 顧客ID */
    @Column(name = "CUSTOMER_ID")
    Integer customerId;

    /** 開始日 */
    @Column(name = "START_DATE")
    LocalDate startDate;

    /** 終了日 */
    @Column(name = "END_DATE")
    LocalDate endDate;

    /** 
     * Returns the projectId.
     * 
     * @return the projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /** 
     * Sets the projectId.
     * 
     * @param projectId the projectId
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /** 
     * Returns the projectName.
     * 
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /** 
     * Sets the projectName.
     * 
     * @param projectName the projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

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
     * Returns the startDate.
     * 
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** 
     * Sets the startDate.
     * 
     * @param startDate the startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /** 
     * Returns the endDate.
     * 
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** 
     * Sets the endDate.
     * 
     * @param endDate the endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}