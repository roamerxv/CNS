package com.alcor.cns.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/26  下午4:10
 */
@Entity
@Table(name = "gather_info", schema = "cns", catalog = "")
public class GatherInfoEntity {
    private String id;
    private String contractId;
    private String name;
    private String description;
    private Double amount;
    private Date gatherDate;
    private Date noticeDate;
    private String noticeContent;
    private String noticeTo;
    private Boolean notice;
    private Boolean gathered;
    private Date gatheredDate;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contract_id")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "gather_date")
    public Date getGatherDate() {
        return gatherDate;
    }

    public void setGatherDate(Date gatherDate) {
        this.gatherDate = gatherDate;
    }

    @Basic
    @Column(name = "notice_date")
    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    @Basic
    @Column(name = "notice_content")
    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    @Basic
    @Column(name = "notice_to")
    public String getNoticeTo() {
        return noticeTo;
    }

    public void setNoticeTo(String noticeTo) {
        this.noticeTo = noticeTo;
    }

    @Basic
    @Column(name = "notice")
    public Boolean getNotice() {
        return notice;
    }

    public void setNotice(Boolean notice) {
        this.notice = notice;
    }

    @Basic
    @Column(name = "gathered")
    public Boolean getGathered() {
        return gathered;
    }

    public void setGathered(Boolean gathered) {
        this.gathered = gathered;
    }

    @Basic
    @Column(name = "gathered_date")
    public Date getGatheredDate() {
        return gatheredDate;
    }

    public void setGatheredDate(Date gatheredDate) {
        this.gatheredDate = gatheredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GatherInfoEntity that = (GatherInfoEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (contractId != null ? !contractId.equals(that.contractId) : that.contractId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (gatherDate != null ? !gatherDate.equals(that.gatherDate) : that.gatherDate != null) return false;
        if (noticeDate != null ? !noticeDate.equals(that.noticeDate) : that.noticeDate != null) return false;
        if (noticeContent != null ? !noticeContent.equals(that.noticeContent) : that.noticeContent != null)
            return false;
        if (noticeTo != null ? !noticeTo.equals(that.noticeTo) : that.noticeTo != null) return false;
        if (notice != null ? !notice.equals(that.notice) : that.notice != null) return false;
        if (gathered != null ? !gathered.equals(that.gathered) : that.gathered != null) return false;
        if (gatheredDate != null ? !gatheredDate.equals(that.gatheredDate) : that.gatheredDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contractId != null ? contractId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (gatherDate != null ? gatherDate.hashCode() : 0);
        result = 31 * result + (noticeDate != null ? noticeDate.hashCode() : 0);
        result = 31 * result + (noticeContent != null ? noticeContent.hashCode() : 0);
        result = 31 * result + (noticeTo != null ? noticeTo.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (gathered != null ? gathered.hashCode() : 0);
        result = 31 * result + (gatheredDate != null ? gatheredDate.hashCode() : 0);
        return result;
    }
}
