package com.creativehub.phonebook.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.model.ContactDB;
import com.creativehub.phonebook.model.ContactDao;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddContactsViewModel extends AndroidViewModel {

    private Disposable disposable;
    public MutableLiveData<Boolean> success;

    public AddContactsViewModel(@NonNull Application application) {
        super(application);
        success = new MutableLiveData<>();
    }

    public void addContact(Contact contact) {
        ContactDB contactDB = ContactDB.getInstance(getApplication().getApplicationContext());
        ContactDao contactDao = contactDB.contactDao();

        Single<List<Long>> single = contactDao.insertAll(contact);
        Single<List<Long>> single1 = single.subscribeOn(Schedulers.newThread());
        Single<List<Long>> single2 = single1.observeOn(AndroidSchedulers.mainThread());
        disposable = single2.subscribe(result -> {
            success.setValue(true);
            Log.d("AddContactsViewModel","Success");
        },error -> {
            success.setValue(false);
            Log.e("AddContactsViewModel","error",error);
        });

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
