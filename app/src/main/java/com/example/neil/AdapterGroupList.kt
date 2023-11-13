package com.example.neil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class AdapterGroupList(private val context: Context, private val groupModel: List<groupListModel>) : RecyclerView.Adapter<AdapterGroupList.MyViewHolder>() {

    private var itemClickListener: OnGroupItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_icon_layout, parent, false)
        return MyViewHolder(view)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.groupName.text = groupModel[position].group
        holder.remNumGroup.text = groupModel[position].groupNum.toString()

        holder.itemView.setOnClickListener {
//            itemClickListener.onGroupItemClicked(groupItem)
            //TODO Niel:continue here
        }

        holder.itemView.setOnClickListener(){

            val clickedGroup = groupModel[position]

        }
    }

    fun setOnGroupItemClickListener(listener: OnGroupItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int {
        return groupModel.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val groupName: TextView = itemView.findViewById(R.id.GroupListName)
        val remNumGroup: TextView = itemView.findViewById(R.id.GroupListNum)

    }

    open class OnGroupItemClickListener() {

    }

}
