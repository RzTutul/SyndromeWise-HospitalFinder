package com.example.hospitalfinder.repos;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hospitalfinder.pojos.PescriptionPojo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PescriptionRespos {
    private FirebaseUser firebaseUser;
    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference pescriptionRef;
    String userID;
    public MutableLiveData<List<PescriptionPojo>> pescriptionLD = new MutableLiveData<>();
    private String DOWNLOAD_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

    public PescriptionRespos() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(firebaseUser.getUid());
        pescriptionRef = userRef.child("PescriptionList");

        userID = firebaseUser.getUid();

    }
    public void AddNewPescription(PescriptionPojo pescriptionPojo)
    {
        String pescriptionID = pescriptionPojo.getP_id();
        pescriptionPojo.setP_id(pescriptionID);
        pescriptionPojo.setUser_Id(userID);

        pescriptionRef.child(pescriptionPojo.getUser_Id()).child(pescriptionID).setValue(pescriptionPojo);
    }
    public MutableLiveData<List<PescriptionPojo>> getAllPescription(){
        pescriptionRef.child(userID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<PescriptionPojo> pescriptionPojos = new ArrayList<>();
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            pescriptionPojos.add(d.getValue(PescriptionPojo.class));
                        }
                        pescriptionLD.postValue(pescriptionPojos);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return pescriptionLD;
    }



}