package com.nankuru.gogangwon.house;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nankuru.gogangwon.R;
import com.nankuru.gogangwon.house.data.EmptyHouse;

import java.util.List;

/**
 * Created by chitacan on 2015. 11. 20..
 */
public class HouseListAdapter extends RecyclerView.Adapter<HouseViewHolder>{

    private List<EmptyHouse.EmptyHouseRow> mHouseInfo = null;
    private View.OnClickListener mClickListener = null;

    HouseListAdapter(View.OnClickListener l)
    {
        mClickListener = l;
    }

    @Override
    public HouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_house, parent, false);
        view.setOnClickListener(mClickListener);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HouseViewHolder holder, int position) {
        EmptyHouse.EmptyHouseRow row = mHouseInfo.get(position);
        holder.addressTv.setText(row.ADDRESS);
        holder.ownerTv.setText(row.MST_NM);
        holder.buildTv.setText(row.CREATE_YEAR);
        holder.usageTv.setText(row.CREATE_USE);
    }

    @Override
    public int getItemCount()
    {
        if(mHouseInfo == null)
        {
            return 0;
        }
        return mHouseInfo.size();
    }

    public void setData(List<EmptyHouse.EmptyHouseRow> info)
    {
        if(mHouseInfo != null)
        {
            mHouseInfo.clear();
            mHouseInfo.addAll(info);
        }
        else
        {
            mHouseInfo = info;
        }
    }
}
