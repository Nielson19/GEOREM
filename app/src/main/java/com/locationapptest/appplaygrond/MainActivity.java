package com.locationapptest.appplaygrond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ReminderModel> ReminderModel = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.reminderList); // create xml item as a variable
        setReminderModel();
        AA_RecyclerAdapterView adapter = new AA_RecyclerAdapterView(this, ReminderModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void setReminderModel(){
        String[] ReminderNames = getResources().getStringArray(R.array.random_reminder_name);
        String[] Address = getResources().getStringArray(R.array.random_reminder_addresses);

        for (int i = 0; i < ReminderNames.length; i++){
            ReminderModel.add(new ReminderModel(ReminderNames[i],Address[i]));

        }

    }
}