package com.example.hospitalfinder.repos;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hospitalfinder.pojos.DonnerPojo;
import com.example.hospitalfinder.viewmodel.RegistrationViewModel;
import com.example.hospitalfinder.pojos.userInformationPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginRepository {

    private MutableLiveData<RegistrationViewModel.AuthenticationState> stateLiveData;
   private MutableLiveData<String> errMsg = new MutableLiveData<>();
   private MutableLiveData<List<DonnerPojo>> donnerLD = new MutableLiveData<>();
    public MutableLiveData<userInformationPojo> userInfoLD = new MutableLiveData<>();


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootRef;
    DatabaseReference userRef;
    DatabaseReference userInfo;
    DatabaseReference donnerList;

    public LoginRepository(MutableLiveData<RegistrationViewModel.AuthenticationState> stateLiveData) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        this.stateLiveData = stateLiveData;
    }
    public MutableLiveData<RegistrationViewModel.AuthenticationState> RegisterFireBaseUser(final userInformationPojo userInformationPojo) {

        //RegistrationFragment.registerProgressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(userInformationPojo.getUserEmail(), userInformationPojo.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    firebaseUser = firebaseAuth.getCurrentUser();
                    stateLiveData.postValue(RegistrationViewModel.AuthenticationState.AUTHENTICATED);
                    Log.i(TAG, "onComplete: "+firebaseUser.getUid());

                    rootRef = FirebaseDatabase.getInstance().getReference();
                    userRef = rootRef.child(firebaseUser.getUid());
                    userInfo = userRef.child("Loginfo");

                    String userId = firebaseUser.getUid();
                    userInformationPojo.setUesrID(userId);

                    userInfo.setValue(userInformationPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    });
                }
                else {
                   // Toast.makeText(LoginFragment.context, "Register Fail", Toast.LENGTH_SHORT).show();
                   /// Toast.makeText(LoginFragment.context, "Check your Internet Connection!", Toast.LENGTH_LONG).show();
                    //RegistrationFragment.registerProgressBar.setVisibility(View.GONE);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                stateLiveData.postValue(RegistrationViewModel.AuthenticationState.UNAUTHENTICATED);
                errMsg.postValue(e.getLocalizedMessage());

            }
        });

        return stateLiveData;
    }


    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public MutableLiveData<RegistrationViewModel.AuthenticationState> loginFirebaseUser(String email, String pass) {

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                firebaseUser = firebaseAuth.getCurrentUser();
                stateLiveData.postValue(RegistrationViewModel.AuthenticationState.AUTHENTICATED);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                stateLiveData.postValue(RegistrationViewModel.AuthenticationState.UNAUTHENTICATED);
                errMsg.postValue(e.getLocalizedMessage());
            }
        });
        return stateLiveData;
    }

    public MutableLiveData<String> getErrMsg()
    {
        return errMsg;
    }

    public void addDonnerTOFirebase(DonnerPojo donnerPojo) {
        rootRef = FirebaseDatabase.getInstance().getReference();
        donnerList = rootRef.child("DonnerList");

        String donner_id= donnerList.push().getKey();
        donnerPojo.setDonnerID(donner_id);
        donnerList.child(donner_id).setValue(donnerPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "onSuccess: "+donner_id);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "onFailure: "+e.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<List<DonnerPojo>> getDonnerList() {
        rootRef = FirebaseDatabase.getInstance().getReference();
        donnerList = rootRef.child("DonnerList");
        donnerList.keepSynced(true);

        donnerList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<DonnerPojo> donnerPojoList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    donnerPojoList.add(dataSnapshot1.getValue(DonnerPojo.class));

                }
                donnerLD.postValue(donnerPojoList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return donnerLD;
    }


    public MutableLiveData<userInformationPojo> getUserInfo() {
        rootRef = FirebaseDatabase.getInstance().getReference();

        userInfo = rootRef.child(firebaseUser.getUid()).child("Loginfo");
        userInfo.keepSynced(true);

        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               userInformationPojo userInformationPojo = new userInformationPojo();

                    userInformationPojo = (dataSnapshot.getValue(userInformationPojo.class));

                userInfoLD.postValue(userInformationPojo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userInfoLD;
    }
}
