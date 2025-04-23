package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.MedicineDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Medicine;
import java.util.List;

public class MedicineRepository {
    private MedicineDao medicineDao;
    private LiveData<List<Medicine>> allMedicines;

    public MedicineRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        medicineDao = db.medicineDao();
        allMedicines = medicineDao.getAllMedicines();
    }

    public LiveData<List<Medicine>> getAllMedicines() {
        return allMedicines;
    }

    public LiveData<List<Medicine>> getLowStockMedicines() {
        return medicineDao.getLowStockMedicines();
    }

    public void importMedicines(List<Medicine> medicines, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                for (Medicine medicine : medicines) {
                    Medicine existing = medicineDao.getMedicineByNameSync(medicine.getName());
                    if (existing != null) {
                        existing.setQuantity(existing.getQuantity() + medicine.getQuantity());
                        medicineDao.update(existing);
                    } else {
                        medicineDao.insert(medicine);
                    }
                }
                callback.onComplete(true);
            } catch (Exception e) {
                callback.onComplete(false);
            }
        });
    }

    public interface OnCompleteCallback {
        void onComplete(boolean success);
    }
}