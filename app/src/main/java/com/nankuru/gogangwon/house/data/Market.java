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

    public class MarketRow
    {
        String GOV_TYPE;
        String MED_NM;
        String MED_ADDRESS;
        String TEL_NUM;
        String FAX_NUM;
        String HOMEPAGE;
    }
}
