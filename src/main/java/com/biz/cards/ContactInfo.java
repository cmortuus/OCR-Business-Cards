package com.biz.cards;

public class ContactInfo {

    private String name;
    private String emailAddress;
    private String phoneNumber;
    ContactInfo(String emailAddress, String phoneNumber, String name){
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return "Name: " + name;
    }

    public String getEmailAddress() {
        return "Email: " + emailAddress;
    }

    public String getPhoneNumber() {
        return "Phone: " + phoneNumber.replaceAll("[^0-9]","");
    }
}
