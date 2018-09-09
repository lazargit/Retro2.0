package com.shamildev.retro.data.entity.tmdb;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamildev.retro.data.entity.Entity;

import java.io.Serializable;


public class GuestSessionEntity implements Serializable, Entity {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("guest_session_id")
    @Expose
    private String guestSessionId;
    @SerializedName("expires_at")
    @Expose
    private String expiresAt;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getGuestSessionId() {
        return guestSessionId;
    }

    public void setGuestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

}