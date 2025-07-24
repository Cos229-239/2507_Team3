package com.ila.ui.classselection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassSelectionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClassSelectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("DashBoard");
    }

    public LiveData<String> getText() {
        return mText;
    }
}