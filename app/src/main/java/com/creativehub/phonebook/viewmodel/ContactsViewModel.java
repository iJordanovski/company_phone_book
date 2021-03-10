package com.creativehub.phonebook.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.model.ContactDB;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactsViewModel extends AndroidViewModel{

    private Disposable disposable;
    public MutableLiveData<List<Contact>> contactList;
    public MutableLiveData<Boolean> success;

    public ContactsViewModel(@NonNull Application application) {
        super(application);
        contactList = new MutableLiveData<>();
        success = new MutableLiveData<>();
    }

    public void getCompanies() {
        disposable = ContactDB.getInstance(getApplication().getApplicationContext())
                .contactDao()
                .getAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    success.setValue(true);
                    contactList.setValue(result);
                }, throwable -> {
                    success.setValue(false);
                });
    }

    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
