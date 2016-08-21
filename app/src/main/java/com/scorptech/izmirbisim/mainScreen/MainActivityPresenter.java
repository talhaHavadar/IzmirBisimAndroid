package com.scorptech.izmirbisim.mainScreen;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.scorptech.izmirbisim.R;
import com.scorptech.izmirbisim.howhireScreen.HowhireFragment;
import com.scorptech.izmirbisim.mainScreen.fragment.MainFragmentPresenter;
import com.scorptech.izmirbisim.mapScreen.MapScreenFragment;
import com.scorptech.izmirbisim.membershipScreen.MembershipFragment;
import com.scorptech.izmirbisim.pricingScreen.PricingFragment;

/**
 * Created by talha on 03.02.2016.
 */
public class MainActivityPresenter extends MvpBasePresenter<MainActivityView> {

    Context mContext;

    public MainActivityPresenter(Context context) {
        mContext = context;
    }


    public void setSubtitleContainerData(String fragmentTAG, RelativeLayout subtitleContainer) {
        TextView subtitle = (TextView) subtitleContainer.findViewById(R.id.subtitletext);
        if (fragmentTAG.equalsIgnoreCase(MapScreenFragment.TAG)) {
            subtitle.setText(R.string.stations_subtitle);
        } else if (fragmentTAG.equalsIgnoreCase(MembershipFragment.TAG)) {
            subtitle.setText(R.string.membership_places_text);
        } else if (fragmentTAG.equalsIgnoreCase(PricingFragment.TAG)) {
            subtitle.setText(R.string.pricing_text);
        } else if (fragmentTAG.equalsIgnoreCase(HowhireFragment.TAG)) {
            subtitle.setText(R.string.how_hire_text);
        }
    }

}
