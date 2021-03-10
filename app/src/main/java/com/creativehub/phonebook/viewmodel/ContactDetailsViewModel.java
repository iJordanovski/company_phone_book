package com.creativehub.phonebook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.model.ContactDB;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactDetailsViewModel extends AndroidViewModel {
    private final CompositeDisposable disposable;
    public final MutableLiveData<Boolean> success;
    public final MutableLiveData<Boolean> deleted;
    public final MutableLiveData<Contact> contact;
    public ContactDB contactDB;

    public ContactDetailsViewModel(@NonNull Application application) {
        super(application);
        deleted = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        success = new MutableLiveData<>();
        contact = new MutableLiveData<>();
    }

    public void updateContact(Contact contact) {
        disposable.add(ContactDB.getInstance(getApplication().getApplicationContext())
                .contactDao()
                .update(contact)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {

                },throwable -> {
                    success.setValue(false);
                }));
    }

    public void deleteContact(Contact contact) {
        disposable.add(ContactDB.getInstance(getApplication().getApplicationContext())
                .contactDao()
                .delete(contact)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    deleted.setValue(true);
                },throwable -> {
                    deleted.setValue(false);
                })
        );
    }
    public void fetchContact(int id) {
        disposable.add(ContactDB.getInstance(getApplication().getApplicationContext())
                .contactDao()
                .getContact(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    contact.setValue(result);
                }, throwable -> {
                    success.setValue(false);
                }));
    }

}
