package com.example.bloothcontroler.ui.dashboard;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public ObservableField<String> mType;
    public ObservableField<String> mGlass;
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mType = new ObservableField<>("Poly");
        mGlass = new ObservableField<>("Single");
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}