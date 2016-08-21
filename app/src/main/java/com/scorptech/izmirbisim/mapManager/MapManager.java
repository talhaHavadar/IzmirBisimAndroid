package com.scorptech.izmirbisim.mapManager;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.adapter.BisimInfoWindow;

/**
 * Created by talha on 10.12.2015.
 */
public class MapManager implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    public static final int DEFAULT_ZOOM = 18;
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String ZOOM = "zoom";
    private static final String BEARING = "bearing";
    private static final String TILT = "tilt";
    private static final String MAPTYPE = "maptype";
    private static final String MAP_PREF_NAME = "mapStatePrefs";
    private Context mContext;
    private LocationManager mLocationManager;
    private SharedPreferences mapSharedPrefs;
    public PositionManager positionManager;

    private GoogleMap map;

    public MapManager(Context context, GoogleMap map) {
        mContext = context;
        mapSharedPrefs = context.getSharedPreferences(MAP_PREF_NAME, Context.MODE_PRIVATE);
        positionManager = new PositionManager(context, map);
        setMap(map);
    }


    public void setMap(GoogleMap map) {
        this.map = map;
        if (map != null) {
            map.getUiSettings().setMyLocationButtonEnabled(true);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {}
            map.setMyLocationEnabled(true);
            map.setOnMapLongClickListener(this);
            map.setOnMapClickListener(this);
            map.setInfoWindowAdapter(new BisimInfoWindow(mContext));
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            positionManager.goLocation(new LatLng(38.4185345,27.1306874), 12);
        }
    }

    public float getZoomLevel() {
        if (map != null) {
            return map.getCameraPosition().zoom;
        }
        return DEFAULT_ZOOM;
    }

    public Location getCurrentLocation() {
        if (map != null) {
            Criteria cri = new Criteria();
            String bbb = mLocationManager.getBestProvider(cri, true);
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            Location myLocation = mLocationManager.getLastKnownLocation(bbb);
            return myLocation;
        }
        return null;
    }

    @SuppressWarnings("unused")
    public void saveMapState(GoogleMap map) {
        if (map != null) {
            SharedPreferences.Editor editor = mapSharedPrefs.edit();
            CameraPosition position = map.getCameraPosition();
            editor.putFloat(LATITUDE, (float) position.target.latitude);
            editor.putFloat(LONGITUDE, (float) position.target.longitude);
            editor.putFloat(ZOOM, position.zoom);
            editor.putFloat(TILT, position.tilt);
            editor.putFloat(BEARING, position.bearing);
            editor.putInt(MAPTYPE, map.getMapType());
            editor.commit();
        }
    }

    public void saveMapState() {
        if (map != null) {
            SharedPreferences.Editor editor = mapSharedPrefs.edit();
            CameraPosition position = map.getCameraPosition();
            editor.putFloat(LATITUDE, (float) position.target.latitude);
            editor.putFloat(LONGITUDE, (float) position.target.longitude);
            editor.putFloat(ZOOM, position.zoom);
            editor.putFloat(TILT, position.tilt);
            editor.putFloat(BEARING, position.bearing);
            editor.putInt(MAPTYPE, map.getMapType());
            editor.commit();
        }
    }

    public PositionManager getPositionManager() {
        return positionManager;
    }

    public CameraPosition getSavedCameraPosition() {
        double lat = mapSharedPrefs.getFloat(LATITUDE, 0);
        if (lat == 0) {
            return null;
        }
        double lng = mapSharedPrefs.getFloat(LONGITUDE, 0);
        LatLng ll = new LatLng(lat, lng);
        float zoom = mapSharedPrefs.getFloat(ZOOM, 0);
        float tilt = mapSharedPrefs.getFloat(TILT, 0);
        float bearing = mapSharedPrefs.getFloat(BEARING, 0);
        return new CameraPosition(ll, zoom, tilt, bearing);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    public GoogleMap getMap() {
        return map;
    }

}
