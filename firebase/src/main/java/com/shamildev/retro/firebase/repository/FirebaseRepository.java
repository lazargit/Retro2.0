package com.shamildev.retro.firebase.repository;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shamildev.retro.domain.core.AppConfig;
import com.shamildev.retro.domain.error.BaseError;
import com.shamildev.retro.domain.models.AppUser;
import com.shamildev.retro.domain.repository.BaseRepository;
import com.shamildev.retro.firebase.exceptions.FirebaseSignInException;
import com.shamildev.retro.firebase.utils.FilePaths;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * An implementation of {@link FirebaseRepository}.
 */

public final class FirebaseRepository implements BaseRepository {



    private static final String KEY_TITLE = "key_title";
    private static final String KEY_DESCRIPTION = "key_description";
    private final CollectionReference collections_users;
    private final FirebaseUser mFUser;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;
    private final DocumentReference noteRef;
    private final StorageReference storageRef;
    private final StorageReference storageUserImagesRef;
    private ListenerRegistration listenerRegistration;

    private FirebaseAuth mAuth;
    //private  FirebaseAuth mAuthinstance;
    private AppUser appUser;

    @Inject
    AppConfig appConfig;

//    @Inject
//    FirebaseAuth mAuth;

    @Inject
    public FirebaseRepository(AppUser appUser) {
        this.appUser = appUser;
        //this.mAuthinstance=instance;

        this.mAuth = FirebaseAuth.getInstance();
        this.mFUser = mAuth.getCurrentUser();
        this.db = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.storageRef = this.storage.getReference();
        this.storageUserImagesRef = this.storageRef.child(FilePaths.FIREBASE_IMAGE_STORAGE);
        noteRef = db.collection("Notebook").document("My First Note");

        collections_users = db.collection("users");

        Log.e("TAG", "FirebaseAuth " + this.mAuth);
        Log.e("TAG", "FirebaseUser " + this.mFUser);
        // firebaseMapper = new FirebaseMapper(appUser);

    }


