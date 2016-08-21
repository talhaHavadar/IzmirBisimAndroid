package com.scorptech.izmirbisim.mapScreen;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.scorptech.izmirbisim.App;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.api.BisimService;
import com.scorptech.izmirbisim.event.RefreshCompletedEvent;
import com.scorptech.izmirbisim.event.RefreshEvent;
import com.scorptech.izmirbisim.mapManager.MapManager;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by talha on 03.02.2016.
 */
public class MapScreenPresenter extends MvpBasePresenter<MapScreenView> {

    private static final String TAG = "MapScreenPresenter";
    BisimService service;
    MapManager mapManager;
    Context mContext;

    public MapScreenPresenter(Context context) {
        mContext = context;
        service = App.getRetrofit().create(BisimService.class);
        App.getEventBus().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof RefreshEvent) {
                            refreshMap();
                        }
                    }
                });
    }

    public void setMap(GoogleMap map) {
        mapManager = new MapManager(mContext, map);
        loadStations();
    }

    public void clearMap() {
        if (mapManager != null) {
            mapManager.getMap().clear();
        }
    }

    public void refreshMap() {
        clearMap();
        loadStations();
    }



    public void loadStations() {
        if (isViewAttached())
            getView().getMainActivity().showRefreshIndicator();
        service.getBisimStations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BisimService.BisimResponse>() {
                    @Override
                    public void onCompleted() {
                        App.getEventBus().send(new RefreshCompletedEvent());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().showMessage(mContext.getString(R.string.unexpected_error_message), true);
                        App.getEventBus().send(new RefreshCompletedEvent());
                    }

                    @Override
                    public void onNext(BisimService.BisimResponse bisimResponse) {
                        if (bisimResponse.success) {
                            if (mapManager != null) {
                                mapManager.getPositionManager().setMarkers(bisimResponse.bisimPlaces);
                            }
                        } else {
                            if (isViewAttached())
                                getView().showMessage(mContext.getString(R.string.unexpected_error_message), true);
                        }
                        App.getEventBus().send(new RefreshCompletedEvent());
                    }
                });

    }


}
