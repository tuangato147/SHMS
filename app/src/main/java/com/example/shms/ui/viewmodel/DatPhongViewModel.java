package com.example.shms.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.shms.R;
import com.example.shms.data.model.Bed;
import java.util.ArrayList;
import java.util.List;

public class DatPhongViewModel extends ViewModel {
    private final MutableLiveData<String> selectedDepartment = new MutableLiveData<>();
    private final MutableLiveData<Integer> selectedDepartmentId = new MutableLiveData<>();
    private final MutableLiveData<String> departmentInfo = new MutableLiveData<>();
    private final MutableLiveData<List<Bed>> bedList = new MutableLiveData<>();

    public DatPhongViewModel() {
        loadBeds();
    }

    public LiveData<String> getSelectedDepartment() {
        return selectedDepartment;
    }

    public LiveData<Integer> getSelectedDepartmentId() {
        return selectedDepartmentId;
    }

    public LiveData<String> getDepartmentInfo() {
        return departmentInfo;
    }

    public LiveData<List<Bed>> getBedList() {
        return bedList;
    }

    public void onDepartmentSelectClick() {
        // Xử lý khi click vào nút chọn khoa
    }

    public void onDepartmentChanged(int checkedId) {
        selectedDepartmentId.setValue(checkedId);
        if (checkedId == R.id.radioButton2) {
            selectedDepartment.setValue("Khoa Nội");
            departmentInfo.setValue("Thông tin chi tiết về Khoa Nội...");
        } else if (checkedId == R.id.radioButton3) {
            selectedDepartment.setValue("Khoa Ngoại");
            departmentInfo.setValue("Thông tin chi tiết về Khoa Ngoại...");
        } else if (checkedId == R.id.radioButton4) {
            selectedDepartment.setValue("Khoa Thần Kinh");
            departmentInfo.setValue("Thông tin chi tiết về Khoa Thần Kinh...");
        }
        loadBeds();
    }

    public void onBedClick(Bed bed) {
        if (!bed.isOccupied()) {
            // Xử lý đặt giường
        }
    }

    private void loadBeds() {
        List<Bed> beds = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            beds.add(new Bed(i + 1, "A" + (i + 1), false));
        }
        bedList.setValue(beds);
    }
}