package com.jozistreet.user.model.res;

public class CheckoutRes {
    private boolean status;
    private String message = "Something went wrong";
    private String type = "collect";
    public CheckoutRes(){}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
