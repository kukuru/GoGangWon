package com.nankuru.gogangwon.db;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class DbConst {
    public static final String TALBES[] = {
            "create table empty_house ("
                    +" _id integer primary key autoincrement, "
                    +"address text not null , "
                    +"owner text not null , "
                    +"build_year text not null"
                    +"usage text not null)",
            "create table hospital ("
                    +" _id integer primary key autoincrement, "
                    +"address text not null , "
                    +"title text not null , "
                    +"telephone text not null"
                    +"homepage text not null)",
            "create table market ("
                    +" _id integer primary key autoincrement, "
                    +"address text not null , "
                    +"title text not null , "
                    +"telephone text not null)",
            "create table youth ("
                    +" _id integer primary key autoincrement, "
                    +"address text not null , "
                    +"title text not null , "
                    +"telephone text not null)"

    };

    public static final String EMPTY_HOUSE_TABLE_NAME = "empty_house" ;

    public static final String HOSPITAL_TABLE_NAME = "hospital";

    public static final String MARKET_TABLE_NAME = "market";

    public static final String YOUTH_TABLE_NAME = "youth";
}
