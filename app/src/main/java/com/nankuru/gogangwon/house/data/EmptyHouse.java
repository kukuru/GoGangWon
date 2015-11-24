package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanjui on 2015. 11. 19..
 */
public class EmptyHouse {
    public int list_total_count;

    @SerializedName("RESULT")
    public ResultData resultData = new ResultData();

    @SerializedName("row")
    public List<EmptyHouseRow> row = new ArrayList<EmptyHouseRow>();

    public ResultData getResultData()
    {
        return resultData;
    }

    public List<EmptyHouseRow> getRow()
    {
        return row;
    }

    public static class EmptyHouseRow {
        @SerializedName("ADDRESS")      String address;
        @SerializedName("MST_NM")       String owner;
        @SerializedName("LAYER_CNT")    String layerCnt;
        @SerializedName("CREATE_YEAR")  String builtYear;
        @SerializedName("CREATE_USE")   String uage;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getLayerCnt() {
            return layerCnt;
        }

        public void setLayerCnt(String layerCnt) {
            this.layerCnt = layerCnt;
        }

        public String getBuiltYear() {
            return builtYear;
        }

        public void setBuiltYear(String builtYear) {
            this.builtYear = builtYear;
        }

        public String getUage() {
            return uage;
        }

        public void setUage(String uage) {
            this.uage = uage;
        }
    }
}


