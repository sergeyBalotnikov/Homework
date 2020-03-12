package ru.mail.sergey_balotnikov.homework2_2.task1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.mail.sergey_balotnikov.homework2_2.R;

public class EditContact extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText mail;
    private Button remove;
    private ImageButton back;
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
        remove.setOnClickListener(this);
        back.setOnClickListener(this);

        Bundle arg = getIntent().getExtras();

        try {
            contact = (Contact) arg.getSerializable(Contact.class.getSimpleName());
            mail.setText(contact.getEMailOrNumber());
            name.setText(contact.getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(EditContact.this, "Contact is Empty", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        Intent replyIntent = new Intent();
        Contact contact = new Contact(name.getText().toString(), mail.getText().toString(), this.contact.getIsNumber());
        contact.set_id(this.contact.get_id());
        switch (view.getId()){
            case R.id.ib_edit_back:
                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(EditContact.this, "Name can't be empty", Toast.LENGTH_LONG).show();
                } else {
                    replyIntent.putExtra(Contact.class.getSimpleName(), contact);
                    replyIntent.putExtra("requestCode", "edit");
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
                break;
            case R.id.btn_remove:
                replyIntent.putExtra(Contact.class.getSimpleName(), contact);
                replyIntent.putExtra("requestCode", "delete");
                setResult(RESULT_OK, replyIntent);
                finish();
                break;
        }
    }
}
