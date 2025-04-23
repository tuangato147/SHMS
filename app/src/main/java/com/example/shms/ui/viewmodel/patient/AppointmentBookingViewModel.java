package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Doctor;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.repository.DoctorRepository;
import com.example.shms.data.repository.AppointmentRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class AppointmentBookingViewModel extends BaseViewModel {
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private LiveData<List<Doctor>> doctors;
    private MutableLiveData<Doctor> selectedDoctor = new MutableLiveData<>();
    private MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private MutableLiveData<String> selectedTime = new MutableLiveData<>();
    private MutableLiveData<String> symptoms = new MutableLiveData<>();

    public AppointmentBookingViewModel(Application application) {
        super(application);
        doctorRepository = new DoctorRepository(application);
        appointmentRepository = new AppointmentRepository(application);
        doctors = doctorRepository.getAvailableDoctors();
        setupValidation();
    }

    private void setupValidation() {
        selectedDoctor.observeForever(doctor -> validateBookingForm());
        selectedDate.observeForever(date -> validateBookingForm());
        selectedTime.observeForever(time -> validateBookingForm());
        symptoms.observeForever(text -> validateBookingForm());
    }

    private void validateBookingForm() {
        boolean isValid = selectedDoctor.getValue() != null
                && selectedDate.getValue() != null && !selectedDate.getValue().isEmpty()
                && selectedTime.getValue() != null && !selectedTime.getValue().isEmpty()
                && symptoms.getValue() != null && !symptoms.getValue().trim().isEmpty();
        isDataValid.setValue(isValid);
    }

    public void bookAppointment() {
        if (!isDataValid.getValue()) {
            showError("Vui lòng điền đầy đủ thông tin");
            return;
        }

        setLoading(true);
        Appointment appointment = new Appointment();
        appointment.setPatientId(getCurrentUserId());
        appointment.setDoctorId(selectedDoctor.getValue().getId());
        appointment.setAppointmentDate(selectedDate.getValue());
        appointment.setAppointmentTime(selectedTime.getValue());
        appointment.setSymptoms(symptoms.getValue());

        appointmentRepository.bookAppointment(appointment, (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            } else {
                clearForm();
            }
        });
    }

    private void clearForm() {
        selectedDoctor.setValue(null);
        selectedDate.setValue("");
        selectedTime.setValue("");
        symptoms.setValue("");
    }

    public void checkDoctorAvailability(String date) {
        if (selectedDoctor.getValue() != null) {
            setLoading(true);
            doctorRepository.getDoctorAvailability(
                    selectedDoctor.getValue().getId(),
                    date,
                    (availableTimes, error) -> {
                        setLoading(false);
                        if (error != null) {
                            showError(error);
                        }
                    }
            );
        }
    }

    // Getters for data binding
    public LiveData<List<Doctor>> getDoctors() {
        return doctors;
    }

    public MutableLiveData<Doctor> getSelectedDoctor() {
        return selectedDoctor;
    }

    public MutableLiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public MutableLiveData<String> getSelectedTime() {
        return selectedTime;
    }

    public MutableLiveData<String> getSymptoms() {
        return symptoms;
    }
}