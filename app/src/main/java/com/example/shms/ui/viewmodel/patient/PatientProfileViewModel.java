package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Patient;
import com.example.shms.data.repository.PatientRepository;
import com.example.shms.utils.BaseViewModel;

public class PatientProfileViewModel extends BaseViewModel {
    private PatientRepository repository;
    private LiveData<Patient> patientData;
    private MutableLiveData<String> newPassword = new MutableLiveData<>();
    private MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);

    public PatientProfileViewModel(Application application) {
        super(application);
        repository = new PatientRepository(application);
        loadPatientData();
    }

    private void loadPatientData() {
        int patientId = getCurrentUserId();
        patientData = repository.getPatientData(patientId);
    }

    public void updateProfile(Patient updatedProfile) {
        if (!validateProfile(updatedProfile)) {
            return;
        }

        setLoading(true);
        repository.updatePatientProfile(updatedProfile, (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    public void changePassword() {
        String newPass = newPassword.getValue();
        String confirmPass = confirmPassword.getValue();

        if (!validatePasswords(newPass, confirmPass)) {
            return;
        }

        setLoading(true);
        repository.changePassword(getCurrentUserId(), newPass, (success, error) -> {
            setLoading(false);
            if (success) {
                clearPasswordFields();
            } else {
                showError(error);
            }
        });
    }

    private boolean validateProfile(Patient profile) {
        if (profile.getPhoneNumber() == null || profile.getPhoneNumber().trim().isEmpty()) {
            showError("Số điện thoại không được để trống");
            return false;
        }
        if (profile.getAddress() == null || profile.getAddress().trim().isEmpty()) {
            showError("Địa chỉ không được để trống");
            return false;
        }
        return true;
    }

    private boolean validatePasswords(String newPass, String confirmPass) {
        if (newPass == null || newPass.trim().isEmpty()) {
            showError("Mật khẩu mới không được để trống");
            return false;
        }
        if (!newPass.equals(confirmPass)) {
            showError("Mật khẩu xác nhận không khớp");
            return false;
        }
        if (newPass.length() < 6) {
            showError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        return true;
    }

    private void clearPasswordFields() {
        newPassword.setValue("");
        confirmPassword.setValue("");
    }

    public void toggleEditMode() {
        isEditMode.setValue(!isEditMode.getValue());
    }

    // Getters for data binding
    public LiveData<Patient> getPatientData() {
        return patientData;
    }

    public MutableLiveData<String> getNewPassword() {
        return newPassword;
    }

    public MutableLiveData<String> getConfirmPassword() {
        return confirmPassword;
    }

    public MutableLiveData<Boolean> getIsEditMode() {
        return isEditMode;
    }
}