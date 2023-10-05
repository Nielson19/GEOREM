package com.example.neil

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

var mapView: MapView? = null

class MainActivity : AppCompatActivity(), OnItemCLickReminder {
    private lateinit var titleReminderInfo: TextView
    private lateinit var titleAddressInfo: TextView
    private lateinit var infoWindow: LinearLayout

    private lateinit var adapter: RecyclerAdapterReminder
    private val reminderModelList = ArrayList<ReminderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mapview call

        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        titleReminderInfo = findViewById(R.id.reminderTitleInfo)
        titleAddressInfo = findViewById(R.id.textAdressInfo)
        infoWindow = findViewById(R.id.infoReminderWindow)

        val recyclerView: RecyclerView = findViewById(R.id.reminderList)
        setReminderModel()

        // Initialize the adapter as a member variable
        adapter = RecyclerAdapterReminder(this, reminderModelList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.info_slide_left)
        val fadeInAnimationLayout: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_info)

        // Fades in new selection variables
        infoWindow.layoutAnimation = fadeInAnimationLayout

        // Collects from the database to add in the text
        titleReminderInfo.text = selectedItem.reminder
        titleAddressInfo.text = selectedItem.address

        // Assuming you have a method like getReminderName() in your ReminderModel class
        // Here will be the code to pass all the info detail of each item
    }

    @SuppressLint("Lifecycle")
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    @SuppressLint("Lifecycle")
    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    @SuppressLint("Lifecycle")
    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }
}