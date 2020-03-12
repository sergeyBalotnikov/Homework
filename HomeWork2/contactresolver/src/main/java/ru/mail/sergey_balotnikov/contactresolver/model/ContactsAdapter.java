package ru.mail.sergey_balotnikov.contactresolver.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import ru.mail.sergey_balotnikov.contactresolver.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {

    private List<Contact> contacts;
    private Context parent;
    private OnContactClickListener listener;

    public ContactsAdapter(Context parent) {
        this.parent = parent;
        if(parent instanceof OnContactClickListener){
            listener = (OnContactClickListener)parent;
        }
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        holder.bind(contacts.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onContactClick(contacts.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts!=null?contacts.size():0;
    }

    public void setContacts(List <Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class ContactHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView number;
        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvContactName);
            number = itemView.findViewById(R.id.tvContactNumber);
        }
        void bind(Contact contact){
            name.setText(contact.getName());
            number.setText(contact.getNumber());
            if(contact.isNumber()){
                number.setTextColor(parent.getResources().getColor(R.color.colorContactNumber));
            } else {
                number.setTextColor(parent.getResources().getColor(R.color.colorContactMail));
            }
        }
    }
    public interface OnContactClickListener{
        void onContactClick(int id);
    }
}
