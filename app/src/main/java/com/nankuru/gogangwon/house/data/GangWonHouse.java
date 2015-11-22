package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 17..
 */
public class GangWonHouse {
    @SerializedName("gangwon-housing-empty_house") EmptyHouse emptyHouse = new EmptyHouse();
    public EmptyHouse getEmptyHouse() {
        return emptyHouse;
    }

}
