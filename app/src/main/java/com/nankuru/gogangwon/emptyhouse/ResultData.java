package com.nankuru.gogangwon.emptyhouse;


import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 17..
 */
public class ResultData {
    String CODE;
    String MESSAGE;


    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
