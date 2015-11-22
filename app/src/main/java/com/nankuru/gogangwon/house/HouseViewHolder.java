package com.nankuru.gogangwon.house;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nankuru.gogangwon.R;

/**
 * Created by chitacan on 2015. 11. 20..
 */
public class HouseViewHolder extends RecyclerView.ViewHolder{
    TextView addressTv;
    TextView ownerTv;
    TextView buildTv;
    TextView usageTv;

    public HouseViewHolder(View itemView) {
        super(itemView);
        addressTv = (TextView) itemView.findViewById(R.id.address);
        ownerTv = (TextView) itemView.findViewById(R.id.owner);
        buildTv = (TextView) itemView.findViewById(R.id.build_year);
        usageTv = (TextView) itemView.findViewById(R.id.usage);
        itemView.setTag(getPosition());
    }
}
