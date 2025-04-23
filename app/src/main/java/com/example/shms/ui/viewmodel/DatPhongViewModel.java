public class DatPhongViewModel extends BaseViewModel {
    private RoomRepository roomRepository;
    private MutableLiveData<List<Bed>> bedList = new MutableLiveData<>();
    private MutableLiveData<String> selectedDepartment = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedDepartmentId = new MutableLiveData<>();

    public DatPhongViewModel(Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
        loadBeds();
    }

    public void onBedClick(Bed bed) {
        if (bed.getState() == Constants.BED_STATE_AVAILABLE) {
            // Show confirmation dialog for patient
            showConfirmationDialog(bed);
        } else if (bed.getState() == Constants.BED_STATE_MAINTENANCE) {
            // Show maintenance completion dialog for staff
            showMaintenanceCompleteDialog(bed);
        } else if (bed.getState() == Constants.BED_STATE_OCCUPIED) {
            // Show discharge dialog for staff
            showDischargeDialog(bed);
        }
    }

    private void updateBedState(Bed bed, int newState) {
        setLoading(true);
        bed.setState(newState);
        roomRepository.updateBed(bed, success -> {
            setLoading(false);
            if (!success) {
                showError("Không thể cập nhật trạng thái giường");
            }
        });
    }

    public void startMaintenance(Bed bed) {
        updateBedState(bed, Constants.BED_STATE_MAINTENANCE);
    }

    public void completeMaintenance(Bed bed) {
        updateBedState(bed, Constants.BED_STATE_AVAILABLE);
    }

    public void occupyBed(Bed bed) {
        updateBedState(bed, Constants.BED_STATE_OCCUPIED);
    }

    // Getters for data binding
    public LiveData<List<Bed>> getBedList() {
        return bedList;
    }
}