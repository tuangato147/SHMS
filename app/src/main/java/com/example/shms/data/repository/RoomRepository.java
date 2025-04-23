package com.example.shms.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.shms.data.local.dao.RoomDao;
import com.example.shms.data.local.database.AppDatabase;
import com.example.shms.data.local.entities.Room;
import com.example.shms.data.local.entities.Bed;
import java.util.List;

public class RoomRepository {
    private RoomDao roomDao;
    private LiveData<List<Room>> allRooms;

    public RoomRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        roomDao = db.roomDao();
        allRooms = roomDao.getAllRooms();
    }

    public LiveData<List<Room>> getAllRooms() {
        return allRooms;
    }

    public LiveData<List<Room>> getAvailableRooms() {
        return roomDao.getRoomsByStatus(Room.STATUS_AVAILABLE);
    }

    public LiveData<List<Room>> getMaintenanceRooms() {
        return roomDao.getRoomsByStatus(Room.STATUS_MAINTENANCE);
    }

    public LiveData<List<Room>> getRoomsByDepartment(String department) {
        return roomDao.getRoomsByDepartment(department);
    }

    public void updateRoomStatus(Room room, String newStatus, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                room.setStatus(newStatus);
                roomDao.update(room);
                callback.onComplete(true, null);
            } catch (Exception e) {
                callback.onComplete(false, e.getMessage());
            }
        });
    }

    public void updateBedStatus(int roomId, int bedNumber, int newState, OnCompleteCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Bed bed = roomDao.getBedByNumber(roomId, bedNumber);
                if (bed != null) {
                    bed.setState(newState);
                    roomDao.updateBed(bed);
                    callback.onComplete(true, null);
                } else {
                    callback.onComplete(false, "Không tìm thấy giường");
                }
            } catch (Exception e) {
                callback.onComplete(false, e.getMessage());
            }
        });
    }

    public interface OnCompleteCallback {
        void onComplete(boolean success, String error);
    }
}