package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class Market {

    public int list_total_count;

    @SerializedName("RESULT")
    public ResultData resultData = new ResultData();

    @SerializedName("row")
    public List<MarketRow> row = new ArrayList<MarketRow>();

    public static class MarketRow
    {
        @SerializedName("CNTNTS_AREA2") String govType;
        @SerializedName("CNTNTS_TITLE") String name;
        @SerializedName("CNTNTS_ADRES") String address;
        @SerializedName("CNTNTS_TELNO") String telNum;

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
    }
}
