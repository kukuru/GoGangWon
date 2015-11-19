package com.nankuru.gogangwon.emptyhouse;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nankuru.gogangwon.CommonValue;
import com.nankuru.gogangwon.emptyhouse.data.GangWonHouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nanjui on 2015. 11. 16..
 */
public class LoadEmptyHouse extends AsyncTask
{
    String start = "1";
    int end;
    int loadCap = 8;
    Context mCtx;

    public LoadEmptyHouse(Context context)
    {
        mCtx = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        String url = CommonValue.gangwonUrl+CommonValue.keyValue+CommonValue.valueType+CommonValue.emptyHouse+"/1/"+loadCap;

        end = loadCap;
        HttpURLConnection con = null;
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
                    int len = reader.read(buffer);
                    while(len != 0)
                    {
                        len = reader.read(buffer);
                    }

                    Gson gson = new GsonBuilder().create();
                    GangWonHouse houseInfo = gson.fromJson(reader, GangWonHouse.class);
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
