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
        public String ADDRESS;
        public String MST_NM;
        public String AREA_FU;
        public String LAYER_CNT;
        public String AREA_LO;
        public String CREATE_TYPE;
        public String CREATE_YEAR;
        public String CREATE_USE;

        public String getADDRESS() {
            return ADDRESS;
        }

        public void setADDRESS(String ADDRESS) {
            this.ADDRESS = ADDRESS;
        }

        public String getMST_NM() {
            return MST_NM;
        }

        public void setMST_NM(String MST_NM) {
            this.MST_NM = MST_NM;
        }

        public String getAREA_FU() {
            return AREA_FU;
        }

        public void setAREA_FU(String AREA_FU) {
            this.AREA_FU = AREA_FU;
        }

        public String getLAYER_CNT() {
            return LAYER_CNT;
        }

        public void setLAYER_CNT(String LAYER_CNT) {
            this.LAYER_CNT = LAYER_CNT;
        }

        public String getAREA_LO() {
            return AREA_LO;
        }

        public void setAREA_LO(String AREA_LO) {
            this.AREA_LO = AREA_LO;
        }

        public String getCREATE_TYPE() {
            return CREATE_TYPE;
        }

        public void setCREATE_TYPE(String CREATE_TYPE) {
            this.CREATE_TYPE = CREATE_TYPE;
        }

        public String getCREATE_YEAR() {
            return CREATE_YEAR;
        }

        public void setCREATE_YEAR(String CREATE_YEAR) {
            this.CREATE_YEAR = CREATE_YEAR;
        }

        public String getCREATE_USE() {
            return CREATE_USE;
        }

        public void setCREATE_USE(String CREATE_USE) {
            this.CREATE_USE = CREATE_USE;
        }
    }
}


