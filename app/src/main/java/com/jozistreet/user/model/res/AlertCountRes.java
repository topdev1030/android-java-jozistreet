package com.jozistreet.user.model.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jozistreet.user.model.common.AlertCountModel;

public class AlertCountRes {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("data")
    @Expose
    private AlertCountModel data;

    @SerializedName("message")
    @Expose
    private String message;


    public AlertCountRes(){

    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public AlertCountModel getData() {
        return data;
    }

    public void setData(AlertCountModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
