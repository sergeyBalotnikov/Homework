package ru.mail.sergey_balotnikov.homework2_2.task1;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ru.mail.sergey_balotnikov.homework2_2.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements Filterable {

    private List<Contact> contactList;
    private List<Contact> contactListFiltered;
    private Context parent;

    public ContactsAdapter(Context context) {
        super();
        parent = context;
        this.contactList = Contact.getContactsList();
        this.contactListFiltered = Contact.getContactsList();

    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_view, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.contactName.setText(contactListFiltered.get(position).getName());
        holder.contactNumberOrEmail.setText(contactListFiltered.get(position).geteMailOrNumber());
        holder.icon.setBackgroundResource(contactListFiltered.get(position).isNumber() ?
                R.drawable.ic_contact_phone_black_24dp : R.drawable.ic_contact_mail_black_24dp);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered!=null?contactListFiltered.size():0;
    }

    public void addItem(){
        contactList = Contact.getContactsList();
        notifyDataSetChanged();
    }

    protected class ContactsViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView contactName;
        private TextView contactNumberOrEmail;

        private ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parent.startActivity(new Intent(parent, EditContact.class).putExtra("position", getAdapterPosition()));
                }
            });
            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactNumberOrEmail = itemView.findViewById(R.id.tv_contact_email_or_number);
            icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence  charSequence) {
            String charString = charSequence.toString().toLowerCase().trim();
            if(charString==null||charString.length()==0){
                contactListFiltered=contactList;
            } else{
                List<Contact> filteredList = new ArrayList<>();
                for(Contact contact:contactList) {
                    if (contact.getName().toLowerCase().contains(charString)) {
                        filteredList.add(contact);
                    }
                }
                contactListFiltered=filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = contactListFiltered;
            return filterResults;
        }

        @Override
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactListFiltered=(ArrayList<Contact>)filterResults.values;
            notifyDataSetChanged();
        }
    };
    }
}
