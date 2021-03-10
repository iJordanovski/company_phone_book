package com.creativehub.phonebook.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creativehub.phonebook.R;
import com.creativehub.phonebook.databinding.FragmentAddContactsBinding;
import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.viewmodel.AddContactsViewModel;


public class AddContactFragment extends Fragment {

    private FragmentAddContactsBinding binding;
    private AddContactsViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddContactsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddContactsViewModel.class);
        setupUi();
        setupObservers();
    }

    private void setupUi() {
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.companyName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"company name cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact();

                    contact.companyName = binding.companyName.getText().toString();
                    contact.address = binding.address.getText().toString();
                    contact.phoneNumber = binding.phoneNumber.getText().toString();
                    contact.website = binding.webSite.getText().toString();
                    contact.email = binding.email.getText().toString();
                    contact.about = binding.about.getText().toString();

                    viewModel.addContact(contact);
                }

            }
        });
    }
    private void setupObservers() {
        viewModel.success.observe(getViewLifecycleOwner(),success -> {
            if (success) {
                Navigation.findNavController(getView()).navigateUp();
            } else {
                Toast.makeText(getContext(),"something went wrong please try again",Toast.LENGTH_SHORT).show();

            }
        });

    }
}