package com.example.neil
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.FrameLayout
import android.animation.ValueAnimator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

    //Variables For expanding the map
    private lateinit var mapWindow: FrameLayout
    private lateinit var bottomWindow: ConstraintLayout


    private var originalHeightMap: Int = 0
    private var originalWidth: Int = 0
    private var originalHeight2: Int = 0
    //variable used as a flag
    private var isExpanded = false

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

        // Initialize the FrameLayout and Button
        mapWindow = findViewById(R.id.mapWindow)
        bottomWindow=findViewById(R.id.BottomWindow)

        originalHeightMap = mapWindow.height
        originalWidth = mapWindow.width
        originalHeight2 = bottomWindow.height


        // bottom menu buttons executable

        bottomMenuAddRemItem.setOnNavigationItemReselectedListener { item ->
            when(item.itemId){
                R.id.addRem -> { //first selection in the bottom menu
                    showPopUp()
                    true
                }
                R.id.mapIcon -> {
                    // map icon executable
                    toggleFrameLayout()

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

    //    expand map function
    @SuppressLint("Recycle")
    private fun expandFrameLayout() {
        // Define the original height and width for the mapWindow
        //The variables are located in the "dimentions.xml" file (the ones after the R.dimen)
        val originalHeightMap = resources.getDimensionPixelSize(R.dimen.expanded_height)
        val originalWidthMap = resources.getDimensionPixelSize(R.dimen.expanded_width)
        val originalHeightBottom = resources.getDimensionPixelSize(R.dimen.expanded_height)

        // Create ValueAnimators to animate the height and width of the mapWindow
        val valueAnimatorHeightMap = ValueAnimator.ofInt(mapWindow.height, originalHeightMap)
        val valueAnimatorWidthMap = ValueAnimator.ofInt(mapWindow.width, originalWidthMap)
        val valueAnimatorHeightBottom = ValueAnimator.ofInt(bottomWindow.height, originalHeightBottom)

        // Set animation duration in milliseconds
        valueAnimatorHeightMap.duration = 500
        valueAnimatorWidthMap.duration = 500
        valueAnimatorHeightBottom.duration =500

        // Add update listeners to animate the layout params
        // These are the animations in the increasing frame
        // Animate the height of the mapWindow
        valueAnimatorHeightMap.addUpdateListener { animator ->
            val layoutParams = mapWindow.layoutParams as LayoutParams
            layoutParams.height = animator.animatedValue as Int
            mapWindow.layoutParams = layoutParams
        }
        // Animate the width of the mapWindow
        valueAnimatorWidthMap.addUpdateListener { animator ->
            val layoutParams = mapWindow.layoutParams as LayoutParams
            layoutParams.width = animator.animatedValue as Int
            mapWindow.layoutParams = layoutParams
        }



        // Animate the height of the bottomWindow
        valueAnimatorHeightBottom.addUpdateListener { animator ->
            val layoutParams = bottomWindow.layoutParams as LayoutParams
            layoutParams.height = animator.animatedValue as Int
            bottomWindow.layoutParams = layoutParams
        }

        // Start the animations
        valueAnimatorHeightMap.start()
        valueAnimatorWidthMap.start()
        valueAnimatorHeightBottom.start()


    }




    // function to minimize the map
    private fun minimizeFrameLayout() {
        // Define the original height and width for the mapWindow and the bottomWindow
        //The variables are located in the "dimentions.xml" file (the ones after the R.dimen)
        val originalHeightMap = resources.getDimensionPixelSize(R.dimen.original_map_height)
        val originalWidthMap = resources.getDimensionPixelSize(R.dimen.original_map_width)
        val originalHeightBottom = resources.getDimensionPixelSize(R.dimen.original_bottom_height)

        // Create ValueAnimators to animate the height and width of the mapWindow and the bottomWindow
        val valueAnimatorHeightMap = ValueAnimator.ofInt(mapWindow.height, originalHeightMap)
        val valueAnimatorWidthMap = ValueAnimator.ofInt(mapWindow.width, originalWidthMap)
        val valueAnimatorHeightBottom = ValueAnimator.ofInt(bottomWindow.height, originalHeightBottom)

        // Set the same duration for animations

        valueAnimatorHeightMap.duration = 500
        valueAnimatorWidthMap.duration =500
        valueAnimatorHeightBottom.duration = 500

        // Animate the height of the mapWindow
        valueAnimatorHeightMap.addUpdateListener { animator ->
            val layoutParams = mapWindow.layoutParams as LayoutParams
            layoutParams.height = animator.animatedValue as Int
            mapWindow.layoutParams = layoutParams
        }

        // Animate the width of the mapWindow
        valueAnimatorWidthMap.addUpdateListener { animator ->
            val layoutParams = mapWindow.layoutParams as LayoutParams
            layoutParams.width = animator.animatedValue as Int
            mapWindow.layoutParams = layoutParams
        }
        // Animate the height of the bottomWindow
        valueAnimatorHeightBottom.addUpdateListener { animator ->
            val layoutParams = bottomWindow.layoutParams as LayoutParams
            layoutParams.height = animator.animatedValue as Int
            bottomWindow.layoutParams = layoutParams
        }

        // Start the animations
        valueAnimatorHeightMap.start()
        valueAnimatorWidthMap.start()
        valueAnimatorHeightBottom.start()
    }


    // function to make the button work in both functions
    private fun toggleFrameLayout() {
        if (isExpanded) {
            minimizeFrameLayout()
        } else {
            expandFrameLayout()
        }
        // Toggle the isExpanded flag AFTER the animation is complete
        isExpanded = !isExpanded
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