package com.example.shms.ui.viewmodel.doctor;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.repository.AppointmentRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;
import java.util.Date;

public class DoctorScheduleViewModel extends BaseViewModel {
    private AppointmentRepository appointmentRepository;
    private MutableLiveData<Date> selectedDate = new MutableLiveData<>();
    private LiveData<List<Appointment>> pendingAppointments;
    private LiveData<List<Appointment>> approvedAppointments;
    private LiveData<List<Appointment>> weeklySchedule;

    public DoctorScheduleViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        selectedDate.setValue(new Date()); // Set to current date
        loadAppointments();
    }

    private void loadAppointments() {
        pendingAppointments = appointmentRepository.getPendingAppointments();
        approvedAppointments = appointmentRepository.getApprovedAppointments();
        weeklySchedule = appointmentRepository.getWeeklySchedule(selectedDate.getValue());
    }

    public void approveAppointment(Appointment appointment) {
        setLoading(true);
        appointmentRepository.approveAppointment(appointment, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể xác nhận lịch hẹn");
            }
        });
    }

    public void monitorPatient(Appointment appointment) {
        appointmentRepository.addToMonitoring(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        setLoading(true);
        appointmentRepository.cancelAppointment(appointment, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể hủy lịch hẹn");
            }
        });
    }

    // Getters for LiveData
    public LiveData<List<Appointment>> getPendingAppointments() {
        return pendingAppointments;
    }

    public LiveData<List<Appointment>> getApprovedAppointments() {
        return approvedAppointments;
    }

    public LiveData<List<Appointment>> getWeeklySchedule() {
        return weeklySchedule;
    }
}