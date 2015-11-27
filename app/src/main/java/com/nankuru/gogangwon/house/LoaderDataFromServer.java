package com.nankuru.gogangwon.house;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.content.AsyncTaskLoader;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nankuru.gogangwon.CommonValue;
import com.nankuru.gogangwon.db.DbConst;
import com.nankuru.gogangwon.db.DbHelper;
import com.nankuru.gogangwon.house.data.EmptyHouse;
import com.nankuru.gogangwon.house.data.GangWonHouse;
import com.nankuru.gogangwon.house.data.GangwonHospital;
import com.nankuru.gogangwon.house.data.GangwonMarket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by nanjui on 2015. 11. 16..
 */
public class LoaderDataFromServer extends AsyncTaskLoader<GangWonHouse>
{
    private final int LOADCAP = 4;
    private String mBaseUrl = CommonValue.gangwonUrl+CommonValue.keyValue+CommonValue.valueType;

    private Context mCtx;
    public LoaderDataFromServer(Context context)
    {
        super(context);
        mCtx = context;
    }

    @Override
    public GangWonHouse loadInBackground() {

        SharedPreferences preferences = mCtx.getSharedPreferences(CommonValue.PREF_NAME, Context.MODE_PRIVATE);
        boolean loaded = preferences.getBoolean(CommonValue.DOES_LOAD_DATA, false);
        if(loaded == false) {
            loadData(mBaseUrl + CommonValue.emptyHouse, DbConst.DATA_TYPE.EMPTY_HOUSE);
            Log.d("NJ LEE", "Done to load house info");
            loadData(mBaseUrl + CommonValue.market, DbConst.DATA_TYPE.MARKET);
            Log.d("NJ LEE", "Done to load market info");
            loadData(mBaseUrl + CommonValue.hospital, DbConst.DATA_TYPE.HOSPITAL);
            Log.d("NJ LEE", "Done to load hospital info");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(CommonValue.DOES_LOAD_DATA, true);
            editor.commit();
        }
        return null;
    }

    private void saveData(DbConst.DATA_TYPE type)
    {
        File file = new File(getContext().getFilesDir() + File.separator + "data.json");
        Gson gson = new GsonBuilder().create();
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            switch (type) {
                case EMPTY_HOUSE:
                    GangWonHouse houseInfo = gson.fromJson(input, GangWonHouse.class);
                    for(int i = 0;i<houseInfo.getEmptyHouse().row.size(); i++)
                    {
                        DbHelper.getInstance(mCtx).insertData(houseInfo.getEmptyHouse().row.get(i));
                    }
                    break;
                case HOSPITAL:
                    GangwonHospital hospitalInfo = gson.fromJson(input, GangwonHospital.class);
                    for(int i = 0;i<hospitalInfo.getHospital().row.size(); i++)
                    {
                        DbHelper.getInstance(mCtx).insertData(hospitalInfo.getHospital().row.get(i));
                    }
                    break;
                case MARKET:
                    GangwonMarket marketInfo = gson.fromJson(input, GangwonMarket.class);
                    for(int i = 0;i<marketInfo.getMarket().row.size(); i++)
                    {
                        DbHelper.getInstance(mCtx).insertData(marketInfo.getMarket().row.get(i));
                    }
                    break;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        file.delete();
    }

    private int getDataCount(String url, DbConst.DATA_TYPE type) {
        int listCnt = 0;
        HttpURLConnection con = null;
        String urlCon = url + "/1/1";
        Gson gson = new GsonBuilder().create();
        try {
            URL urlUse = new URL(urlCon);
            con = (HttpURLConnection) urlUse.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status = con.getResponseCode();
            switch (status) {
                case HttpURLConnection.HTTP_OK:
                    InputStream input = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);

                    char buffer[] = new char[1024];
                    reader.read(buffer);
                    switch (type) {
                        case EMPTY_HOUSE:
                            GangWonHouse houseInfo = gson.fromJson(String.valueOf(buffer).trim(), GangWonHouse.class);
                            listCnt = houseInfo.getEmptyHouse().list_total_count;
                            break;
                        case HOSPITAL:
                            GangwonHospital hospitalInfo = gson.fromJson(String.valueOf(buffer).trim(), GangwonHospital.class);
                            listCnt = hospitalInfo.getHospital().list_total_count;
                            break;
                        case MARKET:
                            GangwonMarket marketInfo = gson.fromJson(String.valueOf(buffer).trim(), GangwonMarket.class);
                            listCnt = marketInfo.getMarket().list_total_count;
                            break;
                    }
            }
            if(con != null) {
                con.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCnt;
    }

    private void loadData(String baseUrl, DbConst.DATA_TYPE type)
    {
        HttpURLConnection con = null;
        int listCount = listCount = getDataCount(baseUrl, type);;

        try {
            String url = baseUrl+"/"+1+"/"+listCount;
            URL urlUse = new URL(url);
            con = (HttpURLConnection) urlUse.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status = con.getResponseCode();
            switch (status) {
                case HttpURLConnection.HTTP_OK:
                    InputStream input = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);
                    FileOutputStream writer = new FileOutputStream(new File(getContext().getFilesDir() + File.separator + "data.json"));
                    int readlen = 0;
                    do {
                        char buffer[] = new char[1024];
                        readlen = reader.read(buffer);
                        writer.write(String.valueOf(buffer).trim().getBytes());
                    } while(readlen >= 0);
                    break;
                default:
                    break;
            }
            if(con != null) {
                con.disconnect();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        saveData(type);
    }

    @Override
    protected void onStartLoading()
    {
        //draw loading circle
    }

    @Override
    protected void onStopLoading()
    {
    }
}
