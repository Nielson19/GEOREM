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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neil.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mapbox.maps.MapView
import com.mapbox.maps.Style

var mapView: MapView? = null

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), OnItemCLickReminder {

    //TODO//////////////////////////////VARIABLES //////////////////////////////////////////////////

    private lateinit var titleReminderInfo: TextView // variable that is going to store the title of reminder
    private lateinit var titleAddressInfo: TextView // variable that is going to store the address of the reminder
    private lateinit var infoWindow: LinearLayout // ??

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter2: AdapterGroupList

    private val reminderGroupList = ArrayList<groupListModel>() // this is use to populate the reminder groups with the data

    private lateinit var bottomMenuAddRemItem: BottomNavigationView // ??

    //Variables For expanding the map

    private lateinit var mapWindow: FrameLayout
    private lateinit var bottomWindow: ConstraintLayout

    private var originalHeightMap: Int = 0
    private var originalWidth: Int = 0
    private var originalHeight2: Int = 0

    //variable used as a flag

    private var isExpanded = false

    //TODO/////////////ON CREATE FUNCTION ///////////////////////////////////////////////////////
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////////////////////////////////RECYCLER VIEW //////////////////////////////

        val recyclerViewList: RecyclerView = findViewById(R.id.recyclerViewListGroup) // created the variable in relation to the xml id
        val recyclerView: RecyclerView = findViewById(R.id.reminderList) // created the variable in relation to the xml id
        adapter2 = AdapterGroupList(this, reminderGroupList)

//        setReminderModel() // initiates the population of the recycler view

        setGroupListModel() // calls the algorithm to populate the back end array list with the current data

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacingRecyclerView) // Adjust the dimension as needed in the recycler
        recyclerView.addItemDecoration(ItemDecoration(2, spacingInPixels))

        // Initialize the adapter as a member variable

        adapter2.setOnGroupItemClickListener(object : AdapterGroupList.OnGroupItemClickListener() {
            fun onGroupItemClicked(groupItem: groupListModel) {

                val fragment = ReminderListFragment()

                // Perform the fragment transaction to replace the current fragment
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_layout, fragment) // R.id.fragment_container is the container in your activity's layout
                transaction.addToBackStack(null) // Add the transaction to the back stack
                transaction.commit()

                // Handle the click action here, e.g., open another RecyclerView
            }
        })

        recyclerView.adapter = adapter2
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        ////////////////////////////////MAP////////////////////////////////////////

        //map variables

        mapView = findViewById(R.id.mapView)
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)


        ////////////////////////////////BOTTOM MENU////////////////////////////////////////

        // bottom menu variables

        bottomMenuAddRemItem = findViewById(R.id.bottomNavigationView)

        // bottom menu buttons executables

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

        ///////////////MAP EXPANSION//////////////////////////////////////////

        mapWindow = findViewById(R.id.mapWindow) // initiate the frame layout
        bottomWindow = findViewById(R.id.BottomWindow) // initiate the button

        //collects the variables with original sizes
        //TODO Jesus: check here if you can change to match parent instead of a number

        originalHeightMap = mapWindow.height
        originalWidth = mapWindow.width
        originalHeight2 = bottomWindow.height

    }


    //TODO Jesus: Fix the expansion to match the with and compile everything into a class to organize it


    //TODO/////////////BOTTOM MENU EXECUTABLES FUNCTIONS/////////////////////////////////////

    /////////////MAP EXPANSION FUNCTIONS ///////////////////////////////////////////////////

    //TODO Jesus: Fix the expansion to match the with and compile everything into a class to organize it
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
        valueAnimatorWidthMap.duration = 500
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

    //////////////POP UP FUNCTIONS /////////////////////////////////////////////////////////
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



    /////////// RECYCLER VIEW FUNCTIONS ////////////////////////////////////////////////////
    private fun setGroupListModel(){
        val groupListName = resources.getStringArray(R.array.group_list)
        val groupAmountRem = resources.getIntArray(R.array.group_list_int)

        for (i in groupListName.indices){
            reminderGroupList.add(groupListModel(groupListName[i],groupAmountRem[i]))
        }

    }

//    click behavior on each element in the list group
    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    //TODO//////// MAP FUNCTIONS///////////////////////////////////////////////////////////////

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


////////////////////////////////////////////////////////////////////////////////////////////////