package com.example.shms.ui.viewmodel.staff;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Room;
import com.example.shms.data.repository.RoomRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class RoomManagementViewModel extends BaseViewModel {
    private RoomRepository repository;
    private LiveData<List<Room>> rooms;
    private MutableLiveData<String> selectedDepartment = new MutableLiveData<>();
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    public RoomManagementViewModel(Application application) {
        super(application);
        repository = new RoomRepository(application);
        rooms = repository.getAllRooms();
    }

    public void filterByDepartment(String department) {
        selectedDepartment.setValue(department);
        if (department != null && !department.isEmpty()) {
            rooms = repository.getRoomsByDepartment(department);
        } else {
            rooms = repository.getAllRooms();
        }
    }

    public void updateRoomStatus(Room room, String newStatus) {
        setLoading(true);
        repository.updateRoomStatus(room, newStatus, (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    public void updateBedStatus(int roomId, int bedNumber, int newState) {
        setLoading(true);
        repository.updateBedStatus(roomId, bedNumber, newState, (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            }
        });
    }

    // Getters for data binding
    public LiveData<List<Room>> getRooms() {
        return rooms;
    }

    public MutableLiveData<String> getSelectedDepartment() {
        return selectedDepartment;
    }

    public MutableLiveData<String> getSearchQuery() {
        return searchQuery;
    }
}