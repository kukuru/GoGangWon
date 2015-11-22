package com.nankuru.gogangwon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.nankuru.gogangwon.house.data.EmptyHouse;
import com.nankuru.gogangwon.house.data.GangWonHouse;
import com.nankuru.gogangwon.house.data.GangwonHospital;
import com.nankuru.gogangwon.house.data.GangwonMarket;
import com.nankuru.gogangwon.house.data.GangwonWelfare;
import com.nankuru.gogangwon.house.data.Market;

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

        }
        else if(data instanceof GangwonWelfare.WelFareChild.WelFareChildRow)
        {
            GangwonWelfare.WelFareChild.WelFareChildRow row = (GangwonWelfare.WelFareChild.WelFareChildRow)data;
            ContentValues values = new ContentValues();
            values.put("address", row.getADDR());
            values.put("title", row.getTITLE());
            values.put("phonenumber", row.getTELNO());
            mDb.insert(DbConst.EMPTY_HOUSE_TABLE_NAME, null, values);
        }
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
