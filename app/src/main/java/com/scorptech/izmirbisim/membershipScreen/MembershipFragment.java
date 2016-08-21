package com.scorptech.izmirbisim.membershipScreen;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scorptech.izmirbisim.R;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;


public class MembershipFragment extends Fragment {

    public static final String TAG = "MembershipFragment";
    @Bind(R.id.content)
    TextView tvContent;
    @BindString(R.string.membership_places_static_text)
    String static_text;

    public MembershipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_membership, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        tvContent.setText(Html.fromHtml(static_text));

    }
}
