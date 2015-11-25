package com.nankuru.gogangwon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.nankuru.gogangwon.house.data.EmptyHouse;
import com.nankuru.gogangwon.house.data.GangWonHouse;
import com.nankuru.gogangwon.house.data.GangwonHospital;
import com.nankuru.gogangwon.house.data.GangwonMarket;
import com.nankuru.gogangwon.house.data.GangwonWelfare;
import com.nankuru.gogangwon.house.data.Hospital;
import com.nankuru.gogangwon.house.data.HouseDetailData;
import com.nankuru.gogangwon.house.data.Market;

import java.util.ArrayList;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class DbHelper{

    private final static String DATABASE_NAME = "gangwon.db";
    private final static int VERSION = 1;
    private static DBOpenHelper mDbOpenHelper;
    private static SQLiteDatabase mDb;
    private static DbHelper mInstance;

    public static DbHelper getInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new DbHelper();
            initDB(context);
        }
        return mInstance;
    }

    private static void initDB(Context context)
    {
        mDbOpenHelper = new DBOpenHelper(context, DATABASE_NAME, null, VERSION);
        mDb = mDbOpenHelper.getWritableDatabase();
    }

    public void insertData(Object data)
    {
        if(mDb == null)
        {
            mDb = mDbOpenHelper.getWritableDatabase();
        }
        if(data instanceof EmptyHouse.EmptyHouseRow)
        {
            EmptyHouse.EmptyHouseRow row = (EmptyHouse.EmptyHouseRow)data;
            ContentValues values = new ContentValues();
            values.put("address", row.getAddress());
            values.put("owner", row.getOwner());
            values.put("build_year", row.getBuiltYear());
            values.put("usage", row.getUage());
            mDb.insert(DbConst.EMPTY_HOUSE_TABLE_NAME, null, values);
        }
        else if(data instanceof Market.MarketRow)
        {
            Hospital.HospitalRow row = (Hospital.HospitalRow)data;
            ContentValues values = new ContentValues();
            values.put("gov_type", row.getGovType());
            values.put("title", row.getName());
            values.put("address", row.getAddress());
            values.put("phonenumber", row.getTelNum());
            values.put("homepage", row.getHomepage());
            mDb.insert(DbConst.MARKET_TABLE_NAME, null, values);
        }
        else if(data instanceof Hospital.HospitalRow)
        {
            Hospital.HospitalRow row = (Hospital.HospitalRow)data;
            ContentValues values = new ContentValues();
            values.put("gov_type", row.getGovType());
            values.put("title", row.getName());
            values.put("address", row.getAddress());
            values.put("phonenumber", row.getTelNum());
            values.put("homepage", row.getHomepage());
            mDb.insert(DbConst.HOSPITAL_TABLE_NAME, null, values);
        }
    }

    public ArrayList<HouseDetailData> queryHouseDetailData(String[] arg)
    {
        ArrayList<HouseDetailData> array = new ArrayList<>();
        Cursor c = mDb.rawQuery(DbConst.QUERY_SELECT_CONTAIN_SPECIFIC_WORD, arg);
        c.moveToFirst();
        do{
            HouseDetailData data = new HouseDetailData();
            data.setAddress(c.getString(1));     //address
            data.setName(c.getString(2));        //name
        }while(c.moveToNext());
        return array;
    }

    public Object getDataAllByType(DbConst.DATA_TYPE type)
    {
        ArrayList<?> rowArrayList = new ArrayList<>();
        switch (type)
        {
            case EMPTY_HOUSE:
                Cursor c_empty = mDb.rawQuery(DbConst.QUERY_SELECT_EMPTY_HOUSE_ALL_DATA, null);
                c_empty.moveToFirst();
                do {
                    EmptyHouse.EmptyHouseRow row_empty = new EmptyHouse.EmptyHouseRow();

                    row_empty.setAddress(c_empty.getString(1));
                    row_empty.setOwner(c_empty.getString(2));
                    row_empty.setBuiltYear(c_empty.getString(3));
                    row_empty.setUage(c_empty.getString(4));
                    ((ArrayList<EmptyHouse.EmptyHouseRow>)rowArrayList).add(row_empty);
                }while(c_empty.moveToNext());
                c_empty.close();
                break;
            case HOSPITAL:
                Cursor c_hospital = mDb.rawQuery(DbConst.QUERY_SELECT_HOSPITAL_ALL_DATA, null);
                c_hospital.moveToFirst();

                do{
                    Hospital.HospitalRow row_hospital = new Hospital.HospitalRow();

                    row_hospital.setGovType(c_hospital.getString(1));
                    row_hospital.setName(c_hospital.getString(2));
                    row_hospital.setAddress(c_hospital.getString(3));
                    row_hospital.setTelNum(c_hospital.getString(4));
                    row_hospital.setHomepage(c_hospital.getString(5));
                    ((ArrayList<Hospital.HospitalRow>)rowArrayList).add(row_hospital);
                }while(c_hospital.moveToNext());
                c_hospital.close();
                break;
            case MARKET:
                Cursor c_market = mDb.rawQuery(DbConst.QUERY_SELECT_MARKET_ALL_DATA, null);
                c_market.moveToFirst();
                do{
                    Market.MarketRow row_market = new Market.MarketRow();
                    row_market.setGovType(c_market.getString(1));
                    row_market.setName(c_market.getString(2));
                    row_market.setAddress(c_market.getString(3));
                    row_market.setTelNum(c_market.getString(4));
                    ((ArrayList<Market.MarketRow>)rowArrayList).add(row_market);
                }while(c_market.moveToNext());
                c_market.close();
                break;
            default:
                break;
        }
        return rowArrayList;
    }


    static class DBOpenHelper extends SQLiteOpenHelper {

        DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (int i = 0; i < DbConst.TALBES.length; i++) {
                db.execSQL(DbConst.TALBES[i]);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
