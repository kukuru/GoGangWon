package com.nankuru.gogangwon.emptyhouse;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.nankuru.gogangwon.CommonValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nanjui on 2015. 11. 16..
 */
public class LoadEmptyHouseInfo extends AsyncTask
{
    String start = "1";
    int end;
    int loadCap = 10;

    @Override
    protected Object doInBackground(Object[] params) {

        String url = CommonValue.gangwonUrl+CommonValue.keyValue+CommonValue.valueType+CommonValue.emptyHouse+"/1/"+loadCap;

        end = loadCap;
        HttpURLConnection con = null;
        try {
            URL urlUse = new URL(url);
            Log.d("NJ LEE", "url : "+url);
            con = (HttpURLConnection)urlUse.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status = con.getResponseCode();
            switch(status)
            {
                case HttpURLConnection.HTTP_OK:
                    InputStream input = con.getInputStream();
                    InputStreamReader reader = new InputStreamReader(input);

                    Log.d("NJ LEE", "status : "+status);
                    Gson gson = new Gson();
                    EmptyHouse houseInfo = gson.fromJson(reader, EmptyHouse.class);
                    Log.d("NJ LEE", houseInfo.resultData.getCODE());
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

       return null;
    }
}
