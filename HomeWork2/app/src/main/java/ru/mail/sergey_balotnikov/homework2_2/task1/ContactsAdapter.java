package ru.mail.sergey_balotnikov.homework2_2.task1;

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

    private List<Contact> contactList = new ArrayList<>();
    private List<Contact> contactListFiltered = new ArrayList<>();

    private OnItemClick itemClick;

    public ContactsAdapter() {
        super();
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
        holder.contactNumberOrEmail.setText(contactListFiltered.get(position).getEMailOrNumber());
        holder.icon.setBackgroundResource(contactListFiltered.get(position).getIsNumber()==1 ?
                R.drawable.ic_contact_phone_black_24dp : R.drawable.ic_contact_mail_black_24dp);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered!=null?contactListFiltered.size():0;
    }

    void setContactList(List<Contact> allContact){
        contactList = allContact;
        contactListFiltered = allContact;
        notifyDataSetChanged();
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

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public List<Contact> getContactList() {
        return contactList;
    }
    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView icon;
        private TextView contactName;
        private TextView contactNumberOrEmail;

        private ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactNumberOrEmail = itemView.findViewById(R.id.tv_contact_email_or_number);
            icon = itemView.findViewById(R.id.iv_icon);
        }
        @SuppressWarnings("deprecation")
        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.onItemClicked(getPosition());
            }
        }
    }
}

