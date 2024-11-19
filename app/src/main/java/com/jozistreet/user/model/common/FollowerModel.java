package com.jozistreet.user.model.common;

public class FollowerModel {
    private int avatar;
    private String name;

    public FollowerModel(int avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }
}
