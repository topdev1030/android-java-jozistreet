package com.jozistreet.user.view.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jozistreet.user.R;
import com.jozistreet.user.adapter.StoreAdapter;
import com.jozistreet.user.base.BaseFragment;
import com.jozistreet.user.model.common.StoreModel;
import com.jozistreet.user.view_model.main.FavStoreFragViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchStoreFragment extends BaseFragment {
    private FavStoreFragViewModel mViewModel;
    private View mFragView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private StoreAdapter recyclerAdapter;

    @BindView(R.id.li_empty)
    LinearLayout li_empty;

    private ArrayList<StoreModel> storeList = new ArrayList<>();
    private int offset = 0;
    private int limit = 20;
    private boolean isLoading = false;
    private boolean isLast = false;
    private boolean firstLoading = false;
    public SearchStoreFragment() {
    }

    public static SearchStoreFragment newInstance() {
        SearchStoreFragment fragment = new SearchStoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FavStoreFragViewModel.class);
        mFragView = inflater.inflate(R.layout.fragment_fav_store, container, false);
        ButterKnife.bind(this, mFragView);
        initView();
        return mFragView;
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
        mViewModel.getStoreList().observe(this, list -> {
            if (offset == 0 && list.size() == 0) {
                li_empty.setVisibility(View.VISIBLE);
                return;
            } else {
                ((SearchActivity)getActivity()).setSearchKey();
                li_empty.setVisibility(View.GONE);
                if (offset == 0) {
                    storeList.clear();
                }
                if (list.size() < limit) {
                    isLast = true;
                }
                storeList.addAll(list);
                recyclerAdapter.setData(storeList);
                isLoading = false;
            }
        });

    }

    public void loadData(String key) {
        if (TextUtils.isEmpty(key)) return;
        mViewModel.loadDataSearch(key);
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    private void initView() {
        storeList.clear();
        setRecycler();
    }
    private void setRecycler() {

        recyclerAdapter = new StoreAdapter(getActivity(), storeList, new StoreAdapter.StoreRecyclerListener() {

            @Override
            public void onItemClicked(int pos, StoreModel model) {
            }

            @Override
            public void onRate(int pos, StoreModel model) {

            }

            @Override
            public void onFollow(int pos, StoreModel model) {

            }

            @Override
            public void onUnFollow(int pos, StoreModel model) {

            }
        });
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
