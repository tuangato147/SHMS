package com.example.shms.ui.viewmodel.doctor;

import android.app.Application;
import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.repository.DoctorRepository;
import com.example.shms.utils.BaseViewModel;

public class DoctorProfileViewModel extends BaseViewModel {
    private DoctorRepository repository;

    // Form fields
    private MutableLiveData<String> fullName = new MutableLiveData<>();
    private MutableLiveData<String> specialization = new MutableLiveData<>();
    private MutableLiveData<String> experience = new MutableLiveData<>();
    private MutableLiveData<String> personalId = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<Uri> avatarUri = new MutableLiveData<>();

    // UI state
    private MutableLiveData<Boolean> isEditing = new MutableLiveData<>(false);

    public DoctorProfileViewModel(Application application) {
        super(application);
        repository = new DoctorRepository(application);
        loadProfile();
    }

    private void loadProfile() {
        int doctorId = getCurrentUserId();
        repository.getDoctorById(doctorId).observeForever(doctor -> {
            if (doctor != null) {
                updateFields(doctor);
            }
        });
    }

    private void updateFields(Doctor doctor) {
        fullName.setValue(doctor.getFullName());
        specialization.setValue(doctor.getSpecialization());
        experience.setValue(doctor.getExperience());
        personalId.setValue(doctor.getPersonalId());
        phone.setValue(doctor.getPhone());
        address.setValue(doctor.getAddress());
        if (doctor.getAvatarUri() != null) {
            avatarUri.setValue(Uri.parse(doctor.getAvatarUri()));
        }
    }

    public void onEditClick() {
        isEditing.setValue(true);
    }

    public void onSaveClick() {
        if (!validateInput()) {
            return;
        }

        setLoading(true);
        Doctor doctor = new Doctor();
        doctor.setId(getCurrentUserId());
        doctor.setFullName(fullName.getValue());
        doctor.setSpecialization(specialization.getValue());
        doctor.setExperience(experience.getValue());
        doctor.setPhone(phone.getValue());
        doctor.setAddress(address.getValue());
        if (avatarUri.getValue() != null) {
            doctor.setAvatarUri(avatarUri.getValue().toString());
        }

        repository.update(doctor, (success, error) -> {
            setLoading(false);
            if (success) {
                isEditing.setValue(false);
            } else {
                showError(error);
            }
        });
    }

    private boolean validateInput() {
        if (fullName.getValue() == null || fullName.getValue().trim().isEmpty()) {
            showError("Vui lòng nhập họ tên");
            return false;
        }
        if (specialization.getValue() == null || specialization.getValue().trim().isEmpty()) {
            showError("Vui lòng chọn chuyên khoa");
            return false;
        }
        return true;
    }

    // Getters and setters for data binding
    public MutableLiveData<String> getFullName() { return fullName; }
    public MutableLiveData<String> getSpecialization() { return specialization; }
    public MutableLiveData<String> getExperience() { return experience; }
    public MutableLiveData<String> getPersonalId() { return personalId; }
    public MutableLiveData<String> getPhone() { return phone; }
    public MutableLiveData<String> getAddress() { return address; }
    public MutableLiveData<Uri> getAvatarUri() { return avatarUri; }
    public MutableLiveData<Boolean> getIsEditing() { return isEditing; }
}