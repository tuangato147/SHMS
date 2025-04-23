package com.example.shms.ui.viewmodel.room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Bed;
import com.example.shms.data.repository.RoomRepository;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.utils.Constants;
import java.util.List;

public class RoomManagementViewModel extends BaseViewModel {
    private RoomRepository repository;
    private LiveData<List<Bed>> bedList;
    private MutableLiveData<Bed> selectedBed = new MutableLiveData<>();

    public RoomManagementViewModel(Application application) {
        super(application);
        repository = new RoomRepository(application);
        loadBeds();
    }

    private void loadBeds() {
        bedList = repository.getAllBeds();
    }

    public void onBedClick(Bed bed) {
        selectedBed.setValue(bed);
        switch (bed.getState()) {
            case Constants.BED_STATE_AVAILABLE:
                // Show maintenance confirmation dialog
                break;
            case Constants.BED_STATE_MAINTENANCE:
                // Show maintenance complete dialog
                break;
            case Constants.BED_STATE_OCCUPIED:
                // Show discharge confirmation dialog
                break;
        }
    }

    public void startMaintenance() {
        Bed bed = selectedBed.getValue();
        if (bed != null) {
            bed.setState(Constants.BED_STATE_MAINTENANCE);
            updateBed(bed);
        }
    }

    public void completeMaintenance() {
        Bed bed = selectedBed.getValue();
        if (bed != null) {
            bed.setState(Constants.BED_STATE_AVAILABLE);
            updateBed(bed);
        }
    }

    private void updateBed(Bed bed) {
        setLoading(true);
        repository.updateBed(bed, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể cập nhật trạng thái giường");
            }
        });
    }

    // Getters for LiveData
    public LiveData<List<Bed>> getBedList() {
        return bedList;
    }

    public LiveData<Bed> getSelectedBed() {
        return selectedBed;
    }
}