package com.creativehub.phonebook.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ContactDao {

    @Insert
    Single<List<Long>> insertAll(Contact... contacts);

    @Update
    Completable update(Contact contact);

    @Delete
    Completable delete(Contact contact);

    @Query("SELECT * FROM contact")
    Single<List<Contact>> getAll();

    @Query("SELECT * FROM contact WHERE id= :id")
    Single<Contact> getContact(int id);

}
