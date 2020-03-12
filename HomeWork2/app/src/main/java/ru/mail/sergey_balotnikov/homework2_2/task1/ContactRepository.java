package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContact;

    ContactRepository (Application application){
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        allContact = contactDao.getAllAlphabetizenContact();
    }

    LiveData<List<Contact>> getAllContact() {
        return allContact;
    }

    void insert(final Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> {
            contactDao.insert(contact);
        });
    }

    void update(final Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(()->
                contactDao.update(contact));
    }

    void delete(final Contact contact){
        ContactRoomDatabase.databaseWriteExecutor.execute(()->
                contactDao.delete(contact));
    }


}
