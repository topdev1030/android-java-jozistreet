package com.jozistreet.user.listener;

import android.view.View;

public interface RecyclerClickListener {
    void onClick(View v, int position);
    void onClick(View v, int position, int type);
}