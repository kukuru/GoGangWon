package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class GangwonMarket {
    @SerializedName("medical_tour-medical") public Market market = new Market();
    public Market getMarket()
    {
        return market;
    }
}
