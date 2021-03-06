package com.shamildev.retro.domain.interactor.usecases.base;

import com.shamildev.retro.domain.core.AppConfig;
import com.shamildev.retro.domain.core.usecase.UseCaseCompletable;
import com.shamildev.retro.domain.interactor.params.ParamsBasic;
import com.shamildev.retro.domain.repository.BaseRepository;

import javax.inject.Inject;

import io.reactivex.Completable;


/**
 * Use case for getting a businesses with a given id.
 */
public final class USECASE_LogoutUser implements UseCaseCompletable<ParamsBasic> {

    private final BaseRepository baseRepository;


    @Inject
    AppConfig appConfig;

    @Inject
    USECASE_LogoutUser(BaseRepository repository) {
        this.baseRepository = repository;
    }




    @Override
    public Completable execute(ParamsBasic params) {
        final int cacheTime = ((Params) params).cacheTime;
        final String language = ((Params) params).language;
        return  this.baseRepository.signOut();
    }







    public static final class Params implements ParamsBasic {



        private int cacheTime = 0;
        private String language;

        public Params(int cacheTime) {
            this.cacheTime = cacheTime;
        }
        public Params(int cacheTime,String language ) {
            this.cacheTime = cacheTime;
            this.language = language;
        }


        public static Params with(int cacheTime) {
            return new Params(cacheTime);
        }
        public static Params withEmailAndPassword(String language, int cacheTime) {
            return new Params(cacheTime,language);
        }



    }
}
