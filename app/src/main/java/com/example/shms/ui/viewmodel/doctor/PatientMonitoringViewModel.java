package com.example.shms.ui.viewmodel.doctor;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.entities.Patient;
import com.example.shms.data.repository.AppointmentRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class PatientMonitoringViewModel extends BaseViewModel {
    private AppointmentRepository appointmentRepository;
    private LiveData<List<Patient>> monitoredPatients;

    public PatientMonitoringViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        loadMonitoredPatients();
    }

    private void loadMonitoredPatients() {
        int doctorId = getCurrentUserId();
        monitoredPatients = appointmentRepository.getMonitoredPatients(doctorId);
    }

    public void stopMonitoring(int patientId) {
        setLoading(true);
        appointmentRepository.toggleMonitoring(patientId, false, (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    public LiveData<List<Patient>> getMonitoredPatients() {
        return monitoredPatients;
    }
}