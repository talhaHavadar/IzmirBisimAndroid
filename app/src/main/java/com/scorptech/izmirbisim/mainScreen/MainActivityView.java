package com.scorptech.izmirbisim.mainScreen;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by talha on 03.02.2016.
 */
public interface MainActivityView extends MvpView {

    void showSubtitleContainer(String fragmentTag);
    void hideSubTitleContainer();
    void showRefreshIndicator();
    void hideRefreshIndicator();

}
