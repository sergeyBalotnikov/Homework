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
import ru.mail.sergey_balotnikov.homework2_2.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> implements Filterable {

    private ArrayList<Contact> contactList;
    private ArrayList<Contact> contactListFiltered;

    public ContactsAdapter(ArrayList<Contact> contactList) {
        super();
        this.contactList = contactList;
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
        holder.contactName.setText(contactList.get(position).getName());
        holder.contactNumberOrEmail.setText(contactList.get(position).geteMailOrNumber());
        holder.icon.setBackgroundResource(contactList.get(position).isNumber() ?
                R.drawable.ic_contact_phone_black_24dp : R.drawable.ic_contact_mail_black_24dp);
    }

    @Override
    public int getItemCount() {
        return contactList!=null?contactList.size():0;
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
            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactNumberOrEmail = itemView.findViewById(R.id.tv_contact_email_or_number);
            icon = itemView.findViewById(R.id.iv_icon);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

        @Override
        public FilterResults performFiltering(CharSequence  charSequence) {
            String charString = charSequence.toString();
            if(charString.isEmpty()){
                contactListFiltered = contactList;
            } else{
                ArrayList<Contact> filteredList = new ArrayList<>();
                for(Contact contact:contactList){
                    if(contact.getName().toLowerCase().contains(charString.toLowerCase())){
                        filteredList.add(contact);
                    }
                }
                contactListFiltered = filteredList;

            }
            FilterResults results = new FilterResults();
            results.values = contactListFiltered;
            return results;
        }

        @Override
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactListFiltered = (ArrayList<Contact>) filterResults.values;
            notifyDataSetChanged();
        }
    };
    }
}
