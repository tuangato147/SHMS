package com.example.shms.ui.viewmodel.staff;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.StockHistory;
import com.example.shms.data.repository.MedicineRepository;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.utils.Constants;
import java.util.List;

public class StockHistoryViewModel extends BaseViewModel {
    private MedicineRepository repository;
    private LiveData<List<StockHistory>> history;
    private MutableLiveData<String> selectedType = new MutableLiveData<>("ALL");
    private MutableLiveData<String> startDate = new MutableLiveData<>();
    private MutableLiveData<String> endDate = new MutableLiveData<>();

    public StockHistoryViewModel(Application application) {
        super(application);
        repository = new MedicineRepository(application);
        loadHistory();
    }

    private void loadHistory() {
        String type = selectedType.getValue();
        String start = startDate.getValue();
        String end = endDate.getValue();

        if ("ALL".equals(type)) {
            history = repository.getAllStockHistory(start, end);
        } else if ("IMPORT".equals(type)) {
            history = repository.getImportHistory(start, end);
        } else if ("EXPORT".equals(type)) {
            history = repository.getExportHistory(start, end);
        }
    }

    public void onTypeSelected(String type) {
        selectedType.setValue(type);
        loadHistory();
    }

    public void setDateRange(String start, String end) {
        startDate.setValue(start);
        endDate.setValue(end);
        loadHistory();
    }

    // Getters for data binding
    public LiveData<List<StockHistory>> getHistory() { return history; }
    public MutableLiveData<String> getSelectedType() { return selectedType; }
    public MutableLiveData<String> getStartDate() { return startDate; }
    public MutableLiveData<String> getEndDate() { return endDate; }
}