package com.nankuru.gogangwon.house;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nankuru.gogangwon.R;
import com.nankuru.gogangwon.house.data.HouseDetailData;

import java.util.ArrayList;

/**
 * Created by chitacan on 2015. 11. 25..
 */
public class HouseDetailListAdapter extends RecyclerView.Adapter<HouseDetailViewHolder> {

    ArrayList<HouseDetailData> mInfoArray = new ArrayList<HouseDetailData>();
    View.OnClickListener mInfoBtnListener;

    HouseDetailListAdapter(ArrayList<HouseDetailData> data, View.OnClickListener l)
    {
        mInfoArray.addAll(data);
        mInfoBtnListener = l;
    }

    @Override
    public HouseDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail, parent, false);
        if(mInfoBtnListener != null) {
            view.findViewById(R.id.info_btn).setOnClickListener(mInfoBtnListener);
        }
        return new HouseDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HouseDetailViewHolder holder, int position) {
        HouseDetailData data = mInfoArray.get(position);
        holder.nameTxt.setText(data.getName());
        holder.subInfoTxt.setText(data.getAddress());
        holder.distanceTxt.setText(data.getDistance());
    }

    public void setData(ArrayList<HouseDetailData> data)
    {
        if(mInfoArray != null)
        {
            mInfoArray.clear();
            mInfoArray.addAll(data);
        }
    }

    @Override
    public int getItemCount() {
        return mInfoArray.size();
    }
}
