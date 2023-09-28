package com.locationapptest.appplaygrond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AA_RecyclerAdapterView extends RecyclerView.Adapter<AA_RecyclerAdapterView.MyViewHolder> {
    Context context;
    ArrayList<ReminderModel> reminderModels;
    public AA_RecyclerAdapterView(Context context, ArrayList<ReminderModel> reminderModels) {
        this.context = context; // used to inflate the layout
        this.reminderModels = reminderModels;
    }

    @NonNull
    @Override
    public AA_RecyclerAdapterView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this is where you inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminder_list_row, parent, false);

        return new AA_RecyclerAdapterView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_RecyclerAdapterView.MyViewHolder holder, int position) {
        //assigned values to the views we created in the recycler_list_row layout file
        // based on the position of the recycler view
        holder.addressText.setText(reminderModels.get(position).getAddress());
        holder.reminderText.setText(reminderModels.get(position).getReminder());
        // for image is holder.ImageView.setImageResource...



    }

    @Override
    public int getItemCount() {
        // recycler view just wants to know the number of items you want displayed
        return reminderModels.size();
    }
     public static class MyViewHolder extends RecyclerView.ViewHolder{
         //assigned values to the views we created in the recycler_list_row layout file
         // kinda like onCrease class

         // ImageView imageView
         TextView addressText, reminderText;

         public MyViewHolder(@NonNull View itemView) {
             super(itemView);

            // making the xml ids into variables to make them dynamic
             addressText = itemView.findViewById(R.id.reminderAddress);
             reminderText = itemView.findViewById(R.id.reminderTitle);


         }
     }
}
