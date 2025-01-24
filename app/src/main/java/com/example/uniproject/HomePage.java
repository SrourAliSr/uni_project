package com.example.uniproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.uniproject.databinding.HomePageFragmentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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

        contacts.add(new ContactItem("Ali", 81986623));
        contacts.add(new ContactItem("Mousa", 7158303));
        contacts.add(new ContactItem("Lynn", 43254856));
        contacts.add(new ContactItem("Layla", 214811238));

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
                    String firstText = firstTextField.getText().toString();
                    String secondText = secondTextField.getText().toString();

                    if (!firstText.isEmpty() && !secondText.isEmpty()) {
                        contacts.add(new ContactItem(firstText, Integer.parseInt(secondText)));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
