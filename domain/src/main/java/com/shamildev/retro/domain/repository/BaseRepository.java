package com.shamildev.retro.domain.repository;

import android.support.annotation.NonNull;

import com.shamildev.retro.domain.models.AppUser;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Shamil Lazar on 30.08.2018.
 */
public interface BaseRepository {
    Flowable<AppUser> initUser();
    Flowable<AppUser> checkUser();
    Flowable<AppUser> signInWithEmailAndPassword(String email, String password);
    Flowable<AppUser> registerWithEmailAndPassword(String email, String password);
    Flowable<AppUser> signIn(String token);


    Maybe<AppUser> signInAnonymously();



    Flowable<AppUser> signInAnonym();
    Flowable<AppUser> signInWithFacebook();
    Flowable<AppUser> signInWithTwitter();
    Completable signOut();



    @NonNull
    Maybe<byte[]> getBytes(@NonNull String storageRefString, long maxDownloadSizeBytes);

    Completable testSaveData();

    Completable insertUser();
    Flowable<AppUser> testReadData();

    Observable<AppUser> testListenerData();

    Flowable<Object> uploadImage(byte[] bytes);
    Flowable<Object> downloadImage();
}
