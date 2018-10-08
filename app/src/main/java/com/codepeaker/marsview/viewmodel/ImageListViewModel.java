package com.codepeaker.marsview.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.codepeaker.marsview.repo.FirebaseDbListener;
import com.codepeaker.marsview.repo.model.Upload;

import java.util.List;


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
