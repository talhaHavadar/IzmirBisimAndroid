package com.scorptech.izmirbisim.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.scorptech.izmirbisim.R;

/**
 * Created by talha on 03.02.2016.
 */
public class BisimStation {

    @SerializedName("name")
    public String name;
    @SerializedName("state")
    public String state;
    @SerializedName("emptyCount")
    public int emptyCount;
    @SerializedName("availableBcycle")
    public int availableBcycle;
    @SerializedName("lat")
    public double lat;
    @SerializedName("lon")
    public double lon;

    public String buildInfoText(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("" + state + "\n")
            .append("" + emptyCount + "\n")
            .append("" + availableBcycle + "\n");

        return sb.toString();
    }

}
