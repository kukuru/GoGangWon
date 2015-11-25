package com.nankuru.gogangwon.house;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nankuru.gogangwon.CommonValue;
import com.nankuru.gogangwon.R;
import com.nankuru.gogangwon.db.DbConst;
import com.nankuru.gogangwon.db.DbHelper;
import com.nankuru.gogangwon.house.data.HouseDetailData;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by nanjui on 2015. 11. 20..
 */
public class HouseDetailActivity extends NMapActivity implements NMapPOIdataOverlay.OnStateChangeListener,
                                                                 NMapView.OnMapStateChangeListener,
                                                                 View.OnClickListener{

    public class Coordinate
    {
        double longitude;
        double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    private enum DATA_TYPE
    {
        HOSPITAL,
        MARKET
    }

    private NMapView mMapView;
    private NMapController mMapController;
    private NMapOverlayManager mMapOverlayMgr;
    private NMapViewerResourceProvider mMapResourceProvider;
    private DbHelper mDbHelper;
    private HouseDetailListAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);

        Intent intent = getIntent();
        String address = intent.getStringExtra("SELECTED_ADDRESS");
        mDbHelper = DbHelper.getInstance(this);
        initView("강원도 " + address);
    }

    private void initView(String address)
    {
        mMapView = (NMapView) findViewById(R.id.naver_map);
        mMapView.setApiKey(CommonValue.MAP_API_KEY);

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

        Button hospital = (Button) findViewById(R.id.hospital_btn);
        hospital.setOnClickListener(this);

        Button market = (Button) findViewById(R.id.market_btn);
        market.setOnClickListener(this);

        RecyclerView convenience_list = (RecyclerView) findViewById(R.id.convenience_list);
        mAdapter = new HouseDetailListAdapter(getData(DATA_TYPE.HOSPITAL), this);
        convenience_list.setAdapter(mAdapter);
    }

    private ArrayList<HouseDetailData> getData(DATA_TYPE type)
    {
        ArrayList<HouseDetailData> dataArray = new ArrayList<>();
        switch (type)
        {
            case HOSPITAL:
                String arg_hospital[] = {DbConst.HOSPITAL_TABLE_NAME, "강릉시"};
                dataArray.addAll(mDbHelper.queryHouseDetailData(arg_hospital));
                break;
            case MARKET:
                String arg_market[] = {DbConst.HOSPITAL_TABLE_NAME, "강릉시"};
                dataArray.addAll(mDbHelper.queryHouseDetailData(arg_market));
                break;
        }
        return dataArray;
    }

    private void displayPin(Coordinate point)
    {
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(1, mMapResourceProvider);
        poiData.beginPOIdata(1);
        poiData.addPOIitem(point.getLongitude(), point.getLatitude(), "위치", markerId, 0);
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
            point.setLongitude(a.getLongitude());
            return point;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.hospital_btn:
                mAdapter.setData(getData(DATA_TYPE.HOSPITAL));
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.market_btn:
                mAdapter.setData(getData(DATA_TYPE.MARKET));
                mAdapter.notifyDataSetChanged();
                break;
        }

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
