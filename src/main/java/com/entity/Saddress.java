package com.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Saddress implements Serializable{
    private Long id;

    private String contact;

    private String addressdesc;

    private String postcode;

    private String tel;

    private Long createdby;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date creationdate;

    private Long modifyby;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date modifydate;

    private Long userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getAddressdesc() {
        return addressdesc;
    }

    public void setAddressdesc(String addressdesc) {
        this.addressdesc = addressdesc == null ? null : addressdesc.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Long getModifyby() {
        return modifyby;
    }

    public void setModifyby(Long modifyby) {
        this.modifyby = modifyby;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Saddress{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", addressdesc='" + addressdesc + '\'' +
                ", postcode='" + postcode + '\'' +
                ", tel='" + tel + '\'' +
                ", createdby=" + createdby +
                ", creationdate=" + creationdate +
                ", modifyby=" + modifyby +
                ", modifydate=" + modifydate +
                ", userid=" + userid +
                '}';
    }
}