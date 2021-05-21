package com.wxj.ui.contactPeople;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactPeopleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactPeopleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is contact fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}