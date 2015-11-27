package com.nankuru.gogangwon.house;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Coordinate mHousePoint;
    private HouseDetailListAdapter.OnRecycleViewItemClickListener mClickListItemListener = new HouseDetailListAdapter.OnRecycleViewItemClickListener() {
        @Override
        public void onRecycleViewItemClickListener(View view) {
            String address = ((TextView) view.findViewById(R.id.sub_info)).getText().toString();
            Coordinate point = getCoordinationFromAddress(address);
            Log.d("NJ LEE", "onClick : " + point.getLongitude() + ", " + point.getLatitude());
            if (point != null) {
                NMapPOIdata poiData = new NMapPOIdata(2, mMapResourceProvider);
                poiData.beginPOIdata(2);
                poiData.addPOIitem(mHousePoint.getLongitude(), mHousePoint.getLatitude(), "출발지", NMapPOIflagType.FROM, 0);
                poiData.addPOIitem(point.getLongitude(), point.getLatitude(), "도착", NMapPOIflagType.TO, 0);
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
        }
    };

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

        mHousePoint = getCoordinationFromAddress(address);
        displayPin(mHousePoint);

        Button hospital = (Button) findViewById(R.id.hospital_btn);
        hospital.setOnClickListener(this);

        Button market = (Button) findViewById(R.id.market_btn);
        market.setOnClickListener(this);

        RecyclerView convenience_list = (RecyclerView) findViewById(R.id.convenience_list);
        convenience_list.setHasFixedSize(true);

        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        layoutMgr.setOrientation(LinearLayoutManager.VERTICAL);
        convenience_list.setLayoutManager(layoutMgr);

        mAdapter = new HouseDetailListAdapter(getData(DATA_TYPE.HOSPITAL), this);
        mAdapter.setOnRecycleViewitemClickListener(mClickListItemListener);
        convenience_list.setAdapter(mAdapter);
    }

    private ArrayList<HouseDetailData> getData(DATA_TYPE type)
    {
        ArrayList<HouseDetailData> dataArray = new ArrayList<>();
        switch (type)
        {
            case HOSPITAL:
                dataArray.addAll(mDbHelper.queryHouseDetailDataWithSpecificAWord(DbConst.HOSPITAL_TABLE_NAME, "강릉시"));
                break;
            case MARKET:
                dataArray.addAll(mDbHelper.queryHouseDetailDataWithSpecificAWord(DbConst.MARKET_TABLE_NAME, "강릉시"));
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

    public Coordinate getCoordinationFromAddress(String address)
    {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> fromLocationName = null;
        try {
            fromLocationName = geocoder.getFromLocationName(address,   1);
            if (fromLocationName != null && fromLocationName.size() > 0)
            {
                Address a = fromLocationName.get(0);
                Coordinate point = new Coordinate();
                point.setLatitude((a.getLatitude()));
                point.setLongitude(a.getLongitude());
                return point;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.d("NJ LEE", "id : " + id);
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
