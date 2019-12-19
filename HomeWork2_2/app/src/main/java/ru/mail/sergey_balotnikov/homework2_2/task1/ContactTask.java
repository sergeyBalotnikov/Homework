package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
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
                startActivity(new Intent(ContactTask.this, AddContactActivity.class));
            }
        });

        searchContactsView = findViewById(R.id.sv_search_field);
        ifEmptyContactList = findViewById(R.id.tv_empty_contact_list);

        contactList=Contact.getContactsList();
        contactRecyclerList = findViewById(R.id.rv_contacts);
        adapter = new ContactsAdapter(contactList);
        contactRecyclerList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        this.orientation = configuration.orientation;
        contactRecyclerList.setLayoutManager(getLayout(orientation));
        adapter.addItem();
        ifEmptyContactList.setVisibility(Contact.getContactsList().size()==0?View.VISIBLE:View.INVISIBLE);
    }

    public RecyclerView.LayoutManager getLayout(final int orientation){

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(this, 2);
        } else {
            return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        }
    }
}
