package com.jozistreet.user.view_model.seeall;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jozistreet.user.api.newsfeed.NewsFeedApi;
import com.jozistreet.user.model.common.FeedModel;
import com.jozistreet.user.model.res.FeedRes;
import com.jozistreet.user.sqlite.DatabaseQueryClass;
import com.jozistreet.user.utils.G;
import com.jozistreet.user.utils.GsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.ArrayList;

public class AllAdViewModel extends ViewModel {
    private MutableLiveData<Boolean> isBusy;
    private MutableLiveData<Boolean> isDeliver;
    private MutableLiveData<Integer> offset;
    private MutableLiveData<ArrayList<FeedModel>> dealList;
    public AllAdViewModel(){
        isBusy = new MutableLiveData<>();
        isBusy.setValue(false);
        isDeliver = new MutableLiveData<>();
        isDeliver.setValue(false);
        offset = new MutableLiveData<>();
        offset.setValue(0);
        dealList = new MutableLiveData<>();
        dealList.setValue(new ArrayList<>());
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
    }
    public MutableLiveData<Boolean> getIsBusy() {
        return isBusy;
    }
    public void setIsBusy(boolean isBusy) {
        this.isBusy.setValue(isBusy);
    }

    public MutableLiveData<Integer> getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset.setValue(offset);
    }

    public MutableLiveData<ArrayList<FeedModel>> getDealList() {
        return dealList;
    }

    public void setDealList(ArrayList<FeedModel> dealList) {
        this.dealList.setValue(dealList);
    }

    public MutableLiveData<Boolean> getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Boolean isDeliver) {
        this.isDeliver.setValue(isDeliver);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onApiSuccessResult(FeedRes res) {
        setIsBusy(false);
        if (res.isStatus()) {
            setDealList(res.getPostList());
            String data = new Gson().toJson(res, new TypeToken<FeedRes>() {
            }.getType());
            DatabaseQueryClass.getInstance().insertData(
                    G.getUserID(),
                    "AllAd",
                    data,
                    "",
                    ""
            );
        }
    }

    public void loadLocalData() {
        try {
            String data = DatabaseQueryClass.getInstance().getData(G.getUserID(), "AllAd", "");
            if (!TextUtils.isEmpty(data)) {
                FeedRes localRes = GsonUtils.getInstance().fromJson(data, FeedRes.class);
                setDealList(localRes.getPostList());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void loadData() {
        NewsFeedApi.getAllAd(offset.getValue(), 20, isDeliver.getValue());
    }

}
