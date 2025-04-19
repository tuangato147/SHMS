package com.example.shms.viewmodel.auth;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.shms.data.local.entities.User;
import com.example.shms.data.repository.UserRepository;

// auth/LoginViewModel.java
public class LoginViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<User> userLiveData;

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        userLiveData = new MutableLiveData<>();
    }
}
