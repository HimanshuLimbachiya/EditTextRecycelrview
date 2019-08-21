package com.android.edittextrecycelrview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Abhin.
 */
class CustomAdapter(private val mContext: Context, private var mList: ArrayList<Count> = ArrayList(), private var mItemChecked : ItemChecked? = null ) : RecyclerView.Adapter<CustomAdapter.UserViewHolder>() {

    private var mFilterList = ArrayList<Count>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    /**
     * remove data form the list
     *
     * @param position which position remove
     */
    /*fun removeItem() {
        val arrayList = ArrayList(mList)
        arrayList.removeAt(position)
        mList = arrayList
        notifyItemRemoved(position)
    }*/


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val model: Count = mList[position]
        holder.textView.text = model.count.toString()
        holder.itemView.setBackgroundColor(if (model.isHide) ContextCompat.getColor(mContext, R.color.colorAccent) else ContextCompat.getColor(mContext, R.color.colorPrimary))
        holder.itemView.setOnClickListener {
            model.isHide = !model.isHide
            holder.itemView.setBackgroundColor(if (model.isHide) ContextCompat.getColor(mContext, R.color.colorAccent) else ContextCompat.getColor(mContext, R.color.colorPrimary))
            mItemChecked!!.selectedItems(position,model.isHide)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: AppCompatTextView = itemView.findViewById(R.id.txt_TextView)
    }

    interface ItemChecked {
        fun selectedItems(position: Int, isChecked: Boolean)
    }
}