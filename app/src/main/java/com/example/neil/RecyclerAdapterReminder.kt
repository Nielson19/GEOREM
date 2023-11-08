package com.example.neil

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapterReminder(
    private var context: ReminderListFragment,
    private var reminderModels: ArrayList<ReminderModel>,
    clickReminderListeners: OnItemCLickReminder
) : RecyclerView.Adapter<RecyclerAdapterReminder.MyViewHolder>() {
    private val clickReminderListeners: OnItemCLickReminder
    var selectedItem = RecyclerView.NO_POSITION // Initialize with no selection for background selection

    init {
        // used to inflate the layout
        this.clickReminderListeners = clickReminderListeners
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // this is where you inflate the layout (Giving a look to our rows)
        val inflater = LayoutInflater.from(context.requireContext())
        val view: View = inflater.inflate(R.layout.reminder_list_row, parent, false)
        return MyViewHolder(view, clickReminderListeners)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //assigned values to the views we created in the recycler_list_row layout file
        // based on the position of the recycler view
        holder.addressText.text = reminderModels[position].address
        holder.reminderText.text = reminderModels[position].reminder

        // Set the background color based on the selected state
//        holder.itemView.setSelected(selectedItem == position); old code
        if (position == selectedItem) {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.selected_item_color))
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }


        // for image is holder.ImageView.setImageResource...
    }

    override fun getItemCount(): Int {
        // recycler view just wants to know the number of items you want displayed
        return reminderModels.size
    }

    inner class MyViewHolder(itemView: View, listener: OnItemCLickReminder?) :
        RecyclerView.ViewHolder(itemView) {
        //TODO create variables and actions for the icon color and use the edit icon to go to the edit window
        //assigned values to the views we created in the recycler_list_row layout file
        // kinda like onCrease class

        private var starIcon: ImageView = itemView.findViewById(R.id.starIcon)
        var addressText: TextView
        var reminderText: TextView
        private var isImage1 = true

        init { //this is where you execute the variables in the list template
            starIcon.setImageResource(R.mipmap.ic_star_empty_icon_foreground)
            starIcon.setOnClickListener {
                // Change the image when the ImageView is clicked
                isImage1 = if(isImage1){
                    starIcon.setImageResource(R.mipmap.ic_star_full_icon_foreground)
                    false
                } else{
                    starIcon.setImageResource(R.mipmap.ic_star_empty_icon_foreground)
                    true
                }

                //TODO add the current reminder to a database that keeps the favorite reminders


            }


            // making the xml ids into variables to make them dynamic
            addressText = itemView.findViewById<TextView>(R.id.reminderAddress)
            reminderText = itemView.findViewById<TextView>(R.id.reminderTitle)
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) //                            listener.onItemClick(position);  old code
                        if (selectedItem != position) {
                            val previousSelectedItem = selectedItem
                            selectedItem = position

                            // Notify the adapter to update the views
//                                notifyItemChanged(previousSelectedItem);
                            notifyItemChanged(selectedItem)

                            // Call the onItemClick listener
                            listener.onItemClick(position)
                        }
                }
            }
        }
    }

    fun getItem(position: Int): ReminderModel {
        return reminderModels[position]
    }
}