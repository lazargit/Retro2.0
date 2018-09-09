package com.shamildev.retro.ui.splash.fragment.view;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shamildev.retro.R;
import com.shamildev.retro.domain.models.ResultWrapper;
import com.shamildev.retro.navigation.Navigator;
import com.shamildev.retro.retroimage.views.RetroImageView;
import com.shamildev.retro.ui.common.view.BaseViewFragment;
import com.shamildev.retro.ui.splash.fragment.presenter.SplashPresenter;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shamil Lazar.

 * A fragment implementation of {@link SplashView}.
 */
public final class SplashFragment extends BaseViewFragment<SplashPresenter> implements SplashView {


    @Inject
    Navigator navigator;

    @Inject
    Application application;



    @BindView(R.id.splashbg)
    RetroImageView splashbg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    @Override
    public RetroImageView getSplashBg() {
        return splashbg;
    }

    @Override
    public void navigateToHome(HashMap<String, ResultWrapper> map) {
       //navigator.navigateToHome(application,map);
      // navigator.navigateToAccount(application);

    }


    @OnClick(R.id.button)
    public void onClickSignIn(Button button) {
        navigator.navigateToSignIn(application);
    }

    @OnClick(R.id.button2)
    public void onClickAccount(Button button) {
        navigator.navigateToAccount(application);
    }




    @Override
    public void makeToast(String message) {
        showToastMessage(message);
    }
}