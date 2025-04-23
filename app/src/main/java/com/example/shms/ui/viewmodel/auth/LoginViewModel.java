package com.example.shms.ui.viewmodel.auth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.User;
import com.example.shms.data.repository.UserRepository;
import com.example.shms.utils.Constants;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<User> currentUser;

    // Two-way databinding for login form
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();

    // UI state LiveData
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isInputValid = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> rememberMe = new MutableLiveData<>(false);

    public LoginViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);

        // Observe username and password changes to validate input
        observeInputChanges();
    }

    private void observeInputChanges() {
        username.observeForever(username -> validateInput());
        password.observeForever(password -> validateInput());
    }

    private void validateInput() {
        String usernameStr = username.getValue();
        String passwordStr = password.getValue();
        boolean isValid = usernameStr != null && !usernameStr.trim().isEmpty()
                && passwordStr != null && !passwordStr.trim().isEmpty();
        isInputValid.setValue(isValid);
    }

    // Data binding getters and setters
    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username.setValue(value);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password.setValue(value);
    }

    public LiveData<Boolean> getIsInputValid() {
        return isInputValid;
    }

    public MutableLiveData<Boolean> getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean value) {
        rememberMe.setValue(value);
    }

    // Click handlers for buttons
    public void onLoginClick() {
        if (!isInputValid.getValue()) {
            errorMessage.setValue("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        isLoading.setValue(true);
        String usernameStr = username.getValue();
        String passwordStr = password.getValue();

        LiveData<User> loginResult = repository.login(usernameStr, passwordStr);
        loginResult.observeForever(user -> {
            isLoading.setValue(false);
            if (user != null) {
                currentUser = loginResult;
                isLoggedIn.setValue(true);
                saveLoginState();
            } else {
                errorMessage.setValue("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        });
    }

    public void onRoleSelectionClick() {
        // Implement role selection logic here
        // This should probably show a dialog or navigate to role selection screen
    }

    private void saveLoginState() {
        if (rememberMe.getValue()) {
            // Save login state to SharedPreferences
            // You'll need to implement this
        }
    }

    // Getters for UI state
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    // Utility methods
    public void clearForm() {
        username.setValue("");
        password.setValue("");
        errorMessage.setValue(null);
        isLoading.setValue(false);
        rememberMe.setValue(false);
    }

    public void logout() {
        currentUser = null;
        isLoggedIn.setValue(false);
        clearForm();
        // Clear saved login state if any
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Clean up any observers
    }
}