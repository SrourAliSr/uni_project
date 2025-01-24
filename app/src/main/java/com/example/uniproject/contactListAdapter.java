package com.example.uniproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

public class contactListAdapter extends ArrayAdapter<ContactItem> {
    private Context context;
    private List<ContactItem> contactItems;
    public contactListAdapter(@NonNull Context context, List<ContactItem> contacts) {
        super(context,R.layout.activity_main,contacts);
        this.context = context;
        this.contactItems = contacts;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }
        ContactItem contact = contactItems.get(position);

        TextView contactName = convertView.findViewById(R.id.contactName);
        TextView phoneNumber = convertView.findViewById(R.id.contactNumber);  // Fix this line

        Map<String, Object> contactInfo = contact.getContact();

        contactName.setText((String) contactInfo.get("contact name"));
        phoneNumber.setText(String.valueOf(contactInfo.get("contact number")));

        return convertView;  // Fix the return statement, use convertView instead of super.getView()
    }

}
