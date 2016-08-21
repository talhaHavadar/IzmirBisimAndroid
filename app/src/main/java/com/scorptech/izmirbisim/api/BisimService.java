package com.scorptech.izmirbisim.api;

import com.google.gson.annotations.SerializedName;
import com.scorptech.izmirbisim.model.BisimStation;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by talha on 03.02.2016.
 */
public interface BisimService {


    public class BisimResponse {
        @SerializedName("success")
        public boolean success;
        @SerializedName("bisimPlaces")
        public List<BisimStation> bisimPlaces;
        @SerializedName("count")
        public int count;
    }

    @GET("/bisim/api/")
    Observable<BisimResponse> getBisimStations();

}
