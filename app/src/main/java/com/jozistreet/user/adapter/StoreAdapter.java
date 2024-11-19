package com.jozistreet.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.makeramen.roundedimageview.RoundedImageView;
import com.jozistreet.user.R;
import com.jozistreet.user.model.common.StoreModel;
import com.jozistreet.user.utils.G;
import com.jozistreet.user.view.detail.StoreDetailActivity;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{
    private Context mContext;
    private StoreRecyclerListener mListener;
    private ArrayList<StoreModel> mList = new ArrayList<>();

    public interface StoreRecyclerListener{
        void onItemClicked(int pos, StoreModel model);
        void onRate(int pos, StoreModel model);
        void onFollow(int pos, StoreModel model);
        void onUnFollow(int pos, StoreModel model);
    }

    public StoreAdapter(Context context, ArrayList<StoreModel> list, StoreRecyclerListener listener){
        this.mContext = context;
        this.mListener = listener;
        this.mList.clear();
        this.mList.addAll(list);
    }
    public void setData(ArrayList<StoreModel> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_store, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(holder.getLayoutPosition());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imgLogo;
        private TextView txtTitle;
        private TextView txtDescription;
        private MaterialRatingBar ratingbar;
        private ImageView imgView;
        private LinearLayout btnFollow;
        private TextView txt_follow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            ratingbar = itemView.findViewById(R.id.ratingBar);
            imgView = itemView.findViewById(R.id.imgView);
            btnFollow = itemView.findViewById(R.id.btnFollow);
            txt_follow = itemView.findViewById(R.id.txt_follow);
        }
        public void setData(int position) {
            StoreModel model = mList.get(position);
            Glide.with(mContext)
                    .load(model.getLogo())
                    .placeholder(R.drawable.ic_me)
                    .into(imgLogo);
            txtTitle.setText(model.getName());
            if (G.location != null) {
                int dist = (int) G.meterDistanceBetweenPoints(G.location.getLatitude(), G.location.getLongitude(), model.getCoordinates().get(0), model.getCoordinates().get(1));
                txtDescription.setText(String.format("%.2f Km from you", ((float)dist )/ 1000.0f));
            } else {
                int dist = (int) G.meterDistanceBetweenPoints(G.user.getLatitude(), G.user.getLongitude(), model.getCoordinates().get(0), model.getCoordinates().get(1));
                txtDescription.setText(String.format("%.2f Km from you", ((float)dist )/ 1000.0f));
            }
            ratingbar.setRating((float) model.getRating());
            if (model.isFollowing()) {
                btnFollow.setBackground(mContext.getDrawable(R.drawable.bk_blue_rect_20));
                txt_follow.setText(mContext.getString(R.string.txt_following));
                txt_follow.setTextColor(mContext.getColor(R.color.white_color));
            } else {
                btnFollow.setBackground(mContext.getDrawable(R.drawable.bk_white_rect_20));
                txt_follow.setText(mContext.getString(R.string.txt_follow));
                txt_follow.setTextColor(mContext.getColor(R.color.grey_dark));
            }
            imgView.setOnClickListener(v -> {
                int store_id = model.getId();
                Intent intent = new Intent(mContext, StoreDetailActivity.class);
                intent.putExtra("id", store_id);
                mContext.startActivity(intent);
            });
            btnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user_id = G.pref.getString("user_id" , "");
                    String status = String.valueOf(!model.isFollowing());
                    mList.get(position).setFollowing(!model.isFollowing());
                    setFollowBtn(position);
                    if (G.isNetworkAvailable(mContext)) {
                        Ion.with(mContext)
                                .load(G.StoreFollowUrl)
                                .addHeader("Authorization", "Bearer " + G.pref.getString("token" , ""))
                                .setBodyParameter("id", String.valueOf(model.getId()))
                                .asString()
                                .setCallback(new FutureCallback<String>() {
                                    @Override
                                    public void onCompleted(Exception e, String result) {
                                    }
                                });
                    }
                }
            });
        }
        private void setFollowBtn(int position){
            if (!mList.get(position).isFollowing()){
                btnFollow.setBackground(mContext.getDrawable(R.drawable.bk_white_rect_20));
                txt_follow.setText(mContext.getString(R.string.txt_follow));
                txt_follow.setTextColor(mContext.getColor(R.color.grey_dark));
            }else{
                btnFollow.setBackground(mContext.getDrawable(R.drawable.bk_blue_rect_20));
                txt_follow.setText(mContext.getString(R.string.txt_following));
                txt_follow.setTextColor(mContext.getColor(R.color.white_color));
            }
        }
    }
}
