package com.example.shms.utils;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        if (url != null) {
            Glide.with(view.getContext())
                    .load(url)
                    .centerCrop()
                    .into(view);
        }
    }

    @BindingAdapter("formattedDate")
    public static void setFormattedDate(TextView view, Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            view.setText(sdf.format(date));
        }
    }

    @BindingAdapter("formattedTime")
    public static void setFormattedTime(TextView view, Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            view.setText(sdf.format(date));
        }
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView view, List<?> items) {
        if (view.getAdapter() instanceof BaseAdapter) {
            ((BaseAdapter) view.getAdapter()).submitList(items);
        }
    }

    @BindingAdapter("bedState")
    public static void setBedState(TextView view, int state) {
        switch (state) {
            case Constants.BED_STATE_AVAILABLE:
                view.setTextColor(view.getContext().getColor(android.R.color.holo_green_dark));
                break;
            case Constants.BED_STATE_MAINTENANCE:
                view.setTextColor(view.getContext().getColor(android.R.color.holo_orange_dark));
                break;
            case Constants.BED_STATE_OCCUPIED:
                view.setTextColor(view.getContext().getColor(android.R.color.holo_red_dark));
                break;
        }
    }
}