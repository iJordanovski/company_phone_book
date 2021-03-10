package com.creativehub.phonebook.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creativehub.phonebook.R;
import com.creativehub.phonebook.databinding.FragmentContactDetailsBinding;
import com.creativehub.phonebook.model.Contact;
import com.creativehub.phonebook.viewmodel.ContactDetailsViewModel;

public class ContactDetailsFragment extends Fragment {
     private int contactId;
     private FragmentContactDetailsBinding binding;
     private ContactDetailsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactDetailsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(ContactDetailsViewModel.class);
        setupObservers();
        Bundle bundle = getArguments();
        if (bundle != null) {
           contactId = ContactDetailsFragmentArgs.fromBundle(bundle).getId();
           viewModel.fetchContact(contactId);
        }
    }

    private void setupObservers() {
        viewModel.success.observe(getViewLifecycleOwner(), isSuccess -> {
            if (!isSuccess) {
                showErrorMessage();
            }
        });
        viewModel.deleted.observe(getViewLifecycleOwner(), isDeleted -> {
            if (isDeleted) {
                Navigation.findNavController(getView()).navigateUp();
            } else {
                showErrorMessage();
            }
        });
        viewModel.contact.observe(getViewLifecycleOwner(), contact -> {
            binding.companyName.setText(contact.companyName);
            binding.phoneNumber.setText(contact.phoneNumber);
            binding.webSite.setText(contact.website);
            binding.email.setText(contact.email);
            binding.about.setText(contact.about);
            binding.address.setText(contact.address);
            getActivity().invalidateOptionsMenu();
        });
    }

    private void showErrorMessage() {
        Toast.makeText(getContext(),"Something went wrong please try again",Toast.LENGTH_SHORT).show();
    }
    private void updateContact() {
        Contact contact = viewModel.contact.getValue();
        contact.phoneNumber = binding.phoneNumber.getText().toString();
        contact.address = binding.address.getText().toString();
        contact.website = binding.webSite.getText().toString();
        contact.email = binding.email.getText().toString();
        contact.companyName = binding.companyName.getText().toString();
        contact.about = binding.about.getText().toString();
        viewModel.updateContact(contact);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contact_details_menu,menu);
        if(viewModel.contact.getValue() != null) {
            if(binding.address.isEnabled()) {
                menu.findItem(R.id.save).setVisible(true);
                menu.findItem(R.id.edit).setVisible(false);
            } else {
                menu.findItem(R.id.save).setVisible(false);
                menu.findItem(R.id.edit).setVisible(true);
            }
            menu.findItem(R.id.delete).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void enableEditTexts(boolean isEnabled) {
        binding.companyName.setEnabled(isEnabled);
        binding.address.setEnabled(isEnabled);
        binding.email.setEnabled(isEnabled);
        binding.webSite.setEnabled(isEnabled);
        binding.phoneNumber.setEnabled(isEnabled);
        binding.about.setEnabled(isEnabled);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            enableEditTexts(true);
            getActivity().invalidateOptionsMenu();
        } else if(item.getItemId() == R.id.save) {
            enableEditTexts(false);
            getActivity().invalidateOptionsMenu();
            updateContact();
        }
         else if(item.getItemId() == R.id.delete) {
            viewModel.deleteContact(viewModel.contact.getValue());
        }
        return super.onOptionsItemSelected(item);
    }
}