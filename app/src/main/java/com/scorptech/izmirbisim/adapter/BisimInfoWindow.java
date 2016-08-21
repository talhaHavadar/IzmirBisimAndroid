package com.scorptech.izmirbisim.adapter;

import android.content.Context;
import android.support.annotation.BinderThread;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.model.BisimStation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by talha on 03.02.2016.
 */
public class BisimInfoWindow implements GoogleMap.InfoWindowAdapter {

    LayoutInflater mInflater;

    public BisimInfoWindow(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = mInflater.inflate(R.layout.layout_bisim_info_window, null);
        String[] info = marker.getSnippet().split("\n");
        ViewHolder holder = new ViewHolder(view);
        holder.tvName.setText(marker.getTitle());
        holder.tvState.setText(info[0]);
        holder.tvEmptypark.setText(info[1]);
        holder.tvAvailableBcycle.setText(info[2]);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        /*
        View view = mInflater.inflate(R.layout.layout_bisim_info_window, null);
        String[] info = marker.getSnippet().split("\n");
        ViewHolder holder = new ViewHolder(view);
        holder.tvName.setText(marker.getTitle());
        holder.tvState.setText(info[0]);
        holder.tvEmptypark.setText(info[1]);
        holder.tvAvailableBcycle.setText(info[2]);

        return view;
        */
        return null;
    }

    class ViewHolder {
        @Bind(R.id.tvState)
        TextView tvState;
        @Bind(R.id.tvAvailableBcycle)
        TextView tvAvailableBcycle;
        @Bind(R.id.tvEmptypark)
        TextView tvEmptypark;
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
