package com.jozistreet.user.model.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jozistreet.user.model.common.ProductDetailModel;

import java.util.ArrayList;

public class ProductListRes {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<ProductDetailModel> data;
    @SerializedName("message")
    @Expose
    private String message;



    public ProductListRes(){
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<ProductDetailModel> getData() {
        return data;
    }

    public void setData(ArrayList<ProductDetailModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
