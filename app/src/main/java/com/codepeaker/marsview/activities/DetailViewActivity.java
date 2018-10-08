package com.codepeaker.marsview.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.codepeaker.marsview.R;
import com.codepeaker.marsview.adapter.DetailImageViewAdapter;
import com.codepeaker.marsview.databinding.ActivityDetailviewBinding;
import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.utils.Constants;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

public class DetailViewActivity extends AppCompatActivity {
    ActivityDetailviewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailview);
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
