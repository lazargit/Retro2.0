package com.shamildev.retro.domain.models;


import com.google.auto.value.AutoValue;
import com.shamildev.retro.domain.core.DomainObject;

/**
 * Created by Shamil Lazar.
 */

@AutoValue
public abstract class TVShowNetwork implements DomainObject {

    public abstract String name();
    public abstract Integer id();

    public static Builder builder() {
        return new AutoValue_TVShowNetwork.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder id(Integer id);

        public abstract TVShowNetwork build();
    }
}
