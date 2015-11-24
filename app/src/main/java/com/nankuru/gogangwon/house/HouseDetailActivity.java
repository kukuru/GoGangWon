package com.nankuru.gogangwon.house;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nankuru.gogangwon.R;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapdata.NmapSaxHandler;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by nanjui on 2015. 11. 20..
 */
public class HouseDetailActivity extends NMapActivity implements NMapPOIdataOverlay.OnStateChangeListener, NMapView.OnMapStateChangeListener{

    public class Coordinate
    {
        double longitute;
        double latitude;

        public double getLongitute() {
            return longitute;
        }

        public void setLongitute(double longitute) {
            this.longitute = longitute;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    public static String url = "http://openapi.map.naver.com/api/geocode.php?key=1b0a35e9142eeb6a6fb0459bf95f5fae&encoding=utf-8&coord=latlng&query=%s";
    private static String MAP_API_KEY = "1b0a35e9142eeb6a6fb0459bf95f5fae";

    private NMapView mMapView;
    private NMapController mMapController;
    private NMapOverlayManager mMapOverlayMgr;
    private NMapViewerResourceProvider mMapResourceProvider;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        Intent intent = getIntent();
        String address = intent.getStringExtra("SELECTED_ADDRESS");
        initView("강원도 " + address);
    }

    private void initView(String address)
    {
        mMapView = (NMapView) findViewById(R.id.naver_map);
        mMapView.setApiKey(MAP_API_KEY);

        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();


        mMapController = mMapView.getMapController();

        // use built in zoom controls
        NMapView.LayoutParams lp = new NMapView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);
        mMapView.setBuiltInZoomControls(true, lp);
        mMapView.setOnMapStateChangeListener(this);

        mMapResourceProvider = new NMapViewerResourceProvider(this);
        mMapOverlayMgr = new NMapOverlayManager(this, mMapView, mMapResourceProvider);

        try
        {
            Coordinate point = null;
            point = getCoordinationFromAddress(address);
            displayPin(point);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void displayPin(Coordinate point)
    {
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(1, mMapResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(point.getLongitute(), point.getLatitude(), "위치", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mMapOverlayMgr.createPOIdataOverlay(poiData, null);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(HouseDetailActivity.this);

        // select an item
        poiDataOverlay.selectPOIitem(0, true);

        // show all POI data
        poiDataOverlay.showAllPOIdata(0);
    }

    public Coordinate getCoordinationFromAddress(String address) throws IOException
    {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> fromLocationName = null;
        fromLocationName = geocoder.getFromLocationName(address,   1);
        if (fromLocationName != null && fromLocationName.size() > 0)
        {
            Address a = fromLocationName.get(0);
            Coordinate point = new Coordinate();
            point.setLatitude((a.getLatitude()));
            point.setLongitute(a.getLongitude());
            return point;
        }
        return null;
    }

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if(nMapError == null)
        {
            NGeoPoint point = new NGeoPoint(27.061, 37.51);
            mMapController.setMapCenter(point, 20);
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }
}
