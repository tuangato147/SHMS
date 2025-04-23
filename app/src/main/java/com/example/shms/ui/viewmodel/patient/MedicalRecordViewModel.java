package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.MedicalRecord;
import com.example.shms.data.repository.MedicalRecordRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class MedicalRecordViewModel extends BaseViewModel {
    private MedicalRecordRepository repository;
    private LiveData<List<MedicalRecord>> medicalRecords;
    private MutableLiveData<MedicalRecord> selectedRecord = new MutableLiveData<>();

    public MedicalRecordViewModel(Application application) {
        super(application);
        repository = new MedicalRecordRepository(application);
        loadMedicalRecords();
    }

    private void loadMedicalRecords() {
        int patientId = getCurrentUserId();
        medicalRecords = repository.getPatientMedicalRecords(patientId);
    }

    public void selectRecord(MedicalRecord record) {
        selectedRecord.setValue(record);
    }

    // Getters for data binding
    public LiveData<List<MedicalRecord>> getMedicalRecords() {
        return medicalRecords;
    }

    public MutableLiveData<MedicalRecord> getSelectedRecord() {
        return selectedRecord;
    }
}