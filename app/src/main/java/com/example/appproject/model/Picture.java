package com.example.appproject.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Picture {

    @Id(autoincrement = true)
    private Long id;

    private String code;

    private String imgurl;

    @Generated(hash = 542267207)
    public Picture(Long id, String code, String imgurl) {
        this.id = id;
        this.code = code;
        this.imgurl = imgurl;
    }

    @Generated(hash = 1602548376)
    public Picture() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgurl() {
        return this.imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}
