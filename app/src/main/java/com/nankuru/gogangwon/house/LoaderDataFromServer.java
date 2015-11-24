package com.nankuru.gogangwon.house;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nankuru.gogangwon.CommonValue;
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
    private final int LOADCAP = 5;
    private String mBaseUrl = CommonValue.gangwonUrl+CommonValue.keyValue+CommonValue.valueType;

    public LoaderDataFromServer(Context context)
    {
        super(context);
    }

    @Override
    public GangWonHouse loadInBackground() {

        //check already have db data
        loadEmptyHouseInfo();
        loadHospital();
        loadMarketInfo();
        return null;
    }

    private void loadEmptyHouseInfo() {
        String url = mBaseUrl + CommonValue.emptyHouse;
        GangWonHouse houseinfo = null;
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
                        houseinfo = gson.fromJson(String.valueOf(buffer).trim(), GangWonHouse.class);
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
        //end drawing loading circle
    }
}
