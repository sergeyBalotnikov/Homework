package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class ContactTask extends AppCompatActivity {//implements ListListener {

    private ArrayList<Contact> contactList = new ArrayList<>();
    private RecyclerView contactRecyclerList;
    private SearchView searchContactsView;
    private TextView ifEmptyContactList;
    private FloatingActionButton addContactFloatingButton;
    private int orientation;
    private ContactsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);

        getSupportActionBar().hide();

        addContactFloatingButton = findViewById(R.id.fb_add_contact);
        addContactFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactTask.this, AddContact.class));
            }
        });

        searchContactsView = findViewById(R.id.sv_search_field);
        ifEmptyContactList = findViewById(R.id.tv_empty_contact_list);

        contactList=Contact.getContactsList();
        contactRecyclerList = findViewById(R.id.rv_contacts);
        adapter = new ContactsAdapter(contactList);
        contactRecyclerList.setAdapter(adapter);

        /*searchContactsView = findViewById(R.id.sv_search_field);
        searchContactsView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchContactsView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);

                return true;
            }
        });*/


    }
    /*private void update(ArrayList<Contact> contacts){
        contactList = contacts;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        this.orientation = configuration.orientation;
        contactRecyclerList.setLayoutManager(getLayout(orientation));
        //ContactsAdapter adapter = (ContactsAdapter)contactRecyclerList.getAdapter();
        adapter.addItem();
        ifEmptyContactList.setVisibility(Contact.getContactsList().size()==0?View.VISIBLE:View.INVISIBLE);
    }

    /*@Override
    public void onContactListChanged(ArrayList<Contact> contacts) {
        update(contacts);
    }
*/
    public RecyclerView.LayoutManager getLayout(final int orientation){

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(this, 2);
        } else {
            return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }*/
}
