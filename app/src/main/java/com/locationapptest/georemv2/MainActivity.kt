package com.locationapptest.georemv2
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnItemCLickReminder {
    private lateinit var titleReminderInfo: TextView
    private lateinit var titleAddressInfo: TextView
    private lateinit var infoWindow: LinearLayout

    private lateinit var adapter: RecyclerAdapterReminder
    private val reminderModelList = ArrayList<ReminderModel>()

    //Variables For expanding the map
    private lateinit var mapWindow: FrameLayout
    private lateinit var bottomWindow: ConstraintLayout
    private lateinit var expandButton: ImageButton
    private var originalHeightMap: Int = 0
    private var originalWidth: Int = 0
    private var originalHeight2: Int = 0
    //variable used as a flag
    private var isExpanded = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        titleReminderInfo = findViewById(R.id.reminderTitleInfo)
        titleAddressInfo = findViewById(R.id.textAdressInfo)
        infoWindow = findViewById(R.id.infoReminderWindow)

        val recyclerView: RecyclerView = findViewById(R.id.reminderList)
        setReminderModel()

        // Initialize the adapter as a member variable
        adapter = RecyclerAdapterReminder(this, reminderModelList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Initialize the FrameLayout and Button
        mapWindow = findViewById(R.id.mapWindow)
        bottomWindow=findViewById(R.id.BottomWindow)
        expandButton = findViewById(R.id.expand_button)
        originalHeightMap = mapWindow.height
        originalWidth = mapWindow.width
        originalHeight2 = bottomWindow.height
        // Set a click listener for the Button
        expandButton.setOnClickListener {
            toggleFrameLayout()
        }


    }

    private fun setReminderModel() {
        val reminderNames = resources.getStringArray(R.array.random_reminder_name)
        val addresses = resources.getStringArray(R.array.random_reminder_addresses)

        for (i in reminderNames.indices) {
            reminderModelList.add(ReminderModel(reminderNames[i], addresses[i]))
        }
    }

    //    expand map function
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
        valueAnimatorHeightMap.duration = 300
        valueAnimatorWidthMap.duration = 300
        valueAnimatorHeightBottom.duration =300

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

        valueAnimatorHeightMap.duration = 300
        valueAnimatorWidthMap.duration =300
        valueAnimatorHeightBottom.duration = 300

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


        override fun onItemClick(position: Int) {
        // Use the member variable 'adapter' to get the item
        val selectedItem: ReminderModel = adapter.getItem(position)
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.info_slide_left)
        val fadeInAnimationLayout: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_info)

        // Fades in new selection variables
        infoWindow.layoutAnimation = fadeInAnimationLayout

        // Collects from the database to add in the text
        titleReminderInfo.text = selectedItem.reminder
        titleAddressInfo.text = selectedItem.address

        // Assuming you have a method like getReminderName() in your ReminderModel class
        // Here will be the code to pass all the info detail of each item
        }
}