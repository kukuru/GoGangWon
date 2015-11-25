package com.nankuru.gogangwon.db;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class DbConst {
    public static final String TALBES[] = {
            "create table empty_house ("
                    +" _id integer primary key autoincrement, "
                    +"address text not null, "
                    +"owner text not null, "
                    +"build_year text not null, "
                    +"usage text not null)",
            "create table hospital ("
                    +" _id integer primary key autoincrement, "
                    +"gov_type text not null, "
                    +"title text not null, "
                    +"address text not null, "
                    +"phonenumber text not null, "
                    +"homepage text not null)",
            "create table market ("
                    +" _id integer primary key autoincrement, "
                    +"gov_type text not null, "
                    +"title text not null, "
                    +"address text not null, "
                    +"phonenumber text not null)",
    };

    public static final String QUERY_SELECT_EMPTY_HOUSE_ALL_DATA = "select * from empty_house";
    public static final String QUERY_SELECT_HOSPITAL_ALL_DATA = "select * from hospital";
    public static final String QUERY_SELECT_MARKET_ALL_DATA = "select * from market";
    public static final String QUERY_SELECT_CONTAIN_SPECIFIC_WORD = "select * from %s where address like '% %s %'";

    public static final String EMPTY_HOUSE_TABLE_NAME = "empty_house" ;

    public static final String HOSPITAL_TABLE_NAME = "hospital";

    public static final String MARKET_TABLE_NAME = "market";

    public static enum DATA_TYPE
    {
        EMPTY_HOUSE,
        HOSPITAL,
        MARKET
    }
}
