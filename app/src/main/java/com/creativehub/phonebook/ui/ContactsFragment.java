package com.creativehub.phonebook.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creativehub.phonebook.R;
import com.creativehub.phonebook.databinding.FragmentContactsBinding;
import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.viewmodel.ContactsViewModel;

import java.util.List;


public class ContactsFragment extends Fragment {

    private FragmentContactsBinding binding;
    private ContactsAdapter adapter;
    private ContactsViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactsBinding.inflate(inflater);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setupUi();
        viewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        setupObservers();
        viewModel.getCompanies();
    }

    private void setupObservers() {
        viewModel.contactList.observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.addAll(contacts);
            }
        });
        viewModel.success.observe(getViewLifecycleOwner(), isSuccess -> {
                if (!isSuccess) {
                    Toast.makeText(getContext(),"something went wrong please try again",Toast.LENGTH_SHORT).show();
                }
        });
    }
    private void setupUi() {

        adapter = new ContactsAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_contact) {
            NavDirections directions = ContactsFragmentDirections.actionAddContactFragment();
            Navigation.findNavController(getView()).navigate(directions);
        }

        return super.onOptionsItemSelected(item);
    }

}