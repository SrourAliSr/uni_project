package com.example.uniproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.uniproject.databinding.HomePageFragmentBinding;


import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.Toolbar;
public class HomePage extends Fragment {

    private HomePageFragmentBinding binding;
    private List<ContactItem> contacts = new ArrayList<>();
    private contactListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = HomePageFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(requireContext());

        Toolbar toolbar = view.findViewById(R.id.appBar);
        toolbar.setTitle("Home");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);

        contacts = dbHelper.getAllContacts();
        adapter = new contactListAdapter(requireContext(), contacts);
        binding.contactsList.setAdapter(adapter);

        binding.fab.setOnClickListener(v -> showPopup(requireContext()));
    }

    private void showPopup(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.add_contact_popup, null);

        EditText firstTextField = dialogView.findViewById(R.id.contactNamePopUp);
        EditText secondTextField = dialogView.findViewById(R.id.contactNumberPopUp);

        new AlertDialog.Builder(context)
                .setTitle("Enter Values")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String contactName = firstTextField.getText().toString();
                    String contactNumber = secondTextField.getText().toString();

                    if (!contactName.isEmpty() && !contactNumber.isEmpty()) {

                        ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
                        dbHelper.addContact(new ContactItem(contactName, Integer.parseInt(contactNumber)));


                        contacts.clear();
                        contacts.addAll(dbHelper.getAllContacts());
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
