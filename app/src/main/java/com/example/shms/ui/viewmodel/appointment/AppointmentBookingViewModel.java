package com.example.shms.ui.viewmodel.appointment;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Appointment;
import com.example.shms.data.repository.AppointmentRepository;
import com.example.shms.data.repository.DoctorRepository;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.utils.Constants;
import java.sql.Date;
import java.util.Calendar;

public class AppointmentBookingViewModel extends BaseViewModel {
    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;

    // Form fields
    private MutableLiveData<String> selectedDepartment = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedDoctorId = new MutableLiveData<>();
    private MutableLiveData<Date> selectedDate = new MutableLiveData<>();
    private MutableLiveData<String> medicalHistory = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<String> selectedRoom = new MutableLiveData<>();
    private MutableLiveData<String> selectedBed = new MutableLiveData<>();

    // States
    private MutableLiveData<Boolean> isDateValid = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isDoctorSelected = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isRoomSelected = new MutableLiveData<>(false);

    public AppointmentBookingViewModel(Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        doctorRepository = new DoctorRepository(application);

        // Validate date selection
        selectedDate.observeForever(date -> {
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Calendar today = Calendar.getInstance();
                isDateValid.setValue(calendar.after(today));
            }
        });
    }

    public void onDepartmentSelected(String department) {
        selectedDepartment.setValue(department);
        // Load doctors for selected department
        loadDoctorsForDepartment(department);
    }

    public void bookAppointment(int patientId) {
        if (!validateBooking()) {
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(selectedDoctorId.getValue());
        appointment.setAppointmentDate(selectedDate.getValue());
        appointment.setDepartment(selectedDepartment.getValue());
        appointment.setRoomNumber(selectedRoom.getValue());
        appointment.setBedNumber(selectedBed.getValue());
        appointment.setMedicalHistory(medicalHistory.getValue());
        appointment.setDescription(description.getValue());
        appointment.setStatus("PENDING");
        appointment.setCreatedAt(new Date(System.currentTimeMillis()));

        setLoading(true);
        appointmentRepository.insert(appointment, (success, errorMessage) -> {
            setLoading(false);
            if (!success) {
                showError(errorMessage);
            }
        });
    }

    private boolean validateBooking() {
        if (selectedDepartment.getValue() == null) {
            showError("Vui lòng chọn khoa");
            return false;
        }
        if (selectedDoctorId.getValue() == null) {
            showError("Vui lòng chọn bác sĩ");
            return false;
        }
        if (!isDateValid.getValue()) {
            showError("Ngày không hợp lệ");
            return false;
        }
        if (selectedRoom.getValue() == null || selectedBed.getValue() == null) {
            showError("Vui lòng chọn phòng và giường");
            return false;
        }
        return true;
    }

    // Getters and Setters for data binding
    public MutableLiveData<String> getSelectedDepartment() {
        return selectedDepartment;
    }

    public MutableLiveData<Date> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date date) {
        selectedDate.setValue(date);
    }

    // ... other getters and setters
}