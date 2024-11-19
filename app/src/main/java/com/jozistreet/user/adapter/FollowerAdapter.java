package com.jozistreet.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jozistreet.user.R;
import com.jozistreet.user.model.common.FollowerModel;

import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {
    private List<FollowerModel> followerList;

    public FollowerAdapter(List<FollowerModel> followerList) {
        this.followerList = followerList;
    }

    @NonNull
    @Override
    public FollowerAdapter.FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_followers, parent, false);
        return new FollowerAdapter.FollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerAdapter.FollowerViewHolder holder, int position) {
        FollowerModel follower = followerList.get(position);

        // Set data
        holder.avatar.setImageResource(follower.getAvatar());
        holder.name.setText(follower.getName());
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }

    static class FollowerViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView avatar;

        FollowerViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.follower_img);
            name = itemView.findViewById(R.id.follower_name);
        }
    }
}