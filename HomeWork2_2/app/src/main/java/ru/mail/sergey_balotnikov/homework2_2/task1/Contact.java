package ru.mail.sergey_balotnikov.homework2_2.task1;

import java.util.ArrayList;
import java.util.Comparator;

public class Contact {
    private String name = "Name";
    private String eMailOrNumber = "number or email";
    private boolean isNumber = false;
    private static ArrayList<Contact> contacts;

    private static final void getInstance(){
        if(contacts==null){
            contacts = new ArrayList<>();
        }
    }
    public static ArrayList<Contact> getContactsList(){
        getInstance();
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getName().compareTo(t1.getName());
            }
        });
        return contacts;
    }

    public static void addContact(Contact contact){
        if (contacts==null) {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
    }

    public Contact() {
    }

    public Contact(String name, String eMailOrNumber, boolean isNumber) {

        this.name = name;
        this.eMailOrNumber = eMailOrNumber;
        this.isNumber = isNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public String geteMailOrNumber() {
        return eMailOrNumber;
    }

/*
    public void setNumber(boolean number) {
        isNumber = number;
    }
*/
    /*
        public void seteMailOrNumber(String eMailOrNumber) {
            this.eMailOrNumber = eMailOrNumber;
        }
    */
    /*
        public void setName(String name) {
            this.name = name;
        }
    */
}

