package com.example.neil

import android.app.Activity
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A fragment representing a list of Items.
 *
 */
@Suppress("DEPRECATION")
class ReminderListFragment : Fragment(), OnItemCLickReminder {

    //    Recycler Variables
    private val reminderModelList = ArrayList<ReminderModel>()
    private lateinit var adapter: RecyclerAdapterReminder

    private lateinit var titleReminderInfo: TextView
    private lateinit var titleAddressInfo: TextView
    private lateinit var infoWindow: LinearLayout


    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setReminderModel() // this runs the recyclerView

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
        Note when you set variables on a fragment you have to make them in this function
        */

        val view = inflater.inflate(R.layout.fragment_reminder_group_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.reminderList)

        titleReminderInfo = view.findViewById(R.id.reminderTitleInfo)
        titleAddressInfo = view.findViewById(R.id.textAddressInfo)
        infoWindow = view.findViewById(R.id.infoReminderWindow)

        adapter = RecyclerAdapterReminder(this, reminderModelList, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)



        return view

    }

    private fun setReminderModel() {
        val reminderNames = resources.getStringArray(R.array.random_reminder_name)
        val addresses = resources.getStringArray(R.array.random_reminder_addresses)

        for (i in reminderNames.indices) {
            reminderModelList.add(ReminderModel(reminderNames[i], addresses[i]))
        }
    }

    override fun onItemClick(position: Int) {
        // Use the member variable 'adapter' to get the item
        val selectedItem: ReminderModel = adapter.getItem(position)
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.info_slide_left)
        val fadeInAnimationLayout: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_info)

        // Fades in new selection variables
        infoWindow.layoutAnimation = fadeInAnimationLayout

        // Collects from the database to add in the text
        titleReminderInfo.text = selectedItem.reminder
        titleAddressInfo.text = selectedItem.address

        // Assuming you have a method like getReminderName() in your ReminderModel class
        // Here will be the code to pass all the info detail of each item
    }
}
