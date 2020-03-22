package com.example.hospitalfinder.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.hospitalfinder.viewmodel.RegistrationViewModel;
import com.example.hospitalfinder.pojos.userInformationPojo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginRepository {

    private MutableLiveData<RegistrationViewModel.AuthenticationState> stateLiveData;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootRef;
    DatabaseReference userRef;
    DatabaseReference userInfo;

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
                          //  RegistrationFragment.registerProgressBar.setVisibility(View.GONE);
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
                //errMsg.postValue(e.getLocalizedMessage());

            }
        });

        return stateLiveData;
    }


    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }
}
