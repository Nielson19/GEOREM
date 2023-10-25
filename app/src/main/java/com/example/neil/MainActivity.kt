package com.example.neil

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neil.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

var mapView: MapView? = null

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnItemCLickReminder {

    //variables

    private lateinit var titleReminderInfo: TextView
    private lateinit var titleAddressInfo: TextView
    private lateinit var infoWindow: LinearLayout

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: RecyclerAdapterReminder
    private val reminderModelList = ArrayList<ReminderModel>()

    private lateinit var bottomMenuAddRemItem: BottomNavigationView
    //FUNCTIONS

    //Bottom menu selection space


    // execution when the app opens
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        titleReminderInfo = findViewById(R.id.reminderTitleInfo)
        titleAddressInfo = findViewById(R.id.textAdressInfo)
        infoWindow = findViewById(R.id.infoReminderWindow)
        bottomMenuAddRemItem = findViewById(R.id.bottomNavigationView)

        val recyclerView: RecyclerView = findViewById(R.id.reminderList)
        setReminderModel()


        // bottom menu buttons executable

        bottomMenuAddRemItem.setOnNavigationItemReselectedListener { item ->
            when(item.itemId){
                R.id.addRem -> { //first selection in the bottom menu
                    showPopUp()
                    true
                }
                R.id.mapIcon -> {
                    // map icon executable
                }
                R.id.settingsIcon ->{

                    // setting icon executable

                }
                // add other menu items if needed

                else -> false // if you have items ypu don't handle here

            }
        }

        // Initialize the adapter as a member variable

        adapter = RecyclerAdapterReminder(this, reminderModelList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }




    // Add reminder window pop up function
    @SuppressLint("ResourceType", "WrongViewCast")
    private fun showPopUp(){

        // variables
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.pop_up_addrem, null)

        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val addButton = dialogView.findViewById<Button>(R.id.addButton)

        builder.setView(dialogView) // adds the layout into the pop up

        // executes the display of the pop up

        val dialog = builder.create()
        dialog.show()

        // functions / executables inside the popUp

        cancelButton.setOnClickListener{
            dialog.dismiss()
        }

        addButton.setOnClickListener{
            // TODO Yonathan: add reminder to database
            // TODO Niel: add reminder information in the recycler

        }




    }

    // database of the RecyclerView
    private fun setReminderModel() {
        val reminderNames = resources.getStringArray(R.array.random_reminder_name)
        val addresses = resources.getStringArray(R.array.random_reminder_addresses)

        for (i in reminderNames.indices) {
            reminderModelList.add(ReminderModel(reminderNames[i], addresses[i]))
        }
    }

    // generate animation when there is a selection in the recyclerView
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




    // mapbox behavior
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