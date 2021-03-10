package com.creativehub.phonebook.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Contact.class},version = 1)
public abstract class ContactDB extends RoomDatabase {
    private final static String DB_NAME = "contact_db";

    private static ContactDB instance;

    public synchronized static ContactDB getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),ContactDB.class,DB_NAME).build();
            }

            return instance;
    }

    public abstract ContactDao contactDao();


}
