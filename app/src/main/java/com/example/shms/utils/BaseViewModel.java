package com.example.shms.utils;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel {
    protected MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    protected MutableLiveData<String> errorMessage = new MutableLiveData<>();
    protected MutableLiveData<Boolean> isDataValid = new MutableLiveData<>(false);

    public BaseViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getIsDataValid() {
        return isDataValid;
    }

    protected void showError(String message) {
        errorMessage.setValue(message);
    }

    protected void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }
}