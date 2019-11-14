package com.softlab.common.model;

import java.io.Serializable;

/**
 * Created by LiXiwen on 2019/3/25.
 **/
public class ColleageContent implements Serializable {
    private Integer id;
    private String bumen;
    private String title;
    private String detail;
    private String time;
    private String kind;
    private String closable;
    private String tip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getClosable() {
        return closable;
    }

    public void setClosable(String closable) {
        this.closable = closable;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getBumen() {
        return bumen;
    }

    public void setBumen(String bumen) {
        this.bumen = bumen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
