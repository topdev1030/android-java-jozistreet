package com.jozistreet.user.model.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreCategoryModel {
    @SerializedName("id")
    @Expose
    private int id;


    @SerializedName("name")
    @Expose
    private String name;


    public StoreCategoryModel(){}
    public StoreCategoryModel(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
