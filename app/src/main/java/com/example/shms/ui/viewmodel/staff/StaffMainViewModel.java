package com.example.shms.ui.viewmodel.staff;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.data.repository.MedicineRepository;

public class StaffMainViewModel extends BaseViewModel {
    private MedicineRepository medicineRepository;
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<Integer> lowStockCount = new MutableLiveData<>(0);

    public StaffMainViewModel(Application application) {
        super(application);
        medicineRepository = new MedicineRepository(application);
        checkLowStockMedicines();
    }

    private void checkLowStockMedicines() {
        medicineRepository.getLowStockMedicines().observeForever(medicines -> {
            lowStockCount.setValue(medicines.size());
        });
    }

    // Navigation methods
    public void onInventoryClick() {
        // Navigate to inventory management
    }

    public void onRoomManagementClick() {
        // Navigate to room management
    }
}