package com.scorptech.izmirbisim.mainScreen.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.base.BaseMvpFragment;
import com.scorptech.izmirbisim.howhireScreen.HowhireFragment;
import com.scorptech.izmirbisim.mainScreen.MainActivity;
import com.scorptech.izmirbisim.mapScreen.MapScreenFragment;
import com.scorptech.izmirbisim.membershipScreen.MembershipFragment;
import com.scorptech.izmirbisim.pricingScreen.PricingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseMvpFragment<MainView, MainFragmentPresenter> implements MainView {

    public static final String TAG = "MainActivityFragment";
    private static final String BISIM_PHONE = "02324335155";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @Override
    public MainFragmentPresenter createPresenter() {
        return new MainFragmentPresenter();
    }

    @OnClick(R.id.llPlaces)
    public void clickMembership(View v) {
        openMembershipScreen();
    }

    @OnClick(R.id.llStation)
    public void clickStation(View v) {
        openMapScreen();
    }

    @OnClick(R.id.llPricing)
    public void clickPricing(View v) {
        openPricingScreen();
    }

    @OnClick(R.id.llHiring)
    public void clickHowHire(View v) {
        openHowhireScreen();
    }

    @OnClick(R.id.llPhone)
    public void clickPhone(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + BISIM_PHONE));
        startActivity(intent);
    }

    @OnClick(R.id.llFeedback)
    public void clickFeedback(View v){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "scorptechdev@gmail.com" });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Izmir Bisim Uygulaması Hakkında");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Buraya uygulamayla ilgili istek ve şikayetinizi yazınız.");
        startActivity(Intent.createChooser(emailIntent, "Email Yolla"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().hideRefreshIndicator();
    }

    @Override
    public void openMapScreen() {
        if (getMainActivity() != null) {
            getMainActivity().openFragment(MapScreenFragment.TAG);
        }
    }

    @Override
    public void openMembershipScreen() {
        if (getMainActivity() != null) {
            getMainActivity().openFragment(MembershipFragment.TAG);
        }
    }

    @Override
    public void openPricingScreen() {
        if (getMainActivity() != null) {
            getMainActivity().openFragment(PricingFragment.TAG);
        }
    }

    @Override
    public void openHowhireScreen() {
        if (getMainActivity() != null) {
            getMainActivity().openFragment(HowhireFragment.TAG);
        }
    }
}
