package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class GangwonHospital {
    @SerializedName("gangwon-housing-empty_house") EmptyHouse emptyHouse = new EmptyHouse();
    public EmptyHouse getHospital() {
        return emptyHouse;
    }
}
