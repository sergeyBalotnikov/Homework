package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import ru.mail.sergey_balotnikov.homework2_2.R;

public class ContactTask extends AppCompatActivity {

    private RecyclerView contactRecyclerList;
    private TextView ifEmptyContactList;
    private FloatingActionButton addContactFloatingButton;
    private int orientation;
    private ContactsAdapter adapter;
    private SearchView search;

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

        search = findViewById(R.id.sv_search);
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        ifEmptyContactList = findViewById(R.id.tv_empty_contact_list);

        contactRecyclerList = findViewById(R.id.rv_contacts);
        adapter = new ContactsAdapter(this);
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
