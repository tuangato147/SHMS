package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.MedicineDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Medicine;
import com.example.shms.data.local.entities.StockHistory;
import java.util.List;
import java.util.Date;

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

    public LiveData<List<Medicine>> searchMedicines(String query) {
        return medicineDao.searchMedicines(query);
    }

    public void importMedicines(List<Medicine> medicines, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                for (Medicine medicine : medicines) {
                    Medicine existing = medicineDao.getMedicineByNameSync(medicine.getName());
                    if (existing != null) {
                        existing.setQuantity(existing.getQuantity() + medicine.getQuantity());
                        medicineDao.update(existing);

                        // Log stock history
                        StockHistory history = new StockHistory();
                        history.setMedicineId(existing.getId());
                        history.setType(StockHistory.TYPE_IMPORT);
                        history.setQuantity(medicine.getQuantity());
                        history.setDate(new Date());
                        medicineDao.insertStockHistory(history);
                    } else {
                        long id = medicineDao.insert(medicine);

                        // Log stock history
                        StockHistory history = new StockHistory();
                        history.setMedicineId((int) id);
                        history.setType(StockHistory.TYPE_IMPORT);
                        history.setQuantity(medicine.getQuantity());
                        history.setDate(new Date());
                        medicineDao.insertStockHistory(history);
                    }
                }
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, e.getMessage());
            }
        });
    }

    public void exportMedicine(int medicineId, int quantity, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Medicine medicine = medicineDao.getMedicineByIdSync(medicineId);
                if (medicine != null) {
                    if (medicine.getQuantity() >= quantity) {
                        medicine.setQuantity(medicine.getQuantity() - quantity);
                        medicineDao.update(medicine);

                        // Log stock history
                        StockHistory history = new StockHistory();
                        history.setMedicineId(medicineId);
                        history.setType(StockHistory.TYPE_EXPORT);
                        history.setQuantity(quantity);
                        history.setDate(new Date());
                        medicineDao.insertStockHistory(history);

                        callback.onComplete(true, null);
                    } else {
                        callback.onComplete(false, "Số lượng xuất vượt quá tồn kho");
                    }
                } else {
                    callback.onComplete(false, "Không tìm thấy thuốc");
                }
            } catch (Exception e) {
                callback.onComplete(false, e.getMessage());
            }
        });
    }

    public LiveData<List<StockHistory>> getAllStockHistory(String startDate, String endDate) {
        return medicineDao.getAllStockHistory(startDate, endDate);
    }

    public LiveData<List<StockHistory>> getImportHistory(String startDate, String endDate) {
        return medicineDao.getStockHistoryByType(StockHistory.TYPE_IMPORT, startDate, endDate);
    }

    public LiveData<List<StockHistory>> getExportHistory(String startDate, String endDate) {
        return medicineDao.getStockHistoryByType(StockHistory.TYPE_EXPORT, startDate, endDate);
    }

    public interface OnCompleteCallback {
        void onComplete(boolean success, String error);
    }
}