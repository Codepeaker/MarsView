package com.codepeaker.marsview.repo;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.codepeaker.marsview.repo.model.Upload;
import com.codepeaker.marsview.utils.AppUtils;
import com.codepeaker.marsview.utils.Constants;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;

public class UploadService {

    private UploadService() {
    }

    private static UploadService uploadService;

    public static synchronized UploadService getInstance() {
        if (uploadService == null) {
            uploadService = new UploadService();
        }
        return uploadService;
    }

    public void uploadFile(final Context context, Uri croppedFilePath) {
        AppUtils.getInstance().showPDialog(context, "Uploading");

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference sRef = storageReference.child(Constants.STORAGE_PATH
                + "Image_" + System.currentTimeMillis() + "." + getFileExtension(context, croppedFilePath));
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH);
        sRef.putFile(croppedFilePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot
                , Task<Uri>>() {

            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return sRef.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                AppUtils.getInstance().hidePDialog(context);
                if (task.isSuccessful()) {
                    Toast.makeText(context, "File Uploaded ", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(task.getResult().toString());
                    //adding an upload to firebase database
                    String uploadId = mDatabase.push().getKey();
                    mDatabase.child(uploadId).setValue(upload);
                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                AppUtils.getInstance().hidePDialog(context);
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getFileExtension(Context context, Uri uri) {
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
