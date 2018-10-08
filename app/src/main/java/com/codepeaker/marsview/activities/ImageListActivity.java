package com.codepeaker.marsview.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.codepeaker.marsview.R;
import com.codepeaker.marsview.adapter.ImageListAdapter;
import com.codepeaker.marsview.databinding.ActivityImageListBinding;
import com.codepeaker.marsview.databinding.ContentImageListBinding;
import com.codepeaker.marsview.repo.UploadService;
import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.utils.AppUtils;
import com.codepeaker.marsview.utils.Constants;
import com.codepeaker.marsview.viewmodel.ImageListViewModel;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;


public class ImageListActivity extends AppCompatActivity implements ImageListAdapter.OnImageClickInterface {

    private ImageListAdapter adapter;
    private ArrayList<Upload> uploads = new ArrayList<>();
    private StorageReference storageReference;
    private Uri croppedFilePath;
    ActivityImageListBinding binding;
    ContentImageListBinding contentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_list);
        setSupportActionBar(binding.toolbar);
        setTitle("Uploads");

        ImageListViewModel viewModel = ViewModelProviders.of(this)
                .get(ImageListViewModel.class);
        viewModel.loadImageUrl();
        viewModel.getUrlLiveData().observe(this, new Observer<List<Upload>>() {
            @Override
            public void onChanged(List<Upload> uploads) {
                if (uploads != null) {
                    ImageListActivity.this.uploads = (ArrayList<Upload>) uploads;
                    adapter = new ImageListAdapter(ImageListActivity.this, uploads);
                    contentBinding = binding.contentImageList;
                    contentBinding.imageRv.setLayoutManager(new GridLayoutManager(
                            ImageListActivity.this, 2));
                    contentBinding.imageRv.setAdapter(adapter);
                }
            }
        });

        AppUtils.getInstance().showPDialog(this,"Please wait..." );

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ImageListActivity.this);
            }
        });
    }

    @Override
    public void onImageClick(int position) {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putParcelableArrayListExtra(Constants.IMAGES_URLS, uploads);
        intent.putExtra(Constants.POSITION, position);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                croppedFilePath = result.getUri();
                if (croppedFilePath == null) {
                    AppUtils.getInstance().showPTALToast(this);
                }
                UploadService.getInstance().uploadFile(this, croppedFilePath);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, result.getError().getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        }
    }


}
