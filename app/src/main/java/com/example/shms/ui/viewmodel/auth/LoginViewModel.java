package com.example.shms.ui.viewmodel.auth;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shms.data.local.entities.User;
import com.example.shms.data.repository.UserRepository;

// auth/LoginViewModel.java
public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<User> currentUser;

    public LoginViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<User> login(String username, String password) {
        return repository.getUserByUsername(username); // creater getUserByUsername in UserRepository
    }
}
