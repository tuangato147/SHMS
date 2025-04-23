package com.example.shms.ui.viewmodel.patient;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Notification;
import com.example.shms.data.repository.NotificationRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class NotificationViewModel extends BaseViewModel {
    private NotificationRepository repository;
    private LiveData<List<Notification>> notifications;
    private MutableLiveData<Boolean> showUnreadOnly = new MutableLiveData<>(false);

    public NotificationViewModel(Application application) {
        super(application);
        repository = new NotificationRepository(application);
        loadNotifications();
    }

    private void loadNotifications() {
        int patientId = getCurrentUserId();
        boolean unreadOnly = showUnreadOnly.getValue() != null && showUnreadOnly.getValue();
        notifications = repository.getPatientNotifications(patientId, unreadOnly);
    }

    public void markAsRead(Notification notification) {
        repository.markNotificationAsRead(notification.getId(), (success, error) -> {
            if (!success) {
                showError(error);
            }
        });
    }

    public void markAllAsRead() {
        setLoading(true);
        repository.markAllNotificationsAsRead(getCurrentUserId(), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    public void deleteNotification(Notification notification) {
        repository.deleteNotification(notification.getId(), (success, error) -> {
            if (!success) {
                showError(error);
            }
        });
    }

    public void toggleUnreadFilter() {
        Boolean currentValue = showUnreadOnly.getValue();
        showUnreadOnly.setValue(currentValue == null ? true : !currentValue);
        loadNotifications();
    }

    public void refreshNotifications() {
        setLoading(true);
        repository.syncNotifications(getCurrentUserId(), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    // Getters for data binding
    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public MutableLiveData<Boolean> getShowUnreadOnly() {
        return showUnreadOnly;
    }

    public int getUnreadCount() {
        List<Notification> notificationList = notifications.getValue();
        if (notificationList == null) return 0;

        return (int) notificationList.stream()
                .filter(notification -> !notification.isRead())
                .count();
    }
}