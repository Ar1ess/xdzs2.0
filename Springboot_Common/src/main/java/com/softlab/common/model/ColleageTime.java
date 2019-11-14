package com.softlab.common.model;

import java.io.Serializable;

/**
 * Created by LiXiwen on 2019/3/25.
 **/
public class ColleageTime implements Serializable {
    private String electric;
    private String library;
    private String swim;
    private String bath;
    private String hospital;
    private String hall;

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getSwim() {
        return swim;
    }

    public void setSwim(String swim) {
        this.swim = swim;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}
