package com.alcor.cns.entity;

import javax.persistence.*;

/**
 * @author roamer - 徐泽宇
 * @create 2017-11-2017/11/6  上午11:53
 */
@Entity
@Table(name = "customer", schema = "cns", catalog = "")
public class CustomerEntity {
    private String id;
    private String name;
    private String contacts;
    private String mobile;
    private String tel;
    private String address;
    private CustomerTypeEntity type;

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
    @Column(name = "contacts", nullable = true, length = 256)
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 36)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "tel", nullable = true, length = 36)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 256)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerEntity that = (CustomerEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (contacts != null ? !contacts.equals(that.contacts) : that.contacts != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    public CustomerTypeEntity getType() {
        return type;
    }

    public void setType(CustomerTypeEntity type) {
        this.type = type;
    }
}
