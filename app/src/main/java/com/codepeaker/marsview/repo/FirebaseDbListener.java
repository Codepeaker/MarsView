package com.codepeaker.marsview.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.utils.AppUtils;
import com.codepeaker.marsview.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseDbListener {
    private static FirebaseDbListener firebaseDbListener;
    private MutableLiveData<List<Upload>> mutableLiveData = new MutableLiveData<>();

    private FirebaseDbListener() {
    }

    public static synchronized FirebaseDbListener getInstance() {
        if (firebaseDbListener == null) {
            firebaseDbListener = new FirebaseDbListener();
        }
        return firebaseDbListener;
    }

    public LiveData<List<Upload>> getDbUrls(final Application application) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                AppUtils.getInstance().hidePDialog(application);
                List<Upload> uploads = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                mutableLiveData.setValue(uploads);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppUtils.getInstance().hidePDialog(application);
            }
        });

        return mutableLiveData;

    }

}
