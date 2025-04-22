package com.example.shms.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shms.data.local.entities.User;
import com.example.shms.data.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LoginViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<User> login(String username, String password) {
        isLoading.setValue(true);
        return Transformations.map(repository.login(username, password), user -> {
            isLoading.setValue(false);
            if (user == null) {
                errorMessage.setValue("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.");
            }
            return user;
        });
    }

    public LiveData<User> loginWithRole(String username, String password, String role) {
        isLoading.setValue(true);
        return Transformations.map(repository.loginWithRole(username, password, role), user -> {
            isLoading.setValue(false);
            if (user == null) {
                errorMessage.setValue("Đăng nhập thất bại. Vui lòng kiểm tra lại quyền truy cập.");
            }
            return user;
        });
    }

    public void register(User user) {
        isLoading.setValue(true);
        repository.insert(user);
        isLoading.setValue(false);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
