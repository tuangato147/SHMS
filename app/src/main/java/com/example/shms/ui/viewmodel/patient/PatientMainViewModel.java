package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import android.widget.ArrayAdapter;
import com.example.shms.utils.BaseViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PatientMainViewModel extends BaseViewModel {
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<Boolean> hasNewMessages = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasUpcomingAppointments = new MutableLiveData<>(false);

    private AppointmentRepository appointmentRepository;
    private ChatRepository chatRepository;

    public PatientMainViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        chatRepository = new ChatRepository(application);
        checkNewMessages();
        checkUpcomingAppointments();
    }

    private void checkNewMessages() {
        int patientId = getCurrentUserId();
        chatRepository.getUnreadMessageCount(patientId).observeForever(count ->
                hasNewMessages.setValue(count > 0)
        );
    }

    private void checkUpcomingAppointments() {
        int patientId = getCurrentUserId();
        appointmentRepository.getUpcomingAppointments(patientId).observeForever(appointments ->
                hasUpcomingAppointments.setValue(!appointments.isEmpty())
        );
    }

    // Navigation methods
    public void onProfileClick() {
        // Navigate to profile
    }

    public void onAppointmentClick() {
        // Navigate to appointment booking
    }

    public void onHistoryClick() {
        // Navigate to medical history
    }

    public void onPrescriptionClick() {
        // Navigate to prescriptions
    }

    public void onChatClick() {
        // Navigate to chat
    }

    // Getters for data binding
    public MutableLiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public LiveData<Boolean> getHasNewMessages() {
        return hasNewMessages;
    }

    public LiveData<Boolean> getHasUpcomingAppointments() {
        return hasUpcomingAppointments;
    }
}