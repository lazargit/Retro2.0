package com.shamildev.retro.domain.models;


import com.google.auto.value.AutoValue;
import com.shamildev.retro.domain.core.DomainObject;
import com.shamildev.retro.domain.core.DomainObjectStorable;

import java.io.Serializable;

import io.reactivex.annotations.Nullable;

/**
 * Created by Shamil Lazar.
 */

@AutoValue
public abstract class Genre implements DomainObject,DomainObjectStorable,Serializable {


    public abstract int id();
    @Nullable
    public abstract String name();
    @Nullable
    public abstract String type();
    @Nullable
    public abstract String language();
    @Nullable
    public abstract Long lastUpdate();

    public static Genre add(Genre genre, String type, String language, Long lastUpdate) {
        return builder()
                .id(genre.id())
                .name(genre.name())
                .type(type)
                .language(language)
                .lastUpdate(lastUpdate)
                .build();
    }


    public static Builder builder() {
        return new AutoValue_Genre.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder type(String type);

        public abstract Builder language(String language);

        public abstract Builder lastUpdate(Long lastUpdate);



        public abstract Genre build();
    }
}
