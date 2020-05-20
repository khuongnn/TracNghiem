package com.example.tracnghiem.activity.main.mainpractice.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tracnghiem.R
import com.example.tracnghiem.data.model.Questions


internal class CheckAnswerAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList: ArrayList<Questions>?
) : ArrayAdapter<CheckAnswerAdapter.ItemHolder>(context, resource) {

    override fun getCount(): Int {
        return if (this.itemList != null) this.itemList.size else 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ItemHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null)
            holder = ItemHolder()
            holder.name = convertView!!.findViewById(R.id.tvNumAns)
            holder.ans = convertView.findViewById(R.id.tvAnswer)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemHolder
        }

        holder.name!!.text =  this.itemList!![position].position.toString()
        if(holder.name!!.text.isNotEmpty()){

        }

        holder.ans!!.text = this.itemList[position].setChoiceAns

        return convertView
    }

    internal class ItemHolder {
        var name: TextView? = null
        var ans: TextView? = null
    }
}