package com.nankuru.gogangwon.house;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.nankuru.gogangwon.R;
import com.nhn.android.maps.NMapView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nanjui on 2015. 11. 20..
 */
public class HouseDetailActivity extends FragmentActivity{

    public class Coordinate
    {
        int longitute;
        int latitude;
    }

    public static String url = "http://openapi.map.naver.com/api/geocode.php?key=1b0a35e9142eeb6a6fb0459bf95f5fae&encoding=utf-8&coord=latlng&query=%s";
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        NMapView mapView  = (NMapView) findViewById(R.id.mapView);
        String address = intent.getStringExtra("SELECTED_ADDRESS");

        Log.d("NJ LEE", "address : "+address);
//        mapView.setApiKey("1b0a35e9142eeb6a6fb0459bf95f5fae");
        String addr = String.format(url, "강원도 강릉시 교동 456");

        LoadMapInformation loader = new LoadMapInformation();
        try {
            loader.execute(new URL(addr));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Coordinate getCoordinate(String address)
    {
        return null;
    }

    class LoadMapInformation extends AsyncTask<URL, Integer, Long>
    {

        @Override
        protected Long doInBackground(URL... params)
        {
            URL urlUse = params[0];
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection)urlUse.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int status = con.getResponseCode();
                Log.d("NJ LEE", "status : " + status);
                switch(status)
                {
                    case HttpURLConnection.HTTP_OK:
                        InputStream input = con.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);

                        char buffer[] = new char[1024];
                        reader.read(buffer);
                        Log.d("NJ LEE", "buffer : " + String.valueOf(buffer).trim());
                        break;
                    case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                        break;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result)
        {

        }

    }
}
