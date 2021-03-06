package com.shamildev.retro.domain.models;

import com.google.auto.value.AutoValue;
import com.shamildev.retro.domain.core.DomainObject;


/**
 * Created by Shamil Lazar.
 */

@AutoValue
public abstract class Dates implements DomainObject {

    abstract String maximum();

    abstract String minimum();



    public static Builder builder() {
        return new AutoValue_Dates.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder maximum(String maximum);

        public abstract Builder minimum(String minimum);



        public abstract Dates build();
    }
}