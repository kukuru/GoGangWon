package com.nankuru.gogangwon.house.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class GangwonHospital {
    @SerializedName("medical_tour-medical")  Hospital hospital = new Hospital();
    public Hospital getHospital() {
        return hospital;
    }
}
