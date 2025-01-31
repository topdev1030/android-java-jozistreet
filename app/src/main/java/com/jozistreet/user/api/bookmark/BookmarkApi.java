package com.jozistreet.user.api.bookmark;

import static com.jozistreet.user.utils.ParseUtil.parseFeed;
import static com.jozistreet.user.utils.ParseUtil.parsePromotion;

import android.text.TextUtils;

import com.jozistreet.user.api.ApiConstants;
import com.jozistreet.user.api.RetrofitClient;
import com.jozistreet.user.api.newsfeed.NewsFeed;
import com.jozistreet.user.model.common.ApiErrorModel;
import com.jozistreet.user.model.common.FeedModel;
import com.jozistreet.user.model.common.PromotionOneModel;
import com.jozistreet.user.model.res.BrandPromotionRes;
import com.jozistreet.user.model.res.CommonRes;
import com.jozistreet.user.model.res.FeedRes;
import com.jozistreet.user.utils.G;
import com.jozistreet.user.utils.GsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkApi {
    public static void getBookmarkCategory() {
        RetrofitClient.getClient(ApiConstants.BASE_URL)
                .create(Bookmark.class)
                .getBookmarkCategory(G.getToken())
                .enqueue(new Callback<CommonRes>() {
                    @Override
                    public void onResponse(Call<CommonRes> call, Response<CommonRes> result) {
                        BrandPromotionRes res = new BrandPromotionRes();
                        if (result.isSuccessful()) {
                            if (result.body().isStatus()) {
                                String dataStr = GsonUtils.getInstance().toJson(result.body().getData());
                                res = GsonUtils.getInstance().fromJson(dataStr, BrandPromotionRes.class);
                                try {
                                    JSONObject jsonObject = new JSONObject(dataStr);
                                    JSONArray jsonArray = new JSONArray(jsonObject.getString("promotionList"));
                                    ArrayList<PromotionOneModel> collectList = new ArrayList<>();
                                    ArrayList<PromotionOneModel> deliverList = new ArrayList<>();
                                    collectList.clear();
                                    deliverList.clear();
                                    if (jsonArray.length() != 0) {
                                        HashMap<String, ArrayList<PromotionOneModel>> tmpData =  parsePromotion(jsonArray);
                                        collectList = tmpData.get("collect");
                                        deliverList = tmpData.get("deliver");
                                    }
                                    res.setCollectList(collectList);
                                    res.setDeliverList(deliverList);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                res.setMessage(result.body().getMessage());
                            }
                            res.setStatus(result.body().isStatus());

                        } else if (result.errorBody() != null) {
                            ResponseBody errorBody = result.errorBody();
                            if (result.code() == 200) {
                                ApiErrorModel errorModel = null;
                                try {
                                    if (TextUtils.isEmpty(errorBody.string()) || errorBody.string().equalsIgnoreCase("Internal Server Error")) {
                                        res.setMessage("Something went wrong");
                                    } else {
                                        try {
                                            errorModel = GsonUtils.getInstance().fromJson(errorBody.string(), ApiErrorModel.class);
                                            res.setMessage(errorModel.getMessage());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                res.setMessage("Something went wrong");
                            }
                        }
                        EventBus.getDefault().post(res);
                    }

                    @Override
                    public void onFailure(Call<CommonRes> call, Throwable t) {
                        EventBus.getDefault().post(new BrandPromotionRes());
                    }
                });

    }
    public static void getAllBookmarkPosts(String category_id, int offset, int page_size) {
        RetrofitClient.getClient(ApiConstants.BASE_URL)
                .create(Bookmark.class)
                .getAllBookmarkPosts(G.getToken(), category_id, offset, page_size, false)
                .enqueue(new Callback<CommonRes>() {
                    @Override
                    public void onResponse(Call<CommonRes> call, Response<CommonRes> result) {
                        FeedRes res = new FeedRes();
                        if (result.isSuccessful()) {
                            if (result.body().isStatus()) {
                                String dataStr = GsonUtils.getInstance().toJson(result.body().getData());
                                res = GsonUtils.getInstance().fromJson(dataStr, FeedRes.class);
                                try {
                                    JSONObject jsonObject = new JSONObject(dataStr);
                                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                                    ArrayList<FeedModel> feedList = new ArrayList<>();
                                    feedList.clear();
                                    if (jsonArray.length() != 0) {
                                        feedList = parseFeed(jsonArray);
                                    }
                                    res.setPostList(feedList);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                res.setMessage(result.body().getMessage());
                            }
                            res.setStatus(result.body().isStatus());
                        } else if (result.errorBody() != null) {
                            ResponseBody errorBody = result.errorBody();
                            if (result.code() == 200) {
                                ApiErrorModel errorModel = null;
                                try {
                                    if (TextUtils.isEmpty(errorBody.string()) || errorBody.string().equalsIgnoreCase("Internal Server Error")) {
                                        res.setMessage("Something went wrong");
                                    } else {
                                        try {
                                            errorModel = GsonUtils.getInstance().fromJson(errorBody.string(), ApiErrorModel.class);
                                            res.setMessage(errorModel.getMessage());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                res.setMessage("Something went wrong");
                            }
                        }
                        EventBus.getDefault().post(res);
                    }

                    @Override
                    public void onFailure(Call<CommonRes> call, Throwable t) {
                        EventBus.getDefault().post(new FeedRes());
                    }
                });
    }

}
