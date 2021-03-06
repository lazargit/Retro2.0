package com.shamildev.retro.ui.signin.fragment.presenter;

/**
 * Created by Shamil Lazar on 13.12.2017.
 */

import com.shamildev.retro.ui.common.presenter.Presenter;

/**
 * A {@link Presenter} that does some work and shows the results.
 */
public interface SignInPresenter extends Presenter {
    void toast(Object obj);
    void snackbar(Object obj);

    void logout();
    void onError(Throwable t);
    void logoutSuccesfull();
    void signSuccesfull();




    void prepareFacebookLogin();
    void prepareTwitterLogin();

    void prepareEmailLogin(String email, String password);
}
