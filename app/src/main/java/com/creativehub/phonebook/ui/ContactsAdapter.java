package com.creativehub.phonebook.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.creativehub.phonebook.R;
import com.creativehub.phonebook.databinding.ItemContactBinding;
import com.creativehub.phonebook.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private final List<Contact> contactList;

    public ContactsAdapter() {
        contactList = new ArrayList<>();
    }

    public void addAll(List<Contact> contacts) {
        contactList.clear();
        contactList.addAll(contacts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contact,parent,false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
            holder.bind(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {

        private ItemContactBinding binding;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemContactBinding.bind(itemView);
        }

        public void bind(Contact contact) {
            binding.companyName.setText(contact.companyName);
            binding.about.setText(contact.about);
            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactsFragmentDirections.ActionContactDetailsFragment directions = ContactsFragmentDirections.actionContactDetailsFragment();
                    directions.setId(contact.id);
                    Navigation.findNavController(v).navigate(directions);
                }
            });
        }
    }
}
