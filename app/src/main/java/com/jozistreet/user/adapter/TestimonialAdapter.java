package com.jozistreet.user.adapter;

// TestimonialAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jozistreet.user.R;
import com.jozistreet.user.model.common.TestimonialModel;

import java.util.List;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.TestimonialViewHolder> {

    private List<TestimonialModel> testimonialList;
//    private Context context;

    // Adapter Constructor
    public TestimonialAdapter(List<TestimonialModel> testimonialList) {
//        this.context = context;
        this.testimonialList = testimonialList;
    }

    @NonNull
    @Override
    public TestimonialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_testimonial_item, parent, false);
        return new TestimonialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimonialViewHolder holder, int position) {
        TestimonialModel testimonial = testimonialList.get(position);

        // Set the data
        holder.profileImage.setImageResource(testimonial.getImageResource());
        holder.descriptionText.setText(testimonial.getDescription());
        holder.nameText.setText(testimonial.getName());

        // Set the rating stars based on the rating value
//        int rating = testimonial.getRating();
//        for (int i = 0; i < holder.stars.length; i++) {
//            if (i < rating) {
//                holder.stars[i].setVisibility(View.VISIBLE);
//            } else {
//                holder.stars[i].setVisibility(View.INVISIBLE);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return testimonialList.size();
    }

    // ViewHolder class
    public static class TestimonialViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView descriptionText;
        TextView nameText;

//        ImageView[] stars;

        public TestimonialViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.testimonial_imgPhoto);
            descriptionText = itemView.findViewById(R.id.testimonial_description);
            nameText = itemView.findViewById(R.id.testimonial_name);

            // Initialize star ImageView array
//            stars = new ImageView[5];
//            stars[0] = itemView.findViewById(R.id.star1);
//            stars[1] = itemView.findViewById(R.id.star2);
//            stars[2] = itemView.findViewById(R.id.star3);
//            stars[3] = itemView.findViewById(R.id.star4);
//            stars[4] = itemView.findViewById(R.id.star5);
        }
    }
}

