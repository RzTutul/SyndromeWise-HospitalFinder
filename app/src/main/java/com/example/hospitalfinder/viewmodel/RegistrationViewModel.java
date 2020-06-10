package com.example.hospitalfinder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hospitalfinder.pojos.DonnerPojo;
import com.example.hospitalfinder.pojos.userInformationPojo;
import com.example.hospitalfinder.repos.LoginRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RegistrationViewModel extends ViewModel {
    private LoginRepository firebaseLoginRepository;
    public MutableLiveData<AuthenticationState> stateLiveData;
    public MutableLiveData<String> errMsg = new MutableLiveData<>();
    public MutableLiveData<userInformationPojo> userInfoLD = new MutableLiveData<>();
    public MutableLiveData<List<DonnerPojo>> donnerLD = new MutableLiveData<>();



    public enum AuthenticationState
    {
        AUTHENTICATED,
        UNAUTHENTICATED
    }
    public RegistrationViewModel() {
        stateLiveData = new MutableLiveData<>();


        firebaseLoginRepository = new LoginRepository(stateLiveData);
        errMsg = firebaseLoginRepository.getErrMsg();
        // errMsg = firebaseLoginRepository.getErrMsg();

        if (firebaseLoginRepository.getFirebaseUser() != null)
        {
            stateLiveData.postValue(AuthenticationState.AUTHENTICATED);

        }
        else
        {
            stateLiveData.postValue(AuthenticationState.UNAUTHENTICATED);
        }

    }


    public void loginUser(String email, String pass) {

        stateLiveData =firebaseLoginRepository.loginFirebaseUser(email,pass);
    }
    public void register(userInformationPojo userInformationPojo)
    {
        stateLiveData =  firebaseLoginRepository.RegisterFireBaseUser(userInformationPojo);

    }
    public void getLogoutUser()
    {
        FirebaseAuth.getInstance().signOut();
        stateLiveData.postValue(AuthenticationState.UNAUTHENTICATED);
    }
    public void AddDonnerList(DonnerPojo donnerPojo) {

       firebaseLoginRepository.addDonnerTOFirebase(donnerPojo);

    }


    public void getDonnerList() {
        donnerLD = firebaseLoginRepository.getDonnerList();
    }


}
