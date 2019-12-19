package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*import java.util.ArrayList;*/

import ru.mail.sergey_balotnikov.homework2_2.R;

public class AddContactActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText name;
    private EditText emailOrNumber;
    private Contact contact;
    private ImageButton check;
    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getSupportActionBar().hide();

        emailOrNumber = findViewById(R.id.et_email);
        radioGroup = findViewById(R.id.rg_mail_or_number);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rb_number){
                    emailOrNumber.setHint(R.string.phoneNumber);
                } else
                    emailOrNumber.setHint(R.string.email);
                }
        });
        name = findViewById(R.id.et_name);

        check = findViewById(R.id.ib_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contact = new Contact(name.getText().toString()
                        , emailOrNumber.getText().toString(),
                        radioGroup.getCheckedRadioButtonId()==R.id.rb_number?true:false);
                Contact.addContact(contact);
/*
                ArrayList<Contact> contactsList = Contact.getContactsList();
                ObserverContactList.getInstance().notyfiListChanged(contactsList);
*/
                finish();
            }
        });

        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
