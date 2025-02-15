package com.example.uniproject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContactItem {
    private String contactName;
    private int contactNumber;

    public ContactItem(String contactName,int contactNumber){
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public void setContact(String contactName, int ContactNumber){
        this.contactName = contactName;
        this.contactNumber = ContactNumber;
    }
    public String getContactName() {
        return contactName;
    }

    public int getContactNumber() {
        return contactNumber;
    }


    public Map<String, Object> getContact(){
       Map<String,Object> contact = new HashMap<>();
       contact.put("contact name",
               contactName);
        contact.put("contact number",
                contactNumber);
       return  contact;
    }

}
