package com.example.uniproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class contactListAdapter extends ArrayAdapter<ContactItem> {
    private Context context;
    private List<ContactItem> contactItems;
    public contactListAdapter(@NonNull Context context, List<ContactItem> contacts) {
        super(context,R.layout.activity_main,contacts);
        this.context = context;
        this.contactItems = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
        }

        TextView contactName = convertView.findViewById(R.id.contactName);
        TextView contactNumber = convertView.findViewById(R.id.contactNumber);
        ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);

        ContactItem contact = contactItems.get(position);
        contactName.setText(contact.getContactName());
        contactNumber.setText(String.valueOf(contact.getContactNumber()));

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("contactName", contact.getContactName());
            context.startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(context);
            dbHelper.deleteContact(contact.getContactNumber());

            contactItems.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

}
