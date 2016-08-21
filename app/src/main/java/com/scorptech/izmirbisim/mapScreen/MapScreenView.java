package com.scorptech.izmirbisim.mapScreen;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.scorptech.izmirbisim.mainScreen.MainActivity;

/**
 * Created by talha on 03.02.2016.
 */
public interface MapScreenView extends MvpView {

    MainActivity getMainActivity();
    void showMessage(String message, boolean error);
}
