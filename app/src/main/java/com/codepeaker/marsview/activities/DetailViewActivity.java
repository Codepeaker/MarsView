package com.codepeaker.marsview.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.codepeaker.marsview.R;
import com.codepeaker.marsview.adapter.DetailImageViewAdapter;
import com.codepeaker.marsview.databinding.ActivityDetailviewBinding;
import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.utils.Constants;

import java.util.List;

public class DetailViewActivity extends AppCompatActivity {
    ActivityDetailviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailview);
        setTitle("Detail View");
        int selectedImage = getIntent().getExtras().getInt(Constants.POSITION);
        List<Upload> uploads = getIntent().getExtras().getParcelableArrayList(Constants.IMAGES_URLS);
        ConstraintLayout.LayoutParams paramsPager = new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.viewPager.setLayoutParams(paramsPager);
        DetailImageViewAdapter detailImageViewAdapter = new DetailImageViewAdapter(this
                , uploads);
        binding.viewPager.setAdapter(detailImageViewAdapter);
        binding.viewPager.setCurrentItem(selectedImage);

    }
}
