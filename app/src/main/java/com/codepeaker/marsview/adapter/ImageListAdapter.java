package com.codepeaker.marsview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepeaker.marsview.R;
import com.codepeaker.marsview.activities.ImageListActivity;
import com.codepeaker.marsview.databinding.ImageListBinding;
import com.codepeaker.marsview.repo.model.Upload;

import java.util.List;


public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private Context context;
    private List<Upload> uploads;

    public ImageListAdapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageListBinding binding = DataBindingUtil.inflate(layoutInflater
                , R.layout.image_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Upload upload = uploads.get(position);
        Glide.with(context).load(upload.getUrl()).into(holder.binding.imageView);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageListActivity) context).onImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageListBinding binding;
        public ViewHolder(ImageListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnImageClickInterface {
        public void onImageClick(int position);
    }
}