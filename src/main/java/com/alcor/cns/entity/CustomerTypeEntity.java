package com.alcor.cns.entity;

import javax.persistence.*;

/**
 * @author roamer - 徐泽宇
 * @create 2017-11-2017/11/6  上午11:53
 */
@Entity
@Table(name = "customer_type", schema = "cns", catalog = "")
public class CustomerTypeEntity {
    private String id;
    private String name;
    private String pId;

    @Id
    @Column(name = "id", nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "p_id", nullable = false, length = 36)
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerTypeEntity that = (CustomerTypeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (pId != null ? !pId.equals(that.pId) : that.pId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        return result;
    }
}
