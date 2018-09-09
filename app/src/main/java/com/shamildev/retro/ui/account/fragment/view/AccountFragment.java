package com.shamildev.retro.ui.account.fragment.view;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.shamildev.retro.R;
import com.shamildev.retro.navigation.Navigator;
import com.shamildev.retro.ui.account.fragment.presenter.AccountPresenter;
import com.shamildev.retro.ui.common.view.BaseViewFragmentV4;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Shamil Lazar.
 * <p>
 * A fragment implementation of {@link AccountView}.
 */
public final class AccountFragment extends BaseViewFragmentV4<AccountPresenter> implements AccountView {


    @Inject
    Navigator navigator;

    @Inject
    Application application;

    @BindView(R.id.textView_userEmail)
    TextView mTextView_userEmail;

    @BindView(R.id.button_logout)
    Button mButton_logout;

    @BindView(R.id.button_signin)
    Button mButton_signin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @OnClick(R.id.button_logout)
    public void onClickLogout(Button button) {
        presenter.logout();
    }

    @Override
    public void onResume() {
        super.onResume();


//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        Log.e("FACEBOOK#", "onSuccess " + loginResult.getAccessToken().getToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.e("FACEBOOK#", "onCancel ");
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Log.e("FACEBOOK#", "onError"+exception.getMessage());
//                    }
//                });

    }
    //
//    @Override
//    public ImageSliderView getImageSliderView() {
//        return img_slider;
//    }
//    @Override
//    public ImageSliderView getImageSliderView2() {
//        return img_slider2;
//    }
//
//    @Override
//    public RetroImageView getCustomImageView() {
//        return customImageView;
//    }


    @Override
    public void makeToast(String message) {
        showToastMessage(message);
    }

    @Override
    public TextView getTextView_UserEmail() {
        return mTextView_userEmail;
    }

    @Override
    public Button getButton_logout() {
        return mButton_logout;
    }
    @Override
    public Button getButton_signin() {
        return mButton_signin;
    }
}