package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Prescription;
import com.example.shms.data.local.entities.PrescriptionDetail;
import com.example.shms.data.repository.PrescriptionRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class PrescriptionViewModel extends BaseViewModel {
    private PrescriptionRepository repository;
    private LiveData<List<Prescription>> prescriptions;
    private MutableLiveData<Prescription> selectedPrescription = new MutableLiveData<>();
    private LiveData<List<PrescriptionDetail>> prescriptionDetails;
    private MutableLiveData<String> startDate = new MutableLiveData<>();
    private MutableLiveData<String> endDate = new MutableLiveData<>();

    public PrescriptionViewModel(Application application) {
        super(application);
        repository = new PrescriptionRepository(application);
        loadPrescriptions();
    }

    private void loadPrescriptions() {
        int patientId = getCurrentUserId();
        String start = startDate.getValue();
        String end = endDate.getValue();
        prescriptions = repository.getPatientPrescriptions(patientId, start, end);
    }

    public void setDateRange(String start, String end) {
        startDate.setValue(start);
        endDate.setValue(end);
        loadPrescriptions();
    }

    public void selectPrescription(Prescription prescription) {
        selectedPrescription.setValue(prescription);
        loadPrescriptionDetails(prescription.getId());
    }

    private void loadPrescriptionDetails(int prescriptionId) {
        prescriptionDetails = repository.getPrescriptionDetails(prescriptionId);
    }

    public void requestRefill(Prescription prescription) {
        setLoading(true);
        repository.requestPrescriptionRefill(prescription.getId(), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    public void downloadPrescription(Prescription prescription) {
        setLoading(true);
        repository.downloadPrescriptionPDF(prescription.getId(), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    // Getters for data binding
    public LiveData<List<Prescription>> getPrescriptions() {
        return prescriptions;
    }

    public MutableLiveData<Prescription> getSelectedPrescription() {
        return selectedPrescription;
    }

    public LiveData<List<PrescriptionDetail>> getPrescriptionDetails() {
        return prescriptionDetails;
    }

    public MutableLiveData<String> getStartDate() {
        return startDate;
    }

    public MutableLiveData<String> getEndDate() {
        return endDate;
    }
}