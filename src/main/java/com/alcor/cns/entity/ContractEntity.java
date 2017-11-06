package com.alcor.cns.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author roamer - 徐泽宇
 * @create 2017-11-2017/11/4  下午11:09
 */
@Entity
@Table(name = "contract", schema = "cns", catalog = "")
public class ContractEntity {
    private String id;
    private String name;
    private String description;
    private String customerId;
    private Double amount;
    private Date firstGatherDate;
    private Integer gatherInterval;
    private Date beginDate;
    private Date endDate;
    private Integer gatherCount;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "customer_id", nullable = false, length = 36)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 2)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "first_gather_date", nullable = false)
    public Date getFirstGatherDate() {
        return firstGatherDate;
    }

    public void setFirstGatherDate(Date firstGatherDate) {
        this.firstGatherDate = firstGatherDate;
    }

    @Basic
    @Column(name = "gather_interval", nullable = false)
    public Integer getGatherInterval() {
        return gatherInterval;
    }

    public void setGatherInterval(Integer gatherInterval) {
        this.gatherInterval = gatherInterval;
    }

    @Basic
    @Column(name = "begin_date", nullable = false)
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractEntity that = (ContractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (firstGatherDate != null ? !firstGatherDate.equals(that.firstGatherDate) : that.firstGatherDate != null)
            return false;
        if (gatherInterval != null ? !gatherInterval.equals(that.gatherInterval) : that.gatherInterval != null)
            return false;
        if (beginDate != null ? !beginDate.equals(that.beginDate) : that.beginDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (firstGatherDate != null ? firstGatherDate.hashCode() : 0);
        result = 31 * result + (gatherInterval != null ? gatherInterval.hashCode() : 0);
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "gather_count", nullable = false)
    public Integer getGatherCount() {
        return gatherCount;
    }

    public void setGatherCount(Integer gatherCount) {
        this.gatherCount = gatherCount;
    }
}
