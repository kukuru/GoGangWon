package com.nankuru.gogangwon.house;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nankuru.gogangwon.R;
import com.nankuru.gogangwon.house.data.HouseDetailData;

import java.util.ArrayList;

/**
 * Created by chitacan on 2015. 11. 25..
 */
public class HouseDetailListAdapter extends RecyclerView.Adapter<HouseDetailListAdapter.HouseDetailViewHolder>{

    ArrayList<HouseDetailData> mInfoArray = new ArrayList<HouseDetailData>();
    View.OnClickListener mInfoBtnListener;
    OnRecycleViewItemClickListener onItemClickListener;

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

    public class HouseDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTxt;
        public TextView subInfoTxt;
        public TextView distanceTxt;
        public ImageButton infoBtn;

        public HouseDetailViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.name);
            subInfoTxt = (TextView) itemView.findViewById(R.id.sub_info);
            distanceTxt = (TextView) itemView.findViewById(R.id.distance);
            infoBtn = (ImageButton) itemView.findViewById(R.id.info_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onRecycleViewItemClickListener(v);
        }
    }

    public void setOnRecycleViewitemClickListener(OnRecycleViewItemClickListener l)
    {
        onItemClickListener = l;
    }

    public interface OnRecycleViewItemClickListener
    {
        void onRecycleViewItemClickListener(View view);
    }


}
