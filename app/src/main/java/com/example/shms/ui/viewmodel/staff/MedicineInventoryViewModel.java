package com.example.shms.ui.viewmodel.staff;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Medicine;
import com.example.shms.data.repository.MedicineRepository;
import com.example.shms.utils.BaseViewModel;
import java.util.List;

public class MedicineInventoryViewModel extends BaseViewModel {
    private MedicineRepository repository;
    private LiveData<List<Medicine>> medicines;
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();

    // Import form fields
    private MutableLiveData<String> medicineName = new MutableLiveData<>();
    private MutableLiveData<Integer> quantity = new MutableLiveData<>(0);
    private MutableLiveData<String> batchNumber = new MutableLiveData<>();
    private MutableLiveData<String> expiryDate = new MutableLiveData<>();

    public MedicineInventoryViewModel(Application application) {
        super(application);
        repository = new MedicineRepository(application);
        medicines = repository.getAllMedicines();
        setupValidation();
    }

    private void setupValidation() {
        medicineName.observeForever(name -> validateInput());
        quantity.observeForever(qty -> validateInput());
        batchNumber.observeForever(batch -> validateInput());
        expiryDate.observeForever(date -> validateInput());
    }

    private void validateInput() {
        boolean isValid = medicineName.getValue() != null && !medicineName.getValue().trim().isEmpty()
                && quantity.getValue() != null && quantity.getValue() > 0
                && batchNumber.getValue() != null && !batchNumber.getValue().trim().isEmpty()
                && expiryDate.getValue() != null && !expiryDate.getValue().trim().isEmpty();
        isDataValid.setValue(isValid);
    }

    public void importMedicine() {
        if (!isDataValid.getValue()) {
            showError("Vui lòng điền đầy đủ thông tin");
            return;
        }

        setLoading(true);
        Medicine medicine = new Medicine();
        medicine.setName(medicineName.getValue());
        medicine.setQuantity(quantity.getValue());
        medicine.setBatchNumber(batchNumber.getValue());
        medicine.setExpiryDate(expiryDate.getValue());

        repository.importMedicines(List.of(medicine), (success, error) -> {
            setLoading(false);
            if (!success) {
                showError(error);
            } else {
                clearForm();
            }
        });
    }

    public void searchMedicines(String query) {
        medicines = repository.searchMedicines(query);
    }

    private void clearForm() {
        medicineName.setValue("");
        quantity.setValue(0);
        batchNumber.setValue("");
        expiryDate.setValue("");
    }

    // Getters for data binding
    public MutableLiveData<String> getMedicineName() { return medicineName; }
    public MutableLiveData<Integer> getQuantity() { return quantity; }
    public MutableLiveData<String> getBatchNumber() { return batchNumber; }
    public MutableLiveData<String> getExpiryDate() { return expiryDate; }
    public LiveData<List<Medicine>> getMedicines() { return medicines; }
}