package com.example.shms.ui.viewmodel.auth;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.User;
import com.example.shms.data.repository.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    private UserRepository repository;

    // Form fields
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<String> selectedRole = new MutableLiveData<>();

    // UI state
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> registrationComplete = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isInputValid = new MutableLiveData<>(false);

    public RegisterViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
        observeInputChanges();
    }

    private void observeInputChanges() {
        // Validate whenever any input changes
        username.observeForever(u -> validateInput());
        password.observeForever(p -> validateInput());
        confirmPassword.observeForever(cp -> validateInput());
        fullName.observeForever(fn -> validateInput());
        email.observeForever(e -> validateInput());
        phone.observeForever(p -> validateInput());
        selectedRole.observeForever(r -> validateInput());
    }

    private void validateInput() {
        boolean isValid = username.getValue() != null && !username.getValue().trim().isEmpty() &&
                password.getValue() != null && !password.getValue().trim().isEmpty() &&
                confirmPassword.getValue() != null && confirmPassword.getValue().equals(password.getValue()) &&
                fullName.getValue() != null && !fullName.getValue().trim().isEmpty() &&
                email.getValue() != null && !email.getValue().trim().isEmpty() &&
                phone.getValue() != null && !phone.getValue().trim().isEmpty() &&
                selectedRole.getValue() != null;

        isInputValid.setValue(isValid);
    }

    public void onRegisterClick() {
        if (!isInputValid.getValue()) {
            errorMessage.setValue("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        isLoading.setValue(true);

        User newUser = new User();
        newUser.setUsername(username.getValue());
        newUser.setPassword(password.getValue());
        newUser.setFullName(fullName.getValue());
        newUser.setEmail(email.getValue());
        newUser.setPhone(phone.getValue());
        newUser.setRole(selectedRole.getValue());

        // Set role flags based on selected role
        switch (selectedRole.getValue()) {
            case "DOCTOR":
                newUser.setDoctor(true);
                break;
            case "STAFF":
                newUser.setStaff(true);
                break;
            case "PATIENT":
                newUser.setPatient(true);
                break;
        }

        repository.register(newUser, new UserRepository.OnRegistrationCallback() {
            @Override
            public void onSuccess() {
                isLoading.setValue(false);
                registrationComplete.setValue(true);
            }

            @Override
            public void onFailure(String error) {
                isLoading.setValue(false);
                errorMessage.setValue(error);
            }
        });
    }

    // Getters and setters for data binding
    public MutableLiveData<String> getUsername() { return username; }
    public void setUsername(String value) { username.setValue(value); }
    public MutableLiveData<String> getPassword() { return password; }
    public void setPassword(String value) { password.setValue(value); }
    public MutableLiveData<String> getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String value) { confirmPassword.setValue(value); }
    public MutableLiveData<String> getFullName() { return fullName; }
    public void setFullName(String value) { fullName.setValue(value); }
    public MutableLiveData<String> getEmail() { return email; }
    public void setEmail(String value) { email.setValue(value); }
    public MutableLiveData<String> getPhone() { return phone; }
    public void setPhone(String value) { phone.setValue(value); }
    public MutableLiveData<String> getSelectedRole() { return selectedRole; }
    public void setSelectedRole(String value) { selectedRole.setValue(value); }

    // Getters for UI state
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<Boolean> getRegistrationComplete() { return registrationComplete; }
    public LiveData<Boolean> getIsInputValid() { return isInputValid; }

    public void clearForm() {
        username.setValue("");
        password.setValue("");
        confirmPassword.setValue("");
        fullName.setValue("");
        email.setValue("");
        phone.setValue("");
        selectedRole.setValue(null);
        errorMessage.setValue(null);
        isLoading.setValue(false);
    }
}