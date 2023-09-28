package com.locationapptest.appplaygrond;

public class ReminderModel {
    String Reminder;
    String Address;
    public ReminderModel(String reminder, String address) {
        Reminder = reminder;
        Address = address;
    }

    public String getReminder() {
        return Reminder;
    }

    public String getAddress() {
        return Address;
    }

    // in the future we will add images to determine the group where is belongs

}
