package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class ContactTask extends AppCompatActivity implements OnItemClick {

    private RecyclerView contactRecyclerList;
    private FloatingActionButton addContactFloatingButton;
    private int orientation;
    private ContactsAdapter adapter;
    private SearchView search;
    private ContactViewModel contactViewModel;
    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_OR_DELETE_CONTACT_ACTIVITY_REQUEST_CODE = 2;

    /*public static ContactViewModel getContactViewModel() {
        return contactViewModel;
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);
        getSupportActionBar().hide();

        addContactFloatingButton = findViewById(R.id.fb_add_contact);
        addContactFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactTask.this, AddContactActivity.class);
                startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
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

        contactRecyclerList = findViewById(R.id.rv_contacts);
        adapter = new ContactsAdapter();
        contactRecyclerList.setAdapter(adapter);
        adapter.setItemClick(this);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContactList(contacts);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Contact contact=null;
            switch (requestCode) {
                case NEW_CONTACT_ACTIVITY_REQUEST_CODE:
                    if (data != null) {
                        Bundle arg = data.getExtras();
                        contact = (Contact) arg.getSerializable(Contact.class.getSimpleName());
                        contactViewModel.insert(contact);
                    }
                    break;
                case EDIT_OR_DELETE_CONTACT_ACTIVITY_REQUEST_CODE:
                    if(data!=null){
                        Bundle arg = data.getExtras();
                        String request = data.getStringExtra("requestCode");
                        contact = (Contact) arg.getSerializable(Contact.class.getSimpleName());
                        if(request.equals("edit")) {
                            contactViewModel.update(contact);
                        } else if(request.equals("delete")){
                            contactViewModel.delete(contact);
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void onResume() {
        Configuration configuration = getResources().getConfiguration();
        this.orientation = configuration.orientation;
        contactRecyclerList.setLayoutManager(getLayout(orientation));
        super.onResume();
    }


    public RecyclerView.LayoutManager getLayout(final int orientation){
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(this, 2);
        } else {
            return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        }
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(ContactTask.this,EditContact.class);
        intent.putExtra(Contact.class.getSimpleName(), adapter.getContactList().get(position));
        startActivityForResult(intent, EDIT_OR_DELETE_CONTACT_ACTIVITY_REQUEST_CODE);
    }
}
