package com.shamildev.retro.ui.signin;

import android.support.v7.app.AppCompatActivity;

import com.shamildev.retro.di.scope.PerActivity;
import com.shamildev.retro.di.scope.PerFragment;
import com.shamildev.retro.ui.common.BaseActivityModule;
import com.shamildev.retro.ui.signin.fragment.modul.SignInFragmentModule;
import com.shamildev.retro.ui.signin.fragment.view.SignInFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Shamil Lazar.


 * Provides Splashscreen activity dependencies.
 */
@Module(includes = {BaseActivityModule.class})
public abstract class SignInActivityModule {





    @Binds
    @PerActivity
    abstract AppCompatActivity activity(SignInActivity activity);


    /**
     * Provides the injector for the {@link SignInFragment}, which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = SignInFragmentModule.class)
    abstract SignInFragment signinFragmentInjector();




}