    @Override
    public Flowable<AppUser> initUser() {
        Log.d("FirebaseRepository", "user###>>>> :");
        mAuth = FirebaseAuth.getInstance();
        Log.d("FirebaseRepository", "user###>>>> :" + mAuth.getCurrentUser());
        if (mAuth.getCurrentUser() != null) {
            Log.d("FirebaseRepository", "user:success" + mAuth.getCurrentUser().getEmail());
        } else {
            mAuth.signInWithEmailAndPassword("shamil@aaa.de", "123456")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("TAG", "sign in:success" + user.getEmail());
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "sig in:failure", task.getException());

                            }


                        }
                    });


        }
        return Flowable.empty();
    }

    @Override
    public Flowable<AppUser> checkUser() {
        return Flowable.create(e -> {
            try {

                if (mFUser != null) {
                    String photoPath = null;

                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = mFUser.getPhotoUrl().getPath();
                    }
                    if (mFUser.isAnonymous()) {
                        Log.e("CHECK USER", "ist anonym: " + mFUser.isAnonymous() + " uid: " + mFUser.getUid() + " " + mFUser.getProviders().size());
                    }
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                } else {

                    e.onComplete();
                }
            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);

    }

    @Override
    public Flowable<AppUser> signInWithEmailAndPassword(String email, String password) {

        Log.d("TAG", "FirebaseUser " + mFUser);
        return Flowable.create(e -> {
            try {
                if (mFUser == null) {
                    mAuth.signInWithEmailAndPassword(email, password)

                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "signInWithEmailAndPassword:onComplete");
                                    if (task.isSuccessful()) {
                                        FirebaseUser currentUser = mAuth.getCurrentUser();

                                        if (currentUser != null) {
                                            String photoPath = null;
                                            if (currentUser.getPhotoUrl() != null) {
                                                photoPath = currentUser.getPhotoUrl().getPath();
                                            }
                                            appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                            e.onNext(appUser);
                                            e.onComplete();
                                        } else {
                                            String photoPath = null;
                                            if (currentUser.getPhotoUrl().getPath() != null) {
                                                photoPath = currentUser.getPhotoUrl().getPath();
                                            }
                                            appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                            e.onNext(appUser);
                                            e.onComplete();
                                        }
                                    } else {
                                        e.onError(new BaseError(401, "Firebase sign in with Email and Password failed!"));

                                    }

                                }
                            })
                    ;
                } else {
                    // firebaseMapper.map(appUser,user);
                    String photoPath = null;
                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = mFUser.getPhotoUrl().getPath();
                    }

                    Log.e("TAG", appUser.toString());
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                }


            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);


    }

    @Override
    public Flowable<AppUser> registerWithEmailAndPassword(String email, String password) {

        Log.d("TAG", "FirebaseUser " + mFUser);
        return Flowable.create(e -> {
            try {
                if (mFUser == null) {
                    mAuth
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "createUserWithEmailAndPassword:onComplete");
                                    if (task.isSuccessful()) {
                                        FirebaseUser currentUser = mAuth.getCurrentUser();

                                        if (currentUser != null) {
                                            String photoPath = null;
                                            if (currentUser.getPhotoUrl() != null) {
                                                photoPath = currentUser.getPhotoUrl().getPath();
                                            }
                                            appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                            e.onNext(appUser);
                                            e.onComplete();
                                        } else {
                                            String photoPath = null;
                                            if (currentUser.getPhotoUrl().getPath() != null) {
                                                photoPath = currentUser.getPhotoUrl().getPath();
                                            }
                                            appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                            e.onNext(appUser);
                                            e.onComplete();
                                        }
                                    } else {
                                        e.onError(new BaseError(401, "Firebase sign in with Email and Password failed!"));

                                    }

                                }
                            })
                    ;
                } else {
                    // firebaseMapper.map(appUser,user);
                    String photoPath = null;
                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = mFUser.getPhotoUrl().getPath();
                    }

                    Log.e("TAG", appUser.toString());
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                }


            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);


    }

    @Override
    public Flowable<AppUser> signIn(String token) {
        return null;
    }


    @Override
    public Maybe<AppUser> signInAnonymously() {
        return Maybe.create(emitter -> mAuth.signInAnonymously()
                .addOnSuccessListener(authResult -> {
                    Log.e("ANONYM", "uid: " + authResult.getUser().getUid() + " name: " + authResult.getUser().getDisplayName() + " isanonymous " + authResult.getUser().isAnonymous());

                })
                .addOnFailureListener(emitter::onError));
    }


    @Override
    public Flowable<AppUser> signInAnonym() {


        return Flowable.create(e -> {
            try {

                if (mFUser == null) {

                    mAuth.signInAnonymously()
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        e.onNext(appUser);
                                        e.onComplete();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        e.onError(new FirebaseSignInException());
                                        e.onComplete();


                                    }
                                }
                            });

//                            .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                        e.onNext(appUser);
//                                        e.onComplete();
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    e.onError(new Exception("sign in fails"));
//                                    e.onComplete();
//
//
//                                }
//
//
//                            });


                } else {
                    // firebaseMapper.map(appUser,user);
                    String photoPath = null;
                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = "https://graph.facebook.com/" + mFUser.getPhotoUrl().getPath() + "?type=large";
                    }

                    Log.e("TAG>", "> " + mFUser.getDisplayName());
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                }


            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<AppUser> signInWithFacebook() {
        return Flowable.create(e -> {
            try {
                AuthCredential credential = FacebookAuthProvider.getCredential(appConfig.getFacebookToken());
                if (mFUser == null || mFUser.isAnonymous()) {

                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        String photoPath = null;
                                        if (currentUser.getPhotoUrl() != null) {
                                            photoPath = currentUser.getPhotoUrl().getPath();
                                        }
                                        // Sign in success, update UI with the signed-in user's information
                                        appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                        Log.e("FACEBOOK", currentUser.toString());
                                        e.onNext(appUser);
                                        e.onComplete();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        e.onComplete();
                                    }
                                }
                            });


                } else {
                    // firebaseMapper.map(appUser,user);
                    String photoPath = null;
                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = "https://graph.facebook.com/" + mFUser.getPhotoUrl().getPath() + "?type=large";
                    }

                    Log.e("TAG>", "> " + mFUser.getDisplayName());
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                }


            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<AppUser> signInWithTwitter() {
        return Flowable.create(e -> {
            try {
                AuthCredential credential = TwitterAuthProvider.getCredential(appConfig.getTwitterToken().key, appConfig.getTwitterToken().value);

                if (mFUser == null) {

                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String photoPath = null;
                                    if (currentUser.getPhotoUrl() != null) {
                                        photoPath = currentUser.getPhotoUrl().getPath();
                                    }
                                    // Sign in success, update UI with the signed-in user's information
                                    appUser.setFirebaseUser(currentUser.getUid(), currentUser.getEmail(), currentUser.getDisplayName(), currentUser.getProviderId(), photoPath);
                                    Log.e("TWITTER", currentUser.toString());
                                    e.onNext(appUser);
                                    e.onComplete();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    e.onComplete();
                                }
                            });


                } else {
                    // firebaseMapper.map(appUser,user);
                    String photoPath = null;
                    if (mFUser.getPhotoUrl() != null) {
                        photoPath = mFUser.getPhotoUrl().getPath();
                    }

                    Log.e("TAG", appUser.toString());
                    appUser.setFirebaseUser(mFUser.getUid(), mFUser.getEmail(), mFUser.getDisplayName(), mFUser.getProviderId(), photoPath);
                    e.onNext(appUser);
                    e.onComplete();
                }


            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Completable signOut() {
        return Completable.create(e -> {
            if (this.mAuth.getCurrentUser() != null) {
                Log.d("TAG", "FirebaseUser befor logout " + mAuth.getCurrentUser());


                mAuth.signOut();
                appUser.removetFirebaseUser();
                Log.d("TAG", "FirebaseUser after logout " + mAuth.getCurrentUser());
                e.onComplete();
            }
            // e.onError(new Exception("User not logged in!"));
        });


    }


    @Override
    public Flowable<Object> uploadImage(byte[] bytes) {


        return Flowable.create(e -> {

            try {
                UploadTask uploadTask;
                if (mAuth.getCurrentUser()!=null) {
                    StorageReference ref =  this.storageUserImagesRef.child(mFUser.getUid());
                    uploadTask =  ref.putBytes(bytes);
                    uploadTask

                            .addOnSuccessListener(taskSnapshot -> {
                                Uri firebaseUrl = taskSnapshot.getUploadSessionUri();


                                e.onNext(ref.getPath());
                                e.onComplete();
                            })

                            .addOnFailureListener(error -> e.onError(error))
                            .addOnProgressListener(taskSnapshot -> {
                                Double progress = (double) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                                e.onNext(progress);
                            });
                }else{
                    e.onError(new FirebaseSignInException("user not sign in!"));
                }
            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);


    }

    @Override
    public Flowable<Object> downloadImage() {
        return null;
    }


    @NonNull
    @Override
    public  Maybe<byte[]> getBytes(@NonNull final String storageRefString,
                                   final long maxDownloadSizeBytes) {



        return Maybe.create(new MaybeOnSubscribe<byte[]>() {

            @Override
            public void subscribe(MaybeEmitter<byte[]> emitter) throws Exception {
               storageRef.child(storageRefString)
                        .getBytes(maxDownloadSizeBytes)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                emitter.onSuccess(bytes);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                            @Override
                            public void onComplete(@NonNull Task<byte[]> task) {
                                emitter.onComplete();
                            }
                        });


            }
        });
    }


    @Override
    public Completable testSaveData() {

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, "testtitle");
        note.put(KEY_DESCRIPTION, "description");

        return Completable.create(e -> noteRef.set(note)
                .addOnSuccessListener(aVoid -> e.onComplete())
                .addOnFailureListener(e::onError));
    }

    @Override
    public Flowable<AppUser> testReadData() {
        return Flowable.create(e -> {
            try {
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    appUser.setName(documentSnapshot.getString(KEY_TITLE));
                                    e.onNext(appUser);
                                    e.onComplete();
                                } else {
                                    e.onComplete();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception error) {
                                e.onError(error);
                            }
                        });

            } catch (Exception t) {
                e.onError(t);
            }


        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Observable<AppUser> testListenerData() {

        return Observable.create(e -> {


            listenerRegistration = noteRef
                    .addSnapshotListener((documentSnapshot, error) -> {

                        if (error != null) {
                            e.onError(error);
                        } else {
                            if (documentSnapshot.exists()) {
                                appUser.setName(documentSnapshot.getString(KEY_TITLE));
                                e.onNext(appUser);

                            } else {
                                e.onComplete();

                            }
                        }

                    });


            e.setCancellable(() -> listenerRegistration.remove());


        });
    }


    @Override
    public Completable insertUser() {
        return null;
    }
}
