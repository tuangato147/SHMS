package com.example.shms.ui.viewmodel.medicine;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Medicine;
import com.example.shms.data.repository.MedicineRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class MedicineManagementViewModel extends BaseViewModel {
    private MedicineRepository repository;

    // Import fields
    private MutableLiveData<String> importMedicineName = new MutableLiveData<>();
    private MutableLiveData<Integer> importQuantity = new MutableLiveData<>();
    private MutableLiveData<List<Medicine>> importList = new MutableLiveData<>();

    // Export fields
    private MutableLiveData<String> exportMedicineName = new MutableLiveData<>();
    private MutableLiveData<Integer> exportQuantity = new MutableLiveData<>();
    private MutableLiveData<List<Medicine>> exportList = new MutableLiveData<>();

    public MedicineManagementViewModel(Application application) {
        super(application);
        repository = new MedicineRepository(application);
    }

    public void addToImportList() {
        if (!validateImportInput()) return;

        Medicine medicine = new Medicine();
        medicine.setName(importMedicineName.getValue());
        medicine.setQuantity(importQuantity.getValue());

        List<Medicine> currentList = importList.getValue();
        currentList.add(medicine);
        importList.setValue(currentList);

        // Clear input fields
        importMedicineName.setValue("");
        importQuantity.setValue(0);
    }

    public void confirmImport() {
        setLoading(true);
        repository.importMedicines(importList.getValue(), success -> {
            setLoading(false);
            if (success) {
                importList.setValue(null);
            } else {
                showError("Không thể nhập thuốc");
            }
        });
    }

    private boolean validateImportInput() {
        if (importMedicineName.getValue() == null || importMedicineName.getValue().isEmpty()) {
            showError("Vui lòng nhập tên thuốc");
            return false;
        }
        if (importQuantity.getValue() == null || importQuantity.getValue() <= 0) {
            showError("Vui lòng nhập số lượng hợp lệ");
            return false;
        }
        return true;
    }

    // Getters and setters for data binding
    public MutableLiveData<String> getImportMedicineName() {
        return importMedicineName;
    }

    public MutableLiveData<Integer> getImportQuantity() {
        return importQuantity;
    }

    public MutableLiveData<List<Medicine>> getImportList() {
        return importList;
    }

    // Similar methods for export functionality...
}