package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class EditContact extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText mail;
    private Button remove;
    private ImageButton back;
    private int position;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        getSupportActionBar().hide();

        name = findViewById(R.id.et_name);
        mail = findViewById(R.id.et_mail);
        remove = findViewById(R.id.btn_remove);
        back = findViewById(R.id.ib_edit_back);
        name.setOnClickListener(this);
        mail.setOnClickListener(this);
        remove.setOnClickListener(this);
        back.setOnClickListener(this);
        position = getIntent().getIntExtra("position", 0);
        contact = Contact.getContactsList().get(position);
        name.setText(contact.getName());
        mail.setText(contact.geteMailOrNumber());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_edit_back:
                Contact contact = new Contact(name.getText().toString(), mail.getText().toString(), this.contact.isNumber());
                contact.editContact(contact, position);
                finish();
                break;
            case R.id.btn_remove:
                this.contact.removeContact(position);
                finish();
                break;
        }
    }
}
