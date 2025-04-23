package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.MedicalRecord;
import com.example.shms.data.repository.MedicalRecordRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class MedicalHistoryViewModel extends BaseViewModel {
    private MedicalRecordRepository repository;
    private LiveData<List<MedicalRecord>> medicalRecords;
    private MutableLiveData<String> startDate = new MutableLiveData<>();
    private MutableLiveData<String> endDate = new MutableLiveData<>();
    private MutableLiveData<String> selectedCategory = new MutableLiveData<>("ALL");

    public MedicalHistoryViewModel(Application application) {
        super(application);
        repository = new MedicalRecordRepository(application);
        loadMedicalRecords();
    }

    private void loadMedicalRecords() {
        int patientId = getCurrentUserId();
        String category = selectedCategory.getValue();
        String start = startDate.getValue();
        String end = endDate.getValue();

        if ("ALL".equals(category)) {
            medicalRecords = repository.getAllMedicalRecords(patientId, start, end);
        } else {
            medicalRecords = repository.getMedicalRecordsByCategory(patientId, category, start, end);
        }
    }

    public void setDateRange(String start, String end) {
        startDate.setValue(start);
        endDate.setValue(end);
        loadMedicalRecords();
    }

    public void onCategorySelected(String category) {
        selectedCategory.setValue(category);
        loadMedicalRecords();
    }

    public void requestMedicalCertificate(MedicalRecord record) {
        setLoading(true);
        repository.requestMedicalCertificate(record.getId(), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    // Getters for data binding
    public LiveData<List<MedicalRecord>> getMedicalRecords() {
        return medicalRecords;
    }

    public MutableLiveData<String> getStartDate() {
        return startDate;
    }

    public MutableLiveData<String> getEndDate() {
        return endDate;
    }

    public MutableLiveData<String> getSelectedCategory() {
        return selectedCategory;
    }
}