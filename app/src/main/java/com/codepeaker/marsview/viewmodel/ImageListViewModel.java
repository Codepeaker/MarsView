package com.codepeaker.marsview.viewmodel;

import android.app.Application;

import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.repo.FirebaseDbListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ImageListViewModel extends AndroidViewModel {

    private LiveData<List<Upload>> urlLiveData = new MutableLiveData<>();

    public ImageListViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadImageUrl() {
        urlLiveData = FirebaseDbListener.getInstance().getDbUrls(getApplication());
    }

    public LiveData<List<Upload>> getUrlLiveData() {
        return urlLiveData;
    }


}
