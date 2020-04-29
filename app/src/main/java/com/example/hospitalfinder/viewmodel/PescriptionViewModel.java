package com.example.hospitalfinder.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalfinder.pojos.PescriptionPojo;
import com.example.hospitalfinder.repos.PescriptionRespos;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PescriptionViewModel extends ViewModel {

    private PescriptionRespos  pescriptionRespos;
    public MutableLiveData<List<PescriptionPojo>> pescriptionLD = new MutableLiveData<>();

    public PescriptionViewModel() {
        pescriptionRespos = new PescriptionRespos();
    }

    public void uploadPescriptionToFirebaseStorage(final Context context, File file, final PescriptionPojo pescriptionPojo) {
/*
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("EventImages/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
*/
        //MomentGallary.uploadProgressBar.setVisibility(View.VISIBLE);
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Wait Image Uploading...");
        pd.show();

        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("PescriptionImages/" + fileUri.getLastPathSegment());

        ///For image Compress
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(),fileUri);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);


        //For get URI Link of Image

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL

                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                    Uri downloadUri = task.getResult();
                    pescriptionPojo.setPesriptionImage(downloadUri.toString());
                    pescriptionRespos.AddNewPescription(pescriptionPojo);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    public void getAllPescription(){

        pescriptionLD = pescriptionRespos.getAllPescription();
    }

}
