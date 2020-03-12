package ru.mail.sergey_balotnikov.weatherforecast.repositories.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_entity")
public class CityEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    @ColumnInfo(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){return this.name;}
}
