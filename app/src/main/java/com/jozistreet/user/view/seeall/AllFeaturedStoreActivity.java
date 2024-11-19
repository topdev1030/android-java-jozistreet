package com.jozistreet.user.view.seeall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jozistreet.user.R;
import com.jozistreet.user.adapter.AllCanMissAdapter;
import com.jozistreet.user.base.BaseActivity;
import com.jozistreet.user.model.common.FeedModel;
import com.jozistreet.user.sqlite.DatabaseQueryClass;
import com.jozistreet.user.utils.G;
import com.jozistreet.user.view.detail.PromotionDetailActivity;
import com.jozistreet.user.view.detail.StoreDetailActivity;
import com.jozistreet.user.view_model.seeall.AllCanMissViewModel;
import com.jozistreet.user.view_model.seeall.AllFeaturedStoreViewModel;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllFeaturedStoreActivity extends BaseActivity {
    private AllFeaturedStoreViewModel mViewModel;
    private AllFeaturedStoreActivity activity;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.li_empty)
    LinearLayout li_empty;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    private AllCanMissAdapter recyclerAdapter;
    ArrayList<FeedModel> dealList = new ArrayList<>();
    private boolean isDeliver = false;
    private int offset = 0;
    private int limit = 20;
    private boolean isLoading = false;
    private boolean isLast = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllFeaturedStoreViewModel.class);
        setContentView(R.layout.activity_all_canmiss);
        ButterKnife.bind(this);
        activity = this;
        isDeliver = getIntent().getBooleanExtra("isDeliver", false);
        initView();
    }

    private void initView() {
        initParam();
        txtTitle.setText(getString(R.string.txt_featured_store));
        nestedScrollView.setNestedScrollingEnabled(false);
        setRecycler();
        mViewModel.setIsDeliver(isDeliver);
        try {
            String local_data = DatabaseQueryClass.getInstance().getData(G.getUserID(), "AllFeaturedStore", "");
            if (TextUtils.isEmpty(local_data)) {
                mViewModel.setIsBusy(true);
            } else {
                mViewModel.loadLocalData();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewModel.loadData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                initParam();
                mViewModel.setIsBusy(true);
                mViewModel.loadData();
            }
        });
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
                if (diff == 0 && !isLoading && !isLast) {
                    isLoading = true;
                    offset = offset + limit;
                    mViewModel.setOffset(offset);
                    mViewModel.setIsBusy(true);
                    mViewModel.loadData();
                }

            }
        });
    }
    private void initParam() {
        offset = 0;
        dealList.clear();
        mViewModel.setOffset(offset);
    }
    @Override
    public void onStart() {
        super.onStart();
        mViewModel.getIsBusy().observe(this, isBusy -> {
            if (isBusy) {
                showLoadingDialog();
            } else {
                hideLoadingDialog();
            }
        });
        mViewModel.getDealList().observe(this, list -> {
            if (offset == 0 && list.size() == 0) {
                li_empty.setVisibility(View.VISIBLE);
                return;
            } else {
                li_empty.setVisibility(View.GONE);
                if (offset == 0) {
                    dealList.clear();
                }
                if (list.size() < limit) {
                    isLast = true;
                }
                dealList.addAll(list);
                recyclerAdapter.setData(dealList);
                isLoading = false;
            }
        });

    }

    private void setRecycler() {
        recyclerAdapter = new AllCanMissAdapter(activity, dealList, new AllCanMissAdapter.AllCanMissAdapterRecyclerListener() {
            @Override
            public void onItemClicked(int pos, FeedModel model) {
//                Intent intent = new Intent(activity, PromotionDetailActivity.class);
//                intent.putExtra("id", model.getId());
//                intent.putExtra("media", model.getSubMedia().get(0).getMedia());
//                intent.putExtra("feed_type", model.getFeed_type());
//                intent.putExtra("brand", model.getTitle());
//                intent.putExtra("isDeliver", isDeliver);
//                startActivity(intent);

                Intent intent = new Intent(activity, StoreDetailActivity.class);
                intent.putExtra("id", model.getRelated_id());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @OnClick({R.id.btBack})
    public void onClickButtons(View view) {
        switch (view.getId()) {
            case R.id.btBack:
                finish();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
