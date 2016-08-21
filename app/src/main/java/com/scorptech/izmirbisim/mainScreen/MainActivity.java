package com.scorptech.izmirbisim.mainScreen;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nineoldandroids.animation.Animator;
import com.scorptech.izmirbisim.App;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.event.RefreshCompletedEvent;
import com.scorptech.izmirbisim.event.RefreshEvent;
import com.scorptech.izmirbisim.howhireScreen.HowhireFragment;
import com.scorptech.izmirbisim.mainScreen.fragment.MainActivityFragment;
import com.scorptech.izmirbisim.mapScreen.MapScreenFragment;
import com.scorptech.izmirbisim.membershipScreen.MembershipFragment;
import com.scorptech.izmirbisim.pricingScreen.PricingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends MvpActivity<MainActivityView,MainActivityPresenter> implements MainActivityView {

    public static final String TAG = "MainActivity";
    @Bind(R.id.subtitleContainer)
    RelativeLayout subtitleContainer;
    FragmentManager mFragmentManager;
    @Bind(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.ibRefresh)
    ImageButton ibRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mFragmentManager = getFragmentManager();
        ButterKnife.bind(this);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setRefreshing(false);
        App.getEventBus().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof RefreshCompletedEvent) {
                            hideRefreshIndicator();
                        }
                    }
                });

        if (savedInstanceState ==  null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment, new MainActivityFragment(), MainActivityFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this);
    }


    public void openFragment(String tag) {
        if (tag.equalsIgnoreCase(MapScreenFragment.TAG)) {
            hideRefreshIndicator();
            ibRefresh.setVisibility(View.VISIBLE);
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_left, R.animator.slide_in_down, R.animator.slide_out_left)
                    .replace(R.id.fragment, new MapScreenFragment(), MapScreenFragment.TAG)
                    .addToBackStack(null)
                    .commit();

        } else if (tag.equalsIgnoreCase(MembershipFragment.TAG)) {
            hideRefreshIndicator();
            ibRefresh.setVisibility(View.GONE);
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_left, R.animator.slide_in_down, R.animator.slide_out_left)
                    .replace(R.id.fragment, new MembershipFragment(), MembershipFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            getPresenter().setSubtitleContainerData(tag, subtitleContainer);
            showSubtitleContainer(tag);
        } else if (tag.equalsIgnoreCase(PricingFragment.TAG)) {
            hideRefreshIndicator();
            ibRefresh.setVisibility(View.GONE);
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_left, R.animator.slide_in_down, R.animator.slide_out_left)
                    .replace(R.id.fragment, new PricingFragment(), PricingFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            getPresenter().setSubtitleContainerData(tag, subtitleContainer);
            showSubtitleContainer(tag);
        } else if (tag.equalsIgnoreCase(HowhireFragment.TAG)) {
            hideRefreshIndicator();
            ibRefresh.setVisibility(View.GONE);
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_down, R.animator.slide_out_left, R.animator.slide_in_down, R.animator.slide_out_left)
                    .replace(R.id.fragment, new HowhireFragment(), HowhireFragment.TAG)
                    .addToBackStack(null)
                    .commit();
            getPresenter().setSubtitleContainerData(tag, subtitleContainer);
            showSubtitleContainer(tag);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.ibRefresh)
    public void clickRefreshAction(View v) {
        App.getEventBus().send(new RefreshEvent());
    }



    @Override
    public void showSubtitleContainer(String fragmentTag) {
        if (subtitleContainer.getVisibility() != View.VISIBLE) {
            getPresenter().setSubtitleContainerData(fragmentTag, subtitleContainer);
            YoYo.with(Techniques.Landing)
                    .duration(300)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            subtitleContainer.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .playOn(subtitleContainer);
        }
    }

    @OnClick(R.id.ibBack)
    public void clickSubBack(View v) {
        mFragmentManager.popBackStack();
        hideSubTitleContainer();
    }

    @Override
    public void hideSubTitleContainer() {
        if (subtitleContainer.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.SlideOutLeft)
                    .duration(300)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            subtitleContainer.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    })
                    .playOn(subtitleContainer);
        }
    }

    @Override
    public void showRefreshIndicator() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if(mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
            hideSubTitleContainer();
        } else {
            super.onBackPressed();
        }
    }
}
