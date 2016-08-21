package com.scorptech.izmirbisim.mapManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.model.BisimStation;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by talha on 10.12.2015.
 */
public class PositionManager implements GoogleMap.OnMarkerClickListener {

    public static String TAG = "class:PositionManager";
    private Context context;
    private GoogleMap mMap;

    private final static int MY_LOCATION_ICON = -1;//R.mipmap.ic_me_location

    private final static int MARKER_ICON = R.mipmap.ic_marker;
    //private final static int SELECTED_MARKER_ICON = R.mipmap.marker_pressed;//R.mipmap.ic_selected_marker;

    private class BisimStationWithMarker {
        public BisimStation station;
        public Marker marker;

        public BisimStationWithMarker(BisimStation station, Marker marker) {
            this.station = station;
            this.marker = marker;
        }
    }

    private List<BisimStationWithMarker> bisimStations;

    public PositionManager(Context context, GoogleMap map) {
        this.context = context;
        mMap = map;
        bisimStations = new ArrayList<>();
        map.setOnMarkerClickListener(this);
    }

    public void setMap(GoogleMap map) {
        mMap = map;
    }


    public void goLocation(LatLng loc) {
        if (loc != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, MapManager.DEFAULT_ZOOM));
        }
    }

    public void goLocation(LatLng loc, float zoom) {
        if (loc != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
        }
    }


    public boolean goMyLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        mMap.setMyLocationEnabled(true);
        Location location = mMap.getMyLocation();
        if (location != null) {
            LatLng target = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition.Builder builder = new CameraPosition.Builder();
            builder.zoom(MapManager.DEFAULT_ZOOM);
            builder.target(target);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
            return true;
        } else {
            return false;
        }
    }


    public Marker addMarker(@NonNull LatLng ll, @Nullable String title, @Nullable String num) {
        MarkerOptions options = new MarkerOptions()
                .position(ll)
                        //.icon(BitmapDescriptorFactory.fromResource(MARKER_ICON))
                .draggable(false);
        Marker marker = mMap.addMarker(options);

        return marker;
    }

    public void setMarkers(List<BisimStation> stations) {
        for (int i = 0; i < stations.size(); i++) {
            BisimStation station = stations.get(i);
            LatLng ll = new LatLng(station.lat, station.lon);
            MarkerOptions options = new MarkerOptions()
                    .position(ll)
                    .title(station.name)
                    .snippet(station.buildInfoText(context))
                    .icon(BitmapDescriptorFactory.fromResource(MARKER_ICON))
                    .draggable(false);
            Marker marker = mMap.addMarker(options);
            BisimStationWithMarker bMarker = new BisimStationWithMarker(station, marker);
            bisimStations.add(bMarker);
        }

    }

    public Marker addMarker(@NonNull BisimStation station) {
        LatLng ll = new LatLng(station.lat, station.lon);
        MarkerOptions options = new MarkerOptions()
                .position(ll)
                .title(station.name)
                .snippet(station.buildInfoText(context))
                .icon(BitmapDescriptorFactory.fromResource(MARKER_ICON))
                .draggable(false);
        Marker marker = mMap.addMarker(options);
        BisimStationWithMarker bMarker = new BisimStationWithMarker(station, marker);
        bisimStations.add(bMarker);
        return marker;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
