package com.creativehub.phonebook.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "company_name")
    public String companyName;
    public String address;
    @ColumnInfo(name = "phone_number")
    public String phoneNumber;
    public String website;
    public String email;
    public String about;

}
