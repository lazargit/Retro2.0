package com.shamildev.retro.ui.watchlist;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.shamildev.retro.di.scope.PerActivity;
import com.shamildev.retro.di.scope.PerFragment;
import com.shamildev.retro.ui.common.BaseActivityModule;
import com.shamildev.retro.ui.splash.fragment.view.SplashFragment;
import com.shamildev.retro.ui.watchlist.fragment.modul.WatchlistFragmentModule;
import com.shamildev.retro.ui.watchlist.fragment.view.WatchlistFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Shamil Lazar.
 * <p>
 * <p>
 * Provides Splashscreen activity dependencies.
 */
@Module(includes = {BaseActivityModule.class})
public abstract class WatchlistActivityModule {

    /**
     * Provides the injector for the {@link SplashFragment}, which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = WatchlistFragmentModule.class)
    abstract WatchlistFragment watchlistFragmentInjector();

    /**
     * As per the contract specified in {@link BaseActivityModule}; "This must be included in all
     * activity modules, which must provide a concrete implementation of {@link Activity}."
     * <p>
     * This provides the activity required to inject the
     * {@link BaseActivityModule#ACTIVITY_FRAGMENT_MANAGER} into the
     * {@link com.shamildev.retro.ui.common.BaseActivity}.
     *
     * @param activity the WatchlistActivity
     * @return the activity
     */
    @Binds
    @PerActivity
    abstract AppCompatActivity activity(WatchlistActivity activity);
}
