package ru.mail.sergey_balotnikov.contactresolver.model;

public class Contact {
    private int id;
    private String name;
    private String number;
    private boolean isNumber;

    public Contact(int id, String name, String number, boolean isNumber) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.isNumber = isNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public boolean isNumber() {
        return isNumber;
    }
}
