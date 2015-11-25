package com.nankuru.gogangwon.house;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nankuru.gogangwon.R;
import com.nankuru.gogangwon.db.DbConst;
import com.nankuru.gogangwon.db.DbHelper;
import com.nankuru.gogangwon.house.data.EmptyHouse;
import com.nankuru.gogangwon.house.data.GangWonHouse;

import java.util.ArrayList;

/**
 * Created by chitacan on 2015. 11. 20..
 */
public class HouseMainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<GangWonHouse>,
        View.OnClickListener
{

    private static int LoaderID = 6767890;
    private RecyclerView mHouseList;
    private HouseListAdapter mAdapter;
    private DbHelper mDbHelper;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty_house);

        getSupportLoaderManager().initLoader(LoaderID, null, this).forceLoad();

        mDbHelper = DbHelper.getInstance(this);

        mHouseList = (RecyclerView) findViewById(R.id.house_list);
        mHouseList.setHasFixedSize(true);
        LinearLayoutManager layoutMgr = new LinearLayoutManager(this);
        layoutMgr.setOrientation(LinearLayoutManager.VERTICAL);
        mHouseList.setLayoutManager(layoutMgr);

        mAdapter = new HouseListAdapter(this);
        mHouseList.setAdapter(mAdapter);
    }

    @Override
    public Loader<GangWonHouse> onCreateLoader(int id, Bundle args)
    {
        return new LoaderDataFromServer(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<GangWonHouse> loader, GangWonHouse data) {

        ArrayList<EmptyHouse.EmptyHouseRow> houseInfo = (ArrayList<EmptyHouse.EmptyHouseRow>) mDbHelper.getDataAllByType(DbConst.DATA_TYPE.EMPTY_HOUSE);
        mAdapter.setData(houseInfo);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<GangWonHouse> loader) {

    }

    @Override
    public void onClick(View v) {
        TextView addr_tv = (TextView) v.findViewById(R.id.address);
        String address = addr_tv.getText().toString();

        Intent i = new Intent(HouseMainActivity.this, HouseDetailActivity.class);
        i.putExtra("SELECTED_ADDRESS", address);
        startActivity(i);
    }
}
