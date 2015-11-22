package com.nankuru.gogangwon.house;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nankuru.gogangwon.CommonValue;
import com.nankuru.gogangwon.house.data.GangWonHouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nanjui on 2015. 11. 16..
 */
public class LoaderHouseInfo extends AsyncTaskLoader<GangWonHouse>
{
    private int start = 1;
    private int loadCap = 5;
    private int end = loadCap;

    private Context mCtx;
    private GangWonHouse houseInfo;
    private HttpURLConnection con = null;


    public LoaderHouseInfo(Context context)
    {
        super(context);
        mCtx = context;
    }

    @Override
    public GangWonHouse loadInBackground() {
        if(houseInfo != null)
            end = (start < houseInfo.getEmptyHouse().list_total_count)?end+loadCap:-1;

        if(end == -1)
            return houseInfo;

        String url = CommonValue.gangwonUrl+CommonValue.keyValue+CommonValue.valueType+CommonValue.emptyHouse+"/"+start+"/"+end;
        try {
            URL urlUse = new URL(url);
            con = (HttpURLConnection)urlUse.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status = con.getResponseCode();
            switch(status)
            {
                case HttpURLConnection.HTTP_OK:
                    InputStream input = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);

                    char buffer[] = new char[1024];
                    reader.read(buffer);
                    Gson gson = new GsonBuilder().create();
                    houseInfo = gson.fromJson(String.valueOf(buffer).trim(), GangWonHouse.class);

                    break;
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                    break;
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        start = end;
        return houseInfo;
    }

    @Override
    protected void onStartLoading()
    {
    }

    @Override
    protected void onStopLoading()
    {
        if(con != null) {
            con.disconnect();
        }
    }
}
