package ru.mail.sergey_balotnikov.homework2_2.task1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contact_table")
public class Contact implements Serializable {

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int _id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private String eMailOrNumber;

    @ColumnInfo(name = "is_number")
    private int isNumber;

    public Contact(String name, String eMailOrNumber, int isNumber) {
        this.name = name;
        this.eMailOrNumber = eMailOrNumber;
        this.isNumber = isNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEMailOrNumber() {
        return eMailOrNumber;
    }

    public void seteMailOrNumber(String eMailOrNumber) {
        this.eMailOrNumber = eMailOrNumber;
    }

    public int getIsNumber() {
        return isNumber;
    }

    public void setIsNumber(int isNumber) {
        this.isNumber = isNumber;
    }
}