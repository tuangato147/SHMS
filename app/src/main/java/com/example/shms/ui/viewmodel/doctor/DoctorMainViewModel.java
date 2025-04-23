package com.example.shms.ui.viewmodel.doctor;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.data.repository.AppointmentRepository;

public class DoctorMainViewModel extends BaseViewModel {
    private AppointmentRepository appointmentRepository;
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<Integer> pendingAppointments = new MutableLiveData<>(0);
    private MutableLiveData<Integer> unreadMessages = new MutableLiveData<>(0);

    public DoctorMainViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        loadPendingAppointments();
    }

    private void loadPendingAppointments() {
        setLoading(true);
        appointmentRepository.getPendingAppointments().observeForever(appointments -> {
            pendingAppointments.setValue(appointments.size());
            setLoading(false);
        });
    }

    // Navigation methods
    public void onProfileClick() {
        // Navigate to doctor profile
    }

    public void onScheduleClick() {
        // Navigate to schedule
    }

    public void onPatientMonitoringClick() {
        // Navigate to patient monitoring
    }

    public void onPrescriptionClick() {
        // Navigate to prescription management
    }
}