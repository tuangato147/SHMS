package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.AppointmentDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Appointment;
import java.sql.Date;
import java.util.List;

public class AppointmentRepository {
    private AppointmentDao appointmentDao;
    private LiveData<List<Appointment>> allAppointments;

    public AppointmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        appointmentDao = db.appointmentDao();
        allAppointments = appointmentDao.getAllAppointments();
    }

    // Existing methods
    public LiveData<List<Appointment>> getAppointmentsByPatient(int patientId) {
        return appointmentDao.getAppointmentsByPatient(patientId);
    }

    public LiveData<List<Appointment>> getAppointmentsByDoctor(int doctorId) {
        return appointmentDao.getAppointmentsByDoctor(doctorId);
    }

    public LiveData<List<Appointment>> getAppointmentsBetweenDates(Date startDate, Date endDate) {
        return appointmentDao.getAppointmentsBetweenDates(startDate, endDate);
    }

    // New methods for appointment management
    public LiveData<List<Appointment>> getPendingAppointments(int doctorId) {
        return appointmentDao.getPendingAppointmentsByDoctor(doctorId);
    }

    public LiveData<List<Appointment>> getApprovedAppointments(int doctorId) {
        return appointmentDao.getApprovedAppointmentsByDoctor(doctorId);
    }

    public LiveData<List<Appointment>> getMonitoredPatients(int doctorId) {
        return appointmentDao.getMonitoredPatientsByDoctor(doctorId);
    }

    public void insert(Appointment appointment, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // Check for time slot conflicts
                boolean hasConflict = appointmentDao.checkTimeSlotConflict(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getStartTime(),
                        appointment.getEndTime()
                );

                if (hasConflict) {
                    callback.onComplete(false, "Khung giờ này đã có lịch hẹn");
                    return;
                }

                // Insert appointment
                long id = appointmentDao.insert(appointment);
                callback.onComplete(id > 0, id > 0 ? null : "Không thể tạo lịch hẹn");
            } catch (Exception e) {
                callback.onComplete(false, "Lỗi: " + e.getMessage());
            }
        });
    }

    public void update(Appointment appointment, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                appointmentDao.update(appointment);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, "Lỗi: " + e.getMessage());
            }
        });
    }

    public void approveAppointment(Appointment appointment, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                appointment.setStatus("APPROVED");
                appointment.setApprovedAt(new Date(System.currentTimeMillis()));
                appointmentDao.update(appointment);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, "Không thể xác nhận lịch hẹn");
            }
        });
    }

    public void cancelAppointment(Appointment appointment, String reason, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                appointment.setStatus("CANCELLED");
                appointment.setCancelReason(reason);
                appointment.setCancelledAt(new Date(System.currentTimeMillis()));
                appointmentDao.update(appointment);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, "Không thể hủy lịch hẹn");
            }
        });
    }

    public void completeAppointment(Appointment appointment, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                appointment.setStatus("COMPLETED");
                appointment.setCompletedAt(new Date(System.currentTimeMillis()));
                appointmentDao.update(appointment);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, "Không thể hoàn thành lịch hẹn");
            }
        });
    }

    public void toggleMonitoring(Appointment appointment, boolean monitored, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                appointment.setMonitored(monitored);
                appointmentDao.update(appointment);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, "Không thể cập nhật trạng thái theo dõi");
            }
        });
    }

    public interface OnCompleteCallback {
        void onComplete(boolean success, String errorMessage);
    }
}