package com.shamildev.retro.ui.splash;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.shamildev.retro.BuildConfig;
import com.shamildev.retro.R;
import com.shamildev.retro.ui.common.BaseActivity;
import com.shamildev.retro.ui.splash.fragment.view.SplashFragment;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Schamil on 30.10.2017.
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SplashFragment());
        }
    }



}
