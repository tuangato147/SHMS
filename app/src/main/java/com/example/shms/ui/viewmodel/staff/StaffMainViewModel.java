package com.example.shms.ui.viewmodel.staff;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.repository.MedicineRepository;
import com.example.shms.data.repository.RoomRepository;
import com.example.shms.utils.BaseViewModel;

public class StaffMainViewModel extends BaseViewModel {
    private MedicineRepository medicineRepository;
    private RoomRepository roomRepository;

    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<Integer> lowStockCount = new MutableLiveData<>(0);
    private MutableLiveData<Integer> maintenanceRoomsCount = new MutableLiveData<>(0);
    private MutableLiveData<Integer> availableRoomsCount = new MutableLiveData<>(0);

    public StaffMainViewModel(Application application) {
        super(application);
        medicineRepository = new MedicineRepository(application);
        roomRepository = new RoomRepository(application);
        loadDashboardData();
    }

    private void loadDashboardData() {
        // Load low stock medicines count
        medicineRepository.getLowStockMedicines().observeForever(medicines ->
                lowStockCount.setValue(medicines.size())
        );

        // Load maintenance rooms count
        roomRepository.getMaintenanceRooms().observeForever(rooms ->
                maintenanceRoomsCount.setValue(rooms.size())
        );

        // Load available rooms count
        roomRepository.getAvailableRooms().observeForever(rooms ->
                availableRoomsCount.setValue(rooms.size())
        );
    }

    // Navigation methods
    public void onProfileClick() {
        // Navigate to staff profile
    }

    public void onMedicineInventoryClick() {
        // Navigate to medicine inventory
    }

    public void onRoomManagementClick() {
        // Navigate to room management
    }

    public void onStockHistoryClick() {
        // Navigate to stock history
    }

    // Search functionality
    public MutableLiveData<String> getSearchQuery() {
        return searchQuery;
    }

    // Stats getters
    public LiveData<Integer> getLowStockCount() {
        return lowStockCount;
    }

    public LiveData<Integer> getMaintenanceRoomsCount() {
        return maintenanceRoomsCount;
    }

    public LiveData<Integer> getAvailableRoomsCount() {
        return availableRoomsCount;
    }
}