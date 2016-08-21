package com.scorptech.izmirbisim.mapScreen;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.base.BaseMvpFragment;
import com.scorptech.izmirbisim.mainScreen.MainActivity;

public class MapScreenFragment extends BaseMvpFragment<MapScreenView,MapScreenPresenter> implements MapScreenView, OnMapReadyCallback {


    private static final String[] PERMISSIONS = {
        Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int PERMISSIONS_REQUEST = 10081996;

    public static final String TAG = "MapScreenFragment";

    GoogleMap mMap;
    private boolean isLocationPermissionGranted;
    public MapScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!checkAccessPermission() && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(PERMISSIONS,PERMISSIONS_REQUEST);
        } else {
            isLocationPermissionGranted = true;
        }
        return inflater.inflate(R.layout.fragment_map_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            doMapThings();
        }
        getMainActivity().showSubtitleContainer(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap == null) {
            doMapThings();
        }
    }

    @Override
    public MapScreenPresenter createPresenter() {
        return new MapScreenPresenter(getActivity());
    }

    private void doMapThings() {
        if (isLocationPermissionGranted) {
            MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
            if (status != ConnectionResult.SUCCESS) {
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), status, 10);
                dialog.show();
            } else {
                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null) {
            mMap = googleMap;
            getPresenter().setMap(mMap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (checkAccessPermission()) {
                    doMapThings();
                } else {
                    showMessage("Haritayı görebilmek için konum erişimine izin vermelisiniz.", true);
                }
                break;
        }

    }

    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void showMessage(String message, boolean error) {
        if (getView() != null)
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                    .show();
    }

    private boolean checkAccessPermission() {
        return hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT < 23) return true;
        if (PackageManager.PERMISSION_GRANTED == getActivity().checkSelfPermission(perm))
            return true;
        return false;
    }

}
