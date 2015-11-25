package com.nankuru.gogangwon.house;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nankuru.gogangwon.R;

/**
 * Created by chitacan on 2015. 11. 25..
 */
public class HouseDetailViewHolder extends RecyclerView.ViewHolder {
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
    }
}
