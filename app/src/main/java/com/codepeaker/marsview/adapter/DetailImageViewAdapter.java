package com.codepeaker.marsview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepeaker.marsview.repo.model.Upload;
import com.jsibbold.zoomage.ZoomageView;

import java.util.List;


public class DetailImageViewAdapter extends PagerAdapter {
    Context context;
    List<Upload> uploads;

    public DetailImageViewAdapter(Context context, List<Upload> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final ZoomageView imageView = new ZoomageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setMinimumHeight(300);
        Glide.with(context).load(uploads.get(position).getUrl()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return uploads.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        (container).removeView((View) object);
    }
}
