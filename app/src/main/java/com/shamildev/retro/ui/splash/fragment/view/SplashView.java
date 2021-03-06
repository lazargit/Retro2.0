package com.shamildev.retro.ui.splash.fragment.view;

import com.shamildev.retro.domain.core.MediaItem;
import com.shamildev.retro.domain.models.ResultWrapper;
import com.shamildev.retro.retroimage.views.RetroImageView;
import com.shamildev.retro.retroimage.views.RetroProfileCircleImageView;
import com.shamildev.retro.retroimage.views.RetroProfileImageView;
import com.shamildev.retro.ui.common.view.MVPView;

import java.util.HashMap;


/**
 * Created by Shamil Lazar.
 */
public interface SplashView extends MVPView {

    RetroImageView getSplashBg();
    RetroImageView getPersonImage();

    RetroProfileImageView getProfileView();

    RetroProfileCircleImageView getProfileCircleView();

    void navigateToHome(HashMap<String, ResultWrapper> map);

}
