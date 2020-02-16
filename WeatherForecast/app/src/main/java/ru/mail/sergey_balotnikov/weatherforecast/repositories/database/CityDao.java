package ru.mail.sergey_balotnikov.weatherforecast.repositories.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCity(CityEntity city);

    @Query("SELECT * FROM city_entity")
    List<CityEntity> getAllCities();

    @Query("SELECT * FROM city_entity WHERE id=:id")
    CityEntity getCityById(long id);

    @Query("DELETE FROM city_entity")
    void deleteAll();
}
