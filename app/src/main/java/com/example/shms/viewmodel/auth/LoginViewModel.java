package com.example.shms.viewmodel.auth;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.shms.data.repository.UserRepository;

// auth/LoginViewModel.java
public class LoginViewModel extends AndroidViewModel {

    public LoginViewModel(Application application) {
        super(application);
        UserRepository userRepository = new UserRepository(application);
    }
}
