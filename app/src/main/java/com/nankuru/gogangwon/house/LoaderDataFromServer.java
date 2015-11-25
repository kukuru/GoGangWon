package com.nankuru.gogangwon.house;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nankuru.gogangwon.CommonValue;
import com.nankuru.gogangwon.db.DbConst;
import com.nankuru.gogangwon.db.DbHelper;
import com.nankuru.gogangwon.house.data.GangWonHouse;
import com.nankuru.gogangwon.house.data.GangwonHospital;
import com.nankuru.gogangwon.house.data.GangwonMarket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            loadEmptyHouseInfo();
            loadHospital();
            loadMarketInfo();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(CommonValue.DOES_LOAD_DATA, true);
        }
        return null;
    }

    private void loadEmptyHouseInfo() {
        GangWonHouse houseinfo = null;
        HttpURLConnection con = null;
        int start = 1;
        int end = LOADCAP;
        Gson gson = new GsonBuilder().create();
        do {
            try {
                String url = mBaseUrl + CommonValue.emptyHouse+"/"+start+"/"+end;
                URL urlUse = new URL(url);
                con = (HttpURLConnection) urlUse.openConnection();
                con.setRequestMethod("GET");
                con.connect();

                int status = con.getResponseCode();
                Log.d("NJ LEE", "status : "+status);
                switch (status) {
                    case HttpURLConnection.HTTP_OK:
                        InputStream input = con.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);

                        char buffer[] = new char[1024];
                        reader.read(buffer);
                        start += LOADCAP;
                        end = start + LOADCAP;
                        Log.d("NJ LEE", "buffer : "+String.valueOf(buffer));
                        houseinfo = gson.fromJson(String.valueOf(buffer).trim(), GangWonHouse.class);
                        for(int i = 0;i<houseinfo.getEmptyHouse().row.size(); i++)
                        {
                            Log.d("NJ LEE", "address : " + houseinfo.getEmptyHouse().getRow().get(i).getAddress());
                            DbHelper.getInstance(mCtx).insertData(houseinfo.getEmptyHouse().getRow().get(i));
                        }
                        //save data;
                        break;
                    default:
                        //Error handleing
                        break;
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
        }while (houseinfo !=null && houseinfo.getEmptyHouse().list_total_count > end);

        if(con != null)
            con.disconnect();
    }

    private void loadMarketInfo() {
        String url = mBaseUrl + CommonValue.market;
        GangwonMarket marketInfo = null;
        HttpURLConnection con = null;
        int start = 1;
        int end = LOADCAP;
        do {
            try {
                URL urlUse = new URL(url);
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
                        Gson gson = new GsonBuilder().create();
                        start += LOADCAP;
                        end = start + LOADCAP;
                        marketInfo = gson.fromJson(String.valueOf(buffer).trim(), GangwonMarket.class);
                        //save data;
                        break;
                    default:
                        //Error handleing
                        break;
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
        }while (marketInfo !=null && marketInfo.getMarket().list_total_count > end);

        if(con != null)
            con.disconnect();
    }

    private void loadHospital() {
        String url = mBaseUrl + CommonValue.market;
        GangwonHospital hospitalInfo = null;
        HttpURLConnection con = null;
        int start = 1;
        int end = LOADCAP;
        do {
            try {
                URL urlUse = new URL(url);
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
                        Gson gson = new GsonBuilder().create();
                        start += LOADCAP;
                        end = start + LOADCAP;
                        hospitalInfo = gson.fromJson(String.valueOf(buffer).trim(), GangwonHospital.class);
                        //save data;
                        break;
                    default:
                        //Error handleing
                        break;
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
        }while (hospitalInfo !=null && hospitalInfo.getHospital().list_total_count > end);

        if(con != null)
            con.disconnect();
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
