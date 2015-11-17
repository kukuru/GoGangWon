package com.nankuru.gogangwon.emptyhouse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nanjui on 2015. 11. 17..
 */
public class GangWonHouse {
    @SerializedName("gangwon-housing-empty_house") EmptyHouse emptyHouse;
    public static class EmptyHouse {
        int list_total_count;

        @SerializedName("RESULT") ResultData resultData;

        @SerializedName("row") List<EmptyHouseRow> row;
    }
}
