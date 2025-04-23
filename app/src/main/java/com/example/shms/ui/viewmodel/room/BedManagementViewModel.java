package com.example.shms.ui.viewmodel.room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.shms.data.local.entities.Bed;
import com.example.shms.data.repository.RoomRepository;
import com.example.shms.utils.BaseViewModel;
import com.example.shms.utils.Constants;
import java.util.List;

public class BedManagementViewModel extends BaseViewModel {
    private RoomRepository repository;
    private LiveData<List<Bed>> bedList;
    private MutableLiveData<Bed> selectedBed = new MutableLiveData<>();

    public BedManagementViewModel(Application application) {
        super(application);
        repository = new RoomRepository(application);
        loadBeds();
    }

    private void loadBeds() {
        bedList = repository.getAllBeds();
    }

    public void startMaintenance(Bed bed) {
        if (bed.getState() != Constants.BED_STATE_AVAILABLE) {
            showError("Giường không sẵn sàng để bảo trì");
            return;
        }

        setLoading(true);
        bed.setState(Constants.BED_STATE_MAINTENANCE);
        repository.updateBed(bed, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể bắt đầu bảo trì");
            }
        });
    }

    public void completeMaintenance(Bed bed) {
        if (bed.getState() != Constants.BED_STATE_MAINTENANCE) {
            showError("Giường không trong trạng thái bảo trì");
            return;
        }

        setLoading(true);
        bed.setState(Constants.BED_STATE_AVAILABLE);
        repository.updateBed(bed, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể hoàn thành bảo trì");
            }
        });
    }

    public LiveData<List<Bed>> getBedList() {
        return bedList;
    }

    public MutableLiveData<Bed> getSelectedBed() {
        return selectedBed;
    }
}