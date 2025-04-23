package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.repository.AppointmentRepository;
import com.example.shms.utils.BaseViewModel;
import java.sql.Date;
import java.util.List;

public class AppointmentScheduleViewModel extends BaseViewModel {
    private AppointmentRepository appointmentRepository;
    private MutableLiveData<Date> selectedDate = new MutableLiveData<>();
    private LiveData<List<Appointment>> appointments;

    public AppointmentScheduleViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        loadAppointments();
    }

    private void loadAppointments() {
        int patientId = getCurrentUserId();
        appointments = appointmentRepository.getAppointmentsByPatient(patientId);
    }

    public void cancelAppointment(Appointment appointment) {
        setLoading(true);
        appointmentRepository.cancelAppointment(appointment, "Hủy bởi bệnh nhân", (success, message) -> {
            setLoading(false);
            if (!success) {
                showError(message);
            }
        });
    }

    public LiveData<List<Appointment>> getAppointments() {
        return appointments;
    }
}