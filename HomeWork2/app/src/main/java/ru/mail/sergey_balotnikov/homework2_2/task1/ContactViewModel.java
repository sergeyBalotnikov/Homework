package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<Contact>> allContact;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContact = repository.getAllContact();
    }
    public LiveData<List<Contact>> getAllContact(){return allContact;}
    public void insert(Contact contact){repository.insert(contact);}
    public void update(Contact contact){repository.update(contact);}
    public void delete(Contact contact){repository.delete(contact);}

}
