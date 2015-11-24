package com.nankuru.gogangwon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.nankuru.gogangwon.house.data.EmptyHouse;
import com.nankuru.gogangwon.house.data.GangWonHouse;
import com.nankuru.gogangwon.house.data.GangwonHospital;
import com.nankuru.gogangwon.house.data.GangwonMarket;
import com.nankuru.gogangwon.house.data.GangwonWelfare;
import com.nankuru.gogangwon.house.data.Hospital;
import com.nankuru.gogangwon.house.data.Market;

import java.util.ArrayList;

/**
 * Created by nanjui on 2015. 11. 21..
 */
public class DbHelper{

    private final String DATABASE_NAME = "gangwon.db";
    private final int VERSION = 1;
    private DBOpenHelper mDbOpenHelper;
    private SQLiteDatabase mDb;
    private Context mCtx;
    private static DbHelper mInstance;

    public DbHelper getInstance(Context context)
    {
        mCtx = context;
        if(mInstance == null)
        {
            mInstance = new DbHelper();
            initDB();
        }
        return mInstance;
    }

    private void initDB()
    {
        mDbOpenHelper = new DBOpenHelper(mCtx, DATABASE_NAME, null, VERSION);
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
            values.put("address", row.getADDRESS());
            values.put("owner", row.MST_NM);
            values.put("build_year", row.CREATE_YEAR);
            values.put("usage", row.CREATE_YEAR);
            mDb.insert(DbConst.EMPTY_HOUSE_TABLE_NAME, null, values);
        }
        else if(data instanceof Market.MarketRow)
        {
            Hospital.HospitalRow row = (Hospital.HospitalRow)data;
            ContentValues values = new ContentValues();
            values.put("gov_type", row.getGOV_TYPE());
            values.put("title", row.getMED_NM());
            values.put("address", row.getMED_ADDRESS());
            values.put("phonenumber", row.getTEL_NUM());
            values.put("homepage", row.getHOMEPAGE());
            mDb.insert(DbConst.MARKET_TABLE_NAME, null, values);
        }
        else if(data instanceof Hospital.HospitalRow)
        {
            Hospital.HospitalRow row = (Hospital.HospitalRow)data;
            ContentValues values = new ContentValues();
            values.put("gov_type", row.getGOV_TYPE());
            values.put("title", row.getMED_NM());
            values.put("address", row.getMED_ADDRESS());
            values.put("phonenumber", row.getTEL_NUM());
            values.put("homepage", row.getHOMEPAGE());
            mDb.insert(DbConst.HOSPITAL_TABLE_NAME, null, values);
        }
    }

    public Object getData(DbConst.DATA_TYPE type)
    {
        ArrayList<?> rowArrayList = new ArrayList<>();
        switch (type)
        {
            case EMPTY_HOUSE:
                Cursor c_empty = mDb.rawQuery(DbConst.QUERY_SELECT_EMPTY_HOUSE_ALL_DATA, null);
                EmptyHouse.EmptyHouseRow row_empty = new EmptyHouse.EmptyHouseRow();
                c_empty.moveToFirst();
                while(c_empty.moveToNext())
                {
                    row_empty.setADDRESS(c_empty.getString(1));
                    row_empty.setMST_NM(c_empty.getString(2));
                    row_empty.setCREATE_YEAR(c_empty.getString(3));
                    row_empty.setCREATE_USE(c_empty.getString(4));
                    ((ArrayList<EmptyHouse.EmptyHouseRow>)rowArrayList).add(row_empty);
                }
                c_empty.close();
                break;
            case HOSPITAL:
                Cursor c_hospital = mDb.rawQuery(DbConst.QUERY_SELECT_HOSPITAL_ALL_DATA, null);
                Market.MarketRow row_hospital = new Market.MarketRow();
                c_hospital.moveToFirst();
                while(c_hospital.moveToNext())
                {
                    row_hospital.setGOV_TYPE(c_hospital.getString(1));
                    row_hospital.setMED_NM(c_hospital.getString(2));
                    row_hospital.setMED_ADDRESS(c_hospital.getString(3));
                    row_hospital.setTEL_NUM(c_hospital.getString(4));
                    row_hospital.setHOMEPAGE(c_hospital.getString(5));
                    ((ArrayList<Market.MarketRow>)rowArrayList).add(row_hospital);
                }
                break;
            case MARKET:
                Cursor c_market = mDb.rawQuery(DbConst.QUERY_SELECT_MARKET_ALL_DATA, null);
                Market.MarketRow row_market = new Market.MarketRow();
                c_market.moveToFirst();
                while(c_market.moveToNext())
                {
                    row_market.setGOV_TYPE(c_market.getString(1));
                    row_market.setMED_NM(c_market.getString(2));
                    row_market.setMED_ADDRESS(c_market.getString(3));
                    row_market.setTEL_NUM(c_market.getString(4));
                    row_market.setHOMEPAGE(c_market.getString(5));
                    ((ArrayList<Market.MarketRow>)rowArrayList).add(row_market);
                }
                break;
            default:
                break;
        }
        return rowArrayList;
    }


    class DBOpenHelper extends SQLiteOpenHelper {

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
