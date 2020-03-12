package ru.mail.sergey_balotnikov.homework2_2.task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Contact {
    private String name = "Name";
    private String eMailOrNumber = "number or email";
    private boolean isNumber = false;
    private static List<Contact> contacts = new ArrayList<>();

    /*private static final void getInstance(){
        if(contacts==null){
            contacts = new ArrayList<>();
        }
    }*/
    public static List<Contact> getContactsList(){
        //getInstance();
        contacts.sort(new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.getName().compareTo(t1.getName());
            }
        });
        return contacts;
    }

    public static void addContact(Contact contact){

        contacts.add(contact);
    }

    public Contact(String name, String eMailOrNumber, boolean isNumber) {

        this.name = name;
        this.eMailOrNumber = eMailOrNumber;
        this.isNumber = isNumber;
    }
    public void editContact(Contact contact, int position){
        contacts.remove(position);
        contacts.add(contact);
    }
    public void removeContact(int position){
        contacts.remove(position);
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
}

