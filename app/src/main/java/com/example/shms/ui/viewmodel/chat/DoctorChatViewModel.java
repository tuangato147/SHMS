package com.example.shms.ui.viewmodel.chat;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.User;

public class DoctorChatViewModel extends ChatViewModel {
    private MutableLiveData<String> patientName = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedPatientId = new MutableLiveData<>();

    public DoctorChatViewModel(Application application) {
        super(application);
    }

    public void loadChat(int patientId) {
        selectedPatientId.setValue(patientId);
        messages = chatRepository.getMessagesByUsers(
                getCurrentUserId(),
                patientId
        );
    }

    public MutableLiveData<String> getPatientName() {
        return patientName;
    }

    public void setPatientName(String name) {
        patientName.setValue(name);
    }
}