package com.shamildev.retro.ui.watchlist.fragment.presenter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shamildev.retro.data.net.error.TMDBError;
import com.shamildev.retro.di.scope.PerFragment;
import com.shamildev.retro.domain.core.AppConfig;
import com.shamildev.retro.domain.core.DataConfig;
import com.shamildev.retro.retroimage.core.RetroImage;
import com.shamildev.retro.ui.common.presenter.BasePresenter;
import com.shamildev.retro.ui.register.fragment.model.RegisterModel;
import com.shamildev.retro.ui.register.fragment.view.RegisterView;
import com.shamildev.retro.ui.splash.fragment.presenter.SplashPresenter;
import com.shamildev.retro.ui.watchlist.fragment.model.WatchlistModel;
import com.shamildev.retro.ui.watchlist.fragment.view.WatchlistView;

import javax.inject.Inject;


/**
 * Created by Shamil Lazar
 */

/**
 * An implementation of {@link SplashPresenter}.
 */
@PerFragment
public final class WatchlistPresenterImpl extends BasePresenter<WatchlistView, WatchlistModel> implements WatchlistPresenter {



    private DataConfig dataConfig;
    private AppConfig appConfig;


    @Inject
    RetroImage retroImage;

    @Inject
    WatchlistPresenterImpl(
            AppConfig appConfig,
            WatchlistView view,
            WatchlistModel model,
            DataConfig dataConfig) {
        super(view, model);
        //       this.networkManager = networkManager;
        //      this.networkManager.add(toString(), this::refreshData);
//            this.bootstrap = bootstrap;
//            this.bootstrap.setUp(this);
        this.dataConfig = dataConfig;
        this.appConfig = appConfig;


    }


    @Override
    public void onStart(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void onError(Throwable t) {

        if (t.getCause() instanceof TMDBError) {
            TMDBError error = (TMDBError) t.getCause();
            Log.d("onError", "<<<<< " + error.getResponseCode() + " : " + error.getMessage() + " : " + error.getStatusCode() + " : " + error.getSuccess());

        } else {

        }
        Log.d("onError", t.getMessage());
    }


    @Override
    public void toast(Object obj) {
        view.makeToast((String) obj);
    }


}