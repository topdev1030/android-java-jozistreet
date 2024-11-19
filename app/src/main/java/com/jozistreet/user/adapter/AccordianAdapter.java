package com.jozistreet.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jozistreet.user.R;
import com.jozistreet.user.model.common.AccordianModel;

import java.util.List;
public class AccordianAdapter extends RecyclerView.Adapter<AccordianAdapter.AccordianViewHolder> {
    private List<AccordianModel> accordianList;
    private Context context;
//    private SparseBooleanArray expandState = new SparseBooleanArray();

    public AccordianAdapter(Context context, List<AccordianModel> accordianList) {
        this.context = context;
        this.accordianList = accordianList;
//        for (int i = 0; i < accordianList.size(); i++) {
//            expandState.put(i, false); // Initially, all items are collapsed
//        }
    }

    @NonNull
    @Override
    public AccordianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_accordian_item, parent, false);
        return new AccordianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccordianViewHolder holder, int position) {
        AccordianModel accordian = accordianList.get(position);

        // Set data
        holder.title.setText(accordian.getTitle());
        holder.date.setText(accordian.getDate());
        holder.a_date.setText(accordian.getA_date());
        holder.a_time.setText(accordian.getA_time());
        holder.a_venue.setText(accordian.getA_venue());
        holder.a_stall_fee.setText(accordian.getA_stall_fee());
        holder.a_phone.setText(accordian.getA_phone());
        holder.image.setImageResource(accordian.getImage());

        // Toggle expand/collapse
        holder.ivExpand.setOnClickListener(v -> {
            boolean isVisible = holder.expandableLayout.getVisibility() == View.VISIBLE;
            holder.expandableLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            holder.ivExpand.setRotation(isVisible ? 0 : 180);
        });

        // Toggle visibility
//        final boolean isExpanded = expandState.get(position);
//        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//
//        holder.headerLayout.setOnClickListener(v -> {
//            expandState.put(position, !isExpanded);
//            notifyItemChanged(position);
//        });

//        holder.bookingButton.setOnClickListener(v -> {
//            // Handle booking click event
//            Toast.makeText(v.getContext(), "Booking for " + accordian.getTitle(), Toast.LENGTH_SHORT).show();
//        });
    }

    @Override
    public int getItemCount() {
        return accordianList.size();
    }

    static class AccordianViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, a_date, a_time, a_venue, a_stall_fee, a_phone;
        ImageView image, ivExpand;
//        Button bookingButton;
        LinearLayout expandableLayout;
//        LinearLayout headerLayout;

        AccordianViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.accordian_image);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            a_date = itemView.findViewById(R.id.accordian_date);
            a_time = itemView.findViewById(R.id.accordian_time);
            a_venue = itemView.findViewById(R.id.accordian_venue);
            a_stall_fee = itemView.findViewById(R.id.accordian_stall_fee);
            a_phone = itemView.findViewById(R.id.accordian_phone);
            ivExpand = itemView.findViewById(R.id.ivExpand);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
//            headerLayout = itemView.findViewById(R.id.header_layout);
        }
    }
}