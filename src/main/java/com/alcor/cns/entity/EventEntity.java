package com.alcor.cns.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author roamer - 徐泽宇
 * @create 2017-10-2017/10/11  下午12:47
 */
@Entity
@Table(name = "event", schema = "cns", catalog = "")
public class EventEntity {
    private String id;
    private String name;
    private String memo;
    private String noticeContent;
    private Date actDate;
    private Integer repeatType;
    private Boolean notice;
    private String noticeMail;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
    @Column(name = "act_date")
    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    @Basic
    @Column(name = "repeat_type")
    public Integer getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(Integer repeatType) {
        this.repeatType = repeatType;
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
    @Column(name = "notice_mail")
    public String getNoticeMail() {
        return noticeMail;
    }

    public void setNoticeMail(String noticeMail) {
        this.noticeMail = noticeMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (memo != null ? !memo.equals(that.memo) : that.memo != null) return false;
        if (noticeContent != null ? !noticeContent.equals(that.noticeContent) : that.noticeContent != null)
            return false;
        if (actDate != null ? !actDate.equals(that.actDate) : that.actDate != null) return false;
        if (repeatType != null ? !repeatType.equals(that.repeatType) : that.repeatType != null) return false;
        if (notice != null ? !notice.equals(that.notice) : that.notice != null) return false;
        if (noticeMail != null ? !noticeMail.equals(that.noticeMail) : that.noticeMail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (memo != null ? memo.hashCode() : 0);
        result = 31 * result + (noticeContent != null ? noticeContent.hashCode() : 0);
        result = 31 * result + (actDate != null ? actDate.hashCode() : 0);
        result = 31 * result + (repeatType != null ? repeatType.hashCode() : 0);
        result = 31 * result + (notice != null ? notice.hashCode() : 0);
        result = 31 * result + (noticeMail != null ? noticeMail.hashCode() : 0);
        return result;
    }
}
