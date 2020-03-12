package ru.mail.sergey_balotnikov.contactresolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import ru.mail.sergey_balotnikov.contactresolver.model.Contact;
import ru.mail.sergey_balotnikov.contactresolver.model.ContactResolver;
import ru.mail.sergey_balotnikov.contactresolver.model.ContactsAdapter;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnContactClickListener {

    private RecyclerView contactList;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=findViewById(R.id.rvContacts);
        adapter=new ContactsAdapter(this);
        contactList.setAdapter(adapter);
        contactList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setContacts(ContactResolver.newInstance(this).getAllContact());
    }

    @Override
    public void onContactClick(int id) {
        ContactResolver.newInstance(this).deleteContactById(id);
        adapter.setContacts(ContactResolver.newInstance(this).getAllContact());
    }
}
