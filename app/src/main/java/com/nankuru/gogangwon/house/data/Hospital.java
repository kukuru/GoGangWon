package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chitacan on 2015. 11. 24..
 */
public class Hospital {
    public int list_total_count;

    @SerializedName("RESULT")
    public ResultData resultData = new ResultData();

    @SerializedName("row")
    public List<HospitalRow> row = new ArrayList<HospitalRow>();

    public static class HospitalRow
    {
        @SerializedName("GOV_TYPE")     String govType;
        @SerializedName("MED_NM")       String name;
        @SerializedName("MED_ADDRESS")  String address;
        @SerializedName("TEL_NUM")      String telNum;
        @SerializedName("HOMEPAGE")     String homepage;

        public String getGovType() {
            return govType;
        }

        public void setGovType(String govType) {
            this.govType = govType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelNum() {
            return telNum;
        }

        public void setTelNum(String telNum) {
            this.telNum = telNum;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }
    }
}
