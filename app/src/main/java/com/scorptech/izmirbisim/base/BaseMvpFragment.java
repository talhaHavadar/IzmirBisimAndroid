package com.scorptech.izmirbisim.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.delegate.BaseMvpDelegateCallback;
import com.hannesdorfmann.mosby.mvp.delegate.FragmentMvpDelegate;
import com.hannesdorfmann.mosby.mvp.delegate.FragmentMvpDelegateImpl;

/**
 * Created by talha on 18.12.2015.
 */
public abstract class BaseMvpFragment <V extends MvpView, P extends MvpPresenter<V>> extends Fragment implements BaseMvpDelegateCallback<V,P>, MvpView {

    protected FragmentMvpDelegate<V, P> mvpDelegate;

    /**
     * The presenter for this view. Will be instantiated with {@link #createPresenter()}
     */
    protected P presenter;

    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     * {@link #setRetainInstance(boolean)} is set to true. This method will be called from
     * {@link #onViewCreated(View, Bundle)}
     */
    public abstract P createPresenter();

    @NonNull protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this);
        }

        return mvpDelegate;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public boolean isRetainInstance() {
        return getRetainInstance();
    }

    @Override
    public boolean shouldInstanceBeRetained() {
        FragmentActivity activity = (FragmentActivity) getActivity();
        boolean changingConfig = activity != null && activity.isChangingConfigurations();
        return getRetainInstance() && changingConfig;
    }


    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }


    @Override public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

}
