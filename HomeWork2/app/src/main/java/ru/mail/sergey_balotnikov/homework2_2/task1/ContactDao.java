package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import io.reactivex.Flowable;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    LiveData<List<Contact>> getAllAlphabetizenContact();

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    /*@Query("DELETE FROM contact_table")
    void deleteAll();

*/
}
