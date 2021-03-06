package com.shamildev.retro.cache.realm.mapper;

import com.shamildev.retro.cache.realm.models.UserRealm;
import com.shamildev.retro.domain.models.User;

import javax.inject.Inject;

import dagger.Reusable;

/**
 * Created by Shamil Lazar on 31.12.2017.
 */

@Reusable
final class UserRealmMapper implements RealmMapper<User, UserRealm> {


    @Inject
    UserRealmMapper() {

    }


    @Override
    public UserRealm map(User model) {


        UserRealm userRealm = new UserRealm();
        userRealm.setUser_id(model.user_id());
        userRealm.setName(model.name());
        userRealm.setLanguage(model.language());
        userRealm.setTmdb_guest_session(model.tmdb_guest_session());
        userRealm.setExpires_at(model.tmdb_expires_at());
        userRealm.setLast_login(model.lastLogin());
        userRealm.setPic(model.pic());

        return userRealm;

    }

    /***
     *
     * @param entity
     * @return User
     */
    @Override
    public User map(UserRealm entity) {
        return User.builder()
                .user_id(entity.getUser_id())
                .name(entity.getName())
                .language(entity.getLanguage())
                .tmdb_guest_session(entity.getTmdb_guest_session())
                .lastLogin(entity.getLast_login())
                .pic(entity.getPic())
                .tmdb_expires_at(entity.getExpires_at())
                .build();
    }


}
