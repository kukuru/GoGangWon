package com.nankuru.gogangwon.emptyhouse.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 19..
 */
public class EmptyHouse {
    int list_total_count;

    @SerializedName("RESULT")
    ResultData resultData = new ResultData();

//    @SerializedName("row") List<EmptyHouseRow> row;
}
