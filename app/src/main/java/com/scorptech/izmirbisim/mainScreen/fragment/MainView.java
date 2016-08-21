package com.scorptech.izmirbisim.mainScreen.fragment;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.scorptech.izmirbisim.mainScreen.MainActivity;

/**
 * Created by talha on 02.02.2016.
 */
public interface MainView extends MvpView {

    MainActivity getMainActivity();
    void openMapScreen();
    void openMembershipScreen();
    void openPricingScreen();
    void openHowhireScreen();
}
